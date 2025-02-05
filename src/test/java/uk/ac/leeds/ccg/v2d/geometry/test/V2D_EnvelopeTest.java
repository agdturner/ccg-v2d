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
package uk.ac.leeds.ccg.v2d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Envelope;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 *
 * @author geoagdt
 */
public class V2D_EnvelopeTest {

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
     * Test of toString method, of class V2D_Envelope.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -6;
        V2D_Envelope instance = new V2D_Envelope(oom, BigRational.ZERO, BigRational.ONE, BigRational.ZERO, BigRational.ONE);;
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
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational x0 = BigRational.ZERO;
        BigRational x1 = BigRational.ONE;
        BigRational x2 = BigRational.TWO;
        BigRational x3 = BigRational.valueOf(3);
        BigRational y0 = BigRational.ZERO;
        BigRational y1 = BigRational.ONE;
        BigRational y2 = BigRational.TWO;
        BigRational y3 = BigRational.valueOf(3);
        V2D_Envelope e = new V2D_Envelope(oom, x1, x2, y1, y2);
        V2D_Envelope instance = new V2D_Envelope(oom, x1, x2, y1, y2);
        V2D_Envelope expResult = new V2D_Envelope(oom, x1, x2, y1, y2);
        V2D_Envelope result = instance.union(e, oom);
        Assertions.assertTrue(expResult.equals(result, oom));
        // Test 2
        e = new V2D_Envelope(oom, x1, x2, y1, y2);
        instance = new V2D_Envelope(oom, x0, x1, y1, y2);
        expResult = new V2D_Envelope(oom, x0, x2, y1, y2);
        result = instance.union(e, oom);
        Assertions.assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V2D_Envelope() {
        System.out.println("isIntersectedBy");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational x0 = BigRational.ZERO;
        BigRational x1 = BigRational.ONE;
        BigRational x2 = BigRational.TWO;
        BigRational x3 = BigRational.valueOf(3);
        BigRational y0 = BigRational.ZERO;
        BigRational y1 = BigRational.ONE;
        BigRational y2 = BigRational.TWO;
        BigRational y3 = BigRational.valueOf(3);
        V2D_Envelope e = new V2D_Envelope(oom, x1, x2, y1, y2);
        V2D_Envelope instance = new V2D_Envelope(oom, x1, x2, y1, y2);
        Assertions.assertTrue(instance.isIntersectedBy(e, oom));
        // Test 2
        e = new V2D_Envelope(oom, x0, x1, y0, y1);
        instance = new V2D_Envelope(oom, x1, x2, y1, y2);
        Assertions.assertTrue(instance.isIntersectedBy(e, oom));
        // Test 3
        e = new V2D_Envelope(oom, x0, x1, y0, y1);
        instance = new V2D_Envelope(oom, x2, x3, y2, y3);
        Assertions.assertFalse(instance.isIntersectedBy(e, oom));
        System.out.println("isIntersectedBy");
        // Test 4
        BigRational ONE = BigRational.ONE;
        BigRational TEN = BigRational.TEN;
        V2D_Point a;
        V2D_Point b = new V2D_Point(ONE, ONE);
        V2D_Envelope be = b.getEnvelope(oom, rm);
        V2D_Envelope abe;
        BigRational aX = ONE;
        BigRational aY = ONE;
        // Test 1
        for (int i = 0; i < 1000; i++) {
            aX = aX.divide(TEN);
            aY = aY.divide(TEN);
            a = new V2D_Point(aX, aY);
            //System.out.println("a " + a.toString());
            abe = new V2D_Envelope(oom, a, b);
            //System.out.println("abe " + abe.toString());
            Assertions.assertTrue(abe.isIntersectedBy(a.getEnvelope(oom, rm), oom));
            Assertions.assertTrue(abe.isIntersectedBy(be, oom));
        }
    }

