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
package uk.ac.leeds.ccg.v2d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
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
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;

/**
 * Test of V2D_Vector class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_VectorTest extends V2D_Test {

    public V2D_VectorTest() {
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
     * Test of getDotProduct method, of class V2D_Vector.
     */
    @Test
    public void testGetDotProduct() {
        System.out.println("getDotProduct");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector v = P0P1;
        V2D_Vector instance = P1P0;
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getDotProduct(v, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        v = P1P0;
        instance = P0N1;
        result = instance.getDotProduct(v, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        v = P1P1;
        instance = N1N1;
        expResult = BigRational.valueOf(-2d);
        result = instance.getDotProduct(v, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of isOrthogonal method, of class V2D_Vector.
     */
    @Test
    public void testIsOrthogonal_double_V2D_Vector() {
        System.out.println("isOrthogonal");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector v;
        V2D_Vector instance;
        // Test 1
        v = P1P0;
        instance = P0P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P0;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = N1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        // Test 2
        v = P1P1;
        instance = P1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1N1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1N1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P0;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = N1P1;
        assertTrue(instance.isOrthogonal(v, oom, rm));
        instance = P0P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
        instance = P1P1;
        assertFalse(instance.isOrthogonal(v, oom, rm));
    }

    /**
     * Test of getMagnitude method, of class V2D_Vector.
     */
    @Test
    public void testGetMagnitudeSquared() {
        System.out.println("getMagnitudeSquared");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = P0P0;
        BigRational expResult = BigRational.ZERO;
        BigRational result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 1
        instance = P1P1;
        expResult = BigRational.valueOf(2);
        result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V2D_Vector(10d, 10d);
        expResult = BigRational.valueOf(200);
        result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        instance = new V2D_Vector(3d, 4d);
        expResult = BigRational.valueOf(25);
        result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        instance = new V2D_Vector(3d, -4d);
        expResult = BigRational.valueOf(25);
        result = instance.getMagnitudeSquared();
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of isScalarMultiple method, of class V2D_Vector.
     */
    @Test
    public void testIsScalarMultiple_double_V2D_Vector() {
        System.out.println("isScalarMultiple");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector v = P0P0;
        V2D_Vector instance = P1P1;
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 2
        instance = P0P0;
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 3
        v = N1N1;
        instance = P0P0;
        assertFalse(instance.isScalarMultiple(v, oom, rm));
        // Test 4
        v = N1N1;
        instance = P1P1;
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 5
        v = P1P0;
        instance = P0P1;
        assertFalse(instance.isScalarMultiple(v, oom, rm));
        // Test 6
        v = new V2D_Vector(0d, 1d);
        oom = -18;
        instance = new V2D_Vector(BigRational.ZERO, BigRational.valueOf("0.000000000000001"));
        //assertFalse(instance.isScalarMultiple(v, oom, rm));
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 7
        instance = new V2D_Vector(3d, 1d);
        v = new V2D_Vector(-6d, -2d);
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 8
        instance = new V2D_Vector(-3d, 0d);
        v = new V2D_Vector(-6d, 0d);
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 10
        v = new V2D_Vector(0d, 1d);
        instance = new V2D_Vector(0d, -1d);
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 11
        v = new V2D_Vector(1d, 1d);
        instance = new V2D_Vector(-1d, -1d);
        assertTrue(instance.isScalarMultiple(v, oom, rm));
        // Test 12
        v = new V2D_Vector(1d, 0d);
        instance = new V2D_Vector(-1d, 0d);
        assertTrue(instance.isScalarMultiple(v, oom, rm));
    }

    /**
     * Test of isReverse method, of class V2D_Vector.
     */
    @Test
    public void testIsReverse() {
        System.out.println("isReverse");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector v = V2D_Vector.I;
        V2D_Vector instance = new V2D_Vector(-1, 0);
        double epsilon = 0d;
        assertTrue(instance.isReverse(v, oom, rm));
        // Test 2
        epsilon = 0.0000000001d;
        instance = new V2D_Vector(-1d + epsilon / 2, 0);
        assertTrue(instance.isReverse(v, oom, rm));
        // Test 3
        oom = -10;
        epsilon = 0.0000000001d;
        instance = new V2D_Vector(-1d + 2 * epsilon, 0);
        assertFalse(instance.isReverse(v, oom, rm));
        // Test 4
        oom = -9;
        epsilon = 0.0000000001d;
        instance = new V2D_Vector(-1d + epsilon, -epsilon);
        assertTrue(instance.isReverse(v, oom, rm));
    }

    /**
     * Test of isZero method, of class V2D_Vector.
     */
    @Test
    public void testIsZeroVector() {
        System.out.println("isZeroVector");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = new V2D_Vector(0, 0);
        assertTrue(instance.isZero(oom, rm));
        // Test 2
        instance = new V2D_Vector(1, 0);
        assertFalse(instance.isZero(oom, rm));
    }

    /**
     * Test of multiply method, of class V2D_Vector.
     */
    @Test
    public void testMultiply() {
        System.out.println("multiply");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = new V2D_Vector(0, 0);
        V2D_Vector expResult = new V2D_Vector(0, 0);
        long l = 1;
        V2D_Vector result = instance.multiply(l, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        l = 0;
        instance = new V2D_Vector(10, 10);
        expResult = new V2D_Vector(0, 0);
        result = instance.multiply(l, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        l = 2L;
        instance = new V2D_Vector(10, 10);
        expResult = new V2D_Vector(20, 20);
        result = instance.multiply(l, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of add method, of class V2D_Vector.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = new V2D_Vector(0, 0);
        V2D_Vector v = new V2D_Vector(0, 0);
        V2D_Vector expResult = new V2D_Vector(0, 0);
        V2D_Vector result = instance.add(v, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        instance = new V2D_Vector(0, 0);
        v = new V2D_Vector(1, 1);
        expResult = new V2D_Vector(1, 1);
        result = instance.add(v, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        instance = new V2D_Vector(2, 3);
        v = new V2D_Vector(7, 1);
        expResult = new V2D_Vector(9, 4);
        result = instance.add(v, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        instance = new V2D_Vector(-2, 3);
        v = new V2D_Vector(7, 1);
        expResult = new V2D_Vector(5, 4);
        result = instance.add(v, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of subtract method, of class V2D_Vector.
     */
    @Test
    public void testSubtract() {
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        System.out.println("subtract");
        V2D_Vector instance = new V2D_Vector(0, 0);
        V2D_Vector v = new V2D_Vector(0, 0);
        V2D_Vector expResult = new V2D_Vector(0, 0);
        V2D_Vector result = instance.subtract(v, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        instance = new V2D_Vector(0, 0);
        v = new V2D_Vector(1, 1);
        expResult = new V2D_Vector(-1, -1);
        result = instance.subtract(v, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of reverse method, of class V2D_Vector.
     */
    @Test
    public void testReverse() {
        System.out.println("reverse");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = new V2D_Vector(0, 0);
        V2D_Vector expResult = new V2D_Vector(0, 0);
        V2D_Vector result = instance.reverse();
        assertTrue(expResult.equals(result));
        // Test 2
        instance = new V2D_Vector(1, 1);
        expResult = new V2D_Vector(-1, -1);
        result = instance.reverse();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getUnitVector method, of class V2D_Vector.
     */
    @Test
    public void testGetUnitVector() {
        System.out.println("getUnitVector");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = V2D_Vector.I;
        V2D_Vector expResult = V2D_Vector.I;
        V2D_Vector result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        instance = new V2D_Vector(100d, 0d);
        expResult = V2D_Vector.I;
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        instance = new V2D_Vector(100d, 100d);
        expResult = new V2D_Vector(100d / Math.sqrt(20000d), 100d / Math.sqrt(20000d));
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        instance = new V2D_Vector(-100, 0);
        expResult = new V2D_Vector(-1, 0);
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 5
        instance = new V2D_Vector(-100, -100);
        expResult = new V2D_Vector(-100d / Math.sqrt(20000d), -100d / Math.sqrt(20000d));
        result = instance.getUnitVector(oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of divide method, of class V2D_Vector.
     */
    @Test
    public void testDivide() {
        System.out.println("divide");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        BigRational s = BigRational.TWO;
        V2D_Vector instance = V2D_Vector.I;
        V2D_Vector expResult = new V2D_Vector(new V2D_Point(0.5d, 0d), oom, rm);
        V2D_Vector result = instance.divide(s, oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getAngle method, of class V2D_Vector.
     */
    @Test
    public void testGetAngle() {
        System.out.println("getAngle");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal(100);
        V2D_Vector v;
        V2D_Vector instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        v = V2D_Vector.I;
        instance = V2D_Vector.J;
        result = instance.getAngle(v, oom, rm);
        expResult = Math_BigRational.round(Math_BigRational.getPi(bd, oom - 2, rm).divide(2), oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDirection method, of class V2D_Vector.
     */
    @Test
    public void testGetDirection() {
        System.out.println("getDirection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = V2D_Vector.I;
        int expResult = 1;
        int result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 2
        instance = V2D_Vector.J;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 3
        instance = V2D_Vector.IJ;
        expResult = 1;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 4
        instance = new V2D_Vector(0D, 0D);
        expResult = 0;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 5
        instance = V2D_Vector.I.reverse();
        expResult = 3;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 6
        instance = V2D_Vector.J.reverse();
        expResult = 2;
        result = instance.getDirection();
        assertTrue(expResult == result);
        // Test 7
        instance = V2D_Vector.IJ.reverse();
        expResult = 4;
        result = instance.getDirection();
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V2D_Vector.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V2D_Vector v = V2D_Vector.ZERO;
        String result = v.toString();
        String expResult = "V2D_Vector(dx=0, dy=0)";
        //System.out.println(result);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of equals method, of class V2D_Vector.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector v = new V2D_Vector(1, 1);
        V2D_Vector instance = new V2D_Vector(1, 1);
        assertTrue(instance.equals(v, oom, rm));
        // Test 2
        instance = new V2D_Vector(1, -1);
        assertFalse(instance.equals(v, oom, rm));
        // Test 3
        instance = new V2D_Vector(BigRational.ONE, BigRational.valueOf("1.0000000000002"));
        assertTrue(instance.equals(v, oom, rm));
        // Test 4
        instance = new V2D_Vector(BigRational.valueOf("1.0000000000002"), BigRational.ONE);
        assertTrue(instance.equals(v, oom, rm));
    }


    /**
     * Test of getDY method, of class V2D_Vector.
     */
    @Test
    public void testGetDY_2args() {
        System.out.println("getDY");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = new V2D_Vector(1, 1);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDY(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDX method, of class V2D_Vector.
     */
    @Test
    public void testGetDX_2args() {
        System.out.println("getDX");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = new V2D_Vector(1, 1);
        BigRational expResult = BigRational.ONE;
        BigRational result = instance.getDX(oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getDX method, of class V2D_Vector.
     */
    @Test
    public void testGetDX_0args() {
        System.out.println("getDX");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = new V2D_Vector(1, 1);
        Math_BigRationalSqrt expResult = new Math_BigRationalSqrt(1L, oom, rm);
        Math_BigRationalSqrt result = instance.getDX();
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of getDY method, of class V2D_Vector.
     */
    @Test
    public void testGetDY_0args() {
        System.out.println("getDY");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Vector instance = new V2D_Vector(1, 1);
        Math_BigRationalSqrt expResult = new Math_BigRationalSqrt(1L, oom, rm);
        Math_BigRationalSqrt result = instance.getDY();
        assertTrue(expResult.equals(result, oom));
    }

    /**
     * Test of rotate method, of class V2D_Vector.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal(100);
        BigRational theta = Math_BigRational.getPi(bd, oom, rm).divide(2);
        V2D_Vector instance = new V2D_Vector(1, 0);
        V2D_Vector expResult = new V2D_Vector(0, -1);
        V2D_Vector result = instance.rotate(theta, bd, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        instance = new V2D_Vector(0, 1);
        expResult = new V2D_Vector(1, 0);
        result = instance.rotate(theta, bd, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

}
