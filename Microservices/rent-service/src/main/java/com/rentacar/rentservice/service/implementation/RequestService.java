package com.rentacar.rentservice.service.implementation;

import com.rentacar.rentservice.client.AuthClient;
import com.rentacar.rentservice.dto.feignClient.RequestAdDTO;
import com.rentacar.rentservice.dto.feignClient.RequestDTO;
import com.rentacar.rentservice.dto.feignClient.SimpleUserDTO;
import com.rentacar.rentservice.dto.client.UUIDResponse;
import com.rentacar.rentservice.dto.request.RequestRequest;
import com.rentacar.rentservice.entity.Request;
import com.rentacar.rentservice.entity.RequestAd;
import com.rentacar.rentservice.repository.IRequestAdRepository;
import com.rentacar.rentservice.repository.IRequestRepository;
import com.rentacar.rentservice.service.IRequestService;
import com.rentacar.rentservice.util.enums.RequestStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@SuppressWarnings("SpellCheckingInspection")
@Service
public class RequestService implements IRequestService {

    private final IRequestRepository _requestRepository;
    private final IRequestAdRepository _requestAdRepository;
    private final AuthClient _authClient;

    public RequestService(IRequestRepository requestRepository, IRequestAdRepository requestAdRepository, AuthClient authClient) {
        _requestRepository = requestRepository;
        _requestAdRepository = requestAdRepository;
        _authClient = authClient;
    }

    @Override
    public void processRequests(List<RequestRequest> requestList) {
        List<RequestRequest> processedList = new ArrayList<>();
        for (RequestRequest requestDTO : requestList) {
            if (requestDTO.isBundle() && !processedList.contains(requestDTO)) {
                List<RequestRequest> bundleList = new ArrayList<>();
                for (RequestRequest agentRequest : requestList) {
                    if(agentRequest.isBundle()
                            && requestDTO.getAgentID().equals(agentRequest.getAgentID())
                                && !bundleList.contains(agentRequest)) {
                        bundleList.add(agentRequest);
                        processedList.add(agentRequest);
                    }
                }
                createBundleRequest(bundleList);
            } else if (!requestDTO.isBundle()) {
                createRequest(requestDTO);
            }
        }
    }

