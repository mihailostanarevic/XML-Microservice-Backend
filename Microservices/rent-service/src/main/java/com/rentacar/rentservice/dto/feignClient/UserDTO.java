package com.rentacar.rentservice.dto.feignClient;

import com.rentacar.rentservice.util.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;
}
