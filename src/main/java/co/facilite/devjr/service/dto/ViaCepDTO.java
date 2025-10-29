package co.facilite.devjr.service.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A DTO for the Via Cep response entity.
 */
@SuppressWarnings({ "serial" })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViaCepDTO implements Serializable {

	public String cep;
	public String logradouro;
	public String complemento;
	public String bairro;
	public String localidade;
	public String uf;
	public Boolean erro; // true quando o CEP nao existe
}
