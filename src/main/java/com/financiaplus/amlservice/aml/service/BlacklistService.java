package com.financiaplus.amlservice.aml.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.financiaplus.amlservice.aml.entity.BlacklistedPerson;
import com.financiaplus.amlservice.aml.repository.BlacklistedPersonRepository;

@Service
public class BlacklistService {

    private final BlacklistedPersonRepository repository;

    public BlacklistService(BlacklistedPersonRepository repository) {
        this.repository = repository;
    }

    public Optional<BlacklistedPerson> checkByDocument(String document) {
        return repository.findByDocument(document.trim());
    }

    public Optional<BlacklistedPerson> checkByName(String name) {
        return repository.findByNameNormalized(name.trim());
    }
}