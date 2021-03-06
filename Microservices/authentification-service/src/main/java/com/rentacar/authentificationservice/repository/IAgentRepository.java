package com.rentacar.authentificationservice.repository;

import com.rentacar.authentificationservice.entity.Agent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IAgentRepository extends JpaRepository<Agent, UUID> {

    Agent findOneById(UUID id);

    Agent findOneBySimpleUserId(UUID id);
}