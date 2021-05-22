package com.clients.admin.implement;

import com.clients.admin.Util.ValidateData;
import com.clients.admin.dao.ClienteDao;
import com.clients.admin.models.entity.Cliente;
import com.clients.admin.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Qualifier("clientImpl")
public class ClienteImplement implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    private ValidateData validateData = new ValidateData();

    @Override
    public ResponseEntity createCliente(Cliente cliente) {
        validateData.validateObject(cliente);
        return clienteDao.createCliente(cliente);
    }

    @Override
    public ResponseEntity findClienteById(int id) {
        return clienteDao.findClienteById(id);
    }

    @Override
    public ResponseEntity<List<Object>> listClient() {
        return clienteDao.listClient();
    }

    @Override
    public ResponseEntity updateClient(Cliente cliente) {
        validateData.validateObject(cliente);
        return clienteDao.updateClient(cliente);
    }

    @Override
    public ResponseEntity deleteCleint(int id) {
        return clienteDao.deleteCleint(id);
    }
}
