package com.company.hrlite.domain;

import static com.company.hrlite.domain.AddressTestSamples.*;
import static com.company.hrlite.domain.EmployeeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.company.hrlite.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AddressTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Address.class);
        Address address1 = getAddressSample1();
        Address address2 = new Address();
        assertThat(address1).isNotEqualTo(address2);

        address2.setId(address1.getId());
        assertThat(address1).isEqualTo(address2);

        address2 = getAddressSample2();
        assertThat(address1).isNotEqualTo(address2);
    }

    @Test
    void employeeTest() {
        Address address = getAddressRandomSampleGenerator();
        Employee employeeBack = getEmployeeRandomSampleGenerator();

        address.setEmployee(employeeBack);
        assertThat(address.getEmployee()).isEqualTo(employeeBack);
        assertThat(employeeBack.getAddress()).isEqualTo(address);

        address.employee(null);
        assertThat(address.getEmployee()).isNull();
        assertThat(employeeBack.getAddress()).isNull();
    }
}
