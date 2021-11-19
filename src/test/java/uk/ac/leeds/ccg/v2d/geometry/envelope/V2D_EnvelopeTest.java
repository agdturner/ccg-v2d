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
import uk.ac.leeds.ccg.math.number.Math_BigRational;
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
        //testIsIntersectedBy();
        //testToString();
        //testUnion();
        //testIsIntersectedBy_V2D_Envelope();
        //testIsContainedBy();
        //testIsIntersectedBy_V2D_Point();
        //testIsIntersectedBy_V2D_Point();
        //testIsIntersectedBy_Math_BigRational_Math_BigRational();
        //testIsIntersectedBy_V2D_LineSegment();
        //testGetIntersection();
        //testGetEnvelope();
        //testEquals();
        //testGetxMin();
        //testGetxMax();
        //testGetyMin();
        //testGetyMax();
    }

    /**
     * Test of toString method, of class V2D_Envelope.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V2D_Envelope instance = new V2D_Envelope(Math_BigRational.ZERO, Math_BigRational.ONE, Math_BigRational.ZERO, Math_BigRational.ONE);;
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
        Math_BigRational x0 = Math_BigRational.ZERO;
        Math_BigRational x1 = Math_BigRational.ONE;
        Math_BigRational x2 = Math_BigRational.TWO;
        Math_BigRational x3 = Math_BigRational.valueOf(3);
        Math_BigRational y0 = Math_BigRational.ZERO;
        Math_BigRational y1 = Math_BigRational.ONE;
        Math_BigRational y2 = Math_BigRational.TWO;
        Math_BigRational y3 = Math_BigRational.valueOf(3);
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

    /**
     * Test of isIntersectedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V2D_Envelope() {
        System.out.println("isIntersectedBy");
        Math_BigRational x0 = Math_BigRational.ZERO;
        Math_BigRational x1 = Math_BigRational.ONE;
        Math_BigRational x2 = Math_BigRational.TWO;
        Math_BigRational x3 = Math_BigRational.valueOf(3);
        Math_BigRational y0 = Math_BigRational.ZERO;
        Math_BigRational y1 = Math_BigRational.ONE;
        Math_BigRational y2 = Math_BigRational.TWO;
        Math_BigRational y3 = Math_BigRational.valueOf(3);
        V2D_Envelope e = new V2D_Envelope(x1, x2, y1, y2);
        V2D_Envelope instance = new V2D_Envelope(x1, x2, y1, y2);
        boolean result = instance.isIntersectedBy(e);
        Assertions.assertTrue(result);
        // Test 2
        e = new V2D_Envelope(x0, x1, y0, y1);
        instance = new V2D_Envelope(x1, x2, y1, y2);
        result = instance.isIntersectedBy(e);
        Assertions.assertTrue(result);
        // Test 3
        e = new V2D_Envelope(x0, x1, y0, y1);
        instance = new V2D_Envelope(x2, x3, y2, y3);
        result = instance.isIntersectedBy(e);
        Assertions.assertFalse(result);
        System.out.println("isIntersectedBy");
        // Test 4
        boolean expResult;
        Math_BigRational ONE = Math_BigRational.ONE;
        Math_BigRational TEN = Math_BigRational.TEN;
        V2D_Point a;
        V2D_Point b = new V2D_Point(ONE, ONE);
        V2D_Envelope be = b.getEnvelope();
        V2D_Envelope abe;
        Math_BigRational aX = ONE;
        Math_BigRational aY = ONE;
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
     * Test of isContainedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsContainedBy() {
        System.out.println("isContainedBy");
        Math_BigRational x0 = Math_BigRational.ZERO;
        Math_BigRational x1 = Math_BigRational.ONE;
        Math_BigRational x2 = Math_BigRational.TWO;
        Math_BigRational x3 = Math_BigRational.valueOf(3);
        Math_BigRational y0 = Math_BigRational.ZERO;
        Math_BigRational y1 = Math_BigRational.ONE;
        Math_BigRational y2 = Math_BigRational.TWO;
        Math_BigRational y3 = Math_BigRational.valueOf(3);
        V2D_Envelope e = new V2D_Envelope(x0, x3, y0, y3);
        V2D_Envelope instance = new V2D_Envelope(x1, x2, y1, y2);
        boolean result = instance.isContainedBy(e);
        Assertions.assertTrue(result);
        // Test 2
        e = new V2D_Envelope(x1, x2, y1, y2);
        instance = new V2D_Envelope(x0, x3, y0, y3);
        result = instance.isContainedBy(e);
        Assertions.assertFalse(result);
        // Test 3
        e = new V2D_Envelope(x0, x3, y0, y3);
        instance = new V2D_Envelope(x1, x2, y1, Math_BigRational.valueOf(4));
        result = instance.isContainedBy(e);
        Assertions.assertFalse(result);
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point() {
        System.out.println("isIntersectedBy");
        Math_BigRational x0 = Math_BigRational.ZERO;
        Math_BigRational x1 = Math_BigRational.ONE;
        Math_BigRational x2 = Math_BigRational.TWO;
        Math_BigRational x3 = Math_BigRational.valueOf(3);
        Math_BigRational y0 = Math_BigRational.ZERO;
        Math_BigRational y1 = Math_BigRational.ONE;
        Math_BigRational y2 = Math_BigRational.TWO;
        Math_BigRational y3 = Math_BigRational.valueOf(3);
        V2D_Point p = new V2D_Point(x0, y0);
        V2D_Envelope instance = new V2D_Envelope(x0, x1, y0, y1);
        Assertions.assertTrue(instance.isIntersectedBy(p));
        // Test 2
        p = new V2D_Point(x1, y0);
        Assertions.assertTrue(instance.isIntersectedBy(p));
        // Test 3
        p = new V2D_Point(x0, y1);
        Assertions.assertTrue(instance.isIntersectedBy(p));
        // Test 3
        p = new V2D_Point(x1, y1);
        Assertions.assertTrue(instance.isIntersectedBy(p));
        // Test 4
        p = new V2D_Point(x3, y1);
        Assertions.assertFalse(instance.isIntersectedBy(p));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_Math_BigRational_Math_BigRational() {
        System.out.println("isIntersectedBy");
        Math_BigRational x0 = Math_BigRational.ZERO;
        Math_BigRational x1 = Math_BigRational.ONE;
        Math_BigRational x2 = Math_BigRational.TWO;
        Math_BigRational x3 = Math_BigRational.valueOf(3);
        Math_BigRational y0 = Math_BigRational.ZERO;
        Math_BigRational y1 = Math_BigRational.ONE;
        Math_BigRational y2 = Math_BigRational.TWO;
        Math_BigRational y3 = Math_BigRational.valueOf(3);
        V2D_Envelope instance = new V2D_Envelope(x0, x1, y0, y1);
        Assertions.assertTrue(instance.isIntersectedBy(x0, y0));
        // Test 2
        Assertions.assertTrue(instance.isIntersectedBy(x1, y0));
        // Test 3
        Assertions.assertTrue(instance.isIntersectedBy(x0, y1));
        // Test 3
        Assertions.assertTrue(instance.isIntersectedBy(x1, y1));
        // Test 4
        Assertions.assertFalse(instance.isIntersectedBy(x3, y1));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V2D_LineSegment() {
        System.out.println("isIntersectedBy");
        Math_BigRational x0 = Math_BigRational.ZERO;
        Math_BigRational x1 = Math_BigRational.ONE;
        Math_BigRational x2 = Math_BigRational.TWO;
        Math_BigRational x3 = Math_BigRational.valueOf(3);
        Math_BigRational y0 = Math_BigRational.ZERO;
        Math_BigRational y1 = Math_BigRational.ONE;
        Math_BigRational y2 = Math_BigRational.TWO;
        Math_BigRational y3 = Math_BigRational.valueOf(3);
        V2D_Envelope instance = new V2D_Envelope(x0, x2, y0, y2);
        V2D_Point p0 = new V2D_Point(x0, y0);
        V2D_Point p1 = new V2D_Point(x0, y1);
        V2D_LineSegment l = new V2D_LineSegment(p0, p1);
        Assertions.assertTrue(instance.isIntersectedBy(l));
        // Test 2
        p0 = new V2D_Point(x3, y3);
        p1 = new V2D_Point(x3, y2);
        l = new V2D_LineSegment(p0, p1);
        Assertions.assertFalse(instance.isIntersectedBy(l, true));
        // Test 3
        p0 = new V2D_Point(x3, y3);
        p1 = new V2D_Point(x2, y2);
        l = new V2D_LineSegment(p0, p1);
        Assertions.assertTrue(instance.isIntersectedBy(l, true));
        // Test 3
        p0 = new V2D_Point(x3, y3);
        p1 = new V2D_Point(x0, y3);
        l = new V2D_LineSegment(p0, p1);
        Assertions.assertFalse(instance.isIntersectedBy(l, true));
        // Test 4
        p0 = new V2D_Point(x3, y3);
        p1 = new V2D_Point(x0, y2);
        l = new V2D_LineSegment(p0, p1);
        Assertions.assertTrue(instance.isIntersectedBy(l, true));
    }

    /**
     * Test of getIntersection method, of class V2D_Envelope.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        Math_BigRational x0 = Math_BigRational.ZERO;
        Math_BigRational x1 = Math_BigRational.ONE;
        Math_BigRational x2 = Math_BigRational.TWO;
        Math_BigRational x3 = Math_BigRational.valueOf(3);
        Math_BigRational y0 = Math_BigRational.ZERO;
        Math_BigRational y1 = Math_BigRational.ONE;
        Math_BigRational y2 = Math_BigRational.TWO;
        Math_BigRational y3 = Math_BigRational.valueOf(3);
        V2D_Envelope en = new V2D_Envelope(x0, x2, y0, y2);
        V2D_Envelope instance = new V2D_Envelope(x0, x1, y0, y1);
        V2D_Envelope expResult = new V2D_Envelope(x0, x1, y0, y1);
        V2D_Envelope result = instance.getIntersection(en);
        Assertions.assertEquals(expResult, result);
        // Test 2
        en = new V2D_Envelope(x0, x1, y0, y1);
        instance = new V2D_Envelope(x0, x1, y0, y1);
        expResult = new V2D_Envelope(x0, x1, y0, y1);
        result = instance.getIntersection(en);
        Assertions.assertEquals(expResult, result);
        // Test 3
        en = new V2D_Envelope(x0, x1, y0, y1);
        instance = new V2D_Envelope(x0, x2, y0, y2);
        expResult = new V2D_Envelope(x0, x1, y0, y1);
        result = instance.getIntersection(en);
        Assertions.assertEquals(expResult, result);
        // Test 4
        en = new V2D_Envelope(x0, x3, y0, y3);
        instance = new V2D_Envelope(x0, x2, y0, y2);
        expResult = new V2D_Envelope(x0, x2, y0, y2);
        result = instance.getIntersection(en);
        Assertions.assertEquals(expResult, result);
        // Test 5
        en = new V2D_Envelope(x0, x2, y0, y2);
        instance = new V2D_Envelope(x0, x1, y1, y3);
        expResult = new V2D_Envelope(x0, x1, y1, y2);
        result = instance.getIntersection(en);
        Assertions.assertEquals(expResult, result);
        // Test 6
        en = new V2D_Envelope(x0, x2, y0, y3);
        instance = new V2D_Envelope(x0, x1, y1, y3);
        expResult = new V2D_Envelope(x0, x1, y1, y3);
        result = instance.getIntersection(en);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getEnvelope method, of class V2D_Envelope.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        Math_BigRational z = Math_BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(z, z, z, z);
        V2D_Envelope expResult = new V2D_Envelope(z, z, z, z);
        V2D_Envelope result = instance.getEnvelope();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of equals method, of class V2D_Envelope.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Math_BigRational z = Math_BigRational.ZERO;
        Object o = new V2D_Envelope(z, z, z, z);
        V2D_Envelope instance = new V2D_Envelope(z, z, z, z);
        Assertions.assertTrue(instance.equals(o));
        // Test 2
        instance = new V2D_Envelope(z, z, z, Math_BigRational.ONE);
        Assertions.assertFalse(instance.equals(o));
    }

    /**
     * Test of hashCode method, of class V2D_Envelope.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        // Intentionally not tested.
    }

    /**
     * Test of getxMin method, of class V2D_Envelope.
     */
    @Test
    public void testGetxMin() {
        System.out.println("getxMin");
        Math_BigRational z = Math_BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(z, z, z, z);
        Math_BigRational expResult = z;
        Math_BigRational result = instance.getxMin();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getxMax method, of class V2D_Envelope.
     */
    @Test
    public void testGetxMax() {
        System.out.println("getxMax");
        Math_BigRational z = Math_BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(z, z, z, z);
        Math_BigRational expResult = z;
        Math_BigRational result = instance.getxMax();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getyMin method, of class V2D_Envelope.
     */
    @Test
    public void testGetyMin() {
        System.out.println("getyMin");
        Math_BigRational z = Math_BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(z, z, z, z);
        Math_BigRational expResult = z;
        Math_BigRational result = instance.getyMin();
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getyMax method, of class V2D_Envelope.
     */
    @Test
    public void testGetyMax() {
        System.out.println("getyMax");
        Math_BigRational z = Math_BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(z, z, z, z);
        Math_BigRational expResult = z;
        Math_BigRational result = instance.getyMax();
    }
}
