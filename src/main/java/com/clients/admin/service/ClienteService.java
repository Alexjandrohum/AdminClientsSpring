package com.clients.admin.service;

import com.clients.admin.models.entity.Cliente;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ClienteService {

    ResponseEntity<?> createCliente(Cliente cliente);

    ResponseEntity<?> findClienteById(int id);

    ResponseEntity<?> listClient();

    ResponseEntity<?> updateClient(Cliente cliente);

    ResponseEntity<?> deleteCleint(int id);

    ResponseEntity<?> uploadFile(MultipartFile foto, Integer id);

    public ResponseEntity<?> verFoto(String nombreFoto);

}
