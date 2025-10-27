package co.facilite.devjr.service.mapper;

import static co.facilite.devjr.domain.DepartmentAsserts.assertDepartmentAllPropertiesEquals;
import static co.facilite.devjr.domain.DepartmentTestSamples.getDepartmentSample1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class DepartmentMapperTest {

	@Autowired
	private DepartmentMapper departmentMapper;

	@Test
	void shouldConvertToDtoAndBack() {
		var expected = getDepartmentSample1();
		var actual = departmentMapper.toEntity(departmentMapper.toDto(expected));
		assertDepartmentAllPropertiesEquals(expected, actual);
	}
}
