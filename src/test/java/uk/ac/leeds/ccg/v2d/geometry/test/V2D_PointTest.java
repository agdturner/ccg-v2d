/*
 * Copyright 2025 Centre for Computational Geography.
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
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.geometry.V2D_AABB;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;
//import org.junit.jupiter.api.Disabled;

/**
 * Test of V2D_Point class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_PointTest extends V2D_Test {

    public V2D_PointTest() {
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
     * Test of equals method, of class V2D_Point.
     */
    @Test
    public void testEquals_V2D_Point() {
        System.out.println("equals");
        V2D_Point instance = pP0P0;
        int oom = -3;
        RoundingMode rm = RoundingMode.HALF_UP;
        double x = 0d;
        double y = 0d;
        V2D_Point p = new V2D_Point(env, x, y);
        assertTrue(instance.equals(p, oom, rm));
        // Test 2
        x = 1d;
        y = 10d;
        instance = new V2D_Point(env, x, y);
        p = new V2D_Point(env, x, y);
        assertTrue(instance.equals(p, oom, rm));
    }

      /**
     * Test of isBetween method, of class V2D_Point.
     */
    @Test
    public void testIsBetween_double_V2D_Point_V2D_Point() {
        System.out.println("isBetween");
        // Test 1
        V2D_Point p = pP0P0;
        V2D_Point a = pN1P0;
        V2D_Point b = pP1P0;
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        assertTrue(p.isBetween(a, b, oom, rm));
        // Test 2
        p = pP0P0;
        a = pP0N1;
        b = pP0P1;
        assertTrue(p.isBetween(a, b, oom, rm));
        // Test 3
        p = pP0P0;
        a = pN1N1;
        b = pP1P1;
        assertTrue(p.isBetween(a, b, oom, rm));
    }

    /**
     * Test of getAABB method, of class V2D_Point.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_AABB expResult = new V2D_AABB(oom, pP1P2);
        V2D_AABB result = pP1P2.getAABB(oom, rm);
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of getDistance method, of class V2D_Point.
     */
    @Test
    public void testGetDistance_V2D_Point() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point p = V2D_Point.ORIGIN;
        V2D_Point instance = V2D_Point.ORIGIN;
        BigRational expResult = BigRational.valueOf(0d);
        BigRational result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = pP1P0;
        expResult = BigRational.valueOf(1d);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = pP1P1;
        expResult = new Math_BigRationalSqrt(2L, oom, rm).getSqrt(oom, rm);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        instance = new V2D_Point(env, 3d, 4d);
        expResult = BigRational.valueOf(5d);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        instance = new V2D_Point(env, -3d, -4d);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 6
        instance = new V2D_Point(env, -3d, 4d);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 7
        instance = pP0P0;
        expResult = BigRational.valueOf(0d);
        result = instance.getDistance(instance, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 15
        p = pP0P0;
        instance = pP1P0;
        expResult = BigRational.valueOf(1d);
        result = instance.getDistance(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Point.
     */
    @Test
    public void testGetDistanceSquared_V2D_Point() {
        System.out.println("getDistanceSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational expResult = BigRational.valueOf(0d);
        BigRational result = pP0P0.getDistance(pP0P0, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        V2D_Point instance = new V2D_Point(env, 3d, 4d);
        expResult = BigRational.valueOf(25d);
        result = instance.getDistanceSquared(pP0P0, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        V2D_Point p = pP1P0;
        instance = pP1P0;
        expResult = BigRational.valueOf(0d);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        p = pP1P0;
        instance = pP2P0;
        expResult = BigRational.valueOf(1d);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        p = pP1P0;
        instance = pP0P1;
        expResult = BigRational.valueOf(2d);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 6
        p = pP1P0;
        instance = pP1P1;
        expResult = BigRational.valueOf(1d);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 7
        p = pP0P0;
        instance = pP1P1;
        expResult = BigRational.valueOf(2d);
        result = instance.getDistanceSquared(p, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toString method, of class V2D_Point.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V2D_Point instance = pP0P1;
        String expResult = "V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=0, dy=1))";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V2D_Point.
     */
    @Test
    public void testToStringSimple() {
        System.out.println("toStringSimple");
        String pad = "";
        V2D_Point instance = pP0P1;
        String expResult = """
                           V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=0, dy=1))""";
        String result = instance.toStringSimple(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getVector method, of class V2D_Point.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point instance = pP0P1;
        V2D_Vector expResult = new V2D_Vector(0, 1);
        V2D_Vector result = instance.getVector(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getX method, of class V2D_Point.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point instance = pP0P1;
        BigRational expResult = BigRational.valueOf(0d);
        BigRational result = instance.getX(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getY method, of class V2D_Point.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point instance = pP0P1;
        BigRational expResult = BigRational.valueOf(1d);
        BigRational result = instance.getY(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of isOrigin method, of class V2D_Point.
     */
    @Test
    public void testIsOrigin() {
        System.out.println("isOrigin");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point instance = pP0P1;
        assertFalse(instance.isOrigin(oom, rm));
        instance = pP0P0;
        assertTrue(instance.isOrigin(oom, rm));
    }

    /**
     * Test of getLocation method, of class V2D_Point.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point instance = V2D_Point.ORIGIN;
        assertEquals(0, instance.getLocation(oom, rm));
        // PP
        instance = pP0P0;
        assertEquals(0, instance.getLocation(oom, rm));
        instance = pP0P1;
        assertEquals(1, instance.getLocation(oom, rm));
        instance = pP1P0;
        assertEquals(1, instance.getLocation(oom, rm));
        instance = pP1P1;
        assertEquals(1, instance.getLocation(oom, rm));
        // PN
        instance = pP0N1;
        assertEquals(2, instance.getLocation(oom, rm));
        // NP
        instance = pN1P0;
        assertEquals(3, instance.getLocation(oom, rm));
        // NN
        instance = pN1N1;
        assertEquals(4, instance.getLocation(oom, rm));
    }

    /**
     * Test of rotate method, of class V2D_Point.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal(100);
        BigRational Pi = Math_BigRational.getPi(bd, -15, rm);
        V2D_Point pt = pP0P0;
        V2D_Point instance = new V2D_Point(pP1P0);
        BigRational theta = Pi;
        V2D_Point result = instance.rotate(pt, theta, bd, oom, rm);
        V2D_Point expResult = pN1P0;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        instance = new V2D_Point(pP0P1);
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = pP0N1;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        V2D_Vector offset = new V2D_Vector(2, 0);
        V2D_Vector rel = new V2D_Vector(1, 0);
        instance = new V2D_Point(env, offset, rel);
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = new V2D_Point(env, -3, 0);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        offset = new V2D_Vector(1, 0);
        rel = new V2D_Vector(2, 0);
        instance = new V2D_Point(env, offset, rel);
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = new V2D_Point(env, -3, 0);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 5
        pt = pP0P1;
        instance = new V2D_Point(pP1P0);
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = pN1P2;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 6
        instance = new V2D_Point(pP2P0);
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = pN2P2;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 7
        instance = new V2D_Point(pN2P0);
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = pP2P2;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 8
        instance = new V2D_Point(pP1P1);
        theta = Pi;
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = pN1P1;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 9
        instance = new V2D_Point(pP1P1);
        theta = Pi.divide(BigRational.valueOf(2d));
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = pP0P0;
        assertTrue(expResult.equals(result, oom, rm));
        // Test 10
        theta = BigRational.valueOf(3).multiply(Pi).divide(BigRational.valueOf(2d));
        result = instance.rotate(pt, theta, bd, oom, rm);
        expResult = pP0P2;
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of setOffset method, of class V2D_Point.
     */
    @Test
    public void testTranslate() {
        System.out.println("setOffset");
        V2D_Point instance = new V2D_Point(pP0P0);
        V2D_Vector offset = P0P1;
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        instance.translate(offset, oom, rm);
        assertTrue(instance.equals(pP0P1, oom, rm));
        // Test 2
        offset = N2N2;
        instance.translate(offset, oom, rm);
        assertTrue(instance.equals(pN2N1, oom, rm));
    }

    /**
     * Test of setOffset method, of class V2D_Point.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector offset = P0P1;
        V2D_Point instance = new V2D_Point(pP0P0);
        instance.setOffset(offset, oom, rm);
        assertTrue(instance.equals(pP0P0, oom, rm));
    }

    /**
     * Test of setRel method, of class V2D_Point.
     */
    @Test
    public void testSetRel() {
        System.out.println("setRel");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector rel = P0P1;
        V2D_Point instance = new V2D_Point(pP0P0);
        instance.setRel(rel, oom, rm);
        assertTrue(instance.equals(pP0P0, oom, rm));
    }

    /**
     * Test of equals method, of class V2D_Point.
     */
    @Test
    public void testGetUnique() {
        System.out.println("getUnique");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        ArrayList<V2D_Point> pts;
        ArrayList<V2D_Point> expResult;
        ArrayList<V2D_Point> result;
        // Test 1
        pts = new ArrayList<>();
        pts.add(pP0P0);
        pts.add(pP0P0);
        expResult = new ArrayList<>();
        expResult.add(pP0P0);
        result = V2D_Point.getUnique(pts, oom, rm);
        testContainsSamePoints(expResult, result, oom, rm);
        // Test 2
        pts = new ArrayList<>();
        pts.add(pP0P0);
        pts.add(pP0P0);
        pts.add(pP0P0);
        pts.add(pP1P0);
        pts.add(pP0P0);
        pts.add(pP0P0);
        pts.add(pP0P0);
        pts.add(pP1P0);
        pts.add(pP0P0);
        pts.add(pP1P0);
        expResult = new ArrayList<>();
        expResult.add(pP0P0);
        expResult.add(pP1P0);
        result = V2D_Point.getUnique(pts, oom, rm);
        testContainsSamePoints(expResult, result, oom, rm);
        // Test 3
        pts = new ArrayList<>();
        pts.add(pP0P1);
        pts.add(pP0P0);
        pts.add(pP0P0);
        pts.add(pP1P0);
        pts.add(pP0P0);
        pts.add(pP0P0);
        pts.add(pP0P0);
        pts.add(pP1P0);
        pts.add(pP0P0);
        pts.add(pP1P0);
        expResult = new ArrayList<>();
        expResult.add(pP0P1);
        expResult.add(pP0P0);
        expResult.add(pP1P0);
        result = V2D_Point.getUnique(pts, oom, rm);
        testContainsSamePoints(expResult, result, oom, rm);
    }

    private void testContainsSamePoints(ArrayList<V2D_Point> expResult,
            ArrayList<V2D_Point> result, int oom, RoundingMode rm) {
        assertEquals(expResult.size(), result.size());
        boolean t = false;
        for (var x : result) {
            for (var y : expResult) {
                if (x.equals(y, oom, rm)) {
                    t = true;
                    break;
                }
            }
            assertTrue(t);
        }
        t = false;
        for (var x : expResult) {
            for (var y : result) {
                if (x.equals(y, oom, rm)) {
                    t = true;
                    break;
                }
            }
            assertTrue(t);
        }
    }
}
