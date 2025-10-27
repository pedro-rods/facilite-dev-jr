package co.facilite.devjr.service.mapper;

import static co.facilite.devjr.domain.EmployeeAsserts.assertEmployeeAllPropertiesEquals;
import static co.facilite.devjr.domain.EmployeeTestSamples.getEmployeeSample1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class EmployeeMapperTest {

	@Autowired
	private EmployeeMapper employeeMapper;

	@Test
	void shouldConvertToDtoAndBack() {
		var expected = getEmployeeSample1();
		var actual = employeeMapper.toEntity(employeeMapper.toDto(expected));
		assertEmployeeAllPropertiesEquals(expected, actual);
	}
}