    /**
     * Check whether the car is available in that period
     * */
    private boolean checkCarAvailability(UUID adID, RequestRequest requestDTO) {
        List<RequestAd> requestAdList = _requestAdRepository.findAllByAdID(adID);
        for (RequestAd requestAd : requestAdList) {
            boolean startEndDate = requestAd.getReturnDate().isBefore(LocalDate.parse(requestDTO.getPickUpDate()));
            if (!startEndDate) {
                boolean endStartDate = LocalDate.parse(requestDTO.getReturnDate()).isBefore(requestAd.getPickUpDate());
                if (!endStartDate) {
                    if(requestAd.getReturnDate().isEqual(LocalDate.parse(requestDTO.getPickUpDate()))) {
                        if (!requestAd.getReturnTime().isBefore(LocalTime.parse(requestDTO.getPickUpTime()))) {
                            return false;
                        }
                    }
                    else if(requestAd.getPickUpDate().isEqual(LocalDate.parse(requestDTO.getReturnDate()))) {
                        if (!requestAd.getPickUpTime().isAfter(LocalTime.parse(requestDTO.getReturnTime()))) {
                            return false;
                        }
                    } else {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public Request createRequest(RequestRequest requestDTO) {
        Request request = new Request();
        UUID simpleUser = null;
        if(requestDTO.getCustomerID() != null) {
            simpleUser = requestDTO.getCustomerID();
        } else {
            // TODO Feign Client (Auth) za getUserIDByUsername()
            UUIDResponse uuidResponse = _authClient.getIDByUsername(requestDTO.getCustomerUsername());
            simpleUser = uuidResponse.getId();
        }
        request.setCustomerID(simpleUser);
        request.setStatus(RequestStatus.PENDING);
        request.setPickUpAddress(requestDTO.getPickUpAddress());
        request.setDeleted(false);
        List<RequestRequest> requestDTOList = new ArrayList<>();
        requestDTOList.add(requestDTO);
        _requestRepository.save(request);
        createRequestAd(request, requestDTOList);
        // TODO Feign Client (Auth) da dodelim useru novu rolu
        _authClient.addUserRole(simpleUser, "ROLE_REQUEST");

        TimerTask taskPending = new TimerTask() {
            public void run() {
                System.out.println("Request performed on: " + LocalTime.now() + ", " +
                        "Request id: " + Thread.currentThread().getName());
                if(request.getStatus().equals(RequestStatus.PENDING)) {
                    request.setStatus(RequestStatus.CANCELED);
                    _requestRepository.save(request);
                }
            }
        };
        Timer timer = new Timer(request.getId().toString());
        long delay = (24 * 60 * 60 * 1000);
        System.out.println("Request received at: " + LocalTime.now());
        timer.schedule(taskPending, delay);

        return request;
    }

    @Override
    public Request createBundleRequest(List<RequestRequest> requestList) {
        Request request = new Request();
        request.setCustomerID(requestList.get(0).getCustomerID());
        request.setStatus(RequestStatus.PENDING);
        request.setPickUpAddress(requestList.get(0).getPickUpAddress());
        request.setDeleted(false);
        _requestRepository.save(request);
        createRequestAd(request, requestList);
        TimerTask taskPending = new TimerTask() {
            public void run() {
                System.out.println("Bundle request performed on: " + LocalTime.now() + ", " +
                        "Request id: " + Thread.currentThread().getName());
                if(request.getStatus().equals(RequestStatus.PENDING)) {
                    request.setStatus(RequestStatus.CANCELED);
                    _requestRepository.save(request);
                }
            }
        };
        Timer timer = new Timer(request.getId().toString());
        long delay = (24 * 60 * 60 * 1000);
        System.out.println("Bundle received at: " + LocalTime.now());
        timer.schedule(taskPending, delay);
        return request;
    }

    private void createRequestAd(Request request, List<RequestRequest> requestDTOList) {
        for (RequestRequest requestDTO : requestDTOList) {
            RequestAd requestAd = new RequestAd();
            requestAd.setPickUpDate(LocalDate.parse(requestDTO.getPickUpDate()));
            requestAd.setPickUpTime(LocalTime.parse(requestDTO.getPickUpTime()));
            requestAd.setReturnDate(LocalDate.parse(requestDTO.getReturnDate()));
            requestAd.setReturnTime(LocalTime.parse(requestDTO.getReturnTime()));
            requestAd.setAdID(requestDTO.getAdID());
            requestAd.setRequest(request);
            _requestAdRepository.save(requestAd);
        }
    }

    @Override
    public void changeAdAvailability(RequestRequest request) {
        for (RequestAd requestAd : _requestAdRepository.findAllByAdID(request.getAdID())) {
            if(!checkCarAvailability(requestAd.getAdID(), request)) {
                requestAd.getRequest().setDeleted(true);
            }
        }
        createRequest(request);
    }

    @Override
    public boolean rentACar(UUID carID) {
        return false;
    }

    @Override
    public boolean changeCarStatus(UUID carID) {
        return false;
    }

    @Override
    public RequestStatus changeRequestStatus(RequestStatus requestStatus) {
        return null;
    }

    public List<RequestDTO> getRequestsByStatus(RequestStatus status){
        List<RequestDTO> retVal = new ArrayList<>();

        List<Request> requests = _requestRepository.findAllByStatus(status);
        for(Request request : requests){
            RequestDTO dto = new RequestDTO();
            dto.setId(request.getId());
            dto.setStatus(request.getStatus());
            dto.setDeleted(request.isDeleted());
            dto.setReceptionDate(request.getReceptionDate());
            SimpleUserDTO simpleUserDTO = _authClient.getSimpleUser(request.getCustomerID());
            dto.setCustomer(simpleUserDTO);
            dto.setPickUpAddress(request.getPickUpAddress());
            List<RequestAdDTO> list = new ArrayList<>();
            for(RequestAd rqAd : request.getRequestAds()){
                System.out.println(rqAd.getRequest().getId());
                System.out.println(rqAd.getAdID());
                RequestAdDTO rqAdDTO = new RequestAdDTO();
                rqAdDTO.setId(rqAd.getId());
                rqAdDTO.setRequest(request.getId());
                rqAdDTO.setAd_id(rqAd.getAdID());
                rqAdDTO.setPickUpTime(rqAd.getPickUpTime());
                rqAdDTO.setPickUpDate(rqAd.getPickUpDate());
                rqAdDTO.setReturnTime(rqAd.getReturnTime());
                rqAdDTO.setReturnDate(rqAd.getReturnDate());
                list.add(rqAdDTO);
            }
            dto.setRequestAds(list);
            retVal.add(dto);
        }

        return retVal;
    }
}
