package com.ysykal.Mocky.repository;


import com.ysykal.Mocky.entity.MockEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface MockEndpointRepository extends JpaRepository<MockEndpoint, UUID> {

    Optional<MockEndpoint> findByPathAndMethod(String path, String method);

    boolean existsByPathAndMethod(String path, String method);
}
