package com.rcintra.vsm.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcintra.vsm.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

	public List<Cliente> findByCpfCnpj(String cpfCnpj);
	
}
