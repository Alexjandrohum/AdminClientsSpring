package com.clients.admin.implement;

import com.clients.admin.Util.Constant;
import com.clients.admin.Util.ValidateData;
import com.clients.admin.dao.ClienteDao;
import com.clients.admin.dao.ClienteJpaDao;
import com.clients.admin.exception.ApiException;
import com.clients.admin.models.entity.Cliente;
import com.clients.admin.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Qualifier("clientImpl")
public class ClienteImplement implements ClienteService {

    @Autowired
    private ClienteDao clienteDao;
    
    @Autowired
    private ClienteJpaDao clienteJpa;

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
            System.out.println("Ruta anterior: " + rutaFotoAnterior);
            File archivoFotoAnterior = rutaFotoAnterior.toFile();
            if (archivoFotoAnterior.exists() && archivoFotoAnterior.canRead()) {
                System.out.println("Archivo eliminado");
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

    public ResponseEntity verFoto(String nombreFoto) {
        Path rutaArchivo = Paths.get(Constant.pathFile).resolve(nombreFoto).toAbsolutePath();

        Resource recurso = null;

        try {
            recurso = new UrlResource(rutaArchivo.toUri());
        } catch (MalformedURLException e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener la ruta de la foto", new ArrayList(Arrays.asList(e.getClass(), e.getMessage(), e.getCause())));
        }
        Map<String, Object> param = new HashMap();
        try {
            param.put("foto", recurso.getFile().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!recurso.exists() && !recurso.isReadable()) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.name(), new ArrayList<>(Arrays.asList("Url erronea")));
        }
        HttpHeaders cabecera = new HttpHeaders();
        cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"");
        return new ResponseEntity(recurso, cabecera, HttpStatus.OK);
    }

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity findClientByPage(Pageable pageable) {
		Page<Cliente> listPage = clienteJpa.findAll(pageable);
		if(listPage != null && !listPage.isEmpty()) {
			return new ResponseEntity(listPage, HttpStatus.OK);
		}
		return new ResponseEntity(HttpStatus.NOT_FOUND);
	}
}
