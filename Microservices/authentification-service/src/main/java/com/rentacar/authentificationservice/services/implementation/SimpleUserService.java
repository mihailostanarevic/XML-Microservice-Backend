package com.rentacar.authentificationservice.services.implementation;

import com.rentacar.authentificationservice.dto.client.UUIDResponse;
import com.rentacar.authentificationservice.entity.Authority;
import com.rentacar.authentificationservice.entity.SimpleUser;
import com.rentacar.authentificationservice.entity.User;
import com.rentacar.authentificationservice.repository.IAuthorityRepository;
import com.rentacar.authentificationservice.repository.ISimpleUserRepository;
import com.rentacar.authentificationservice.repository.IUserRepository;
import com.rentacar.authentificationservice.services.ISimpleUserService;
import com.rentacar.authentificationservice.util.enums.RequestStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SimpleUserService implements ISimpleUserService {

    private final ISimpleUserRepository _simpleUserRepository;
    private final IUserRepository _userRepository;
    private final IAuthorityRepository _authorityRepository;

    public SimpleUserService(ISimpleUserRepository simpleUserRepository, IUserRepository userRepository, IAuthorityRepository authorityRepository) {
        _simpleUserRepository = simpleUserRepository;
        _userRepository = userRepository;
        _authorityRepository = authorityRepository;
    }

    @Override
    public void blockSimpleUserByAdmin(UUID id) throws Exception {
        SimpleUser simpleUser = _simpleUserRepository.findOneById(id);
//        simpleUser.setBlocked(true);
        _simpleUserRepository.save(simpleUser);
    }

    @Override
    public void unblockSimpleUserByAdmin(UUID id) throws Exception {
        SimpleUser simpleUser = _simpleUserRepository.findOneById(id);
//        simpleUser.setBlocked(false);
        _simpleUserRepository.save(simpleUser);
    }

    @Override
    public void activateSimpleUserByAdmin(UUID id) throws Exception {
        SimpleUser simpleUser = _simpleUserRepository.findOneById(id);
        simpleUser.setRequestStatus(RequestStatus.APPROVED);
        _simpleUserRepository.save(simpleUser);
    }

    @Override
    public void deactivateSimpleUserByAdmin(UUID id) throws Exception {
        SimpleUser simpleUser = _simpleUserRepository.findOneById(id);
        simpleUser.setRequestStatus(RequestStatus.DENIED);
        _simpleUserRepository.save(simpleUser);
    }

    @Override
    public void deleteSimpleUserByAdmin(UUID id) throws Exception {
        SimpleUser simpleUser = _simpleUserRepository.findOneById(id);
        simpleUser.getUser().setDeleted(true);
        _userRepository.save(simpleUser.getUser());
        _simpleUserRepository.save(simpleUser);
    }

    @Override
    public UUIDResponse getIDByUsername(String username) {
        User user = _userRepository.findOneByUsername(username);
        if(user != null) {
            SimpleUser simpleUser = _simpleUserRepository.findOneByUser(user);
            if(simpleUser != null) {
                UUID id = simpleUser.getId();
                UUIDResponse retUuidResponse = new UUIDResponse();
                retUuidResponse.setId(id);
                return retUuidResponse;
            }
        }
        return null;
    }

    @Override
    public void addUserRole(UUID simpleUserID, String userRole) {
        SimpleUser simpleUser = _simpleUserRepository.findOneById(simpleUserID);
        User user = simpleUser.getUser();
        Authority authority = _authorityRepository.findByName(userRole);
        user.getRoles().add(authority);
        _userRepository.save(user);
    }
}
