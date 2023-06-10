package com.clients.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.clients.admin.models.entity.Cliente;

@Repository
public interface ClienteJpaDao extends JpaRepository<Cliente, Integer> {
	
}
