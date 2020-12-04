/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.v2d.geometry.envelope;

import ch.obermuhlner.math.big.BigRational;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;

/**
 *
 * @author geoagdt
 */
public class V2D_EnvelopeTest {

    V2D_Environment env;

    public V2D_EnvelopeTest() {
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
     * A master controller for all tests.
     */
    @Test
    public void testAll() {
        testIsIntersectedBy();
    }

    /**
     * Test of getIntersectsFailFast method, of class V2D_Envelope.
     */
    //@Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        boolean expResult;
        boolean result;
        BigRational ONE = BigRational.ONE;
        BigRational TEN = BigRational.TEN;
        V2D_Point a;
        V2D_Point b = new V2D_Point(ONE, ONE);
        V2D_Envelope be = b.getEnvelope();
        V2D_Envelope abe;
        BigRational aX = ONE;
        BigRational aY = ONE;
        // Test 1
        for (int i = 0; i < 1000; i++) {
            aX = aX.divide(TEN);
            aY = aY.divide(TEN);
            a = new V2D_Point(aX, aY);
            //System.out.println("a " + a.toString());
            abe = new V2D_Envelope(a, b);
            //System.out.println("abe " + abe.toString());
            expResult = true;
            result = abe.isIntersectedBy(a.getEnvelope());
            //System.out.println("abe.IsIntersectedBy(a.getEnvelope()) " + result);
            Assertions.assertEquals(expResult, result);
            result = abe.isIntersectedBy(be);
            //System.out.println("be " + be.toString());
            //System.out.println("abe.IsIntersectedBy(be) " + result);
            Assertions.assertEquals(expResult, result);
        }
    }

    /**
     * Test of toString method, of class V2D_Envelope.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V2D_Envelope instance = new V2D_Envelope(BigRational.ZERO, BigRational.ONE, BigRational.ZERO, BigRational.ONE); ;
        String expResult = "V2D_Envelope(xMin=0, xMax=1, yMin=0, yMax=1)";
        String result = instance.toString();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of union method, of class V2D_Envelope.
     */
    @Test
    public void testUnion() {
        System.out.println("union");
        BigRational x0 = BigRational.ZERO;
        BigRational x1 = BigRational.ONE;
        BigRational x2 = BigRational.TWO;
        BigRational x3 = BigRational.valueOf(3);
        BigRational y0 = BigRational.ZERO;
        BigRational y1 = BigRational.ONE;
        BigRational y2 = BigRational.TWO;
        BigRational y3 = BigRational.valueOf(3);
        V2D_Envelope e = new V2D_Envelope(x1, x2, y1, y2);
        V2D_Envelope instance = new V2D_Envelope(x1, x2, y1, y2);
        V2D_Envelope expResult = new V2D_Envelope(x1, x2, y1, y2);
        V2D_Envelope result = instance.union(e);
        Assertions.assertEquals(expResult, result);
        // Test 2
        e = new V2D_Envelope(x1, x2, y1, y2);
        instance = new V2D_Envelope(x0, x1, y1, y2);
        expResult = new V2D_Envelope(x0, x2, y1, y2);
        result = instance.union(e);
        Assertions.assertEquals(expResult, result);
    }

//    /**
//     * Test of isIntersectedBy method, of class V2D_Envelope.
//     */
//    @Test
//    public void testIsIntersectedBy_V2D_Envelope() {
//        System.out.println("isIntersectedBy");
//        V2D_Envelope e = null;
//        V2D_Envelope instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(e);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isContainedBy method, of class V2D_Envelope.
//     */
//    @Test
//    public void testIsContainedBy() {
//        System.out.println("isContainedBy");
//        V2D_Envelope e = null;
//        V2D_Envelope instance = null;
//        boolean expResult = false;
//        boolean result = instance.isContainedBy(e);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V2D_Envelope.
//     */
//    @Test
//    public void testIsIntersectedBy_V2D_Point() {
//        System.out.println("isIntersectedBy");
//        V2D_Point p = null;
//        V2D_Envelope instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(p);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V2D_Envelope.
//     */
//    @Test
//    public void testIsIntersectedBy_BigRational_BigRational() {
//        System.out.println("isIntersectedBy");
//        BigRational x = null;
//        BigRational y = null;
//        V2D_Envelope instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(x, y);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isIntersectedBy method, of class V2D_Envelope.
//     */
//    @Test
//    public void testIsIntersectedBy_V2D_LineSegment() {
//        System.out.println("isIntersectedBy");
//        V2D_LineSegment l = null;
//        V2D_Envelope instance = null;
//        boolean expResult = false;
//        boolean result = instance.isIntersectedBy(l);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersection method, of class V2D_Envelope.
//     */
//    @Test
//    public void testGetIntersection() {
//        System.out.println("getIntersection");
//        V2D_Envelope en = null;
//        V2D_Envelope instance = null;
//        V2D_Envelope expResult = null;
//        V2D_Envelope result = instance.getIntersection(en);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEnvelope method, of class V2D_Envelope.
//     */
//    @Test
//    public void testGetEnvelope() {
//        System.out.println("getEnvelope");
//        V2D_Envelope instance = null;
//        V2D_Envelope expResult = null;
//        V2D_Envelope result = instance.getEnvelope();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of equals method, of class V2D_Envelope.
//     */
//    @Test
//    public void testEquals() {
//        System.out.println("equals");
//        Object o = null;
//        V2D_Envelope instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of hashCode method, of class V2D_Envelope.
//     */
//    @Test
//    public void testHashCode() {
//        System.out.println("hashCode");
//        V2D_Envelope instance = null;
//        int expResult = 0;
//        int result = instance.hashCode();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getxMin method, of class V2D_Envelope.
//     */
//    @Test
//    public void testGetxMin() {
//        System.out.println("getxMin");
//        V2D_Envelope instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getxMin();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getxMax method, of class V2D_Envelope.
//     */
//    @Test
//    public void testGetxMax() {
//        System.out.println("getxMax");
//        V2D_Envelope instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getxMax();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getyMin method, of class V2D_Envelope.
//     */
//    @Test
//    public void testGetyMin() {
//        System.out.println("getyMin");
//        V2D_Envelope instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getyMin();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getyMax method, of class V2D_Envelope.
//     */
//    @Test
//    public void testGetyMax() {
//        System.out.println("getyMax");
//        V2D_Envelope instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getyMax();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}
