package co.facilite.devjr.service.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import co.facilite.devjr.domain.Address;
import co.facilite.devjr.domain.Department;
import co.facilite.devjr.domain.Employee;
import co.facilite.devjr.service.dto.AddressDTO;
import co.facilite.devjr.service.dto.DepartmentDTO;
import co.facilite.devjr.service.dto.EmployeeDTO;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
	@Override
	@Mapping(target = "address", source = "address", qualifiedByName = "addressId")
	@Mapping(target = "department", source = "department", qualifiedByName = "departmentName")
	EmployeeDTO toDto(Employee s);

	@Named("addressId")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	AddressDTO toDtoAddressId(Address address);

	@Named("departmentName")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", source = "id")
	@Mapping(target = "name", source = "name")
	DepartmentDTO toDtoDepartmentName(Department department);

	@Override
	@Mapping(target = "address.employee", ignore = true)
	Employee toEntity(EmployeeDTO dto);
	
	@Override
	@Named("partialUpdate")
	@Mapping(target = "address.employee", ignore = true)
	@BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
	void partialUpdate(@MappingTarget Employee entity, EmployeeDTO dto);

}
