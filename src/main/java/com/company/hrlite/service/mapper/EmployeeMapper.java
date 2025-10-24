package com.company.hrlite.service.mapper;

import com.company.hrlite.domain.Address;
import com.company.hrlite.domain.Department;
import com.company.hrlite.domain.Employee;
import com.company.hrlite.service.dto.AddressDTO;
import com.company.hrlite.service.dto.DepartmentDTO;
import com.company.hrlite.service.dto.EmployeeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Employee} and its DTO {@link EmployeeDTO}.
 */
@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDTO, Employee> {
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
}
