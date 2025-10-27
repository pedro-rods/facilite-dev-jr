package co.facilite.devjr.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.facilite.devjr.service.CepLookupService;
import co.facilite.devjr.service.dto.AddressDTO;
import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api")
public class CepLookupResource {

	@Autowired
	private CepLookupService service;

	@GetMapping("/cep/{cep}")
	@Operation(summary = "Buscar endereco pelo cep")
	public ResponseEntity<AddressDTO> lookup(@PathVariable(value = "cep", required = true) String cep) {

		return new ResponseEntity<>(service.lookup(cep), null, HttpStatus.OK);
	}

}
