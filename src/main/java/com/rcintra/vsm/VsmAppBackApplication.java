package com.rcintra.vsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.rcintra.vsm.models.Cidade;
import com.rcintra.vsm.models.Cliente;
import com.rcintra.vsm.service.ClienteService;

@SpringBootApplication
public class VsmAppBackApplication implements CommandLineRunner {
	
	private static Logger LOG = LoggerFactory
		      .getLogger(VsmAppBackApplication.class);
	
	@Autowired
	private ClienteService service;

	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(VsmAppBackApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}
	
	@Override
	public void run(String... args) throws Exception {
		LOG.info("EXECUTING : command line runner");

		Cidade saoPaulo = new Cidade(null, "Sao Paulo");
		service.saveCidade(saoPaulo);
		LOG.info("SAVE: " + saoPaulo);
		
		Cliente rafael = new Cliente();
		rafael.setNome("Rafael");
		rafael.setCpfCnpj("1234123");
		rafael.setCidade(saoPaulo);
		rafael.setHabilitado(Boolean.TRUE);
		
		service.saveCliente(rafael);
		LOG.info("SAVE: " + rafael);
		
	}

}
