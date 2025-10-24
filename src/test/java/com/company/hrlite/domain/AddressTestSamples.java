package com.company.hrlite.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AddressTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Address getAddressSample1() {
        return new Address()
            .id(1L)
            .cep("cep1")
            .street("street1")
            .number("number1")
            .complement("complement1")
            .district("district1")
            .city("city1");
    }

    public static Address getAddressSample2() {
        return new Address()
            .id(2L)
            .cep("cep2")
            .street("street2")
            .number("number2")
            .complement("complement2")
            .district("district2")
            .city("city2");
    }

    public static Address getAddressRandomSampleGenerator() {
        return new Address()
            .id(longCount.incrementAndGet())
            .cep(UUID.randomUUID().toString())
            .street(UUID.randomUUID().toString())
            .number(UUID.randomUUID().toString())
            .complement(UUID.randomUUID().toString())
            .district(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString());
    }
}
