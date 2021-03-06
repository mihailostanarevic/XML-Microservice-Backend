package com.rentacar.authentificationservice.repository;

import com.rentacar.authentificationservice.entity.SimpleUser;
import com.rentacar.authentificationservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ISimpleUserRepository extends JpaRepository<SimpleUser, UUID> {

    SimpleUser findOneById(UUID id);

    SimpleUser findOneByUser(User user);
}