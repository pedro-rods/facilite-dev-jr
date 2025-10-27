package co.facilite.devjr.service.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import co.facilite.devjr.domain.enumeration.Uf;
import co.facilite.devjr.service.CepLookupService;
import co.facilite.devjr.service.dto.AddressDTO;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class CepLookupServiceImpl implements CepLookupService {

	private static ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
			false);

	public String normalizeCep(String raw) {
		if (raw == null) {
			throw new IllegalArgumentException("CEP não pode ser nulo.");
		}
		String cep = raw.replaceAll("\\D", "");
		if (cep.length() != 8) {
			throw new IllegalArgumentException("CEP inválido. Use 8 dígitos (ex.: 01001000).");
		}

		return cep;
	}

	public AddressDTO lookup(String cep) {
		cep = normalizeCep(cep);
		String url = "https://viacep.com.br/ws/" + cep + "/json/";
		try {
			// 3) Faz a requisição HTTP
			HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

			HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).timeout(Duration.ofSeconds(10))
					.header("Accept", "application/json").GET().build();

			HttpResponse<String> response = client.send(request,
					HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));

			if (response.statusCode() != 200) {
				throw new IllegalStateException("Falha ao consultar ViaCEP: HTTP " + response.statusCode());
			}

			// 4) Desserializa a resposta
			ViaCepResponse via = MAPPER.readValue(response.body(), ViaCepResponse.class);

			if (Boolean.TRUE.equals(via.erro)) {
				throw new IllegalArgumentException("CEP não encontrado no ViaCEP.");
			}

			// 5) Preenche o AddressDTO
			AddressDTO dto = new AddressDTO();
			dto.setCep(via.cep != null ? via.cep : cep);
			dto.setStreet(via.logradouro);
			dto.setNumber(null); // ViaCEP não informa número
			dto.setComplement((via.complemento));
			dto.setDistrict(via.bairro);
			dto.setCity(via.localidade);

			// Converte a sigla para seu enum Uf (ex.: "SP" -> Uf.SP)
			if (via.uf == null || via.uf.isBlank()) {
				throw new IllegalStateException("UF ausente na resposta do ViaCEP.");
			}
			dto.setUf(Uf.valueOf(via.uf.toUpperCase())); // assume que o enum tem as siglas das UFs

			return dto;

		} catch (RuntimeException e) {
			// repropaga erros de validação ou estado
			throw e;
		} catch (Exception e) {
			// encapsula outras exceções (IO, timeout, parsing…)
			throw new IllegalStateException("Erro ao consultar o CEP no ViaCEP.", e);
		}
	}

	private static class ViaCepResponse {
		public String cep;
		public String logradouro;
		public String complemento;
		public String bairro;
		public String localidade;
		public String uf;
		public Boolean erro; // presente e true quando o CEP não existe
	}
}
