package com.yg0r2.pet.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class DummyWebTest {

    @Test
    void test() {
        System.out.println("HERE: " + getClass().getName());
        Assertions.assertTrue(true);
    }

}
