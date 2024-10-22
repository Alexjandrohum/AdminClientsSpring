package com.clients.admin.dao;

import com.clients.admin.exception.ApiException;
import com.clients.admin.exception.MessageGeneric;
import com.clients.admin.models.entity.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import java.util.*;

@Repository
public class ClienteDao {

    @PersistenceContext
    EntityManager entityManager;

    public ResponseEntity<?> createCliente(Cliente cliente) {

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
            return ResponseEntity.status(HttpStatus.CREATED).body(new Cliente(idCliente, nombre, apellidoPaterno, apellidoMaterno, email, date, null));
        } else if(code == 2){
            throw new ApiException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.name(), new ArrayList<>(Arrays.asList(msg)));
        } else {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, msg, new ArrayList<>(Arrays.asList("Pongase en contacto con el administrador", "Ocurrio un error interno")));
        }

    }

    public ResponseEntity<?> findClienteById(int id) {
        Cliente cliente = null;
        Map<String, Object> map = new HashMap<>();
        StoredProcedureQuery spq = getStore("SP_FIND_CLIENT");

        spq.registerStoredProcedureParameter("C_ID", Integer.class, ParameterMode.INOUT);
        spq.registerStoredProcedureParameter("C_NAME", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_APELLIDOP", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_APELLIDOM", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_EMAIL", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_DATE", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_STATUS", String.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("C_FOTO", String.class, ParameterMode.OUT);
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
            String foto = (String) spq.getOutputParameterValue("C_FOTO");
            cliente = new Cliente(idCliente, nombre, apellidoPaterno, apellidoMaterno, email, fecha, status, foto);
            return ResponseEntity.ok(cliente);
        } else {
            map.put("CODE", code);
            map.put("SP_PROCEDURE", "SP_FIND_CLIENT");
            map.put("Clase: ", ".findClienteById");
            map.put("Message", msg);
            throw new ApiException(HttpStatus.NOT_FOUND, msg, new ArrayList(Arrays.asList(map)));
        }
    }

    public ResponseEntity<?> listClient() {
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
                    objectCliente[6].toString(),
                    (Optional.ofNullable(objectCliente[7]).isPresent() ? objectCliente[7].toString() : "")
            ));
        }
        return ResponseEntity.ok(listCliente);
    }

    public ResponseEntity<?> updateClient(Cliente cliente) {

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
            return ResponseEntity.ok(cliente);
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), new ArrayList(Arrays.asList(Arrays.asList(msg))));
        }
    }

    public ResponseEntity<?> deleteCleint(int id) {
        StoredProcedureQuery spq = getStore("SP_DELETE_CLIENT");

        spq.registerStoredProcedureParameter("C_ID", Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("CODE", Integer.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("MSG", String.class, ParameterMode.OUT);
        spq.setParameter("C_ID", id);
        spq.execute();

        Integer code = (Integer) spq.getOutputParameterValue("CODE");
        String msg = (String) spq.getOutputParameterValue("MSG");
        if (code == 0) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageGeneric(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(), new ArrayList(Arrays.asList(msg))));
        } else {
            throw new ApiException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.name(), new ArrayList(Arrays.asList(Arrays.asList(msg))));
        }
    }

    public ResponseEntity<?> uploadFile(Integer id, String foto) {
        StoredProcedureQuery spq = getStore("SP_UPLOAD_FOTO_CLIENT");

        spq.registerStoredProcedureParameter("C_ID", Integer.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("C_FOTO", String.class, ParameterMode.IN);
        spq.registerStoredProcedureParameter("CODE", Integer.class, ParameterMode.OUT);
        spq.registerStoredProcedureParameter("MSG", String.class, ParameterMode.OUT);
        spq.setParameter("C_ID", id);
        spq.setParameter("C_FOTO", foto);
        spq.execute();

        Integer code = (Integer) spq.getOutputParameterValue("CODE");
        String msg = (String) spq.getOutputParameterValue("MSG");

        if(code != 0){
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno", new ArrayList(Arrays.asList(msg)));
        }
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new MessageGeneric(HttpStatus.ACCEPTED.value(), HttpStatus.ACCEPTED.name(), new ArrayList(Arrays.asList(msg, foto))));
    }

    private StoredProcedureQuery getStore(String procedure) {
        return entityManager.createStoredProcedureQuery(procedure);
    }
}
