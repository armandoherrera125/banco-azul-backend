package com.financiaplus.amlservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.financiaplus.amlservice.client.entity.Client;
import com.financiaplus.amlservice.client.repository.ClientRepository;

@Component
public class ClientDataLoader implements CommandLineRunner {

    private final ClientRepository repository;

    public ClientDataLoader(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {

        if (repository.findByDocument("123456789").isEmpty()) {
            repository.save(new Client(
                    null,
                    "Juan Perez",
                    "123456789",
                    "juan@mail.com",
                    "70001234",
                    "1995-05-10",
                    "masculino",
                    "San Salvador",
                    "Col. Escalón, Calle Principal #123",
                    7.2,
                    1200.0,
                    500.0));
        }

        if (repository.findByDocument("876543219").isEmpty()) {
            repository.save(new Client(
                    null,
                    "Maria Lopez",
                    "876543219",
                    "maria@mail.com",
                    "71234567",
                    "1992-03-15",
                    "femenino",
                    "La Libertad",
                    "Antiguo Cuscatlán, Residencial Las Palmas",
                    5.8,
                    900.0,
                    600.0));
        }
    }
}
