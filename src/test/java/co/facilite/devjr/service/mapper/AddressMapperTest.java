package co.facilite.devjr.service.mapper;

import static co.facilite.devjr.domain.AddressAsserts.assertAddressAllPropertiesEquals;
import static co.facilite.devjr.domain.AddressTestSamples.getAddressSample1;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class AddressMapperTest {

	@Autowired
	private AddressMapper addressMapper;

	@Test
	void shouldConvertToDtoAndBack() {
		var expected = getAddressSample1();
		var actual = addressMapper.toEntity(addressMapper.toDto(expected));
		assertAddressAllPropertiesEquals(expected, actual);
	}
}
