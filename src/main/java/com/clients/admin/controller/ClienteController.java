package com.clients.admin.controller;

import com.clients.admin.Util.Constant;
import com.clients.admin.models.entity.Cliente;
import com.clients.admin.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:8080", "*"})
@RestController
@RequestMapping(value = Constant.VERSION)
public class ClienteController {

    @Autowired
    @Qualifier(value = "clientImpl")
    private ClienteService serviceClient;

    @PostMapping(value = "/createClient")
    public ResponseEntity createCliente(@RequestBody Cliente cliente) {
        return serviceClient.createCliente(cliente);
    }

    @GetMapping(value = "/findClient/{id}")
    public ResponseEntity findClienteById(@PathVariable int id) {
        return serviceClient.findClienteById(id);
    }

    @GetMapping(value = "/listClient")
    public ResponseEntity<List<Object>> listClient() {
        return serviceClient.listClient();
    }

    @PutMapping(value = "/updateClient")
    public ResponseEntity updateClient(@RequestBody Cliente cliente) {
        return serviceClient.updateClient(cliente);
    }

    @DeleteMapping(value = "/deleteClient/{id}")
    public ResponseEntity deleteClient(@PathVariable int id) {
        return serviceClient.deleteCleint(id);
    }

    @PostMapping("/clientes/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile archivo, @RequestParam("id") String id) {
        return serviceClient.uploadFile(archivo, Integer.parseInt(id));
    }

    @GetMapping("/clientes/uploads/img/{nombreFoto:.+}")
    public ResponseEntity verFoto(@PathVariable String nombreFoto) {
        return serviceClient.verFoto(nombreFoto);
    }

}
