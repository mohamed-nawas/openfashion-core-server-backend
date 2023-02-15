package com.computicsolutions.openfashion;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * This class tests {@link OpenFashionCoreApplication} class
 */
@SpringBootTest
public class OpenFashionCoreApplicationTests {

    /**
     * This method tests Spring application main run method
     */
    @Test
    public void Should_RunSpringApplication() {
        OpenFashionCoreApplication.main(new String[]{});

        assertTrue(true, "Spring Application Context Loaded Successfully");
    }
}
