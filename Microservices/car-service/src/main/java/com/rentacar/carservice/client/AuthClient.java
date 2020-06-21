package com.rentacar.carservice.client;

import com.rentacar.carservice.dto.feignClient.AgentDTO;
import com.rentacar.carservice.dto.feignClient.SimpleUserDTO;
import com.rentacar.carservice.dto.feignClient.UserDTO;
import com.rentacar.carservice.dto.feignClient.UserMessageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "auth")
public interface AuthClient {

    @GetMapping("/simple-users/{username}")
    UUID getIDByUsername(@PathVariable("username") String username);

    @GetMapping("/agents/{id}/address")
    String getAgentAddress(@PathVariable("id") UUID id);

    @GetMapping("/agents/{id}")
    AgentDTO getAgent(@PathVariable("id") UUID id);

    @GetMapping("/simple-users/get/{id}")
    SimpleUserDTO getSimpleUser(@PathVariable("id") UUID id);

    @GetMapping("/agents/{id}/user")
    UserMessageDTO getUserFromAgent(@PathVariable("id") UUID id);

    @GetMapping("/users/{id}")
    UserMessageDTO getUser(@PathVariable("id") UUID id);
}
