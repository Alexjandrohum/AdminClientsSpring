package com.clients.admin.dao;

import com.clients.admin.exception.ApiException;
import com.clients.admin.exception.MessageGeneric;
import com.clients.admin.models.entity.Cliente;
import com.clients.admin.service.ClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.*;

@Repository
public class ClienteDao implements ClienteService {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public ResponseEntity createCliente(Cliente cliente) {

        StoredProcedureQuery spq = getStore("SP_CREATE_CLIENT");
        spq.registerStoredProcedureParameter("C_ID", Integer.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_NAME", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_APELLIDOP", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_APELLIDOM", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_EMAIL", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_DATE", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("CODE", Integer.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("MSG", String.class, ParameterMode.OUT);

        spq.setParameter("C_NAME", cliente.getNombre());
        spq.setParameter("C_APELLIDOP", cliente.getApellidoPaterno());
        spq.setParameter("C_APELLIDOM", cliente.getApellidoMaterno());
        spq.setParameter("C_EMAIL", cliente.getEmail());
        spq.setParameter("C_DATE", new Date().toString());

        spq.execute();

        Integer code = (Integer) spq.getOutputParameterValue("CODE");
        String msg = (String) spq.getOutputParameterValue("MSG");

        if (code == 0) {
            Integer idCliente = (Integer) spq.getOutputParameterValue("C_ID");
            String nombre = (String) spq.getOutputParameterValue("C_NAME");
            String apellidoPaterno = (String) spq.getOutputParameterValue("C_APELLIDOP");
            String apellidoMaterno = (String) spq.getOutputParameterValue("C_APELLIDOM");
            String email = (String) spq.getOutputParameterValue("C_EMAIL");
            String date = (String) spq.getOutputParameterValue("C_DATE");
            return new ResponseEntity(new Cliente(idCliente, nombre, apellidoPaterno, apellidoMaterno, email, date), HttpStatus.CREATED);
        } else {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, msg, new ArrayList<>(Arrays.asList("Pongase en contacto con el administrador", "Ocurrio un error interno")));
        }

    }

    @Override
    public ResponseEntity findClienteById(int id) {
        Cliente cliente = null;
        StoredProcedureQuery spq = getStore("SP_FIND_CLIENT");

        spq.registerStoredProcedureParameter("C_ID", Integer.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_NAME", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_APELLIDOP", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_APELLIDOM", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_EMAIL", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_DATE", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_STATUS", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("CODE", Integer.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("MSG", String.class, ParameterMode.OUT);
        spq.setParameter("C_ID", id);
        spq.execute();
        Integer code = (Integer) spq.getOutputParameterValue("CODE");
        String msg = (String) spq.getOutputParameterValue("MSG");

        if (code == 0) {
            Integer idCliente = (Integer) spq.getOutputParameterValue("C_ID");
            String nombre = (String) spq.getOutputParameterValue("C_NAME");
            String apellidoPaterno = (String) spq.getOutputParameterValue("C_APELLIDOP");
            String apellidoMaterno = (String) spq.getOutputParameterValue("C_APELLIDOM");
            String email = (String) spq.getOutputParameterValue("C_EMAIL");
            String fecha = (String) spq.getOutputParameterValue("C_DATE");
            String status = (String) spq.getOutputParameterValue("C_STATUS");
            cliente = new Cliente(idCliente, nombre, apellidoPaterno, apellidoMaterno, email, fecha, status);
            return new ResponseEntity(cliente, HttpStatus.OK);
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), new ArrayList(Arrays.asList(Arrays.asList(msg))));
        }
    }

    @Override
    public ResponseEntity<List<Object>> listClient() {
        StoredProcedureQuery spq = getStore("SP_LIST_CLIENT");
        spq.registerStoredProcedureParameter("cursor_cliente", Void.class, ParameterMode.REF_CURSOR);

        List<Object[]> listObject = spq.getResultList();
        List<Cliente> listCliente = new ArrayList<>();
        for (Object[] objectCliente : listObject) {
            listCliente.add(new Cliente(
                    Integer.parseInt(objectCliente[0].toString()),
                    objectCliente[1].toString(),
                    objectCliente[2].toString(),
                    objectCliente[3].toString(),
                    (Optional.ofNullable(objectCliente[4]).isPresent() ? objectCliente[4].toString() : ""),
                    objectCliente[5].toString(),
                    objectCliente[6].toString()
            ));
        }
        return new ResponseEntity(listCliente, HttpStatus.OK);
    }

    @Override
    public ResponseEntity updateClient(Cliente cliente) {

        StoredProcedureQuery spq = getStore("SP_UPDATE_CLIENT");
        spq.registerStoredProcedureParameter("C_ID", Integer.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_NAME", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_APELLIDOP", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_APELLIDOM", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_EMAIL", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_DATE", String.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_STATUS", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("CODE", Integer.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("MSG", String.class, ParameterMode.OUT);

        spq.setParameter("C_ID", cliente.getId());
        spq.setParameter("C_NAME", cliente.getNombre());
        spq.setParameter("C_APELLIDOP", cliente.getApellidoPaterno());
        spq.setParameter("C_APELLIDOM", cliente.getApellidoMaterno());
        spq.setParameter("C_EMAIL", cliente.getEmail());
        spq.setParameter("C_DATE", new Date().toString());

        spq.execute();

        Integer code = (Integer) spq.getOutputParameterValue("CODE");
        String msg = (String) spq.getOutputParameterValue("MSG");
        if (code == 0) {
            Integer idCliente = (Integer) spq.getOutputParameterValue("C_ID");
            String nombre = (String) spq.getOutputParameterValue("C_NAME");
            String apellidoPaterno = (String) spq.getOutputParameterValue("C_APELLIDOP");
            String apellidoMaterno = (String) spq.getOutputParameterValue("C_APELLIDOM");
            String email = (String) spq.getOutputParameterValue("C_EMAIL");
            String fecha = (String) spq.getOutputParameterValue("C_DATE");
            String status = (String) spq.getOutputParameterValue("C_STATUS");
            cliente = new Cliente(idCliente, nombre, apellidoPaterno, apellidoMaterno, email, fecha, status);
            return new ResponseEntity(cliente, HttpStatus.OK);
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), new ArrayList(Arrays.asList(Arrays.asList(msg))));
        }
    }

    @Override
    public ResponseEntity deleteCleint(int id) {
        StoredProcedureQuery spq = getStore("SP_DELETE_CLIENT");

        spq.registerStoredProcedureParameter("C_ID", Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("CODE", Integer.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("MSG", String.class, ParameterMode.OUT);
        spq.setParameter("C_ID", id);
        spq.execute();

        Integer code = (Integer) spq.getOutputParameterValue("CODE");
        String msg = (String) spq.getOutputParameterValue("MSG");
        if (code == 0) {
            return new ResponseEntity(new MessageGeneric(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(), new ArrayList(Arrays.asList(msg))), HttpStatus.ACCEPTED);
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), new ArrayList(Arrays.asList(Arrays.asList(msg))));
        }
    }

    private StoredProcedureQuery getStore(String procedure) {
        return entityManager.createStoredProcedureQuery(procedure);
    }
}
