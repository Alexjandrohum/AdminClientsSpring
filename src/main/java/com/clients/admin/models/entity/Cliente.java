package com.clients.admin.models.entity;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Cliente implements Serializable {

    private Integer id;
    @NotNull(message = "no puede ser nulo")
    @NotEmpty(message = "no puede ser vacio")
    private String nombre;
    @NotNull(message = "no puede ser nulo")
    @NotEmpty(message = "no puede ser vacio")
    private String apellidoPaterno;
    private String apellidoMaterno;
    @NotNull(message = "no puede ser nulo")
    @Email(message = "formato incorrecto")
    @NotEmpty(message = "no puede ser vacio")
    private String email;
    private String createAt;
    private String status;
    private String foto;

    public Cliente() {
    }

    public Cliente(Integer id, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String createAt, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.createAt = createAt;
        this.foto = foto;
    }

    public Cliente(String nombre, String apellidoPaterno, String apellidoMaterno, String email, String createAt, String activo) {
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.createAt = createAt;
    }

    public Cliente(Integer id, String nombre, String apellidoPaterno, String apellidoMaterno, String email, String createAt, String status, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.email = email;
        this.createAt = createAt;
        this.status = status;
        this.foto = foto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellidoPaterno='" + apellidoPaterno + '\'' +
                ", apellidoMaterno='" + apellidoMaterno + '\'' +
                ", email='" + email + '\'' +
                ", createAt=" + createAt +
                '}';
    }
}