    /**
     * Test of isContainedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsContainedBy() {
        System.out.println("isContainedBy");
        int oom = -6;
        BigRational x0 = BigRational.ZERO;
        BigRational x1 = BigRational.ONE;
        BigRational x2 = BigRational.TWO;
        BigRational x3 = BigRational.valueOf(3);
        BigRational y0 = BigRational.ZERO;
        BigRational y1 = BigRational.ONE;
        BigRational y2 = BigRational.TWO;
        BigRational y3 = BigRational.valueOf(3);
        V2D_Envelope e = new V2D_Envelope(oom, x0, x3, y0, y3);
        V2D_Envelope instance = new V2D_Envelope(oom, x1, x2, y1, y2);
        boolean result = instance.isContainedBy(e, oom);
        Assertions.assertTrue(result);
        // Test 2
        e = new V2D_Envelope(oom, x1, x2, y1, y2);
        instance = new V2D_Envelope(oom, x0, x3, y0, y3);
        result = instance.isContainedBy(e, oom);
        Assertions.assertFalse(result);
        // Test 3
        e = new V2D_Envelope(oom, x0, x3, y0, y3);
        instance = new V2D_Envelope(oom, x1, x2, y1, BigRational.valueOf(4));
        result = instance.isContainedBy(e, oom);
        Assertions.assertFalse(result);
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational x0 = BigRational.ZERO;
        BigRational x1 = BigRational.ONE;
        BigRational x2 = BigRational.TWO;
        BigRational x3 = BigRational.valueOf(3);
        BigRational y0 = BigRational.ZERO;
        BigRational y1 = BigRational.ONE;
        BigRational y2 = BigRational.TWO;
        BigRational y3 = BigRational.valueOf(3);
        V2D_Point p = new V2D_Point(x0, y0);
        V2D_Envelope instance = new V2D_Envelope(oom, x0, x1, y0, y1);
        Assertions.assertTrue(instance.isIntersectedBy(p, oom, rm));
        // Test 2
        p = new V2D_Point(x1, y0);
        Assertions.assertTrue(instance.isIntersectedBy(p, oom, rm));
        // Test 3
        p = new V2D_Point(x0, y1);
        Assertions.assertTrue(instance.isIntersectedBy(p, oom, rm));
        // Test 3
        p = new V2D_Point(x1, y1);
        Assertions.assertTrue(instance.isIntersectedBy(p, oom, rm));
        // Test 4
        p = new V2D_Point(x3, y1);
        Assertions.assertFalse(instance.isIntersectedBy(p, oom, rm));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_BigRational_BigRational() {
        System.out.println("isIntersectedBy");
        int oom = -6;
        //RoundingMode rm = RoundingMode.HALF_UP;
        BigRational x0 = BigRational.ZERO;
        BigRational x1 = BigRational.ONE;
        BigRational x2 = BigRational.TWO;
        BigRational x3 = BigRational.valueOf(3);
        BigRational y0 = BigRational.ZERO;
        BigRational y1 = BigRational.ONE;
        BigRational y2 = BigRational.TWO;
        BigRational y3 = BigRational.valueOf(3);
        V2D_Envelope instance = new V2D_Envelope(oom, x0, x1, y0, y1);
        Assertions.assertTrue(instance.isIntersectedBy(x0, y0, oom));
        // Test 2
        Assertions.assertTrue(instance.isIntersectedBy(x1, y0, oom));
        // Test 3
        Assertions.assertTrue(instance.isIntersectedBy(x0, y1, oom));
        // Test 3
        Assertions.assertTrue(instance.isIntersectedBy(x1, y1, oom));
        // Test 4
        Assertions.assertFalse(instance.isIntersectedBy(x3, y1, oom));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Envelope.
     */
    @Test
    public void testIsIntersectedBy_V2D_LineSegment() {
        System.out.println("isIntersectedBy");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational x0 = BigRational.ZERO;
        BigRational x1 = BigRational.ONE;
        BigRational x2 = BigRational.TWO;
        BigRational x3 = BigRational.valueOf(3);
        BigRational y0 = BigRational.ZERO;
        BigRational y1 = BigRational.ONE;
        BigRational y2 = BigRational.TWO;
        BigRational y3 = BigRational.valueOf(3);
        V2D_Envelope instance = new V2D_Envelope(oom, x0, x2, y0, y2);
        V2D_Point p0 = new V2D_Point(x0, y0);
        V2D_Point p1 = new V2D_Point(x0, y1);
        V2D_LineSegment l = new V2D_LineSegment(oom, rm, p0, p1);
        Assertions.assertTrue(instance.isIntersectedBy(l, oom, rm));
        // Test 2
        p0 = new V2D_Point(x3, y3);
        p1 = new V2D_Point(x3, y2);
        l = new V2D_LineSegment(p0, p1, oom, rm);
        Assertions.assertFalse(instance.isIntersectedBy(l, oom, rm));
        // Test 3
        p0 = new V2D_Point(x3, y3);
        p1 = new V2D_Point(x2, y2);
        l = new V2D_LineSegment(p0, p1, oom, rm);
        Assertions.assertTrue(instance.isIntersectedBy(l, oom, rm));
        // Test 3
        p0 = new V2D_Point(x3, y3);
        p1 = new V2D_Point(x0, y3);
        l = new V2D_LineSegment(p0, p1, oom, rm);
        Assertions.assertFalse(instance.isIntersectedBy(l, oom, rm));
        // Test 4
        p0 = new V2D_Point(x3, y3);
        p1 = new V2D_Point(x0, y2);
        l = new V2D_LineSegment(p0, p1, oom, rm);
        Assertions.assertTrue(instance.isIntersectedBy(l, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V2D_Envelope.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        int oom = -6;
        //RoundingMode rm = RoundingMode.HALF_UP;
        BigRational x0 = BigRational.ZERO;
        BigRational x1 = BigRational.ONE;
        BigRational x2 = BigRational.TWO;
        BigRational x3 = BigRational.valueOf(3);
        BigRational y0 = BigRational.ZERO;
        BigRational y1 = BigRational.ONE;
        BigRational y2 = BigRational.TWO;
        BigRational y3 = BigRational.valueOf(3);
        V2D_Envelope en = new V2D_Envelope(oom, x0, x2, y0, y2);
        V2D_Envelope instance = new V2D_Envelope(oom, x0, x1, y0, y1);
        V2D_Envelope expResult = new V2D_Envelope(oom, x0, x1, y0, y1);
        V2D_Envelope result = instance.getIntersection(en, oom);
        Assertions.assertTrue(expResult.equals(result, oom));
        // Test 2
        en = new V2D_Envelope(oom, x0, x1, y0, y1);
        instance = new V2D_Envelope(oom, x0, x1, y0, y1);
        expResult = new V2D_Envelope(oom, x0, x1, y0, y1);
        result = instance.getIntersection(en, oom);
        Assertions.assertTrue(expResult.equals(result, oom));
        // Test 3
        en = new V2D_Envelope(oom, x0, x1, y0, y1);
        instance = new V2D_Envelope(oom, x0, x2, y0, y2);
        expResult = new V2D_Envelope(oom, x0, x1, y0, y1);
        result = instance.getIntersection(en, oom);
        Assertions.assertTrue(expResult.equals(result, oom));
        // Test 4
        en = new V2D_Envelope(oom, x0, x3, y0, y3);
        instance = new V2D_Envelope(oom, x0, x2, y0, y2);
        expResult = new V2D_Envelope(oom, x0, x2, y0, y2);
        result = instance.getIntersection(en, oom);
        Assertions.assertTrue(expResult.equals(result, oom));
        // Test 5
        en = new V2D_Envelope(oom, x0, x2, y0, y2);
        instance = new V2D_Envelope(oom, x0, x1, y1, y3);
        expResult = new V2D_Envelope(oom, x0, x1, y1, y2);
        result = instance.getIntersection(en, oom);
        Assertions.assertTrue(expResult.equals(result, oom));
        // Test 6
        en = new V2D_Envelope(oom, x0, x2, y0, y3);
        instance = new V2D_Envelope(oom, x0, x1, y1, y3);
        expResult = new V2D_Envelope(oom, x0, x1, y1, y3);
        result = instance.getIntersection(en, oom);
        Assertions.assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of equals method, of class V2D_Envelope.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        int oom = -6;
        //RoundingMode rm = RoundingMode.HALF_UP;
        BigRational z = BigRational.ZERO;
        V2D_Envelope e = new V2D_Envelope(oom, z, z, z, z);
        V2D_Envelope instance = new V2D_Envelope(oom, z, z, z, z);
        Assertions.assertTrue(instance.equals(e, oom));
        // Test 2
        instance = new V2D_Envelope(oom, z, z, z, BigRational.ONE);
        Assertions.assertFalse(instance.equals(e, oom));
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
        int oom = -6;
        //RoundingMode rm = RoundingMode.HALF_UP;
        System.out.println("getxMin");
        BigRational z = BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(oom, z, z, z, z);
        BigRational expResult = z;
        BigRational result = instance.getXMin(oom);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getxMax method, of class V2D_Envelope.
     */
    @Test
    public void testGetxMax() {
        System.out.println("getxMax");
        int oom = -6;
        //RoundingMode rm = RoundingMode.HALF_UP;
        BigRational z = BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(oom, z, z, z, z);
        BigRational expResult = z;
        BigRational result = instance.getXMax(oom);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getyMin method, of class V2D_Envelope.
     */
    @Test
    public void testGetyMin() {
        System.out.println("getyMin");
        int oom = -6;
        //RoundingMode rm = RoundingMode.HALF_UP;
        BigRational z = BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(oom, z, z, z, z);
        BigRational expResult = z;
        BigRational result = instance.getYMax(oom);
        Assertions.assertEquals(expResult, result);
    }

    /**
     * Test of getyMax method, of class V2D_Envelope.
     */
    @Test
    public void testGetyMax() {
        System.out.println("getyMax");
        int oom = -6;
        //RoundingMode rm = RoundingMode.HALF_UP;
        BigRational z = BigRational.ZERO;
        V2D_Envelope instance = new V2D_Envelope(oom, z, z, z, z);
        BigRational expResult = z;
        BigRational result = instance.getYMax(oom);
    }
}
