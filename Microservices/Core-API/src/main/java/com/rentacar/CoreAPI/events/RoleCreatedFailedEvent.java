package com.rentacar.CoreAPI.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleCreatedFailedEvent {

    private String ticketAggregateId;

    private UUID requestId;
    private String reason;

}
