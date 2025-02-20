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
package uk.ac.leeds.ccg.v2d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.v2d.geometry.d.test.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Envelope;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Geometry;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Line;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;

/**
 * Test of V2D_LineSegment class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_LineSegmentTest extends V2D_Test {

    public V2D_LineSegmentTest() {
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
     * Test of toString method, of class V2D_LineSegment.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        String expResult = """
                           V2D_LineSegment
                           (
                            offset=V2D_Vector(dx=0, dy=0),
                            l= offset=V2D_Vector(dx=0, dy=0),
                            p=V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=0, dy=0)),
                            v= V2D_Vector(dx=1, dy=0)
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of getEnvelope method, of class V2D_LineSegment.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment instance = new V2D_LineSegment(pN1N1, pP1P1, oom, rm);
        V2D_Envelope expResult = new V2D_Envelope(oom, pN1N1, pP1P1);
        V2D_Envelope result = instance.getEnvelope(oom, rm);
        assertTrue(expResult.equals(result, oom));
        // Test 2
        instance = new V2D_LineSegment(pP1N1, pN1P1, oom, rm);
        result = instance.getEnvelope(oom, rm);
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of intersects method, of class V2D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point() {
        System.out.println("intersects");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point p = pP0P0;
        V2D_LineSegment instance = new V2D_LineSegment(pN1N1, pP1P1, oom, rm);
        assertTrue(instance.intersects(p, oom, rm));
        // Test2
        p = pP1P1;
        instance = new V2D_LineSegment(pN1N1, pP1P1, oom, rm);
        assertTrue(instance.intersects(p, oom, rm));
    }

    /**
     * Test of equals method, of class V2D_LineSegment.
     */
    @Test
    public void testEquals_V2D_LineSegment_double() {
        System.out.println("equals");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_LineSegment instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        assertTrue(instance.equals(l, oom, rm));
        // Test 2
        instance = new V2D_LineSegment(pP1P0, pP0P0, oom, rm);
        assertFalse(instance.equals(l, oom, rm));
    }

    /**
     * Test of equalsIgnoreDirection method, of class V2D_LineSegment.
     */
    @Test
    public void testEqualsIgnoreDirection() {
        System.out.println("equalsIgnoreDirection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l = new V2D_LineSegment(pP0P0, pP1P1, oom, rm);
        V2D_LineSegment instance = new V2D_LineSegment(pP0P0, pP2P0, oom, rm);
        assertFalse(instance.equalsIgnoreDirection(l, oom, rm));
        // Test 2
        instance = new V2D_LineSegment(pP1P1, pP0P0, oom, rm);
        assertTrue(instance.equalsIgnoreDirection(l, oom, rm));
        // Test 3
        instance = new V2D_LineSegment(pP0P0, pP1P1, oom, rm);
        assertTrue(instance.equalsIgnoreDirection(l, oom, rm));
    }

    /**
     * Test of multiply method, of class V2D_LineSegment.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector v = V2D_Vector.I;
        V2D_LineSegment instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_LineSegment expResult = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(instance, oom, rm));
        // Test 2
        instance = new V2D_LineSegment(pP0P0, pP0P1, oom, rm);
        expResult = new V2D_LineSegment(pP1P0, pP1P1, oom, rm);
        instance.translate(v, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(instance, oom, rm));
    }

    
    /**
     * Test of getIntersection method, of class V2D_LineSegment.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal(100);
        BigRational Pi = Math_BigRational.getPi(bd, oom - 2, rm);
        BigRational theta = Pi;
        V2D_LineSegment instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_LineSegment expResult = new V2D_LineSegment(pP0P0, pN1P0, oom, rm);
        V2D_LineSegment result = instance.rotate(pP0P0, theta, bd, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
        // Test 2
        theta = Pi.divide(2);
        expResult = new V2D_LineSegment(pP0P0, pP0N1, oom, rm);
        result = instance.rotate(pP0P0, theta, bd, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
        // Test 2
        theta = BigRational.valueOf(3).multiply(Pi.divide(2));
        expResult = new V2D_LineSegment(pP0P0, pP0P1, oom, rm);
        result = instance.rotate(pP0P0, theta, bd, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
    }
    
    /**
     * Test of getIntersection method, of class V2D_LineSegment.
     */
    @Test
    public void testGetIntersection_V2D_Line() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP0P0, pP1P0, oom, rm);
        V2D_LineSegment instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_Geometry expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 2
        instance = new V2D_LineSegment(pP0P0, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 3
        instance = new V2D_LineSegment(pN1N1, pN1P0, oom, rm);
        expResult = pN1P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 4
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        instance = new V2D_LineSegment(pP1N1, pN1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 5
        l = new V2D_Line(pP0P0, pP1P0, oom, rm);
        instance = new V2D_LineSegment(pN1P0, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = new V2D_LineSegment(pN1P0, pP1P0, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 6
        l = new V2D_Line(pN1P0, pN1P1, oom, rm);
        instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertNull(result);
    }

    /**
     * Test of getIntersection method, of class V2D_LineSegment.
     */
    @Test
    public void testGetIntersection_V2D_LineSegment_double() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_LineSegment instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_Geometry expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 2
        instance = new V2D_LineSegment(pP0P0, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 3
        instance = new V2D_LineSegment(pN1N1, pN1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertNull(result);
        // Test 4
        l = new V2D_LineSegment(pN1N1, pP1P1, oom, rm);
        instance = new V2D_LineSegment(pP1N1, pN1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 5
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_LineSegment(pN1P0, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getLength2 method, of class V2D_LineSegment.
     */
    @Test
    public void testGetLength2() {
        System.out.println("getLength2");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getLength2(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        instance = new V2D_LineSegment(pP0P0, pP2P0, oom, rm);
        expResult = BigRational.valueOf(4);
        result = instance.getLength2(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_LineSegment covered by
     * {@link #testGetDistanceSquared_V2D_Point_int()}.
     */
    @Test
    public void testGetDistance_V2D_Point() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l = new V2D_LineSegment(pP0P0, pP2P0, oom, rm);
        V2D_Point instance = pP1P1;
        BigRational expResult = BigRational.ONE;
        BigRational result = l.getDistance(instance, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        instance = pN1N1;
        result = l.getDistance(instance, oom, rm);
        expResult = new Math_BigRationalSqrt(2, oom, rm).getSqrt(oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        instance = pP2P2;
        expResult = BigRational.TWO;
        result = l.getDistance(instance, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 4
        instance = pP2P2;
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        expResult = new Math_BigRationalSqrt(5, oom, rm).getSqrt(oom, rm);
        result = l.getDistance(instance, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_LineSegment.
     */
    @Test
    public void testGetDistanceSquared_V2D_Point() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment instance;
        V2D_Point p;
        BigRational expResult;
        BigRational result;
        // Test 1
        instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        p = pP0P0;
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        p = pP1P0;
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        instance = new V2D_LineSegment(pP0P0, pP2P0, oom, rm);
        p = pP1P0;
        expResult = BigRational.ZERO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 4
        p = pP0P1;
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 5
        p = pN1N1;
        expResult = BigRational.TWO;
        result = instance.getDistanceSquared(pN1N1, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 6
        p = pN2N2;
        expResult = BigRational.valueOf(8);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 7
        p = pN1P0;
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 8
        p = pN1P1;
        expResult = BigRational.TWO;
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_LineSegment.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineSegment() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l;
        V2D_LineSegment instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_LineSegment(pP0P1, pP1P1, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 2
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_LineSegment(pN1P0, pN1P1, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistanceSquared(l, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 3
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 4
        l = new V2D_LineSegment(pP1P0, pP0P1, oom, rm);
        instance = new V2D_LineSegment(pN1P0, pN1P1, oom, rm);
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 5
        instance = new V2D_LineSegment(pP1P0, pP0P1, oom, rm);
        l = new V2D_LineSegment(pN1P0, pN1P1, oom, rm);
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 6
        instance = new V2D_LineSegment(pP0P0, pP0P1, oom, rm);
        l = new V2D_LineSegment(pN1P0, pN1P1, oom, rm);
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
        // Test 7
        instance = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        l = new V2D_LineSegment(pN1P0, pN1P1, oom, rm);
        expResult = BigRational.ONE;
        result = l.getDistanceSquared(instance, oom, rm);
        assertTrue(Math_BigRational.equals(expResult, result, oom));
    }

    /**
     * Test of getMidpoint method, of class V2D_LineSegment.
     */
    @Test
    public void testGetMidpoint() {
        System.out.println("getMidpoint");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment instance;
        V2D_Point expResult;
        V2D_Point result;
        // Test 1
        instance = new V2D_LineSegment(pP0P0, pP2P0, oom, rm);
        expResult = pP1P0;
        result = instance.getMidpoint(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getLineOfIntersection method, of class V2D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V2D_Line() {
        System.out.println("getLineOfIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l0 = new V2D_LineSegment(pP1P0, pP1P1, oom, rm);
        V2D_Line l1 = new V2D_Line(pP0P0, pP0P1, oom, rm);
        V2D_Geometry expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_Geometry result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 2
        l1 = new V2D_Line(pP0P0, pP0P1, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 3
        l0 = new V2D_LineSegment(pP1P0, pP1P1, oom, rm);
        l1 = new V2D_Line(pN1P0, pN2P0, oom, rm);
        expResult = null;
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertNull(result);
        // Test 4
        l0 = new V2D_LineSegment(pP1P0, pP1P1, oom, rm);
        l1 = new V2D_Line(pN1P0, pN1P1, oom, rm);
        expResult = new V2D_LineSegment(pN1P0, pP1P0, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 5
        l0 = new V2D_LineSegment(pP1P0, pP1P1, oom, rm);
        l1 = new V2D_Line(pN1P0, pP0P1, oom, rm);
        expResult = new V2D_LineSegment(new V2D_Point(env, 0.5d, 1.5d), pP1P1, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getLineOfIntersection method, of class V2D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V2D_LineSegment() {
        System.out.println("getLineOfIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l0 = new V2D_LineSegment(pP1P0, pP1P1, oom, rm);
        V2D_LineSegment l1 = new V2D_LineSegment(pP0P0, pP0P1, oom, rm);
        V2D_LineSegment expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_LineSegment result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
        // Test 2
        l1 = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertNull(result);
        // Test 3
        l0 = new V2D_LineSegment(pP0P1, pP0P2, oom, rm);
        l1 = new V2D_LineSegment(pN1P0, pN2P0, oom, rm);
        expResult = new V2D_LineSegment(pN1P0, pP0P1, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
        // Test 4
        l0 = new V2D_LineSegment(pP1P0, pP0P1, oom, rm);
        l1 = new V2D_LineSegment(pN1P0, pN1P1, oom, rm);
        expResult = new V2D_LineSegment(pN1P1, pP0P1, oom, rm);
        result = l0.getLineOfIntersection(l1, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection(result, oom, rm));
    }
}
