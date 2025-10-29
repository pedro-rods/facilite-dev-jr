package co.facilite.devjr.service.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.facilite.devjr.domain.enumeration.Uf;
import co.facilite.devjr.service.CepLookupService;
import co.facilite.devjr.service.dto.AddressDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CepLookupServiceImpl implements CepLookupService {

	private static final ObjectMapper MAPPER = new ObjectMapper()
			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

	private static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

	@Override
	public String normalizeCep(String raw) {
		if (raw == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP inválido");
		}
		String cep = raw.replaceAll("\\D", "");
		if (cep.length() != 8) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP inválido");
		}
		return cep;
	}

	@Override
	public AddressDTO lookup(String rawCep) {
		String cep = normalizeCep(rawCep);

		HttpRequest req = HttpRequest.newBuilder().uri(URI.create("https://viacep.com.br/ws/" + cep + "/json/"))
				.timeout(Duration.ofSeconds(8)).GET().build();

		HttpResponse<String> resp;
		try {
			resp = HTTP.send(req, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Serviço de CEP indisponível", e);
		}

		int status = resp.statusCode();
		// mapeamento de erros
		if (status >= 500) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Serviço de CEP indisponível");
		}

		if (status == 404) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP não encontrado");
		}
		if (status == 400) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "CEP inválido");
		}

		if (status != 200) {
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Serviço de CEP indisponível");
		}
		// tenta mapear o corpo json da req para a entidade ViaCepResponse
		ViaCepResponse body;
		try {
			body = MAPPER.readValue(resp.body(), ViaCepResponse.class);
		} catch (Exception e) {
			
			throw new ResponseStatusException(HttpStatus.BAD_GATEWAY, "Serviço de CEP indisponível", e);
		}

		if (body == null || Boolean.TRUE.equals(body.erro)) {
			// Sem dados para o CEP informado
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CEP não encontrado");
		}

		AddressDTO dto = new AddressDTO();
		dto.setCep(body.cep != null ? body.cep.replaceAll("\\D", "") : cep);
		dto.setStreet(body.logradouro);
		dto.setComplement(body.complemento);
		dto.setDistrict(body.bairro);
		dto.setCity(body.localidade);
		if (body.uf != null) {
			try {
				dto.setUf(Uf.valueOf(body.uf));
			} catch (IllegalArgumentException ex) {
				// UF invalida vira null
				dto.setUf(null);
			}
		}
		return dto;
	}

	private static class ViaCepResponse {
		public String cep;
		public String logradouro;
		public String complemento;
		public String bairro;
		public String localidade;
		public String uf;
		public Boolean erro; // true quando o CEP nao existe
	}
}
