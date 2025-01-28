/*
 * Copyright 2021 Centre for Computational Geography.
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

import java.math.RoundingMode;
import uk.ac.leeds.ccg.v2d.geometry.d.test.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Geometry;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Line;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Ray;

/**
 * Test class for V2D_Ray.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_RayTest extends V2D_Test {

    public V2D_RayTest() {
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
     * Test of equals method, of class V2D_Ray.
     */
    @Test
    public void testEquals_V2D_Ray_int() {
        System.out.println("equals");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Ray r = new V2D_Ray(pP0P0, pP1P1, oom, rm);
        V2D_Ray instance = new V2D_Ray(pP0P0, pP1P1, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 2
        instance = new V2D_Ray(pP0P0, pP2P2, oom, rm);
        assertTrue(instance.equals(r, oom, rm));
        // Test 2
        instance = new V2D_Ray(pP1P1, pP2P2, oom, rm);
        assertFalse(instance.equals(r, oom, rm));
    }

    /**
     * Test of translate method, of class V2D_Ray.
     */
    @Test
    public void testApply() {
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Ray.
     */
    @Test
    public void testIsIntersectedBy_double_V2D_Point() {
        System.out.println("isIntersectedBy");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point pt = pP0P0;
        V2D_Ray instance = new V2D_Ray(pN1N1, pP1P1, oom, rm);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 2
        pt = pP1P1;
        instance = new V2D_Ray(pN1N1, pP1P1, oom, rm);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 3
        pt = pN2N2;
        instance = new V2D_Ray(pN1N1, pP1P1, oom, rm);
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
        // Test 4
        pt = pP1P0;
        instance = new V2D_Ray(pP0P0, pP1P0, oom, rm);
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 5
        pt = pP2P0;
        assertTrue(instance.isIntersectedBy(pt, oom, rm));
        // Test 6
        pt = pN2P0;
        assertFalse(instance.isIntersectedBy(pt, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V2D_Ray.
     */
    @Test
    public void testGetIntersection_V2D_Line_double() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Line l = new V2D_Line(pP0P0, pP1P0, oom, rm);
        V2D_Ray instance = new V2D_Ray(pP0P0, pP1P0, oom, rm);
        V2D_Geometry expResult = new V2D_Ray(pP0P0, pP1P0, oom, rm);
        V2D_Geometry result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Ray) expResult).equals((V2D_Ray) result, oom, rm));
        // Test 2
        instance = new V2D_Ray(pP0P0, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 3
        instance = new V2D_Ray(pN1N1, pN1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pN1P0;
        assertTrue(((V2D_Point) expResult).equals( 
                (V2D_Point) result, oom, rm));
        // Test 4
        l = new V2D_Line(pN1P0, pP1P0, oom, rm);
        instance = new V2D_Ray(pN1N1, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0;
        assertTrue(((V2D_Point) expResult).equals( 
                (V2D_Point) result, oom, rm));
        // Test 5
        l = new V2D_Line(pP0P0, pP1P0, oom, rm);
        instance = new V2D_Ray(pN1P0, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = new V2D_Ray(pN1P0, pP1P0, oom, rm);
        assertTrue(((V2D_Ray) expResult).equals((V2D_Ray) result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V2D_Ray.
     */
    @Test
    public void testGetIntersection_3args_2() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l;
        V2D_Ray instance;
        V2D_Geometry expResult;
        V2D_Geometry result;
        // Test 1
        l = new V2D_LineSegment(P0P0, P0P0, P1P0, oom, rm);
        instance = new V2D_Ray(P0P0, P1P0, P2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP1P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 2
        instance = new V2D_Ray(pP0P0, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 3
        instance = new V2D_Ray(pN1N1, pN1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertNull(result);
        // Test 4
        l = new V2D_LineSegment(pN1P0, pP1P0, oom, rm);
        instance = new V2D_Ray(pN1N1, pP1P1, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = pP0P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 5
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_Ray(pN1P0, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }

    /**
     * Test of getIntersection method, of class V2D_Ray.
     */
    @Test
    public void testGetIntersection_V2D_Ray_double() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Ray r;
        V2D_Ray instance;
        V2D_Geometry expResult;
        V2D_Geometry result;
        // Test 1: Collinear Pointing the same way
        r = new V2D_Ray(P0P0, P0P0, P1P0, oom, rm);
        instance = new V2D_Ray(P0P0, P1P0, P2P0, oom, rm);
        result = instance.getIntersection(r, oom, rm);
        expResult = new V2D_Ray(P0P0, P1P0, P2P0, oom, rm);
        assertTrue(((V2D_Ray) expResult).equals((V2D_Ray) result, oom, rm));
        // Test 2: Collinear Pointing the same way 
        r = new V2D_Ray(P0P0, N2P0, N1P0, oom, rm);
        instance = new V2D_Ray(P0P0, P1P0, P2P0, oom, rm);
        result = instance.getIntersection(r, oom, rm);
        expResult = new V2D_Ray(P0P0, P1P0, P2P0, oom, rm);
        assertTrue(((V2D_Ray) expResult).equals((V2D_Ray) result, oom, rm));
        /**
         * The rays may point along the same line. If they point in the same
         * direction, then they intersect and the start of the ray is the start
         * point of the ray that intersects both rays. If they point in opposite
         * directions, then they do not intersect unless the points they start
         * at intersect with the other ray and in this instance, the
         * intersection is the line segment between them.
         */
        // Test 3: Collinear pointing opposite ways overlapping in a line segment.
        r = new V2D_Ray(P0P0, P0P0, P1P0, oom, rm);
        instance = new V2D_Ray(P0P0, P1P0, P0P0, oom, rm);
        expResult = new V2D_LineSegment(P0P0, P0P0, P1P0, oom, rm);
        result = instance.getIntersection(r, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection( 
                (V2D_LineSegment) result, oom, rm));
        // Test 4: Collinear pointing opposite ways overlapping at a point.
        r = new V2D_Ray(P0P0, P0P0, P1P0, oom, rm);
        instance = new V2D_Ray(P0P0, P0P0, N1P0, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(r, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 4: Collinear pointing opposite ways not overlapping.
        r = new V2D_Ray(P0P0, P1P0, P2P0, oom, rm);
        instance = new V2D_Ray(P0P0, P0P0, N1P0, oom, rm);
        result = instance.getIntersection(r, oom, rm);
        assertNull(result);
        // Test 5: Intersecting at a point.
        r = new V2D_Ray(P0P0, N2P0, N1P0, oom, rm);
        instance = new V2D_Ray(P0P0, P1P0, P1P1, oom, rm);
        result = instance.getIntersection(r, oom, rm);
        expResult = pP1P0;
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 6: Not intersecting.
        r = new V2D_Ray(P0P0, P1P0, P2P0, oom, rm);
        instance = new V2D_Ray(P0P0, P0P0, P1P1, oom, rm);
        result = instance.getIntersection(r, oom, rm);
        assertNull(result);
    }

    /**
     * Test of getIntersection method, of class V2D_Ray.
     */
    @Test
    public void testGetIntersection_V2D_LineSegment_int() {
        System.out.println("getIntersection");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_LineSegment l;
        V2D_Ray instance;
        V2D_Geometry expResult;
        V2D_Geometry result;
        // Test 1
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_Ray(pP0P0, pP1P0, oom, rm);
        expResult = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
        // Test 2
        l = new V2D_LineSegment(pP0P0, pP1P0, oom, rm);
        instance = new V2D_Ray(pP0P0, pN1P0, oom, rm);
        expResult = pP0P0;
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_Point) expResult).equals((V2D_Point) result, oom, rm));
        // Test 3
        l = new V2D_LineSegment(pP0P0, pP2P0, oom, rm);
        instance = new V2D_Ray(pP1P0, pP2P0, oom, rm);
        expResult = new V2D_LineSegment(pP1P0, pP2P0, oom, rm);
        result = instance.getIntersection(l, oom, rm);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment) result, oom, rm));
    }
}
