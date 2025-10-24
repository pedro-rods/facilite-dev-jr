package co.facilite.devjr.web.rest;

import co.facilite.devjr.service.dto.AddressDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CepLookupResource {

    @GetMapping("/cep/{cep}")
    public ResponseEntity<AddressDTO> get(@PathVariable String cep) {
        /* TODO */
        return null;
    }
}
