package com.clients.admin.service;

import com.clients.admin.models.entity.Cliente;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClienteService {

    ResponseEntity createCliente(Cliente cliente);

    ResponseEntity findClienteById(int id);

    ResponseEntity<List<Object>> listClient();

    ResponseEntity updateClient(Cliente cliente);

    ResponseEntity deleteCleint(int id);

    ResponseEntity uploadFile(MultipartFile foto, Integer id);

    public ResponseEntity verFoto(String nombreFoto);
    
    ResponseEntity findClientByPage(Pageable pageable);

}
