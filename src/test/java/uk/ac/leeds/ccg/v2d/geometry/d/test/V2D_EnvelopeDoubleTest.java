/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v2d.geometry.d.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_EnvelopeDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_VectorDouble;

/**
 * Test class for V2D_EnvelopeDouble.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_EnvelopeDoubleTest extends V2D_TestDouble {

    public V2D_EnvelopeDoubleTest() {
        super();
    }

    @BeforeAll
    public static void setUpClass() {
    }

    @AfterAll
    public static void tearDownClass() {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of toString method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP0P0);
        String expResult = "V2D_EnvelopeDouble(xMin=0.0, xMax=0.0, yMin=0.0,"
                + " yMax=0.0)";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(result.equalsIgnoreCase(expResult));
    }

    /**
     * Test of envelop method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testEnvelope() {
        System.out.println("envelope");
        V2D_EnvelopeDouble e1 = new V2D_EnvelopeDouble(pP0P0);
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP1P1);
        V2D_EnvelopeDouble expResult = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        V2D_EnvelopeDouble result = instance.union(e1);
        assertTrue(result.equals(expResult));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testIsIntersectedBy_V2D_EnvelopeDouble() {
        System.out.println("isIntersectedBy");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP0P0);
        V2D_EnvelopeDouble en = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        assertTrue(instance.isIntersectedBy(en));
        // Test 2
        instance = new V2D_EnvelopeDouble(pN1N1, pP0P0);
        assertTrue(instance.isIntersectedBy(en));
        // Test 3
        en = new V2D_EnvelopeDouble(pN2N2, pP2P2);
        assertTrue(instance.isIntersectedBy(en));
        // Test 4
        en = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        instance = new V2D_EnvelopeDouble(pN1N1, pN1P0);
        assertFalse(instance.isIntersectedBy(en));
        // Test 5
        en = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        instance = new V2D_EnvelopeDouble(pN1P0, pP0P1);
        assertTrue(instance.isIntersectedBy(en));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testIsIntersectedBy_V2D_PointDouble() {
        System.out.println("isIntersectedBy");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pN1N1, pP1P1);
        // Test 1 the centre
        assertTrue(instance.isIntersectedBy(pP0P0));
        // Test 2 to 6 the corners
        // Test 2
        assertTrue(instance.isIntersectedBy(pP1P1));
        // Test 3
        assertTrue(instance.isIntersectedBy(pN1N1));
        // Test 4
        assertTrue(instance.isIntersectedBy(pN1P1));
        // Test 6
        assertTrue(instance.isIntersectedBy(pP1N1));
    }

    /**
     * Test of equals method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        V2D_EnvelopeDouble en = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        assertTrue(instance.equals(en));
        // Test 2
        en = new V2D_EnvelopeDouble(pP1P1, pP0P0);
        assertTrue(instance.equals(en));
        // Test 3
        en = new V2D_EnvelopeDouble(pP1N1, pP0P0);
        assertFalse(instance.equals(en));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_EnvelopeDouble. No need for a full
     * set of test here as this is covered by
     * {@link #testIsIntersectedBy_V2D_PointDouble()}
     */
    @Test
    public void testIsIntersectedBy_2args() {
        System.out.println("isIntersectedBy");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pN1N1, pP1P1);
        assertTrue(instance.isIntersectedBy(0d, 0d));
        instance = new V2D_EnvelopeDouble(pP1P0);
        assertFalse(instance.isIntersectedBy(0d, 0d));
    }

    /**
     * Test of getIntersection method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testGetIntersection_V2D_EnvelopeDouble() {
        System.out.println("getIntersection");
        V2D_EnvelopeDouble en;
        V2D_EnvelopeDouble instance;
        V2D_EnvelopeDouble expResult;
        V2D_EnvelopeDouble result;
        // Test 1
        en = new V2D_EnvelopeDouble(pN1N1, pP1P1);
        instance = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        expResult = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        result = instance.getIntersection(en);
        assertTrue(result.equals(expResult));
        // Test 2
        en = new V2D_EnvelopeDouble(pN1N1, pP0P0);
        instance = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        expResult = new V2D_EnvelopeDouble(pP0P0);
        result = instance.getIntersection(en);
        assertTrue(result.equals(expResult));
        // Test 3
        en = new V2D_EnvelopeDouble(pN1N1, pP0P0);
        instance = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        expResult = new V2D_EnvelopeDouble(pP0P0);
        result = instance.getIntersection(en);
        assertTrue(result.equals(expResult));
        // Test 4
        en = new V2D_EnvelopeDouble(pN1N1, pP0P1);
        instance = new V2D_EnvelopeDouble(pP0N1, pP1P1);
        expResult = new V2D_EnvelopeDouble(pP0N1, pP0P1);
        result = instance.getIntersection(en);
        assertTrue(result.equals(expResult));
    }

    /**
     * Test of union method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        V2D_EnvelopeDouble en = new V2D_EnvelopeDouble(pN2N2, pP1P1);
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pN1N1, pP2P2);
        V2D_EnvelopeDouble expResult = new V2D_EnvelopeDouble(pN2N2, pP2P2);
        V2D_EnvelopeDouble result = instance.union(en);
        assertTrue(result.equals(expResult));
    }

    /**
     * Test of isContainedBy method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testIsContainedBy() {
        System.out.println("isContainedBy");
        V2D_EnvelopeDouble en = new V2D_EnvelopeDouble(pN2N2, pP2P2);
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pN1N1, pP1P1);
        assertTrue(instance.isContainedBy(en));
        // Test 2
        instance = new V2D_EnvelopeDouble(pN2N2, pP2P2);
        assertTrue(instance.isContainedBy(en));
        // Test 3
        en = new V2D_EnvelopeDouble(pN1N1, pP2P2);
        assertFalse(instance.isContainedBy(en));
    }

    /**
     * Test of getXMin method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testGetXMin() {
        System.out.println("getxMin");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP0N1, pP0N1, pN2N2);
        double expResult = -2d;
        double result = instance.getXMin();
        assertTrue(expResult == result);
    }

    /**
     * Test of getXMax method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testGetXMax() {
        System.out.println("getxMax");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP0N1, pP0P0);
        double expResult = 0d;
        double result = instance.getXMax();
        assertTrue(expResult == result);
    }

    /**
     * Test of getYMin method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testGetYMin() {
        System.out.println("getyMin");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP0N1, pP0P0, pN2N2);
        double expResult = -2d;
        double result = instance.getYMin();
        assertTrue(expResult == result);
    }

    /**
     * Test of getYMax method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testGetYMax() {
        System.out.println("getyMax");
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP0N1, pP0N1, pN2N2);
        double expResult = -1d;
        double result = instance.getYMax();
        assertTrue(expResult == result);
    }

    /**
     * Test of translate method, of class V2D_EnvelopeDouble.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        V2D_VectorDouble v = P1P1;
        V2D_EnvelopeDouble instance = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        V2D_EnvelopeDouble expResult = new V2D_EnvelopeDouble(pP1P1, pP2P2);
        instance.translate(v);
        assertTrue(expResult.equals(instance));
        // Test 2
        v = N1N1;
        instance = new V2D_EnvelopeDouble(pP0P0, pP1P1);
        expResult = new V2D_EnvelopeDouble(pN1N1, pP0P0);
        instance.translate(v);
        assertTrue(expResult.equals(instance));
    }
}
