/*
 * Copyright 2025 Andy Turner, University of Leeds.
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
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v2d.core.d.V2D_EnvironmentDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_VectorDouble;

/**
 * Test of V2D_VectorDouble class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_VectorDoubleTest extends V2D_TestDouble {

    public V2D_VectorDoubleTest() {
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
     * Test of getDotProduct method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetDotProduct() {
        System.out.println("getDotProduct");
        V2D_VectorDouble v = P0P1;
        V2D_VectorDouble instance = P1P0;
        double expResult = 0d;
        double result = instance.getDotProduct(v);
        assertTrue(expResult == result);
        // Test 2
        v = P1P0;
        instance = P0N1;
        result = instance.getDotProduct(v);
        assertTrue(expResult == result);
        // Test 3
        v = P1P1;
        instance = N1N1;
        expResult = -2d;
        result = instance.getDotProduct(v);
        assertTrue(expResult == result);
    }

    /**
     * Test of isOrthogonal method, of class V2D_VectorDouble.
     */
    @Test
    public void testIsOrthogonal_double_V2D_VectorDouble() {
        System.out.println("isOrthogonal");
        double epsilon = 0.0000000001D;
        V2D_VectorDouble v;
        V2D_VectorDouble instance;
        // Test 1
        v = P1P0;
        instance = P0P1;
        assertTrue(instance.isOrthogonal(epsilon, v));
        instance = P0P0;
        assertTrue(instance.isOrthogonal(epsilon, v));
        instance = P0N1;
        assertTrue(instance.isOrthogonal(epsilon, v));
        instance = N1P0;
        assertFalse(instance.isOrthogonal(epsilon, v));
        instance = P1P1;
        assertFalse(instance.isOrthogonal(epsilon, v));
        instance = P1N1;
        assertFalse(instance.isOrthogonal(epsilon, v));
        instance = N1N1;
        assertFalse(instance.isOrthogonal(epsilon, v));
        // Test 2
        v = P1P1;
        instance = P1P0;
        assertFalse(instance.isOrthogonal(epsilon, v));
        instance = P1N1;
        assertTrue(instance.isOrthogonal(epsilon, v));
        instance = P0N1;
        assertFalse(instance.isOrthogonal(epsilon, v));
        instance = N1N1;
        assertFalse(instance.isOrthogonal(epsilon, v));
        instance = N1P0;
        assertFalse(instance.isOrthogonal(epsilon, v));
        instance = N1P1;
        assertTrue(instance.isOrthogonal(epsilon, v));
        instance = P0P1;
        assertFalse(instance.isOrthogonal(epsilon, v));
        instance = P1P1;
        assertFalse(instance.isOrthogonal(epsilon, v));
    }

    /**
     * Test of isOrthogonal method, of class V2D_VectorDouble.
     */
    @Test
    public void testIsOrthogonal_V2D_VectorDouble() {
        System.out.println("isOrthogonal");
        V2D_VectorDouble v;
        V2D_VectorDouble instance;
        // Test 1
        v = P1P0;
        instance = P0P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P0;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1;
        assertTrue(instance.isOrthogonal(v));
        instance = N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1;
        assertFalse(instance.isOrthogonal(v));
        // Test 2
        v = P1P1;
        instance = P1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = P1N1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1N1;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P0;
        assertFalse(instance.isOrthogonal(v));
        instance = N1P1;
        assertTrue(instance.isOrthogonal(v));
        instance = P0P1;
        assertFalse(instance.isOrthogonal(v));
        instance = P1P1;
        assertFalse(instance.isOrthogonal(v));
    }

    /**
     * Test of getMagnitude method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetMagnitude() {
        System.out.println("getMagnitude");
        V2D_VectorDouble instance = P0P0;
        double expResult = 0d;
        double result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 1
        instance = P1P1;
        expResult = Math.sqrt(2d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 2
        instance = new V2D_VectorDouble(10d, 10d);
        expResult = Math.sqrt(200d);
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 3
        instance = new V2D_VectorDouble(3d, 4d);
        expResult = 5d;
        result = instance.getMagnitude();
        assertTrue(expResult == result);
        // Test 4
        instance = new V2D_VectorDouble(3d, -4d);
        expResult = 5d;
        result = instance.getMagnitude();
        assertTrue(expResult == result);
    }

    /**
     * Test of initMagnitude method, of class V2D_VectorDouble.
     */
    @Test
    public void testInitMagnitude() {
        System.out.println("initMagnitude");
        assertTrue(true); // No need to test.
    }

    /**
     * Test of isScalarMultiple method, of class V2D_VectorDouble.
     */
    @Test
    public void testIsScalarMultiple_double_V2D_Vector() {
        System.out.println("isScalarMultiple");
        double epsilon = 0.0000000001d;
        V2D_VectorDouble v = P0P0;
        V2D_VectorDouble instance = P1P1;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 2
        instance = P0P0;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 3
        v = N1N1;
        instance = P0P0;
        assertFalse(instance.isScalarMultiple(epsilon, v));
        // Test 4
        v = N1N1;
        instance = P1P1;
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 5
        v = P1P0;
        instance = P0P1;
        assertFalse(instance.isScalarMultiple(epsilon, v));
        // Test 6
        v = new V2D_VectorDouble(0d, 1d);
        instance = new V2D_VectorDouble(0d, 1d + epsilon);
        //assertFalse(instance.isScalarMultiple(epsilon, v));
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 7
        instance = new V2D_VectorDouble(3d, 1d);
        v = new V2D_VectorDouble(-6d, -2d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 8
        instance = new V2D_VectorDouble(-3d, 0d);
        v = new V2D_VectorDouble(-6d, 0d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 10
        v = new V2D_VectorDouble(0d, 1d);
        instance = new V2D_VectorDouble(0d, -1d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 11
        v = new V2D_VectorDouble(1d, 1d);
        instance = new V2D_VectorDouble(-1d, -1d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
        // Test 12
        v = new V2D_VectorDouble(1d, 0d);
        instance = new V2D_VectorDouble(-1d, 0d);
        assertTrue(instance.isScalarMultiple(epsilon, v));
    }

    /**
     * Test of isScalarMultiple method, of class V2D_VectorDouble.
     */
    @Test
    public void testIsScalarMultiple_V2D_Vector() {
        System.out.println("isScalarMultiple");
        double epsilon = 0.0000000001d;
        V2D_VectorDouble v = P0P0;
        V2D_VectorDouble instance = P1P1;
        assertTrue(instance.isScalarMultiple(v));
        // Test 2
        instance = P0P0;
        assertTrue(instance.isScalarMultiple(v));
        // Test 3
        v = N1N1;
        instance = P0P0;
        assertFalse(instance.isScalarMultiple(v));
        // Test 4
        v = N1N1;
        instance = P1P1;
        assertTrue(instance.isScalarMultiple(v));
        // Test 5
        v = P1P0;
        instance = P0P1;
        assertFalse(instance.isScalarMultiple(v));
        // Test 6
        v = new V2D_VectorDouble(0d, 1d);
        instance = new V2D_VectorDouble(0d, 1d + epsilon);
        assertTrue(instance.isScalarMultiple(v));
        // Test 7
        instance = new V2D_VectorDouble(3d, 1d);
        v = new V2D_VectorDouble(-6d, -2d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 8
        instance = new V2D_VectorDouble(-3d, 0d);
        v = new V2D_VectorDouble(-6d, 0d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 9
        epsilon = 0.0000000000000000001d;
        v = new V2D_VectorDouble(0d, 1d);
        instance = new V2D_VectorDouble(0d + epsilon, 1d);
        assertFalse(instance.isScalarMultiple(v));
        // Test 10
        v = new V2D_VectorDouble(0d, 1d);
        instance = new V2D_VectorDouble(0d, -1d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 11
        v = new V2D_VectorDouble(1d, 1d);
        instance = new V2D_VectorDouble(-1d, -1d);
        assertTrue(instance.isScalarMultiple(v));
        // Test 12
        v = new V2D_VectorDouble(1d, 0d);
        instance = new V2D_VectorDouble(-1d, 0d);
        assertTrue(instance.isScalarMultiple(v));
    }

    /**
     * Test of equals method, of class V2D_VectorDouble.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Object o = V2D_VectorDouble.I;
        V2D_VectorDouble instance = V2D_VectorDouble.I;
        assertTrue(instance.equals(o));
        // Test 2
        instance = V2D_VectorDouble.J;
        assertFalse(instance.equals(o));
        // Test 3
        o = P0P0;
        instance = P0P0;
        assertTrue(instance.equals(o));
        // Test 4
        o = P0P0;
        instance = P1P0;
        assertFalse(instance.equals(o));
    }

    /**
     * Test of isReverse method, of class V2D_VectorDouble.
     */
    @Test
    public void testIsReverse() {
        System.out.println("isReverse");
        V2D_VectorDouble v = V2D_VectorDouble.I;
        V2D_VectorDouble instance = new V2D_VectorDouble(-1, 0);
        double epsilon = 0d;
        assertTrue(instance.isReverse(v, epsilon));
        // Test 2
        epsilon = 0.0000000001d;
        instance = new V2D_VectorDouble(-1d + epsilon / 2, 0);
        assertTrue(instance.isReverse(v, epsilon));
        // Test 3
        epsilon = 0.0000000001d;
        instance = new V2D_VectorDouble(-1d + 2 * epsilon, 0);
        assertFalse(instance.isReverse(v, epsilon));
        // Test 4
        epsilon = 0.0000000001d;
        instance = new V2D_VectorDouble(-1d + epsilon, -epsilon);
        assertTrue(instance.isReverse(v, epsilon));
    }

    /**
     * Test of isZero method, of class V2D_VectorDouble.
     */
    @Test
    public void testIsZeroVector() {
        System.out.println("isZeroVector");
        V2D_VectorDouble instance = new V2D_VectorDouble(0, 0);
        assertTrue(instance.isZero());
        // Test 2
        instance = new V2D_VectorDouble(1, 0);
        assertFalse(instance.isZero());
    }

    /**
     * Test of isZero method, of class V2D_VectorDouble.
     */
    @Test
    public void testIsZeroVector_double() {
        System.out.println("isZeroVector");
        double epsilon = 0.000000001d;
        V2D_VectorDouble instance = new V2D_VectorDouble(0, 0);
        assertTrue(instance.isZero(epsilon));
        // Test 2
        instance = new V2D_VectorDouble(1, 0);
        assertFalse(instance.isZero(epsilon));
        // Test 3
        instance = new V2D_VectorDouble(epsilon, 0);
        assertTrue(instance.isZero(epsilon));
        // Test 4
        instance = new V2D_VectorDouble(epsilon, epsilon);
        assertTrue(instance.isZero(epsilon));
        // Test 5
        instance = new V2D_VectorDouble(2d * epsilon, 0);
        assertFalse(instance.isZero(epsilon));
        // Test 6
        instance = new V2D_VectorDouble(2d * epsilon, 2d * epsilon);
        assertFalse(instance.isZero(epsilon));
    }

    /**
     * Test of multiply method, of class V2D_VectorDouble.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        double s = 0d;
        V2D_VectorDouble instance = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble expResult = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble result = instance.multiply(s);
        assertTrue(expResult.equals(result));
        // Test 2
        s = 0d;
        instance = new V2D_VectorDouble(10, 10);
        expResult = new V2D_VectorDouble(0, 0);
        result = instance.multiply(s);
        assertTrue(expResult.equals(result));
        // Test 3
        s = 2d;
        instance = new V2D_VectorDouble(10, 10);
        expResult = new V2D_VectorDouble(20, 20);
        result = instance.multiply(s);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of add method, of class V2D_VectorDouble.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        V2D_VectorDouble instance = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble v = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble expResult = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V2D_VectorDouble(0, 0);
        v = new V2D_VectorDouble(1, 1);
        expResult = new V2D_VectorDouble(1, 1);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V2D_VectorDouble(2, 3);
        v = new V2D_VectorDouble(7, 1);
        expResult = new V2D_VectorDouble(9, 4);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
        // Test 3
        instance = new V2D_VectorDouble(-2, 3);
        v = new V2D_VectorDouble(7, 1);
        expResult = new V2D_VectorDouble(5, 4);
        result = instance.add(v);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of subtract method, of class V2D_VectorDouble.
     */
    @Test
    public void testSubtract() {
        System.out.println("subtract");
        V2D_VectorDouble instance = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble v = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble expResult = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble result = instance.subtract(v);
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V2D_VectorDouble(0, 0);
        v = new V2D_VectorDouble(1, 1);
        expResult = new V2D_VectorDouble(-1, -1);
        result = instance.subtract(v);
        //assertTrue(expResult.compareTo(result) == 0);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of reverse method, of class V2D_VectorDouble.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        V2D_VectorDouble instance = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble expResult = new V2D_VectorDouble(0, 0);
        V2D_VectorDouble result = instance.reverse();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V2D_VectorDouble(1, 1);
        expResult = new V2D_VectorDouble(-1, -1);
        result = instance.reverse();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getMagnitudeSquared method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetMagnitudeSquared() {
        System.out.println("getMagnitudeSquared");
        V2D_VectorDouble instance = new V2D_VectorDouble(0, 0);
        double expResult = 0d;
        double result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
        // Test 2
        instance = new V2D_VectorDouble(1, 1);
        expResult = 2d;
        result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
        // Test 3
        instance = new V2D_VectorDouble(2, 2);
        expResult = 8d;
        result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
        // Test 4
        instance = new V2D_VectorDouble(-2, 3);
        expResult = 13d;
        result = instance.getMagnitudeSquared();
        assertTrue(expResult == result);
    }

    /**
     * Test of getUnitVector method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetUnitVector() {
        System.out.println("getUnitVector");
        V2D_VectorDouble instance = V2D_VectorDouble.I;
        V2D_VectorDouble expResult = V2D_VectorDouble.I;
        V2D_VectorDouble result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        assertTrue(result.getMagnitudeSquared() == 1d);
        // Test 2
        instance = new V2D_VectorDouble(100d, 0d);
        expResult = V2D_VectorDouble.I;
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        assertTrue(result.getMagnitudeSquared() == 1d);
        // Test 3
        instance = new V2D_VectorDouble(100d, 100d);
        expResult = new V2D_VectorDouble(100d / Math.sqrt(20000d), 100d / Math.sqrt(20000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        //assertTrue(result.getMagnitudeSquared().compareTo(double.ONE) != 1);
        // Test 4
        instance = new V2D_VectorDouble(-100, 0);
        expResult = new V2D_VectorDouble(-1, 0);
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
        // Test 5
        instance = new V2D_VectorDouble(-100, -100);
        expResult = new V2D_VectorDouble(-100d / Math.sqrt(20000d), -100d / Math.sqrt(20000d));
        result = instance.getUnitVector();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDX method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetDX() {
        System.out.println("getDX");
        V2D_VectorDouble instance = V2D_VectorDouble.I;
        double expResult = 1d;
        double result = instance.dx;
        assertTrue(expResult == result);
        // Test 2
        instance = V2D_VectorDouble.I.reverse();
        expResult = -1d;
        result = instance.dx;
        assertTrue(expResult == result);
    }

    /**
     * Test of getDY method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetDY() {
        System.out.println("getDY");
        V2D_VectorDouble instance = V2D_VectorDouble.J;
        double expResult = 1d;
        double result = instance.dy;
        assertTrue(expResult == result);
        // Test 2
        instance = V2D_VectorDouble.J.reverse();
        expResult = -1d;
        result = instance.dy;
        assertTrue(expResult == result);
    }

    /**
     * Test of divide method, of class V2D_VectorDouble.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(0.00000001d);
        double s = 2d;
        V2D_VectorDouble instance = V2D_VectorDouble.I;
        V2D_VectorDouble expResult = new V2D_VectorDouble(
                new V2D_PointDouble(env, 0.5d, 0d));
        V2D_VectorDouble result = instance.divide(s);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getAngle method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetAngle() {
        System.out.println("getAngle");
        V2D_VectorDouble v;
        V2D_VectorDouble instance;
        double expResult;
        double result;
        // Test 1
        v = V2D_VectorDouble.I;
        instance = V2D_VectorDouble.J;
        result = instance.getAngle(v);
        expResult = Math.PI / 2d;
        assertTrue(expResult == result);
    }

    /**
     * Test of getDirection method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        V2D_VectorDouble instance = V2D_VectorDouble.I;
        int expResult = 1;
        int result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 2
        instance = V2D_VectorDouble.J;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 3
        instance = V2D_VectorDouble.IJ;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 4
        instance = new V2D_VectorDouble(0D, 0D);
        expResult = 0;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 5
        instance = V2D_VectorDouble.I.reverse();
        expResult = 3;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 6
        instance = V2D_VectorDouble.J.reverse();
        expResult = 2;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 7
        instance = V2D_VectorDouble.IJ.reverse();
        expResult = 4;
        result = instance.getDirection();
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V2D_VectorDouble.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V2D_VectorDouble v = V2D_VectorDouble.ZERO;
        String result = v.toString();
        String expResult = "V2D_VectorDouble(dx=0.0, dy=0.0)";
//        String expResult = "V2D_VectorDouble\n"
//                + "(\n"
//                + " dx=double(x=0, sqrtx=0, oom=0),\n"
//                + " dy=double(x=0, sqrtx=0, oom=0),\n"
//                + " dz=double(x=0, sqrtx=0, oom=0)\n"
//                + ")";
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V2D_VectorDouble.
     */
    @Test
    public void testEquals_V2D_VectorDouble() {
        System.out.println("equals");
        V2D_VectorDouble v = new V2D_VectorDouble(1, 1);
        V2D_VectorDouble instance = new V2D_VectorDouble(1, 1);
        assertTrue(instance.equals(v));
        instance = new V2D_VectorDouble(1, -1);
        assertFalse(instance.equals(v));
    }

    /**
     * Test of equals method, of class V2D_VectorDouble.
     */
    @Test
    public void testEquals_V2D_VectorDouble_double() {
        System.out.println("equals");
        double epsilon = 1 / 10000d;
        double ediv2 = epsilon / 2.0d;
        V2D_VectorDouble v = new V2D_VectorDouble(1, 1);
        V2D_VectorDouble instance = new V2D_VectorDouble(1, 1);
        assertTrue(instance.equals(epsilon, v));
        // Test 2
        instance = new V2D_VectorDouble(1, -1);
        assertFalse(instance.equals(epsilon, v));
        // Test 3
        instance = new V2D_VectorDouble(1, 1 + ediv2);
        assertTrue(instance.equals(epsilon, v));
        // Test 5
        instance = new V2D_VectorDouble(1 + ediv2, 1);
        assertTrue(instance.equals(epsilon, v));

    }

    /**
     * Test of getDX method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetDX_int() {
        System.out.println("getDX");
        V2D_VectorDouble instance = new V2D_VectorDouble(1, 1);
        double expResult = 1d;
        assertTrue(expResult == instance.dx);
    }

    /**
     * Test of getDY method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetDY_int() {
        System.out.println("getDY");
        V2D_VectorDouble instance = new V2D_VectorDouble(1, 1);
        double expResult = 1d;
        assertTrue(expResult == instance.dy);
    }

    /**
     * Test of getDX method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetDX_0args() {
        System.out.println("getDX");
        V2D_VectorDouble instance = new V2D_VectorDouble(1, 1);
        double expResult = 1d;
        double result = instance.dx;
        assertTrue(expResult == result);
    }

    /**
     * Test of getDY method, of class V2D_VectorDouble.
     */
    @Test
    public void testGetDY_0args() {
        System.out.println("getDY");
        V2D_VectorDouble instance = new V2D_VectorDouble(1, 1);
        double expResult = 1d;
        double result = instance.dy;
        assertTrue(expResult == result);
    }

    /**
     * Test of rotate method, of class V2D_VectorDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1 / 10000d;
        double Pi = Math.PI;
        double theta = Pi / 2d;
        V2D_VectorDouble instance = new V2D_VectorDouble(1, 0);
        V2D_VectorDouble expResult = new V2D_VectorDouble(0, -1);
        V2D_VectorDouble result = instance.rotate(theta);
        assertTrue(expResult.equals(epsilon, result));
        // Test 2
        theta = Pi / 2d;
        instance = new V2D_VectorDouble(0, 1);
        expResult = new V2D_VectorDouble(1, 0);
        result = instance.rotate(theta);
        assertTrue(expResult.equals(epsilon, result));
    }

}
