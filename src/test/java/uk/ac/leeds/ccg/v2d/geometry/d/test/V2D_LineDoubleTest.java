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
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.math.matrices.Math_Matrix_Double;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_FiniteGeometryDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_GeometryDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineSegmentDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_RayDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_VectorDouble;

/**
 * Test class for V2D_LineDouble.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_LineDoubleTest extends V2D_TestDouble {

    private static final long serialVersionUID = 1L;

    public V2D_LineDoubleTest() {
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
     * Test of toString method, of class V2D_LineDouble.
     */
    @Test
    public void testToString_0args() {
        System.out.println("toString");
        V2D_LineDouble instance = new V2D_LineDouble(pP0P0, pP1P0);
        String expResult = """
                           V2D_LineDouble
                           (
                            offset=V2D_VectorDouble(dx=0.0, dy=0.0),
                            p=V2D_PointDouble(offset=V2D_VectorDouble(dx=0.0, dy=0.0), rel=V2D_VectorDouble(dx=0.0, dy=0.0)),
                            v= V2D_VectorDouble(dx=1.0, dy=0.0)
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_LineDouble.
     */
    @Test
    public void testIsIntersectedBy_double_V2D_PointDouble() {
        System.out.println("isIntersectedBy");
        double epsilon = 0.0000000001d;
        V2D_PointDouble pt = pP0P0;
        V2D_LineDouble instance = new V2D_LineDouble(pN1N1, pP1P1);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
        // Test 2
        pt = new V2D_PointDouble(P0_1E2, P0_1E2);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
        // Test 3 works as the rounding puts pt on the line.
        pt = new V2D_PointDouble(P0_1E12, P0_1E12);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
        // Test 4 works as the rounding puts pt on the line.
        pt = new V2D_PointDouble(N0_1E12, N0_1E12);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
        // Test 5 works as the rounding puts pt on the line.
        double a = P0_1E2 + P1E12;
        pt = new V2D_PointDouble(a, a);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
        // Test 6 works as the rounding puts pt on the line.
        a = N0_1E2 + N1E12;
        pt = new V2D_PointDouble(a, a);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
        // Test 7
        instance = new V2D_LineDouble(pP0N1, pP2P1);
        pt = new V2D_PointDouble(-1d, -2d);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
        // Test 8
        a = N0_1E2 + N1E12;
        pt = new V2D_PointDouble(a, a);
        assertFalse(instance.isIntersectedBy(epsilon, pt));
        pt = new V2D_PointDouble(a + 1d, a);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
        // Test 9
        a = N0_1E12 + N1E12;
        pt = new V2D_PointDouble(a, a);
        assertFalse(instance.isIntersectedBy(epsilon, pt));
        pt = new V2D_PointDouble(a + 1d, a);
        assertTrue(instance.isIntersectedBy(epsilon, pt));
    }

    /**
     * Test of isParallel method, of class V2D_LineDouble.
     */
    @Test
    public void testIsParallel_V2D_LineDouble_double() {
        System.out.println("isParallel");
        double epsilon = 0.000000000001d;
        V2D_LineDouble l = V2D_LineDouble.X_AXIS;
        V2D_LineDouble instance = V2D_LineDouble.X_AXIS;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 2
        instance = V2D_LineDouble.Y_AXIS;
        assertFalse(instance.isParallel(l, epsilon));
        // Test 3
        instance = new V2D_LineDouble(pP0P1, pP1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 4
        instance = new V2D_LineDouble(pP0P1, pP1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 5
        instance = new V2D_LineDouble(pP0P0, pP1P0);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 6
        instance = new V2D_LineDouble(pP1P0, pP0P0);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 8
        instance = new V2D_LineDouble(pP1P0, pP0P1);
        assertFalse(instance.isParallel(l, epsilon));
        // Test 9
        l = V2D_LineDouble.Y_AXIS;
        instance = new V2D_LineDouble(pP0P0, pP0P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 9
        instance = new V2D_LineDouble(pP1P0, pP1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 10
        instance = new V2D_LineDouble(pN1P0, pN1P1);
        assertTrue(instance.isParallel(l, epsilon));
        // Test 12
        double a = P0_1E12 + P1E12;
        double b = N0_1E12 + N1E12;
        double a1 = P0_1E12 + P1E12 + 1d;
        double b1 = N0_1E12 + N1E12 + 1d;
        l = new V2D_LineDouble(new V2D_PointDouble(a, a), new V2D_PointDouble(b, b));
        instance = new V2D_LineDouble(new V2D_PointDouble(a1, a), new V2D_PointDouble(b1, b));
        epsilon = 0.00000001d;
        assertTrue(instance.isParallel(l, epsilon));
        // Test 13
        a = P0_1E12 + P1E12;
        b = N0_1E12 + N1E12;
        a1 = P0_1E12 + P1E12 + 10d;
        b1 = N0_1E12 + N1E12 + 10d;
        l = new V2D_LineDouble(new V2D_PointDouble(a, a), new V2D_PointDouble(b, b));
        instance = new V2D_LineDouble(new V2D_PointDouble(a1, a),
                new V2D_PointDouble(b1, b));
        assertTrue(instance.isParallel(l, epsilon));
    }

    /**
     * Test of getIntersection method, of class V2D_LineDouble.
     */
    @Test
    public void testGetIntersection_V2D_LineDouble_double() {
        System.out.println("getIntersection");
        double epsilon = 0.0000000001d;
        V2D_LineDouble l;
        V2D_LineDouble instance;
        V2D_GeometryDouble expResult;
        V2D_GeometryDouble result;
        // Test 1
        //l = new V2D_LineDouble(N1N1, P1P1);
        l = new V2D_LineDouble(pP1P1, pN1N1);
        //instance = new V2D_LineDouble(N1P1, P1N1);
        instance = new V2D_LineDouble(pP1N1, pN1P1);
        expResult = pP0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 2
        l = new V2D_LineDouble(pN1N1, pP1P1);
        instance = new V2D_LineDouble(pP1P1, pP1P1);
        expResult = pP1P1;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 3
        expResult = pP0P0;
        instance = new V2D_LineDouble(pN1N1, pP1P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 4
        l = new V2D_LineDouble(pN1N1, pP1P1);
        instance = new V2D_LineDouble(new V2D_VectorDouble(3d, 1d, 1d), new V2D_VectorDouble(1d, 3d, 3d));
        expResult = pP2P2;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 5
        l = new V2D_LineDouble(pN1N1, pP1P1);
        instance = new V2D_LineDouble(new V2D_VectorDouble(3d, 3d), new V2D_VectorDouble(3d, 3d));
        expResult = new V2D_PointDouble(3d, 3d);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 6
        l = new V2D_LineDouble(pN1N1, pP1P1);
        instance = new V2D_LineDouble(pP1N1, pN1P1);
        expResult = pP0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 7
        l = new V2D_LineDouble(pP0P0, pP1P1);
        instance = new V2D_LineDouble(pP1N1, pN1P1);
        expResult = pP0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 8
        l = new V2D_LineDouble(pN1N1, pP0P0);
        instance = new V2D_LineDouble(pP1N1, pN1P1);
        expResult = pP0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 9
        l = new V2D_LineDouble(pN2N2, pN1N1);
        instance = new V2D_LineDouble(pP1N1, pP0P0);
        expResult = pP0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 10
        l = new V2D_LineDouble(pN2N2, pN1N1);
        instance = new V2D_LineDouble(pP0P0, pN1P1);
        expResult = pP0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 12 to 14
        // v.dx = 0, v.dy != 0
        // Test 11
        l = new V2D_LineDouble(pN1N1, pP1P1);
        expResult = pP0P0;
        instance = new V2D_LineDouble(pP0P0, pP0P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 15
        l = new V2D_LineDouble(pP0N1, pP2P1);
        expResult = pP1P0;
        instance = new V2D_LineDouble(pP1P0, pP1P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 16
        l = new V2D_LineDouble(P0N1, new V2D_VectorDouble(2d, 1d));
        expResult = pP1P0;
        instance = new V2D_LineDouble(new V2D_VectorDouble(1d, 0d),
                new V2D_VectorDouble(1d, 1d));
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 17 to 18
        // v.dx != 0, v.dy = 0
        // Test 17
        l = new V2D_LineDouble(pN1N1, pP1P1);
        expResult = pP0P0;
        instance = new V2D_LineDouble(pP0P0, pP1P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 18
        l = new V2D_LineDouble(pP0N1, pP2P1);
        expResult = pP1P0;
        instance = new V2D_LineDouble(pP1P0, pP2P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 19
        l = new V2D_LineDouble(pP0N1, pP2P1);
        expResult = pP1P0;
        instance = new V2D_LineDouble(pP1P0, pP2P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 20 to 21
        // v.dx != 0, v.dy = 0, v.dz != 0
        // Test 20
        l = new V2D_LineDouble(pN1N1, pP1P1);
        expResult = pP0P0;
        instance = new V2D_LineDouble(pP0P0, pP1P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 21
        l = new V2D_LineDouble(pP0N1, pP2P1);
        expResult = pP1P0;
        instance = new V2D_LineDouble(pP1P0, pP2P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 22
        l = new V2D_LineDouble(P0P1, new V2D_VectorDouble(2d, 3d));
        expResult = pP1P2;
        instance = new V2D_LineDouble(pP1P2, pP2P2);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 23
        epsilon = 1d / 10000000d;
        l = new V2D_LineDouble(pP0P0, pP1P1);
        instance = new V2D_LineDouble(pP0P0, pP1P1);
        expResult = new V2D_LineDouble(pP0P0, pP1P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineDouble) expResult).equals(epsilon,
                (V2D_LineDouble) result));
        // Test 24
        instance = new V2D_LineDouble(pP1P1, pP0P0);
        expResult = new V2D_LineDouble(pP0P0, pP1P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineDouble) expResult).equals(epsilon,
                (V2D_LineDouble) result));
        // Test 25
        //instance = new V2D_LineDouble(P0P1, P0N1);
        instance = new V2D_LineDouble(pP0N1, pP0P1);
        l = new V2D_LineDouble(pP1P1, pP0P0);
        expResult = pP0P0;
        epsilon = 1d / 100000000000d;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals(epsilon,
                (V2D_PointDouble) result));
        // Test 26
        instance = new V2D_LineDouble(pN1P1, pP1N1);
        l = new V2D_LineDouble(pP0P2, pP1P1);
        //expResult = null;
        result = instance.getIntersection(l, epsilon);
        //System.out.println(result);
        assertNull(result);
        // Test 27
        l = new V2D_LineDouble(pN1N1, pP1P1);
        instance = new V2D_LineDouble(pN1P1, pP1N1);
        expResult = pP0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals(epsilon,
                (V2D_PointDouble) result));
        // Test 28
        l = new V2D_LineDouble(pN1N1, pP1P1);
        instance = new V2D_LineDouble(pN1P1, pP1N1);
        expResult = pP0P0;
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals(epsilon,
                (V2D_PointDouble) result));
        // Test 29
        l = new V2D_LineDouble(new V2D_PointDouble(-1d + (0.1d), -1d + (0.1d)),
                new V2D_PointDouble(1d + (0.1d), 1d + (0.1d)));
        instance = new V2D_LineDouble(new V2D_PointDouble(-1d + (0.1d), 1d + (0.1d)),
                new V2D_PointDouble(1d + (0.1d), -1d + (0.1d)));
        expResult = new V2D_PointDouble(0d + (0.1d), 0d + (0.1d));
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals(epsilon,
                (V2D_PointDouble) result));
        // Test 30
        epsilon = 1d / 100000d;
        l = new V2D_LineDouble(new V2D_PointDouble(-100d, -100d),
                new V2D_PointDouble(100d, 100d));
        instance = new V2D_LineDouble(new V2D_PointDouble(-100d, -100d),
                new V2D_PointDouble(100d, 100d));
        expResult = new V2D_LineDouble(new V2D_PointDouble(-100d, -100d),
                new V2D_PointDouble(100d, 100d));
//        expResult = new V2D_LineDouble(new V2D_PointDouble(-100d, -100d),
//                new V2D_PointDouble(100d, 100d));
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineDouble) expResult).equals(epsilon,
                (V2D_LineDouble) result));
    }

    /**
     * Test of equals method, of class V2D_LineDouble.
     */
    @Test
    public void testEquals_double_V2D_LineDouble() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V2D_LineDouble l = new V2D_LineDouble(pP0P0, pP1P1);
        V2D_LineDouble instance = new V2D_LineDouble(pP0P0, pP1P1);
        assertTrue(instance.equals(epsilon, l));
        // Test 2
        instance = new V2D_LineDouble(pP1P1, pP0P0);
        assertTrue(instance.equals(epsilon, l));
        // Test 3
        l = V2D_LineDouble.X_AXIS;
        instance = V2D_LineDouble.X_AXIS;
        assertTrue(instance.equals(epsilon, l));
        // Test 4
        instance = V2D_LineDouble.Y_AXIS;
        assertFalse(instance.equals(epsilon, l));
    }

    /**
     * Test of isParallelToX0 method, of class V2D_LineDouble.
     */
    @Test
    public void testIsParallelToX0() {
        System.out.println("isParallelToX0");
        //double epsilon = 1d / 10000000d;
        V2D_LineDouble instance = new V2D_LineDouble(pP1P0, pP1P1);
        assertTrue(instance.isParallelToX0());
        // Test 2
        instance = new V2D_LineDouble(pP0P0, pP0P1);
        assertTrue(instance.isParallelToX0());
        // Test 3
        instance = new V2D_LineDouble(pP0P0, pP1P1);
        assertFalse(instance.isParallelToX0());
    }

    /**
     * Test of isParallelToY0 method, of class V2D_LineDouble.
     */
    @Test
    public void testIsParallelToY0() {
        System.out.println("isParallelToY0");
        V2D_LineDouble instance = new V2D_LineDouble(pP0P1, pP1P1);
        assertTrue(instance.isParallelToY0());
        // Test 2
        instance = new V2D_LineDouble(pP0P0, pP1P0);
        assertTrue(instance.isParallelToY0());
        // Test 3
        instance = new V2D_LineDouble(pP0P0, pP1P1);
        assertFalse(instance.isParallelToY0());
    }

    /**
     * Test of getAsMatrix method, of class V2D_LineDouble.
     */
    @Test
    public void testGetAsMatrix() {
        System.out.println("getAsMatrix");
        V2D_LineDouble instance = V2D_LineDouble.X_AXIS;
        double[][] m = new double[2][2];
        m[0][0] = 0d;
        m[0][1] = 0d;
        m[1][0] = 1d;
        m[1][1] = 0d;
        Math_Matrix_Double expResult = new Math_Matrix_Double(m);
        Math_Matrix_Double result = instance.getAsMatrix();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V2D_LineDouble.
     */
    @Test
    public void testGetDistance_V2D_PointDouble_double() {
        System.out.println("getDistance");
        double epsilon = 1d / 10000000d;
        V2D_PointDouble pt;
        V2D_LineDouble instance;
        double expResult;
        double result;
        // Test 1
        pt = pP0P0;
        instance = new V2D_LineDouble(pP1P0, pP1P1);
        expResult = 1d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 2
        instance = new V2D_LineDouble(pP0P1, pP1P1);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 3
        pt = pP1P1;
        expResult = 0d;
        instance = new V2D_LineDouble(pP0P0, pP1P1);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 4
        pt = pP0P1;
        instance = new V2D_LineDouble(pP0P0, pP1P1);
        expResult = Math.sqrt(1d / 2d);
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
        // Test 5
        instance = new V2D_LineDouble(pP0P0, pP0P1);
        pt = pP1P0;
        expResult = 1d;
        result = instance.getDistance(pt, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of getLineOfIntersection method, of class V2D_LineDouble.
     */
    @Test
    public void testGetLineOfIntersection_V2D_PointDouble_double() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 10000000d;
        V2D_PointDouble pt;
        V2D_LineDouble instance;
        V2D_LineSegmentDouble expResult;
        V2D_GeometryDouble result;
        // Test 1
        pt = pP0P0;
        instance = new V2D_LineDouble(pP1P0, pP1P1);
        expResult = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        result = instance.getLineOfIntersection(pt, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, (V2D_LineSegmentDouble) result));
        // Test 2
        instance = new V2D_LineDouble(pP1N1, pP1P1);
        expResult = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        //result = instance.getLineOfIntersection(pt, epsilon);
        //System.out.println(result);
        result = instance.getLineOfIntersection(pt, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, (V2D_LineSegmentDouble) result));
    }

    /**
     * Test of getPointOfIntersection method, of class V2D_LineDouble. No test:
     * Test covered by {@link #testGetLineOfIntersection_V2D_PointDouble()}
     */
    @Test
    public void testGetPointOfIntersection() {
        System.out.println("getPointOfIntersection");
        double epsilon = 1d / 10000000d;
        V2D_PointDouble pt = pP2P0;
        V2D_LineDouble instance = new V2D_LineDouble(pP0P0, pP0P2);
        V2D_PointDouble expResult = pP0P0;
        V2D_PointDouble result = instance.getPointOfIntersection(pt, epsilon);
        assertTrue(expResult.equals(epsilon, result));
        // Test 2
         pt = pN2P0;
         instance = new V2D_LineDouble(pP0P0, pP0P2);
         expResult = pP0P0;
         result = instance.getPointOfIntersection(pt, epsilon);
        assertTrue(expResult.equals(epsilon, result));
        // Test 3
         pt = pN2P2;
         instance = new V2D_LineDouble(pP0P0, pP0P2);
         expResult = pP0P1;
         result = instance.getPointOfIntersection(pt, epsilon);
        assertTrue(expResult.equals(epsilon, result));
        // Test 4
         pt = pN2N2;
         instance = new V2D_LineDouble(pP0P0, pP0P2);
         expResult = pP0N1;
         result = instance.getPointOfIntersection(pt, epsilon);
        assertTrue(expResult.equals(epsilon, result));
    }

    /**
     * Test of getLineOfIntersection method, of class V2D_LineDouble.
     */
    @Test
    public void testGetLineOfIntersection_V2D_LineDouble_double() {
        System.out.println("getLineOfIntersection");
        double epsilon = 1d / 10000000d;
        V2D_LineDouble l = new V2D_LineDouble(pP1P0, pP1P1);
        V2D_PointDouble p = new V2D_PointDouble(pP0P0);
        V2D_LineSegmentDouble expResult = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        V2D_LineSegmentDouble result = (V2D_LineSegmentDouble) l.getLineOfIntersection(p, epsilon);
        assertTrue(expResult.equalsIgnoreDirection(epsilon, result));
        // Test 2
        l = new V2D_LineDouble(pP0P0, pP0P1);
        result = (V2D_LineSegmentDouble) l.getLineOfIntersection(p, epsilon);
        assertNull(result);
    }

    /**
     * Test of getDistance method, of class V2D_LineDouble.
     */
    @Test
    public void testGetDistance_V2D_LineDouble_double() {
        System.out.println("getDistance");
        double epsilon = 1d / 100000000d;
        V2D_LineDouble l;
        V2D_LineDouble instance;
        double expResult;
        double result;
        // Test 1
        l = new V2D_LineDouble(pP0P0, pP1P1);
        instance = new V2D_LineDouble(pP1N1, pP2P0);
        expResult = Math.sqrt(2d);
        result = instance.getDistance(l, epsilon);
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V2D_LineDouble.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V2D_LineDouble instance = V2D_LineDouble.X_AXIS;
        String expResult = """
                           V2D_LineDouble
                           (
                            offset=V2D_VectorDouble
                            (
                             dx=0.0,
                             dy=0.0
                            )
                            ,
                            p=V2D_PointDouble
                            (
                             offset=V2D_VectorDouble
                             (
                              dx=0.0,
                              dy=0.0
                             )
                             ,
                             rel=V2D_VectorDouble
                             (
                              dx=0.0,
                              dy=0.0
                             )
                            )
                            ,
                            v=V2D_VectorDouble
                            (
                             dx=1.0,
                             dy=0.0
                            )
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V2D_LineDouble.
     */
    @Test
    public void testToStringSimple() {
        System.out.println("toStringSimple");
        String pad = "";
        V2D_LineDouble instance = V2D_LineDouble.X_AXIS;
        String expResult = """
                           V2D_LineDouble
                           (
                            offset=V2D_VectorDouble(dx=0.0, dy=0.0),
                            p=V2D_PointDouble(offset=V2D_VectorDouble(dx=0.0, dy=0.0), rel=V2D_VectorDouble(dx=0.0, dy=0.0)),
                            v= V2D_VectorDouble(dx=1.0, dy=0.0)
                           )""";
        String result = instance.toStringSimple(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getP method, of class V2D_LineDouble.
     */
    @Test
    public void testGetP() {
        System.out.println("getP");
        V2D_LineDouble instance = new V2D_LineDouble(pP0P0, pP1P0);
        V2D_PointDouble expResult = pP0P0;
        V2D_PointDouble result = instance.getP();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of rotate method, of class V2D_LineDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        double Pi = Math.PI;
        double theta = Pi / 2d;
        V2D_LineDouble instance = new V2D_LineDouble(pP0P0, pP1P0);
        V2D_LineDouble expResult = new V2D_LineDouble(pP0P0, pP0P1);
        V2D_LineDouble result = instance.rotate(pP0P0, theta, epsilon);
        assertTrue(expResult.equals(epsilon, result));
        // Test 2
        instance = new V2D_LineDouble(pP0P0, pP0P1);
        expResult = new V2D_LineDouble(pP0P0, pP1P0);
        result = instance.rotate(pP0P0, theta, epsilon);
        assertTrue(expResult.equals(epsilon, result));
    }

    /**
     * Test of isCollinear method, of class V2D_LineDouble.
     */
    @Test
    public void testIsCollinear_double_V2D_LineDouble_V2D_PointDoubleArr() {
        System.out.println("isCollinear");
        double epsilon = 1d / 10000000d;
        V2D_LineDouble l;
        V2D_PointDouble[] points = new V2D_PointDouble[2];
        // Test 1
        l = new V2D_LineDouble(pN1N1, pP1P1);
        points[0] = pP2P2;
        points[1] = pN2N2;
        assertTrue(V2D_LineDouble.isCollinear(epsilon, l, points));
        // Test 2
        points[1] = pN2N1;
        assertFalse(V2D_LineDouble.isCollinear(epsilon, l, points));
        // Test 3
        points[0] = pN1N2;
        assertFalse(V2D_LineDouble.isCollinear(epsilon, l, points));
    }

    /**
     * Test of isCollinear method, of class V2D_LineDouble.
     */
    @Test
    public void testIsCollinear_double_V2D_PointDoubleArr() {
        System.out.println("isCollinear");
        double epsilon = 1d / 10000000d;
        V2D_PointDouble[] points = new V2D_PointDouble[3];
        points[0] = pP0P0;
        points[1] = pP1P1;
        points[2] = pP2P2;
        assertTrue(V2D_LineDouble.isCollinear(epsilon, points));
        points[2] = pN2N2;
        assertTrue(V2D_LineDouble.isCollinear(epsilon, points));
        points[2] = pN1N1;
        assertTrue(V2D_LineDouble.isCollinear(epsilon, points));
        points[2] = pN2N1;
        assertFalse(V2D_LineDouble.isCollinear(epsilon, points));
        // P2*
        points[0] = pP2P2;
        points[1] = pP2P1;
        points[2] = pP2P0;
        assertTrue(V2D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2N1;
        assertTrue(V2D_LineDouble.isCollinear(epsilon, points));
        points[2] = pP2N2;
        assertTrue(V2D_LineDouble.isCollinear(epsilon, points));
        points[2] = pN2N1;
        assertFalse(V2D_LineDouble.isCollinear(epsilon, points));
    }

}
