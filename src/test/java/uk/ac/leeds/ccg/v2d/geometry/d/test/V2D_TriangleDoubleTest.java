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

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_Double;
import uk.ac.leeds.ccg.v2d.core.d.V2D_EnvironmentDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_ConvexHullDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_EnvelopeDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_FiniteGeometryDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_GeometryDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_LineSegmentDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_PointDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_RayDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_TriangleDouble;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_VectorDouble;

/**
 * Test of V2D_TriangleDouble class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_TriangleDoubleTest extends V2D_TestDouble {

    public V2D_TriangleDoubleTest() {
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
     * Test of isIntersectedBy method, of class V2D_TriangleDouble.
     */
    @Test
    public void testIsAligned_V2D_PointDouble_double() {
        System.out.println("isAligned");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble pt = pP0N1;
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, pP0P2, pP0N2, pP2P0);
        assertTrue(instance.isAligned(pt, epsilon));
        pt = pN1P0;
        assertFalse(instance.isAligned(pt, epsilon));
        instance = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P1);
        pt = pP2P2;
        assertFalse(instance.isAligned(pt, epsilon));
        
    }

    /**
     * Test of isIntersectedBy method, of class V2D_TriangleDouble.
     */
    @Test
    public void testIsAligned_V2D_LineSegmentDouble_double() {
        System.out.println("isAligned");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineSegmentDouble ls;
        V2D_TriangleDouble instance;
        instance = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P1);
        ls = new V2D_LineSegmentDouble(pP2P2, pP2P1);
        assertFalse(instance.isAligned(ls, epsilon));
        
    }

    /**
     * Test of isIntersectedBy method, of class V2D_TriangleDouble.
     */
    @Test
    public void testIsIntersectedBy() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble pt = pP0P0;
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, pN1N1, pP0P2, pP1N1);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 2
        pt = pP0P1;
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 3
        pt = pP1P2;
        assertFalse(instance.isIntersectedBy(pt, epsilon));
        // Test 4
        pt = pN1P2;
        assertFalse(instance.isIntersectedBy(pt, epsilon));
    }

    /**
     * Test of getEnvelope method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        V2D_EnvelopeDouble expResult = new V2D_EnvelopeDouble(pP0P0, pP0P1, pP1P0);
        V2D_EnvelopeDouble result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getArea method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetArea() {
        System.out.println("getArea");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        double expResult = 1d / 2d;
        double result = instance.getArea();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of translate method, of class V2D_TriangleDouble.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of getPerimeter method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetPerimeter() {
        System.out.println("getPerimeter");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        double expResult = 2d + Math.sqrt(2);
        double result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        expResult = 2d + Math.sqrt(2);
        result = instance.getPerimeter();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetIntersection_V2D_LineDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_LineDouble l = new V2D_LineDouble(pP1N1, pP1P2);
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, pP0P0, pP1P1, pP2P0);
        V2D_GeometryDouble expResult = new V2D_LineSegmentDouble(pP1P0, pP1P1);
        V2D_GeometryDouble result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        System.out.println("getIntersection");
        // Test 2
        instance = new V2D_TriangleDouble(env, P0P0, P1P0, P1P2, P2P0);
        l = new V2D_LineDouble(pP1P1, pP1N1);
        result = instance.getIntersection(l, epsilon);
        expResult = new V2D_LineSegmentDouble(pP1P0, pP1P2);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 3
        l = new V2D_LineDouble(pP1P0, pP2P0);
        expResult = new V2D_LineSegmentDouble(pP1P0, pP2P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 4
        l = new V2D_LineDouble(pP1P0, pP2P0);
        expResult = new V2D_LineSegmentDouble(pP1P0, pP2P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
    }

    /**
     * Test of getCentroid method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetCentroid() {
        System.out.println("getCentroid");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble instance;
        V2D_PointDouble expResult;
        V2D_PointDouble result;
        // Test
        instance = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP1P1);
        expResult = new V2D_PointDouble(2d / 3d, 1d / 3d);
        result = instance.getCentroid();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of toString method, of class V2D_TriangleDouble.
     */
    @Test
    public void testToString() {
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        System.out.println("toString");
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, P0P0, P1P0, P0P1, P0P0);
        String expResult = """
                           V2D_TriangleDouble(
                            offset=(V2D_VectorDouble(dx=0.0, dy=0.0)),
                            p=(V2D_VectorDouble(dx=1.0, dy=0.0)),
                            q=(V2D_VectorDouble(dx=0.0, dy=1.0)),
                            r=(V2D_VectorDouble(dx=0.0, dy=0.0)))""";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V2D_TriangleDouble.
     */
    @Test
    public void testIsIntersectedBy_V2D_PointDouble() {
        System.out.println("isIntersectedBy");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble pt;
        V2D_TriangleDouble instance;
        instance = new V2D_TriangleDouble(env, P0P0, P1P0, P0P1, P0P0);
        assertTrue(instance.isIntersectedBy(pP1P0, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P1, epsilon));
        assertTrue(instance.isIntersectedBy(pP0P0, epsilon));
        pt = new V2D_PointDouble(P1P0, P0P0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 5
        pt = new V2D_PointDouble(P0P1, P0P0);
        assertTrue(instance.isIntersectedBy(pt, epsilon));
        // Test 6
        instance = new V2D_TriangleDouble(env, P0P0, P1P0, P1P2, P2P0);
        assertTrue(instance.isIntersectedBy(pP1P2, epsilon));
        assertTrue(instance.isIntersectedBy(pP1P1, epsilon));
        assertFalse(instance.isIntersectedBy(pN1P0, epsilon));
    }

    /**
     * Test of getIntersection method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetIntersection_V2D_LineSegmentDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineSegmentDouble l;
        V2D_TriangleDouble instance;
        V2D_GeometryDouble expResult;
        V2D_GeometryDouble result;
        // Test 1
        instance = new V2D_TriangleDouble(env, pP1P0, pP1P2, pP2P0);
        l = new V2D_LineSegmentDouble(pP1P1, pP1N1);
        expResult = new V2D_LineSegmentDouble(pP1P1, pP1P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                 epsilon, (V2D_LineSegmentDouble) result));
        // Test 2
        l = new V2D_LineSegmentDouble(pP1P0, pP2P0);
        expResult = new V2D_LineSegmentDouble(pP1P0, pP2P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 3
        l = new V2D_LineSegmentDouble(pP1P0, pP2P0);
        expResult = new V2D_LineSegmentDouble(pP1P0, pP2P0);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 4
        l = new V2D_LineSegmentDouble(pP2N2, pP2P1);
        instance = new V2D_TriangleDouble(env, P0P0, P2P2,
                new V2D_VectorDouble(4d, 0d, 0d));
        expResult = new V2D_LineSegmentDouble(pP2P0, pP2P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 5
        l = new V2D_LineSegmentDouble(pP0P0, pP0P1);
        instance = new V2D_TriangleDouble(env, pN2N2, pP0P2, pP2N2);
        expResult = new V2D_LineSegmentDouble(pP0P0, pP0P1);
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 6
        l = new V2D_LineSegmentDouble(new V2D_PointDouble(4d, -2d), pP2P0);
        instance = new V2D_TriangleDouble(env, pP0P0, new V2D_PointDouble(4d, 0d), new V2D_PointDouble(2d, -4d));
        expResult = new V2D_LineSegmentDouble(pP2P0, new V2D_PointDouble(10d/3d, - 4d/3d));
        result = instance.getIntersection(l, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
    }

    /**
     * Test of equals method, of class V2D_TriangleDouble.
     */
    @Test
    public void testEquals_V2D_TriangleDouble() {
        System.out.println("equals");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t = new V2D_TriangleDouble(env, pP1P0, pP1P2, pP2P0);
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, P1P0, P0P0, P0P2, P1P0);
        boolean result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 2
        instance = new V2D_TriangleDouble(env, P1P0, P0P2, P0P0, P1P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 3
        instance = new V2D_TriangleDouble(env, P1P0, P0P2, P1P0, P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 4
        instance = new V2D_TriangleDouble(env, P1P0, P1P0, P0P2, P0P0);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        // Test 4
        instance = new V2D_TriangleDouble(env, P1P0, P1P0, P0P2, P0P0);
        t = new V2D_TriangleDouble(env, P0P0, instance);
        result = instance.equals(t, epsilon);
        assertTrue(result);
        
        
    }

    /**
     * Test of rotate method, of class V2D_TriangleDouble.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        double theta;
        V2D_TriangleDouble instance;
        V2D_TriangleDouble result;
        V2D_TriangleDouble expResult;
        double Pi = Math.PI;
        // Test 1
        instance = new V2D_TriangleDouble(env, pP1P0, pP0P1, pP1P1);
        V2D_PointDouble pt = pP0P0;
        theta = Pi;
        result = instance.rotate(pt, theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pN1P0, pN1N1, pP0N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 2
        instance = new V2D_TriangleDouble(env, pP1P0, pP0P1, pP1P1);
        theta = Pi / 2d;
        result = instance.rotate(pt, theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pP0N1, pP1P0, pP1N1);
        //expResult = new V2D_TriangleDouble(env, pN1P0, pN1P1, pP0P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 3
        instance = new V2D_TriangleDouble(env, pP2P0, pP0P2, pP2P2);
        theta = Pi;
        result = instance.rotate(pt,  theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pN2P0, pN2N2, pP0N2);
        assertTrue(expResult.equals(result, epsilon));
        // Test 4
        instance = new V2D_TriangleDouble(env, pP2P0, pP0P2, pP2P2);
        theta = Pi / 2d;
        result = instance.rotate(pt, theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pP0N2, pP2P0, pP2N2);
        assertTrue(expResult.equals(result, epsilon));
        // Test 5
        instance = new V2D_TriangleDouble(env, pP2P0, pP0P2, pP2P2);
        theta = 3d * Pi / 2d;
        result = instance.rotate(pt, theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pN2P0, pN2P2, pP0P2);
        assertTrue(expResult.equals(result, epsilon));
        // Test 5
        instance = new V2D_TriangleDouble(env, pP1P0, pP0P1, pP1P1);
        instance.translate(P1P0);
        theta = Pi;
        result = instance.rotate(pt, theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pN2P0, pN2N1, pN1N1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 6
        instance = new V2D_TriangleDouble(env, pP1P0, pP0P1, pP1P1);
        theta = Pi;
        result = instance.rotate(pP0P1, theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pN1P1, pN1P2, pP0P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 7
        instance = new V2D_TriangleDouble(env, pP1P0, pP0P1, pP1P1);
        theta = Pi / 2d;
        result = instance.rotate(pP0P1, theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pP0P0, pN1P0, pP0P1);
        assertTrue(expResult.equals(result, epsilon));
        // Test 8
        instance = new V2D_TriangleDouble(env, pP2P0, pP0P2, pP2P2);
        theta = Pi;
        result = instance.rotate(pP0P1, theta, epsilon);
        expResult = new V2D_TriangleDouble(env, pN2P0, pP0P0, pN2P2);
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getIntersection method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetIntersection_V2D_RayDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t;
        V2D_RayDouble r;
        V2D_GeometryDouble expResult;
        V2D_GeometryDouble result;
        // Test 1
        t = new V2D_TriangleDouble(env, pP0P1, pP1P0, pP1P1);
        r = new V2D_RayDouble(pP0P0, pP1P0);
        result = t.getIntersection(r, epsilon);
        expResult = pP1P0;
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 2
        t = new V2D_TriangleDouble(env, pP0N2, pP0P2, pP2P0);
        r = new V2D_RayDouble(pP1P0, pP2P0);
        result = t.getIntersection(r, epsilon);
        expResult = new V2D_LineSegmentDouble(pP1P0, pP2P0);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 3
        r = new V2D_RayDouble(pN1P0, pP2P0);
        result = t.getIntersection(r, epsilon);
        expResult = new V2D_LineSegmentDouble(pP0P0, pP2P0);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
//        // Test 4
//        r = new V2D_RayDouble(pN2P0N2, pP0P0P0);
//        result = t.getIntersection(r, epsilon);
//        expResult = pP0P0P0;
//        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
//        // Test 5
//        r = new V2D_RayDouble(pN2P0N2, pP0N1P0);
//        expResult = pP0N1P0;
//        result = t.getIntersection(r, epsilon);
//        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
//        // Test 6
//        r = new V2D_RayDouble(pN2P0N2, pN1P0P0);
//        assertNull(t.getIntersection(r, epsilon));
//        // Test 7
//        t = new V2D_TriangleDouble(env, pP0N2P0, pP0P2P0, pP2P0P0);
//        r = new V2D_RayDouble(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersection(r, epsilon);
//        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
//        // Test 8
//        t = new V2D_TriangleDouble(env, pP0N2P0, pP0P2P0, pP2P2P1);
//        r = new V2D_RayDouble(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersection(r, epsilon);
//        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
//        // Test 9
//        t = new V2D_TriangleDouble(env, pN1N2P0, pN1P2P0, pP2P2P0);
//        r = new V2D_RayDouble(pN2N2N2, pN1N1N1);
//        expResult = pP0P0P0;
//        result = t.getIntersection(r, epsilon);
//        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
//        // Test 10
//        t = new V2D_TriangleDouble(env, pN1N2P0, pN1P2P0, pP2P2N1);
//        r = new V2D_RayDouble(pN2N2N2, pN1N1N1);
//        double nq = -1d / 4d;
//        expResult = new V2D_PointDouble(nq, nq, nq);
//        result = t.getIntersection(r, epsilon);
//        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
    }

//    /**
//     * For getting a ray from the camera focal point through the centre of the
//     * screen pixel with ID id.
//     *
//     * @param id The ID of the screen pixel.
//     * @return The ray from the camera focal point through the centre of the
//     * screen pixel.
//     */
//    protected V2D_RayDouble getRay(int row, int col, V2D_RectangleDouble screen,
//            V2D_PointDouble pt, V2D_VectorDouble vd, V2D_VectorDouble v2d,
//            double epsilon) {
//        V2D_VectorDouble rv = v2d.multiply((double) row);
//        V2D_VectorDouble cv = vd.multiply((double) col);
//        V2D_PointDouble rcpt = new V2D_PointDouble(screen.getP());
//        rcpt.translate(rv.add(cv));
//        return new V2D_RayDouble(pt, rcpt);
//    }

    /**
     * Test of getIntersection method, of class V2D_TriangleDouble.
     *
     * Look for some examples here:
     * https://math.stackexchange.com/questions/1220102/how-do-i-find-the-intersection-of-two-2d-triangles
     */
    @Test
    public void testGetIntersection_V2D_TriangleDouble() {
        System.out.println("getIntersection");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        V2D_GeometryDouble expResult = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        V2D_GeometryDouble result = instance.getIntersection(t, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals((V2D_TriangleDouble) result, epsilon));
        // Test 2
        t = new V2D_TriangleDouble(env, pN1N1, pP0P2, pP2N1);
        instance = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        expResult = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals((V2D_TriangleDouble) result, epsilon));
        // Test 3
        t = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        instance = new V2D_TriangleDouble(env, pN1N1, pP0P2, pP2N1);
        expResult = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals((V2D_TriangleDouble) result, epsilon));
        // Test 4
        t = new V2D_TriangleDouble(env, pP0P0, pP2P0, pP2P2);
        instance = new V2D_TriangleDouble(env, pP1P0, pP2P0, pP2P2);
        expResult = new V2D_TriangleDouble(env, pP1P0, pP2P0, pP2P2);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals((V2D_TriangleDouble) result, epsilon));
        // Test 5
        t = new V2D_TriangleDouble(env, pP0P0, pP2P0, pP2P2);
        instance = new V2D_TriangleDouble(env, pP1P0, pP2P0, new V2D_PointDouble(3d, 2d));
        expResult = new V2D_TriangleDouble(env, pP1P0, pP2P0, pP2P1);
        result = instance.getIntersection(t, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals((V2D_TriangleDouble) result, epsilon));
        // Test 6 - Test 7 Are more complex intersections
        V2D_PointDouble origin = new V2D_PointDouble(0d, 0d);
        ArrayList<V2D_TriangleDouble> expected;
        V2D_TriangleDouble t0;
        V2D_TriangleDouble t1;
        double theta;
        // Test 6
        t0 = new V2D_TriangleDouble(env, 
                new V2D_PointDouble(-50d, -50d),
                new V2D_PointDouble(0d, 50d),
                new V2D_PointDouble(50d, -50d));
        theta = Math.PI;
        t1 = t0.rotate(origin, theta, epsilon);
        expected = new ArrayList<>();
        /** 
         * Using an additional centroid point and connecting each edge with this 
         * would result in 4 triangles.
         */
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(0d, 0d),
//                new V2D_PointDouble(-25d, 0d),
//                new V2D_PointDouble(0d, 50d)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(0d, 0d),
//                new V2D_PointDouble(0d, 50d),
//                new V2D_PointDouble(25d, 0d)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(0d, 0d),
//                new V2D_PointDouble(25d, 0d),
//                new V2D_PointDouble(0d, -50d)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(0d, 0d),
//                new V2D_PointDouble(0d, -50d),
//                new V2D_PointDouble(-25d, 0d)));
        expected.add(new V2D_TriangleDouble(env, 
                new V2D_PointDouble(0d, -50d),
                new V2D_PointDouble(25d, 0d),
                new V2D_PointDouble(0d, 50d)));
        expected.add(new V2D_TriangleDouble(env, 
                new V2D_PointDouble(0d, 50d),
                new V2D_PointDouble(-25d, 0d),
                new V2D_PointDouble(0d, -50d)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(-25d, 0d),
//                new V2D_PointDouble(0d, 50d),
//                new V2D_PointDouble(25d, 0d)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(-25d, 0d),
//                new V2D_PointDouble(25d, 0d),
//                new V2D_PointDouble(0d, -50d)));
        // Calculate the intersection
        // We are expecting a convex hull with 4 points that can be tested to 
        // see if they are made up of the two triangles as expected.
        V2D_FiniteGeometryDouble gi = t0.getIntersection(t1, epsilon);
        ArrayList<V2D_TriangleDouble> git = ((V2D_ConvexHullDouble) gi).getTriangles();
        for (int i = 0; i < git.size(); i ++) {
            assertTrue(expected.get(i).equals(git.get(i), epsilon));
        }
        // Test 7
        t0 = new V2D_TriangleDouble(env, new V2D_PointDouble(-30d, -30d),
        new V2D_PointDouble(0d, 60d),
        new V2D_PointDouble(30d, -30d));
        theta = Math.PI;
        t1 = t0.rotate(origin, theta, epsilon);
        expected = new ArrayList<>();
        expected.add(new V2D_TriangleDouble(env, 
                new V2D_PointDouble(-10d, -30d),
                new V2D_PointDouble(10d, -30d),
                new V2D_PointDouble(20d, 0d)));
        expected.add(new V2D_TriangleDouble(env, 
                new V2D_PointDouble(-10d, -30d),
                new V2D_PointDouble(20d, 0d),
                new V2D_PointDouble(10d, 30d)));
        expected.add(new V2D_TriangleDouble(env, 
                new V2D_PointDouble(-10d, -30d),
                new V2D_PointDouble(10d, 30d),
                new V2D_PointDouble(-10d, 30d)));
        expected.add(new V2D_TriangleDouble(env, 
                new V2D_PointDouble(-10d, -30d),
                new V2D_PointDouble(-10d, 30d),
                new V2D_PointDouble(-20d, 0)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(-20d, 0d),
//                new V2D_PointDouble(-10d, 30d),
//                new V2D_PointDouble(20d, 0d)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(-20d, 0d),
//                new V2D_PointDouble(20d, 0d),
//                new V2D_PointDouble(10d, 30d)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(-20d, 0d),
//                new V2D_PointDouble(10d, 30d),
//                new V2D_PointDouble(-10d, -30d)));
//        expected.add(new V2D_TriangleDouble(env, 
//                new V2D_PointDouble(-20d, 0d),
//                new V2D_PointDouble(-10d, -30d),
//                new V2D_PointDouble(10d, -30d)));
        // Calculate the intersection
        // Expecting a convex hull with 6 points that can be tested to 
        // see if they are made up of the four triangles as expected.
        gi = t0.getIntersection(t1, epsilon);
        git = ((V2D_ConvexHullDouble) gi).getTriangles();
        for (int i = 0; i < git.size(); i ++) {
            assertTrue(expected.get(i).equals(git.get(i), epsilon));
        }
    }

    /**
     * Test of translate method, of class V2D_TriangleDouble.
     */
    @Test
    public void testTranslate() {
        System.out.println("translate");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, P0P0, P1P0, P0P1, P1P1);
        instance.translate(V2D_VectorDouble.I);
        V2D_TriangleDouble expResult = new V2D_TriangleDouble(env, P0P0, P2P0, P1P1, P2P1);
        assertTrue(expResult.equals(instance, epsilon));
        // Test 2
        instance = new V2D_TriangleDouble(env, P0P0, P1P0, P0P1, P1P1);
        instance.translate(V2D_VectorDouble.IJ);
        expResult = new V2D_TriangleDouble(env, P1P1, P1P0, P0P1, P1P1);
        assertTrue(expResult.equals(instance, epsilon));
    }

    /**
     * Test of getGeometry method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetGeometry_3args_1() {
        System.out.println("getGeometry");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble p;
        V2D_PointDouble q;
        V2D_PointDouble r;
        V2D_GeometryDouble expResult;
        V2D_GeometryDouble result;
        // Test 1
        p = new V2D_PointDouble(pP0P0);
        q = new V2D_PointDouble(pP0P0);
        r = new V2D_PointDouble(pP0P0);
        expResult = new V2D_PointDouble(pP0P0);
        result = V2D_TriangleDouble.getGeometry(env, p, q, r, epsilon);
        assertTrue(((V2D_PointDouble) expResult).equals((V2D_PointDouble) result));
        // Test 2
        p = new V2D_PointDouble(pP1P0);
        q = new V2D_PointDouble(pP0P0);
        r = new V2D_PointDouble(pP0P0);
        expResult = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        result = V2D_TriangleDouble.getGeometry(env, p, q, r, epsilon);
        assertTrue(((V2D_LineSegmentDouble) expResult).equalsIgnoreDirection(
                epsilon, (V2D_LineSegmentDouble) result));
        // Test 2
        p = new V2D_PointDouble(pP1P0);
        q = new V2D_PointDouble(pP0P1);
        r = new V2D_PointDouble(pP0P0);
        expResult = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        result = V2D_TriangleDouble.getGeometry(env, p, q, r, epsilon);
        assertTrue(((V2D_TriangleDouble) expResult).equals((V2D_TriangleDouble) result, epsilon));
    }

    /**
     * Test of getOpposite method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetOpposite() {
        System.out.println("getOpposite");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineSegmentDouble l = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        V2D_TriangleDouble instance = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        V2D_PointDouble expResult = pP0P1;
        V2D_PointDouble result = instance.getOpposite(l, epsilon);
        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V2D_PointDouble()}.
     */
    @Test
    public void testGetDistance_V2D_PointDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V2D_PointDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble p;
        V2D_TriangleDouble t;
        double expResult;
        // Test 1
        p = pP0P0;
        t = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        expResult = 0d;
        double result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        p = pP1P1;
        t = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        expResult = 1d / 2d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        p = pN1N1;
        t = new V2D_TriangleDouble(env, pN2N2, pP2N2, pN2P2);
        expResult = 0d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        p = new V2D_PointDouble(-1, -10);
        expResult = 64d;
        result = t.getDistanceSquared(p, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getDistance method, of class V2D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V2D_LineDouble()}.
     */
    @Test
    public void testGetDistance_V2D_LineDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineDouble l;
        double expResult;
        double result;
        V2D_TriangleDouble instance;
        // Test 1
        l = new V2D_LineDouble(pP0P0, pP1P0);
        instance = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        l = new V2D_LineDouble(pP0P1, pP1P1);
        instance = new V2D_TriangleDouble(env, pN2N2, pP2N2, pN2P2);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V2D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V2D_LineSegmentDouble()}.
     */
    @Test
    public void testGetDistance_V2D_LineSegmentDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V2D_LineSegmentDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_LineSegmentDouble l;
        V2D_TriangleDouble instance;
        double expResult;
        double result;
        // Test 1
        l = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        instance = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 2
        l = new V2D_LineSegmentDouble(pP0P0, pP1P0);
        instance = new V2D_TriangleDouble(env, pN2N2, pP2N2, pN2P2);
        expResult = 0d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
        // Test 3
        l = new V2D_LineSegmentDouble(pP0P1, pP1P1);
        expResult = 1d / 2d;
        result = instance.getDistanceSquared(l, epsilon);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V2D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V2D_PlaneDouble()}.
     */
    @Test
    public void testGetDistance_V2D_PlaneDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistance method, of class V2D_TriangleDouble covered by
     * {@link #testGetDistanceSquared_V2D_TriangleDouble()}.
     */
    @Test
    public void testGetDistance_V2D_TriangleDouble() {
        System.out.println("getDistance");
    }

    /**
     * Test of getDistanceSquared method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetDistanceSquared_V2D_TriangleDouble() {
        System.out.println("getDistanceSquared");
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t;
        V2D_TriangleDouble instance;
        double expResult;
        double result;
        // Test 1
        t = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        instance = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        expResult = 0d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        instance = new V2D_TriangleDouble(env, pP1P0, pP1P1, pP0P1);
        expResult = 0d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V2D_TriangleDouble(env, pP0P0, pP1P0, pP0P1);
        instance = new V2D_TriangleDouble(env, pP1P1, pP1P2, pP0P2);
        expResult = 1d / 2d;
        result = instance.getDistanceSquared(t, epsilon);
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }
    
    /**
     * Test of getCircumcentre method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetCircumcentre() {
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_PointDouble a = new V2D_PointDouble(3, 2);
        V2D_PointDouble b = new V2D_PointDouble(1, 4);
        V2D_PointDouble c = new V2D_PointDouble(5, 4);
        V2D_TriangleDouble t = new V2D_TriangleDouble(env, a, b, c);
        V2D_PointDouble expResult = new V2D_PointDouble(3, 4);
        V2D_PointDouble result = t.getCircumcenter();
        assertTrue(result.equals(expResult, epsilon));
        // Test 2
         a = new V2D_PointDouble(-2, -3);
         b = new V2D_PointDouble(-1, 0);
         c = new V2D_PointDouble(7, -6);
         t = new V2D_TriangleDouble(env, a, b, c);
         expResult = new V2D_PointDouble(3, -3);
         result = t.getCircumcenter();
        assertTrue(result.equals(expResult, epsilon));
    }
    
    /**
     * Test of getAngleP method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetAngleP() {
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P1);
        double expResult = Math.PI/4d;
        double result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_TriangleDouble(env, pP0P0, pP1P1, pP1P0);
        expResult = Math.PI/4d;
        result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        expResult = Math.PI/2d;
        result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        t = new V2D_TriangleDouble(env, pP1P1, pP1P2, pP2P1);
        expResult = Math.PI/2d;
        result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        t = new V2D_TriangleDouble(env, pP1P1, pP2P2, pP2P1);
        expResult = Math.PI/4d;
        result = t.getAngleP();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }

    /**
     * Test of getAngleQ method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetAngleQ() {
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P1);
        double expResult = Math.PI/2d;
        double result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_TriangleDouble(env, pP0P0, pP1P1, pP1P0);
        expResult = Math.PI/4d;
        result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        expResult = Math.PI/4d;
        result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        t = new V2D_TriangleDouble(env, pP1P1, pP1P2, pP2P1);
        expResult = Math.PI/4d;
        result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        t = new V2D_TriangleDouble(env, pP1P1, pP2P2, pP2P1);
        expResult = Math.PI/4d;
        result = t.getAngleQ();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }
    
    /**
     * Test of getAngleR method, of class V2D_TriangleDouble.
     */
    @Test
    public void testGetAngleR() {
        double epsilon = 1d / 10000000d;
        V2D_EnvironmentDouble env = new V2D_EnvironmentDouble(epsilon);
        V2D_TriangleDouble t = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P1);
        double expResult = Math.PI/4d;
        double result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 2
        t = new V2D_TriangleDouble(env, pP0P0, pP1P1, pP1P0);
        expResult = Math.PI/2d;
        result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 3
        t = new V2D_TriangleDouble(env, pP0P0, pP0P1, pP1P0);
        expResult = Math.PI/4d;
        result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 4
        t = new V2D_TriangleDouble(env, pP1P1, pP1P2, pP2P1);
        expResult = Math.PI/4d;
        result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
        // Test 5
        t = new V2D_TriangleDouble(env, pP1P1, pP2P2, pP2P1);
        expResult = Math.PI/2d;
        result = t.getAngleR();
        assertTrue(Math_Double.equals(expResult, result, epsilon));
    }
}
