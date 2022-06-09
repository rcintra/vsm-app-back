package com.rcintra.vsm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rcintra.vsm.models.Cliente;
import com.rcintra.vsm.repository.ClienteRepository;

@SpringBootTest
class VsmAppBackApplicationTests {
	
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Test
	public void shouldNotAllowToPersistNullClienteName() {
		clienteRepository.save(new Cliente());
	}

}
