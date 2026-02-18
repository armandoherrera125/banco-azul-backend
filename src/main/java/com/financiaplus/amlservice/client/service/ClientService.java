package com.financiaplus.amlservice.client.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.financiaplus.amlservice.client.entity.Client;
import com.financiaplus.amlservice.client.repository.ClientRepository;

@Service
public class ClientService {

    private final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Optional<Client> getClientByDocument(String document) {
        return repository.findByDocument(document.trim());
    }

    public boolean isEligible(Client client) {
        return client.getCreditScore() >= 7.0;
    }

    public List<Client> getAllClients() {
        return repository.findAll();
    }

    public Client saveClient(Client client) {
        return repository.save(client);
    }
}