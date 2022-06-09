package com.rcintra.vsm.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rcintra.vsm.models.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
