package com.rentacar.carservice.service.impl;

import com.rentacar.carservice.dto.request.CreateCarModelRequest;
import com.rentacar.carservice.dto.request.GetCarModelsFilterRequest;
import com.rentacar.carservice.dto.request.UpdateCarModelRequest;
import com.rentacar.carservice.dto.response.CarModelResponse;
import com.rentacar.carservice.entity.CarModel;
import com.rentacar.carservice.repository.ICarModelRepository;
import com.rentacar.carservice.service.ICarModelService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarModelService implements ICarModelService {

    private final ICarModelRepository _carModelRepository;

    public CarModelService(ICarModelRepository carModelRepository) {
        _carModelRepository = carModelRepository;
    }

    @Override
    public CarModelResponse createCarModel(CreateCarModelRequest request) throws Exception {
        CarModel carModel = new CarModel();
        carModel.setDeleted(false);
        carModel.setName(request.getName());
        CarModel savedCarModel = _carModelRepository.save(carModel);
        return mapCarModelToCarModelResponse(savedCarModel);
    }

    @Override
    public CarModelResponse updateCarModel(UpdateCarModelRequest request, UUID id) throws Exception {
        CarModel carModel = _carModelRepository.findOneById(id);
        carModel.setName(request.getName());
        CarModel savedCarModel = _carModelRepository.save(carModel);
        return mapCarModelToCarModelResponse(savedCarModel);
    }

    @Override
    public void deleteCarModel(UUID id) throws Exception {
        CarModel carModel = _carModelRepository.findOneById(id);
        carModel.setDeleted(true);
        _carModelRepository.save(carModel);
    }

    @Override
    public CarModelResponse getCarModel(UUID id) throws Exception {
        CarModel carModel = _carModelRepository.findOneById(id);
        return mapCarModelToCarModelResponse(carModel);
    }

    @Override
    public List<CarModelResponse> getAllCarModels() throws Exception {
        List<CarModel> carModels = _carModelRepository.findAllByDeleted(false);
        return carModels.stream()
                .map(carModel -> mapCarModelToCarModelResponse(carModel))
                .collect(Collectors.toList());
    }

    @Override
    public List<CarModelResponse> getAllCarModelsWithFilter(GetCarModelsFilterRequest request) {
        List<CarModel> allCarModels = _carModelRepository.findAllByDeleted(false);

        return allCarModels
                .stream()
                .filter(carModel -> {
                    if(request.getBrandName() != null) {
                        return carModel.getCarBrand().getName().toLowerCase().contains(request.getBrandName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .filter(carModel -> {
                    if(request.getClassName() != null) {
                        return carModel.getCarClass().getName().toLowerCase().contains(request.getClassName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .map(cm -> mapCarModelToCarModelResponse(cm))
                .collect(Collectors.toList());
    }

    private CarModelResponse mapCarModelToCarModelResponse(CarModel carModel) {
        CarModelResponse response = new CarModelResponse();
        response.setBrandName(carModel.getCarBrand().getName());
        response.setClassName(carModel.getCarClass().getName());
        response.setId(carModel.getId());
        response.setName(carModel.getName());
        return response;
    }
}
