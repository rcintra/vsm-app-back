package com.rcintra.vsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VsmAppBackApplication {
	
	private static Logger LOG = LoggerFactory
		      .getLogger(VsmAppBackApplication.class);
	
	public static void main(String[] args) {
		LOG.info("STARTING THE APPLICATION");
		SpringApplication.run(VsmAppBackApplication.class, args);
		LOG.info("APPLICATION FINISHED");
	}
	
}
