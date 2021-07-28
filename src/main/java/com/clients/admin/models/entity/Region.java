package com.clients.admin.models.entity;

import java.io.Serializable;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class Region implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	@NotNull(message = "No puede ir null")
	@NotEmpty(message = "No puede ir vacio")
	private String nombre;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Region [id=" + id + ", nombre=" + nombre + "]";
	}

}
