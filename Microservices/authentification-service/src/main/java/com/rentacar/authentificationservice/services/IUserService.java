package com.rentacar.authentificationservice.services;

import com.rentacar.authentificationservice.dto.response.UserResponse;
import com.rentacar.authentificationservice.entity.User;

import java.util.List;
import com.rentacar.authentificationservice.dto.feignClient.UserDTO;
import com.rentacar.authentificationservice.dto.feignClient.UserMessageDTO;

import java.util.UUID;

public interface IUserService {

    List<UserResponse> getAllUsers();

    User getOneUser(UUID id);

    List<UserResponse> getCustomers();

    UserMessageDTO getUser(UUID id);
}
