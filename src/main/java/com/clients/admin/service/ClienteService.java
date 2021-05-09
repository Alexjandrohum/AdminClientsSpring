package com.clients.admin.service;

import com.clients.admin.models.entity.Cliente;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ClienteService {

    ResponseEntity createCliente(Cliente cliente);

    ResponseEntity findClienteById(int id);

    ResponseEntity<List<Object>> listClient();

    ResponseEntity updateClient(Cliente cliente);

    ResponseEntity deleteCleint(int id);

}
