package co.facilite.devjr.domain;

import static co.facilite.devjr.domain.AddressTestSamples.*;
import static co.facilite.devjr.domain.DepartmentTestSamples.*;
import static co.facilite.devjr.domain.EmployeeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import co.facilite.devjr.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EmployeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = getEmployeeSample1();
        Employee employee2 = new Employee();
        assertThat(employee1).isNotEqualTo(employee2);

        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);

        employee2 = getEmployeeSample2();
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    void addressTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Address addressBack = getAddressRandomSampleGenerator();

        employee.setAddress(addressBack);
        assertThat(employee.getAddress()).isEqualTo(addressBack);

        employee.address(null);
        assertThat(employee.getAddress()).isNull();
    }

    @Test
    void departmentTest() {
        Employee employee = getEmployeeRandomSampleGenerator();
        Department departmentBack = getDepartmentRandomSampleGenerator();

        employee.setDepartment(departmentBack);
        assertThat(employee.getDepartment()).isEqualTo(departmentBack);

        employee.department(null);
        assertThat(employee.getDepartment()).isNull();
    }
}
