package com.rentacar.authentificationservice.services;

import com.rentacar.CoreAPI.dto.RoleList;
import com.rentacar.authentificationservice.dto.request.*;
import com.rentacar.authentificationservice.dto.response.StringResponse;
import com.rentacar.authentificationservice.dto.response.UserResponse;
import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.UUID;

public interface IAuthenticationService {

    UserResponse login(LoginCredentialsDTO request, HttpServletRequest httpServletRequest);

    void changePassword(ChangePasswordDTO request);

    void banUser(ChangePasswordDTO request);

    void updateSimpleUser(UpdateUserRequestDTO request);

    void updateAgent(UpdateAgentRequestDTO request);

    void updateAdmin(UpdateAdminRequestDTO request);

    void deleteUser(ChangePasswordDTO request);

    UserResponse createAgent(CreateAgentRequest request);

    UserResponse createSimpleUser(CreateSimpleUserRequest request);

    UserResponse setNewPassword(UUID id, NewPassordRequest request);

    void confirmRegistrationRequest(GetIdRequest request);

    void approveRegistrationRequest(GetIdRequest request);

    void denyRegistrationRequest(GetIdRequest request);

    String getPermission(String token);

    StringResponse limitRedirect(HttpServletRequest request);

    List<UserResponse> getAllRegistrationRequests() throws Exception;

    void addUserRole(UUID simpleUserId, RoleList roleList) throws NotFoundException;
}
