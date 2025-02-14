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
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigRational;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_BR;
import uk.ac.leeds.ccg.math.number.Math_BigRationalSqrt;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Geometry;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Line;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;

/**
 * Test class for V2D_Line.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_LineTest extends V2D_Test {

    public V2D_LineTest() {
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
     * Test of toString method, of class V2D_Line.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line instance = new V2D_Line(pP0P0, pP1P0, oom, rm);
        String expResult = """
                           V2D_Line
                           (
                            offset=V2D_Vector(dx=0, dy=0),
                            p=V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=0, dy=0)),
                            v= V2D_Vector(dx=1, dy=0)
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Line.
     */
    @Test
    public void testIsIntersectedBy_3args() {
        System.out.println("isIntersectedBy");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt = pP0P0;
        V2D_Line instance = new V2D_Line(pN1N1, pP1P1, oom, rm);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 2
        pt = new V2D_Point(env, P0_1E2, P0_1E2);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 3 works as the rounding puts pt on the line.
        pt = new V2D_Point(env, P0_1E12, P0_1E12);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 4 works as the rounding puts pt on the line.
        pt = new V2D_Point(env, N0_1E12, N0_1E12);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 5 works as the rounding puts pt on the line.
        double a = P0_1E2 + P1E12;
        pt = new V2D_Point(env, a, a);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 6 works as the rounding puts pt on the line.
        a = N0_1E2 + N1E12;
        pt = new V2D_Point(env, a, a);
        assertTrue(instance.isIntersectedBy(pt, -12, rm));
        //assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 7
        instance = new V2D_Line(pP0N1, pP2P1, oom, rm);
        pt = new V2D_Point(env, -1d, -2d);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 8
        //epsilon = 0.00000000001d; // Too big!
        //epsilon = 0.000000000001d;
        a = N0_1E2 + N1E12;
        pt = new V2D_Point(env, a, a);
        assertFalse(instance.isIntersectedBy(pt, -12, rm));
        pt = new V2D_Point(env, a + 1d, a);
        assertTrue(instance.isIntersectedBy(pt, -12, rm));
        // Test 9
        a = N0_1E12 + N1E12;
        pt = new V2D_Point(env, a, a);
        assertFalse(instance.isIntersectedBy(pt, -12, rm));
        pt = new V2D_Point(env, a + 1d, a);
        assertTrue(instance.isIntersectedBy(pt, -12, rm));
    }

    /**
     * Test of isParallel method, of class V2D_Line.
     */
    @Test
    public void testIsParallel_V2D_Line_double() {
        System.out.println("isParallel");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = V2D_Line.X_AXIS;
        V2D_Line instance = V2D_Line.X_AXIS;
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 2
        instance = V2D_Line.Y_AXIS;
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 3
        instance = new V2D_Line(pP0P1, pP1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 4
        instance = new V2D_Line(pP0P1, pP1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 5
        instance = new V2D_Line(pP0P0, pP1P0, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 6
        instance = new V2D_Line(pP1P0, pP0P0, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 8
        instance = new V2D_Line(pP1P0, pP0P1, oom, rm);
        assertFalse(instance.isParallel(l, oom, rm));
        // Test 9
        l = V2D_Line.Y_AXIS;
        instance = new V2D_Line(pP0P0, pP0P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 9
        instance = new V2D_Line(pP1P0, pP1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 10
        instance = new V2D_Line(pN1P0, pN1P1, oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 12
        double a = P0_1E12 + P1E12;
        double b = N0_1E12 + N1E12;
        double a1 = P0_1E12 + P1E12 + 1d;
        double b1 = N0_1E12 + N1E12 + 1d;
        l = new V2D_Line(new V2D_Point(env, a, a), new V2D_Point(env, b, b), oom, rm);
        instance = new V2D_Line(new V2D_Point(env, a1, a), new V2D_Point(env, b1, b), oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
        // Test 13
        a = P0_1E12 + P1E12;
        b = N0_1E12 + N1E12;
        a1 = P0_1E12 + P1E12 + 10d;
        b1 = N0_1E12 + N1E12 + 10d;
        l = new V2D_Line(new V2D_Point(env, a, a), new V2D_Point(env, b, b), oom, rm);
        instance = new V2D_Line(new V2D_Point(env, a1, a), new V2D_Point(env, b1, b), oom, rm);
        assertTrue(instance.isParallel(l, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V2D_Line.
     */
    @Test
    public void testGetIntersection_3args() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l;
        V2D_Line instance;
        V2D_Geometry expResult;
        V2D_Geometry result;
        // Test 1
        //l = new V2D_Line(N1N1, P1P1);
        l = new V2D_Line(pP1P1, pN1N1, oom, rm);
        //instance = new V2D_Line(N1P1, P1N1, oom, rm);
        instance = new V2D_Line(pP1N1, pN1P1, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 2
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        instance = new V2D_Line(pP1P1, pP0P2, oom, rm);
        expResult = pP1P1;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 3
        expResult = pP0P0;
        instance = new V2D_Line(pP1N1, pN1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 4
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        instance = new V2D_Line(env, P0P0, new V2D_Vector(3, 2), new V2D_Vector(5, 2), oom, rm);
        expResult = pP2P2;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 5
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        instance = new V2D_Line(pP1N1, pN1P1, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 6
        l = new V2D_Line(pP0P0, pP1P1, oom, rm);
        instance = new V2D_Line(pP1N1, pN1P1, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 7
        l = new V2D_Line(pN1N1, pP0P0, oom, rm);
        instance = new V2D_Line(pP1N1, pN1P1, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 8
        l = new V2D_Line(pN2N2, pN1N1, oom, rm);
        instance = new V2D_Line(pP1N1, pP0P0, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 9
        l = new V2D_Line(pN2N2, pN1N1, oom, rm);
        instance = new V2D_Line(pP0P0, pN1P1, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 10 to 12
        // v.dx = 0, v.dy != 0
        // Test 10
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        expResult = pP0P0;
        instance = new V2D_Line(pP0P0, pP0P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 11
        l = new V2D_Line(pP0N1, pP2P1, oom, rm);
        expResult = pP1P0;
        instance = new V2D_Line(pP1P0, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 12
        l = new V2D_Line(env, P0P0, P0N1, new V2D_Vector(2d, 1d), oom, rm);
        expResult = pP1P0;
        instance = new V2D_Line(env, P0P0, P1P0, P1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 12 to 14
        // v.dx != 0, v.dy = 0
        // Test 12
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        expResult = pP0P0;
        instance = new V2D_Line(pP0P0, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 13
        l = new V2D_Line(pP0N1, pP2P1, oom, rm);
        expResult = pP1P0;
        instance = new V2D_Line(pP1P0, pP2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 14
        l = new V2D_Line(pP0N1, pP2P1, oom, rm);
        expResult = pP1P0;
        instance = new V2D_Line(pP1P0, pP2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 15
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        expResult = pP0P0;
        instance = new V2D_Line(pP0P0, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 16
        l = new V2D_Line(pP0N1, pP2P1, oom, rm);
        expResult = pP1P0;
        instance = new V2D_Line(pP1P0, pP2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 17
        l = new V2D_Line(env, P0P0, P0P1, new V2D_Vector(2d, 3d), oom, rm);
        expResult = pP1P2;
        instance = new V2D_Line(pP1P2, pP2P2, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 18
        l = new V2D_Line(pP0P0, pP1P1, oom, rm);
        instance = new V2D_Line(pP0P0, pP1P1, oom, rm);
        expResult = new V2D_Line(pP0P0, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Line) expResult).equals((V2D_Line) result, oom, rm));
        // Test 19
        instance = new V2D_Line(pP1P1, pP0P0, oom, rm);
        expResult = new V2D_Line(pP0P0, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Line) expResult).equals((V2D_Line) result, oom, rm));
        // Test 20
        //instance = new V2D_Line(P0P1, P0N1);
        instance = new V2D_Line(pP0N1, pP0P1, oom, rm);
        l = new V2D_Line(pP1P1, pP0P0, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 21
        instance = new V2D_Line(pN1P1, pP1N1, oom, rm);
        l = new V2D_Line(pP0P2, pP1P1, oom, rm);
        //expResult = null;
        result = instance.getIntersection(l, oom, rm);
        //System.out.println(result);
        assertNull(result);
        // Test 22
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        instance = new V2D_Line(pN1P1, pP1N1, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 23
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        instance = new V2D_Line(pN1P1, pP1N1, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 24
        l = new V2D_Line(new V2D_Point(env, -1d + (0.1d), -1d + (0.1d)),
                new V2D_Point(env, 1d + (0.1d), 1d + (0.1d)), oom, rm);
        instance = new V2D_Line(new V2D_Point(env, -1d + (0.1d), 1d + (0.1d)),
                new V2D_Point(env, 1d + (0.1d), -1d + (0.1d)), oom, rm);
        expResult = new V2D_Point(env, 0d + (0.1d), 0d + (0.1d));
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 25
        l = new V2D_Line(new V2D_Point(env, -100d, -100d),
                new V2D_Point(env, 100d, 100d), oom, rm);
        instance = new V2D_Line(new V2D_Point(env, -100d, -100d),
                new V2D_Point(env, 100d, 100d), oom, rm);
        expResult = new V2D_Line(new V2D_Point(env, -100d, -100d),
                new V2D_Point(env, 100d, 100d), oom, rm);
//        expResult = new V2D_Line(new V2D_Point(-100d, -100d),
//                new V2D_Point(100d, 100d), oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Line) expResult).equals((V2D_Line) result, oom, rm));
    }

    /**
     * Test of equals method, of class V2D_Line.
     */
    @Test
    public void testEquals_double_V2D_Line() {
        System.out.println("equals");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP0P0, pP1P1, oom, rm);
        V2D_Line instance = new V2D_Line(pP0P0, pP1P1, oom, rm);
        assertTrue(instance.equals(l, oom, rm));
        // Test 2
        instance = new V2D_Line(pP1P1, pP0P0, oom, rm);
        assertTrue(instance.equals(l, oom, rm));
        // Test 3
        l = V2D_Line.X_AXIS;
        instance = V2D_Line.X_AXIS;
        assertTrue(instance.equals(l, oom, rm));
        // Test 4
        instance = V2D_Line.Y_AXIS;
        assertFalse(instance.equals(l, oom, rm));
    }

    /**
     * Test of isParallelToX0 method, of class V2D_Line.
     */
    @Test
    public void testIsParallelToX0() {
        System.out.println("isParallelToX0");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line instance = new V2D_Line(pP1P0, pP1P1, oom, rm);
        assertTrue(instance.isParallelToX0(oom, rm));
        // Test 2
        instance = new V2D_Line(pP0P0, pP0P1, oom, rm);
        assertTrue(instance.isParallelToX0(oom, rm));
        // Test 3
        instance = new V2D_Line(pP0P0, pP1P1, oom, rm);
        assertFalse(instance.isParallelToX0(oom, rm));
    }

    /**
     * Test of isParallelToY0 method, of class V2D_Line.
     */
    @Test
    public void testIsParallelToY0() {
        System.out.println("isParallelToY0");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line instance = new V2D_Line(pP0P1, pP1P1, oom, rm);
        assertTrue(instance.isParallelToY0(oom, rm));
        // Test 2
        instance = new V2D_Line(pP0P0, pP1P0, oom, rm);
        assertTrue(instance.isParallelToY0(oom, rm));
        // Test 3
        instance = new V2D_Line(pP0P0, pP1P1, oom, rm);
        assertFalse(instance.isParallelToY0(oom, rm));
    }

    /**
     * Test of getAsMatrix method, of class V2D_Line.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line instance = V2D_Line.X_AXIS;
        BigRational[][] m = new BigRational[2][2];
        m[0][0] = BigRational.ZERO;
        m[0][1] = BigRational.ZERO;
        m[1][0] = BigRational.ONE;
        m[1][1] = BigRational.ZERO;
        Math_Matrix_BR expResult = new Math_Matrix_BR(m);
        Math_Matrix_BR result = instance.getAsMatrix(oom, rm);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V2D_Line.
     */
    @Test
    public void testGetDistance_V2D_Point_double() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt;
        V2D_Line instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        pt = pP0P0;
        instance = new V2D_Line(pP1P0, pP1P1, oom, rm);
        expResult = BigRational.ONE;
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        instance = new V2D_Line(pP0P1, pP1P1, oom, rm);
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 3
        pt = pP1P1;
        expResult = BigRational.ZERO;
        instance = new V2D_Line(pP0P0, pP1P1, oom, rm);
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 4
        pt = pP0P1;
        instance = new V2D_Line(pP0P0, pP1P1, oom, rm);
        expResult = new Math_BigRationalSqrt(BigRational.valueOf("0.5"), oom, rm).getSqrt(oom, rm);
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 5
        instance = new V2D_Line(pP0P0, pP0P1, oom, rm);
        pt = pP1P0;
        expResult = BigRational.ONE;
        result = instance.getDistance(pt, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getLineOfIntersection method, of class V2D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V2D_Point_double() {
        System.out.println("getLineOfIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt;
        V2D_Line instance;
        V2D_LineSegment expResult;
        V2D_Geometry result;
        // Test 1
        pt = pP0P0;
        instance = new V2D_Line(pP1P0, pP1P1, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        result = instance.getLineOfIntersection(pt, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection((V2D_LineSegment) result, oom, rm));
        // Test 2
        instance = new V2D_Line(pP1N1, pP1P1, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        //result = instance.getLineOfIntersection(pt, epsilon);
        //System.out.println(result);
        result = instance.getLineOfIntersection(pt, oom, rm);
        assertTrue(expResult.equalsIgnoreDirection((V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getPointOfIntersection method, of class V2D_Line. No test:
     * Test covered by {@link #testGetLineOfIntersection_V2D_Point()}
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt = pP2P0;
        V2D_Line instance = new V2D_Line(pP0P0, pP0P2, oom, rm);
        V2D_Point expResult = pP0P0;
        V2D_Point result = instance.getPointOfIntersection(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        pt = pN2P0;
        instance = new V2D_Line(pP0P0, pP0P2, oom, rm);
        expResult = pP0P0;
        result = instance.getPointOfIntersection(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        pt = pN2P2;
        instance = new V2D_Line(pP0P0, pP0P2, oom, rm);
        expResult = pP0P2;
        result = instance.getPointOfIntersection(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 4
        pt = pN2N2;
        instance = new V2D_Line(pP0P0, pP0P2, oom, rm);
        expResult = pP0N2;
        result = instance.getPointOfIntersection(pt, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of getLineOfIntersection method, of class V2D_Line.
     */
    @Test
    public void testGetLineOfIntersection_V2D_Line_double() {
        System.out.println("getLineOfIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP1P0, pP1P1, oom, rm);
        V2D_Point p = new V2D_Point(pP0P0);
        V2D_Geometry expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        V2D_Geometry result = l.getLineOfIntersection(p, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection((V2D_LineSegment) result, oom, rm));
        // Test 2
        l = new V2D_Line(pP0P0, pP0P1, oom, rm);
        result = l.getLineOfIntersection(p, oom, rm);
        expResult = new V2D_Point(pP0P0);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
    }

    /**
     * Test of getDistance method, of class V2D_Line.
     */
    @Test
    public void testGetDistance_V2D_Line_double() {
        System.out.println("getDistance");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l;
        V2D_Line instance;
        BigRational expResult;
        BigRational result;
        // Test 1
        l = new V2D_Line(pP0P0, pP1P1, oom, rm);
        instance = new V2D_Line(pP1N1, pP2P0, oom, rm);
        expResult = new Math_BigRationalSqrt(2L, oom, rm).getSqrt(oom, rm);
        result = instance.getDistance(l, oom, rm);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of toString method, of class V2D_Line.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V2D_Line instance = V2D_Line.X_AXIS;
        String expResult = """
                           V2D_Line
                           (
                            offset=V2D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                            ,
                            p=V2D_Point
                            (
                             offset=V2D_Vector
                             (
                              dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                              dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                             )
                             ,
                             rel=V2D_Vector
                             (
                              dx=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0),
                              dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                             )
                            )
                            ,
                            v=V2D_Vector
                            (
                             dx=Math_BigRationalSqrt(x=1, sqrtx=1, oom=0),
                             dy=Math_BigRationalSqrt(x=0, sqrtx=0, oom=0)
                            )
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V2D_Line.
     */
    @Test
    public void testToStringSimple() {
        System.out.println("toStringSimple");
        String pad = "";
        V2D_Line instance = V2D_Line.X_AXIS;
        String expResult = """
                           V2D_Line
                           (
                            offset=V2D_Vector(dx=0, dy=0),
                            p=V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=0, dy=0)),
                            v= V2D_Vector(dx=1, dy=0)
                           )""";
        String result = instance.toStringSimple(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of getP method, of class V2D_Line.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line instance = new V2D_Line(pP0P0, pP1P0, oom, rm);
        V2D_Point expResult = pP0P0;
        V2D_Point result = instance.getP();
        assertTrue(expResult.equals(result, oom, rm));
    }

    /**
     * Test of rotate method, of class V2D_Line.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        Math_BigDecimal bd = new Math_BigDecimal(100);
        BigRational Pi = Math_BigRational.getPi(bd, oom - 2, rm);
        BigRational theta = Pi.divide(2);
        V2D_Line instance = new V2D_Line(pP0P0, pP1P0, oom, rm);
        V2D_Line expResult = new V2D_Line(pP0P0, pP0P1, oom, rm);
        V2D_Line result = instance.rotate(pP0P0, theta.negate(), bd, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 2
        expResult = new V2D_Line(pP0P0, pP0N1, oom, rm);
        result = instance.rotate(pP0P0, theta, bd, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
        // Test 3
        instance = new V2D_Line(pP0P0, pP0P1, oom, rm);
        expResult = new V2D_Line(pP0P0, pP1P0, oom, rm);
        result = instance.rotate(pP0P0, theta, bd, oom, rm);
        assertTrue(expResult.equals(result, oom, rm));
    }
    
    /**
     * Test of isOnSameSide.
     */
    @Test
    public void testIsOnSameSide() {
        System.out.println("isOnSameSide");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l;
        // Test 1
        l = new V2D_Line(pP0P1, pP1P2, oom, rm);
        V2D_Point a = pN1P1;
        V2D_Point b = pP1P0;
        assertFalse(l.isOnSameSide(a, b, oom, rm));        
        // Test 2
        a = pP1P1;
        assertTrue(l.isOnSameSide(a, b, oom, rm));  
        // Test 3
        a = pP1P0;
        assertTrue(l.isOnSameSide(a, b, oom, rm));
    }

    /**
     * Test of isCollinear method, of class V2D_Line.
     */
    @Test
    public void testIsCollinear_double_V2D_Line_V2D_PointArr() {
        System.out.println("isCollinear");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l;
        V2D_Point[] points = new V2D_Point[2];
        // Test 1
        l = new V2D_Line(pN1N1, pP1P1, oom, rm);
        points[0] = pP2P2;
        points[1] = pN2N2;
        assertTrue(V2D_Line.isCollinear(oom, rm, l, points));
        // Test 2
        points[1] = pN2N1;
        assertFalse(V2D_Line.isCollinear(oom, rm, l, points));
        // Test 3
        points[0] = pN1N2;
        assertFalse(V2D_Line.isCollinear(oom, rm, l, points));
    }

    /**
     * Test of isCollinear method, of class V2D_Line.
     */
    @Test
    public void testIsCollinear_double_V2D_PointArr() {
        System.out.println("isCollinear");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point[] points = new V2D_Point[3];
        points[0] = pP0P0;
        points[1] = pP1P1;
        points[2] = pP2P2;
        assertTrue(V2D_Line.isCollinear(oom, rm, points));
        points[2] = pN2N2;
        assertTrue(V2D_Line.isCollinear(oom, rm, points));
        points[2] = pN1N1;
        assertTrue(V2D_Line.isCollinear(oom, rm, points));
        points[2] = pN2N1;
        assertFalse(V2D_Line.isCollinear(oom, rm, points));
        // P2*
        points[0] = pP2P2;
        points[1] = pP2P1;
        points[2] = pP2P0;
        assertTrue(V2D_Line.isCollinear(oom, rm, points));
        points[2] = pP2N1;
        assertTrue(V2D_Line.isCollinear(oom, rm, points));
        points[2] = pP2N2;
        assertTrue(V2D_Line.isCollinear(oom, rm, points));
        points[2] = pN2N1;
        assertFalse(V2D_Line.isCollinear(oom, rm, points));
    }

}
