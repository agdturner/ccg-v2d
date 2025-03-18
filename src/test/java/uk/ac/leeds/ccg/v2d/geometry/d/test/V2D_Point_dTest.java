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
package uk.ac.leeds.ccg.v2d.geometry.d.test;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_AABB_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Point_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Vector_d;

/**
 * Test of V2D_Point_d class.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Point_dTest extends V2D_Test_d {

    public V2D_Point_dTest() {
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
     * Test of equals method, of class V2D_Point_d.
     */
    @Test
    public void testEquals_V2D_Point_d() {
        System.out.println("equals");
        V2D_Point_d instance = pP0P0;
        double x = 0d;
        double y = 0d;
        V2D_Point_d p = new V2D_Point_d(env, x, y);
        assertTrue(instance.equals(p));
        // Test 2
        x = 1d;
        y = 10d;
        instance = new V2D_Point_d(env, x, y);
        p = new V2D_Point_d(env, x, y);
        assertTrue(instance.equals(p));
    }

    /**
     * Test of isBetween method, of class V2D_Point_d.
     */
    @Test
    public void testIsBetween_double_V2D_Point_d_V2D_Point_d() {
        System.out.println("isBetween");
        // Test 1
        V2D_Point_d p = pP0P0;
        V2D_Point_d a = pN1P0;
        V2D_Point_d b = pP1P0;
        double epsilon = 1 / 100000000d;
        assertTrue(p.isBetween(epsilon, a, b));
        // Test 2
        p = pP0P0;
        a = pP0N1;
        b = pP0P1;
        assertTrue(p.isBetween(epsilon, a, b));
        // Test 3
        p = pP0P0;
        a = pN1N1;
        b = pP1P1;
        assertTrue(p.isBetween(epsilon, a, b));
    }

    /**
     * Test of getAABB method, of class V2D_Point_d.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V2D_AABB_d expResult = new V2D_AABB_d(pP1P2);
        V2D_AABB_d result = pP1P2.getAABB();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getDistance method, of class V2D_Point_d.
     */
    @Test
    public void testGetDistance_V2D_Point_d() {
        System.out.println("getDistance");
        V2D_Point_d p = V2D_Point_d.ORIGIN;
        V2D_Point_d instance = V2D_Point_d.ORIGIN;
        double expResult = 0d;
        double result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 2
        instance = pP1P0;
        expResult = 1d;
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 3
        instance = pP1P1;
        expResult = Math.sqrt(2d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 4
        instance = new V2D_Point_d(env, 3d, 4d);
        expResult = 5d;
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 5
        instance = new V2D_Point_d(env, -3d, -4d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 6
        instance = new V2D_Point_d(env, -3d, 4d);
        result = instance.getDistance(p);
        assertEquals(expResult, result);
        // Test 7
        instance = pP0P0;
        expResult = 0d;
        result = instance.getDistance(instance);
        assertEquals(expResult, result);
        // Test 15
        p = pP0P0;
        instance = pP1P0;
        expResult = 1d;
        result = instance.getDistance(p);
        assertTrue(expResult == result);
    }

    /**
     * Test of getDistanceSquared method, of class V2D_Point_d.
     */
    @Test
    public void testGetDistanceSquared_V2D_Point_d() {
        System.out.println("getDistanceSquared");
        double expResult = 0d;
        double result = pP0P0.getDistanceSquared(pP0P0);
        assertEquals(expResult, result);
        // Test 2
        V2D_Point_d instance = new V2D_Point_d(env, 3d, 4d);
        expResult = 25d;
        result = instance.getDistanceSquared(pP0P0);
        assertTrue(expResult == result);
        // Test 3
        V2D_Point_d p = pP1P0;
        instance = pP1P0;
        expResult = 0d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
        // Test 4
        p = pP1P0;
        instance = pP2P0;
        expResult = 1d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
        // Test 5
        p = pP1P0;
        instance = pP0P1;
        expResult = 2d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
        // Test 6
        p = pP1P0;
        instance = pP1P1;
        expResult = 1d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
        // Test 7
        p = pP0P0;
        instance = pP1P1;
        expResult = 2d;
        result = instance.getDistanceSquared(p);
        assertTrue(expResult == result);
    }

    /**
     * Test of toString method, of class V2D_Point_d.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        V2D_Point_d instance = pP0P1;
        String expResult = "V2D_Point_d("
                + "offset=V2D_Vector_d(dx=0.0, dy=0.0), "
                + "rel=V2D_Vector_d(dx=0.0, dy=1.0))";
        String result = instance.toString();
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toString method, of class V2D_Point_d.
     */
    @Test
    public void testToString_String() {
        System.out.println("toString");
        String pad = "";
        V2D_Point_d instance = pP0P1;
        String expResult = """
                           V2D_Point_d
                           (
                            offset=V2D_Vector_d
                            (
                             dx=0.0,
                             dy=0.0
                            )
                            ,
                            rel=V2D_Vector_d
                            (
                             dx=0.0,
                             dy=1.0
                            )
                           )""";
        String result = instance.toString(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }

    /**
     * Test of toStringFields method, of class V2D_Point_d.
     */
    @Test
    public void testToStringSimple() {
        System.out.println("toStringSimple");
        String pad = "";
        V2D_Point_d instance = pP0P1;
        String expResult = """
                           V2D_Point_d(offset=V2D_Vector_d(dx=0.0, dy=0.0), rel=V2D_Vector_d(dx=0.0, dy=1.0))""";
        String result = instance.toStringSimple(pad);
        //System.out.println(result);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of getVector method, of class V2D_Point_d.
     */
    @Test
    public void testGetVector() {
        System.out.println("getVector");
        V2D_Point_d instance = pP0P1;
        V2D_Vector_d expResult = new V2D_Vector_d(0, 1);
        V2D_Vector_d result = instance.getVector();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getX method, of class V2D_Point_d.
     */
    @Test
    public void testGetX() {
        System.out.println("getX");
        V2D_Point_d instance = pP0P1;
        double expResult = 0d;
        double result = instance.getX();
        assertTrue(expResult == result);
    }

    /**
     * Test of getY method, of class V2D_Point_d.
     */
    @Test
    public void testGetY() {
        System.out.println("getY");
        V2D_Point_d instance = pP0P1;
        double expResult = 1d;
        double result = instance.getY();
        assertTrue(expResult == result);
    }

    /**
     * Test of isOrigin method, of class V2D_Point_d.
     */
    @Test
    public void testIsOrigin() {
        System.out.println("isOrigin");
        V2D_Point_d instance = pP0P1;
        assertFalse(instance.isOrigin());
        instance = pP0P0;
        assertTrue(instance.isOrigin());
    }

    /**
     * Test of getLocation method, of class V2D_Point_d.
     */
    @Test
    public void testGetLocation() {
        System.out.println("getLocation");
        V2D_Point_d instance = V2D_Point_d.ORIGIN;
        assertEquals(0, instance.getLocation());
        // PP
        instance = pP0P0;
        assertEquals(0, instance.getLocation());
        instance = pP0P1;
        assertEquals(1, instance.getLocation());
        instance = pP1P0;
        assertEquals(1, instance.getLocation());
        instance = pP1P1;
        assertEquals(1, instance.getLocation());
        // PN
        instance = pP0N1;
        assertEquals(2, instance.getLocation());
        // NP
        instance = pN1P0;
        assertEquals(3, instance.getLocation());
        // NN
        instance = pN1N1;
        assertEquals(4, instance.getLocation());
    }

    /**
     * Test of rotate method, of class V2D_Point_d.
     */
    @Test
    public void testRotate() {
        System.out.println("rotate");
        double Pi = Math.PI;
        //double epsilon = 0.0000000000000001D; // epsilon too small
        double epsilon = 0.000000000000001D;
        V2D_Point_d pt = pP0P0;
        V2D_Point_d instance = new V2D_Point_d(pP1P0);
        double theta = Pi;
        V2D_Point_d result = instance.rotate(pt, theta);
        V2D_Point_d expResult = pN1P0;
        assertTrue(expResult.equals(result, epsilon));
        // Test 2
        instance = new V2D_Point_d(pP0P1);
        theta = Pi;
        result = instance.rotate(pt, theta);
        expResult = pP0N1;
        assertTrue(expResult.equals(result, epsilon));
        // Test 3
        V2D_Vector_d offset = new V2D_Vector_d(2, 0);
        V2D_Vector_d rel = new V2D_Vector_d(1, 0);
        instance = new V2D_Point_d(env, offset, rel);
        theta = Pi;
        result = instance.rotate(pt, theta);
        expResult = new V2D_Point_d(env, -3, 0);
        assertTrue(expResult.equals(result, epsilon));
        // Test 4
        offset = new V2D_Vector_d(1, 0);
        rel = new V2D_Vector_d(2, 0);
        instance = new V2D_Point_d(env, offset, rel);
        theta = Pi;
        result = instance.rotate(pt, theta);
        expResult = new V2D_Point_d(env, -3, 0);
        assertTrue(expResult.equals(result, epsilon));

        // Test 5
        pt = pP0P1;
        instance = new V2D_Point_d(pP1P0);
        theta = Pi;
        result = instance.rotate(pt, theta);
        expResult = pN1P2;
        assertTrue(expResult.equals(result, epsilon));
//        // Test 6
//        instance = new V2D_Point_d(pP2P0P0);
//        theta = Pi;
//        result = instance.rotate(pt, uv, theta);
//        expResult = pN2P0P0;
//        assertTrue(expResult.equals(result, epsilon));
//        // Test 7
//        instance = new V2D_Point_d(pN2P0P0);
//        theta = Pi;
//        result = instance.rotate(pt, uv, theta);
//        expResult = pP2P0P0;
//        assertTrue(expResult.equals(result, epsilon));
//
//        // Test 8
//        pt = new V2D_RayDouble(P0P0P0, V2D_Vector_d.IJK);
//        uv = pt.l.v.getUnitVector();
//        instance = new V2D_Point_d(pP1P1P1);
//        theta = Pi;
//        result = instance.rotate(pt, uv, theta);
//        expResult = pP1P1P1;
//        assertTrue(expResult.equals(result, epsilon));
//        // Test 9
//        instance = new V2D_Point_d(pP1P1P0);
//        theta = 2d * Pi / 3d;
//        result = instance.rotate(pt, uv, theta);
//        expResult = pP0P1P1;
//        assertTrue(expResult.equals(result, epsilon));
//        // Test 10
//        theta = 4d * Pi / 3d;
//        result = instance.rotate(pt, uv, theta);
//        expResult = pP1P0P1;
//        assertTrue(expResult.equals(result, epsilon));
    }

    /**
     * Test of setOffset method, of class V2D_Point_d.
     */
    @Test
    public void testTranslate() {
        System.out.println("setOffset");
        V2D_Point_d instance = new V2D_Point_d(pP0P0);
        V2D_Vector_d offset = P0P1;
        double epsilon = 1 / 100000000d;
        instance.translate(offset);
        assertTrue(instance.equals(pP0P1, epsilon));
        // Test 2
        offset = N2N2;
        instance.translate(offset);
        assertTrue(instance.equals(pN2N1, epsilon));
    }

    /**
     * Test of setOffset method, of class V2D_Point_d.
     */
    @Test
    public void testSetOffset() {
        System.out.println("setOffset");
        V2D_Vector_d offset = P0P1;
        V2D_Point_d instance = new V2D_Point_d(pP0P0);
        instance.setOffset(offset);
        assertTrue(instance.equals(pP0P0));
    }

    /**
     * Test of setRel method, of class V2D_Point_d.
     */
    @Test
    public void testSetRel() {
        System.out.println("setRel");
        V2D_Vector_d rel = P0P1;
        V2D_Point_d instance = new V2D_Point_d(pP0P0);
        instance.setRel(rel);
        assertTrue(instance.equals(pP0P0));
    }

    /**
     * Test of equals method, of class V2D_Point.
     */
    @Test
    public void testGetUnique() {
        System.out.println("getUnique");
        ArrayList<V2D_Point_d> pts;
        ArrayList<V2D_Point_d> expResult;
        ArrayList<V2D_Point_d> result;
        double epsilon = 1 / 100000000d;
        // Test 1
        pts = new ArrayList<>();
        pts.add(pP0P0);
        pts.add(pP0P0);
        expResult = new ArrayList<>();
        expResult.add(pP0P0);
        result = V2D_Point_d.getUnique(pts, epsilon);
        testContainsSamePoints(expResult, result, epsilon);
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
        result = V2D_Point_d.getUnique(pts, epsilon);
        testContainsSamePoints(expResult, result, epsilon);
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
        result = V2D_Point_d.getUnique(pts, epsilon);
        testContainsSamePoints(expResult, result, epsilon);
    }

    private void testContainsSamePoints(ArrayList<V2D_Point_d> expResult,
            ArrayList<V2D_Point_d> result, double epsilon) {
        assertEquals(expResult.size(), result.size());
        boolean t = false;
        for (var x : result) {
            for (var y : expResult) {
                if (x.equals(y, epsilon)) {
                    t = true;
                    break;
                }
            }
            assertTrue(t);
        }
        t = false;
        for (var x : expResult) {
            for (var y : result) {
                if (x.equals(y, epsilon)) {
                    t = true;
                    break;
                }
            }
            assertTrue(t);
        }
    }
}
