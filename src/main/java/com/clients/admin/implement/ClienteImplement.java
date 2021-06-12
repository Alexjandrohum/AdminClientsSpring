package com.clients.admin.implement;

import com.clients.admin.Util.Constant;
import com.clients.admin.Util.ValidateData;
import com.clients.admin.dao.ClienteDao;
import com.clients.admin.exception.ApiException;
import com.clients.admin.models.entity.Cliente;
import com.clients.admin.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Qualifier("clientImpl")
public class ClienteImplement implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;

    private final ValidateData validateData = new ValidateData();

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

    @Override
    public ResponseEntity uploadFile(MultipartFile foto, Integer id) {
        String nombreArchivo = "";

        Cliente clienteObtenido = (Cliente) clienteDao.findClienteById(id).getBody();
        String fotoAnterior = clienteObtenido.getFoto();
        if (fotoAnterior != null && fotoAnterior.length() > 0) {
            Path rutaFotoAnterior = Paths.get(Constant.pathFile).resolve(fotoAnterior).toAbsolutePath();
            File archivoFotoAnterior = rutaFotoAnterior.toFile();
            if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                archivoFotoAnterior.delete();
            }
        }

        if (!foto.isEmpty()) {
            nombreArchivo = UUID.randomUUID() + "_" + foto.getOriginalFilename().replace(" ", "");
            Path rutaArchivo = Paths.get(Constant.pathFile).resolve(nombreArchivo).toAbsolutePath();

            try {
                Files.copy(foto.getInputStream(), rutaArchivo);
            } catch (IOException e) {
                throw new ApiException(HttpStatus.BAD_REQUEST, "Error al subir la foto en la ruta del servidor",
                        new ArrayList(Arrays.asList(e.getClass(), e.getMessage(), e.getCause())));
            }

        }
        return clienteDao.uploadFile(id, nombreArchivo);
    }
}
