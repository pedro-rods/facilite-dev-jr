package co.facilite.devjr.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import co.facilite.devjr.domain.Address;
import co.facilite.devjr.domain.enumeration.Uf;
import co.facilite.devjr.service.dto.AddressDTO;
import co.facilite.devjr.service.dto.ViaCepDTO;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

	@Override
	@Mapping(target = "employee", ignore = true)
	Address toEntity(AddressDTO dto);

	@Override
	@Named("partialUpdate")
	@Mapping(target = "employee", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void partialUpdate(@MappingTarget Address entity, AddressDTO dto);

	@Mapping(source = "cep", target = "cep", qualifiedByName = "digitsOnly")
	@Mapping(source = "logradouro", target = "street")
	@Mapping(source = "bairro", target = "district")
	@Mapping(source = "localidade", target = "city")
	@Mapping(source = "complemento", target = "complement")
	@Mapping(source = "uf", target = "uf", qualifiedByName = "getUf")
	AddressDTO toDtoFromCepApi(ViaCepDTO viaCep);

	// metodo auxiliar pra limpar o cep
	@Named("digitsOnly")
	default String digitsOnly(String raw) {
		if (raw == null)
			return null;
		return raw.replaceAll("\\D", "");
	}

	@Named("getUf")
	default Uf getUF(String ufCep) {
		try {
			return Uf.valueOf(ufCep);
		} catch (IllegalArgumentException ex) {
			return null;
		}
	}
}
