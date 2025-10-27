package co.facilite.devjr.service;

import org.springframework.stereotype.Service;

import co.facilite.devjr.service.dto.AddressDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public interface CepLookupService {
	String normalizeCep(String raw);

	AddressDTO lookup(String cep);
}
