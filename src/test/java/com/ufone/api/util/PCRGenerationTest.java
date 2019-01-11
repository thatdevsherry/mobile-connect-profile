package com.ufone.api.authorization_code;

import com.ufone.api.util.PCRGeneration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class PCRGenerationTest {
        @Test
        public void testPCRLength() {
                PCRGeneration testClass = new PCRGeneration();
                String pcr = testClass.generatePCR();
                assertEquals(pcr.length(), 36);
        }
}
