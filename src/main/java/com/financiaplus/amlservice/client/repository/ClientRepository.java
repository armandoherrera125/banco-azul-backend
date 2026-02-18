package com.financiaplus.amlservice.client.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.financiaplus.amlservice.client.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByDocument(String document);
}