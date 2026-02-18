package com.financiaplus.amlservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.financiaplus.amlservice.aml.entity.BlacklistedPerson;
import com.financiaplus.amlservice.aml.repository.BlacklistedPersonRepository;

@Component
public class DataLoader implements CommandLineRunner {

    private final BlacklistedPersonRepository repository;

    public DataLoader(BlacklistedPersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.findByDocument("123456798").isEmpty()) {
            repository.save(
                    new BlacklistedPerson(
                            null,
                            "Armando Herrera",
                            "123456798",
                            "1990-01-01",
                            "OFAC"));
        }
    }
}
