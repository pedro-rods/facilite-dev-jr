package co.facilite.devjr.service.mapper;

import co.facilite.devjr.domain.Department;
import co.facilite.devjr.service.dto.DepartmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Department} and its DTO {@link DepartmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface DepartmentMapper extends EntityMapper<DepartmentDTO, Department> {
	
    @Override
    Department toEntity(DepartmentDTO dto);

    @Override
    DepartmentDTO toDto(Department entity);
}
