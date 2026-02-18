package com.financiaplus.amlservice.aml.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.financiaplus.amlservice.aml.entity.BlacklistedPerson;
import com.financiaplus.amlservice.aml.repository.BlacklistedPersonRepository;
import com.financiaplus.amlservice.exception.DuplicateResourceException;

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

    public BlacklistedPerson save(BlacklistedPerson person) {

        if (repository.existsByDocument(person.getDocument().trim())) {
            throw new DuplicateResourceException(
                    "Person with this document is already blacklisted");
        }

        return repository.save(person);
    }
}
