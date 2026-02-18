package com.financiaplus.amlservice.client.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.web.bind.annotation.*;

import com.financiaplus.amlservice.client.entity.Client;
import com.financiaplus.amlservice.client.service.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/client")
@Tag(name = "Clientes", description = "Consulta, creación y validación de clientes dentro del sistema financiero")
public class ClientController {

  private final ClientService service;

  public ClientController(ClientService service) {
    this.service = service;
  }

  @Operation(summary = "Crear nuevo cliente", description = "Registra un cliente en el sistema financiero.")
  @ApiResponse(responseCode = "200", description = "Cliente creado correctamente", content = @Content(schema = @Schema(example = """
      {
        "created": true,
        "client": {
          "id": 3,
          "name": "Carlos Méndez",
          "document": "12345678-9",
          "email": "carlos@mail.com",
          "phone": "7777-8888",
          "birthDate": "1995-06-12",
          "gender": "M",
          "state": "Activo",
          "address": "San Salvador",
          "creditScore": 8.2,
          "monthlyIncome": 1500,
          "monthlyExpenses": 600
        }
      }
      """)))
  @PostMapping
  public Map<String, Object> createClient(@RequestBody Client client) {

    Map<String, Object> response = new HashMap<>();

    Client savedClient = service.saveClient(client);

    response.put("created", true);
    response.put("client", savedClient);

    return response;
  }

  @Operation(summary = "Consultar cliente por documento", description = "Busca un cliente existente y valida si cumple con el score mínimo requerido para continuar procesos financieros.")
  @ApiResponse(responseCode = "200", description = "Resultado de búsqueda del cliente", content = @Content(schema = @Schema(example = """
      {
        "eligible": true,
        "client": {
          "id": 1,
          "name": "Juan Pérez",
          "document": "01234567-8",
          "creditScore": 8.5,
          "monthlyIncome": 1200,
          "monthlyExpenses": 600
        }
      }
      """)))
  @GetMapping("/{document}")
  public Map<String, Object> getClient(@PathVariable String document) {

    Optional<Client> client = service.getClientByDocument(document);

    Map<String, Object> response = new HashMap<>();

    if (client.isPresent()) {

      if (!service.isEligible(client.get())) {
        response.put("eligible", false);
        response.put("message", "Client credit score below required threshold");
        return response;
      }

      response.put("eligible", true);
      response.put("client", client.get());

    } else {
      response.put("exists", false);
    }

    return response;
  }

  @Operation(summary = "Obtener información financiera del cliente", description = "Devuelve métricas financieras clave del cliente: score crediticio, ingresos mensuales y gastos.")
  @ApiResponse(responseCode = "200", description = "Datos financieros del cliente", content = @Content(schema = @Schema(example = """
      {
        "financialData": {
          "creditScore": 8.5,
          "monthlyIncome": 1200,
          "monthlyExpenses": 600
        }
      }
      """)))
  @GetMapping("/{document}/financial")
  public Map<String, Object> getFinancialData(@PathVariable String document) {

    Optional<Client> client = service.getClientByDocument(document);

    Map<String, Object> response = new HashMap<>();

    if (client.isPresent()) {

      Map<String, Object> financial = new HashMap<>();
      financial.put("creditScore", client.get().getCreditScore());
      financial.put("monthlyIncome", client.get().getMonthlyIncome());
      financial.put("monthlyExpenses", client.get().getMonthlyExpenses());

      response.put("financialData", financial);

    } else {
      response.put("exists", false);
    }

    return response;
  }

  @Operation(summary = "Listar todos los clientes", description = "Obtiene el listado completo de clientes almacenados en la base de datos junto con el total.")
  @GetMapping("/all")
  public Map<String, Object> getAllClients() {

    Map<String, Object> response = new HashMap<>();

    var clients = service.getAllClients();

    response.put("count", clients.size());
    response.put("clients", clients);

    return response;
  }
}