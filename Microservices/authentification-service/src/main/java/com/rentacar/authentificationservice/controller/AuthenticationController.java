package com.rentacar.authentificationservice.controller;

import com.rentacar.authentificationservice.dto.request.*;
import com.rentacar.authentificationservice.dto.response.UserResponseDTO;
import com.rentacar.authentificationservice.services.IAuthenticationService;
import com.rentacar.authentificationservice.util.enums.GeneralException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private IAuthenticationService authService;

    public AuthenticationController(IAuthenticationService authService) {
        this.authService = authService;
    }

    @GetMapping("/verify/{token}")
    public boolean verify(@PathVariable("token") String token) {
        return true;
    }

    @GetMapping("/hello")
    public ResponseEntity<?> hello(){
        return new ResponseEntity<>("Hello from auth service", HttpStatus.OK);
    }

    @PutMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCredentialsDTO request) throws GeneralException {
        return new ResponseEntity<>(authService.login(request), HttpStatus.OK);
    }

    @PostMapping("/admin")
    public void registerAdmin(AdminDetailsDTO request) {
        authService.registerAdmin(request);
    }

    @PostMapping("/agent")
    public void registerAgent(AgentDetailsDTO request) {
        authService.registerAgent(request);
    }

    @PostMapping("/user")
    public void registerSimpleUser(SimpleUserDetailsDTO request) {
        authService.registerSimpleUser(request);
    }

    @PostMapping("/ban")
    public void banUser(ChangePasswordDTO request){
        authService.banUser(request);
    }

    @PostMapping("/password-change")
    public void passwordChange(ChangePasswordDTO request){
        authService.changePassword(request);
    }

    @PutMapping("/admin")
    public void updateAdmin(UpdateAdminRequestDTO request) {
        authService.updateAdmin(request);
    }

    @PutMapping("/agent")
    public void registerAgent(UpdateAgentRequestDTO request) {
        authService.updateAgent(request);
    }

    @PutMapping("/user")
    public void updateSimpleUser(UpdateUserRequestDTO request) {
        authService.updateSimpleUser(request);
    }

    @DeleteMapping("/user")
    public void deleteUser(ChangePasswordDTO request){
        authService.deleteUser(request);
    }

}
