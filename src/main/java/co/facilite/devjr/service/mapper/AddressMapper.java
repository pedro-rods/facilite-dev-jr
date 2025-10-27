package co.facilite.devjr.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import co.facilite.devjr.domain.Address;
import co.facilite.devjr.service.dto.AddressDTO;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {

	// Quando for do DTO -> Entity, ignore o back-reference Address.employee
	@Override
	@Mapping(target = "employee", ignore = true)
	Address toEntity(AddressDTO dto);

	@Override
	AddressDTO toDto(Address entity);

	@Override
	@Named("partialUpdate")
	@Mapping(target = "employee", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void partialUpdate(@MappingTarget Address entity, AddressDTO dto);
}
