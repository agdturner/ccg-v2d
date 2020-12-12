/*
 * Copyright 2020 Centre for Computational Geography.
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
package uk.ac.leeds.ccg.v2d.geometry;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;

/**
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_LineTest {

    public V2D_LineTest() {
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
    public void testToString() {
        System.out.println("toString");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 0);
        V2D_Line instance = new V2D_Line(p, q);
        String expResult = "V2D_Line[p=V2D_Point(x=0 y=0), "
                + "q=V2D_Point(x=1 y=0), v=V2D_Vector(dx=1, dy=0)]";
        String result = instance.toString();
        System.out.println(result);
        assertEquals(expResult, result);;
    }

    /**
     * Test of equals method, of class V2D_Line.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 0);
        Object o = new V2D_LineSegment(p, q);
        V2D_Line instance = new V2D_LineSegment(p, q);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V2D_LineSegment(q, p);
        assertFalse(instance.equals(o));
    }

    /**
     * Test of hashCode method, of class V2D_Line.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        // No test.
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Line.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point() {
        System.out.println("isIntersectedBy");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(2, 0);
        V2D_Point pt = new V2D_Point(1, 0);
        V2D_Line instance = new V2D_LineSegment(p, q);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 2
        pt = new V2D_Point(0, 0);
        instance = new V2D_LineSegment(p, q);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 3
        pt = new V2D_Point(2, 0);
        instance = new V2D_LineSegment(p, q);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 4
        pt = new V2D_Point(0, 1);
        instance = new V2D_LineSegment(p, q);
        assertFalse(instance.isIntersectedBy(pt));
    }

    /**
     * Test of isParallel method, of class V2D_Line.
     */
    @Test
    public void testIsParallel() {
        System.out.println("isParallel");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(2, 0);
        V2D_Line l = new V2D_LineSegment(p, q);
        V2D_Line instance = new V2D_LineSegment(p, q);
        assertTrue(instance.isParallel(l));
        // Test 2
        instance = new V2D_LineSegment(q, p);
        assertTrue(instance.isParallel(l));
        // Test 3
        V2D_Point r = new V2D_Point(-2, 0);
        instance = new V2D_LineSegment(r, p);
        assertTrue(instance.isParallel(l));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_Line.
     */
    @Test
    public void testIsIntersectedBy_V2D_Line() {
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(2, 0);
        V2D_Line l = new V2D_LineSegment(p, q);
        V2D_Line instance = new V2D_LineSegment(p, q);
        assertTrue(instance.isIntersectedBy(l));
        // Test 2
        V2D_Point r = new V2D_Point(1, 1);
        V2D_Point s = new V2D_Point(1, -1);
        instance = new V2D_LineSegment(r, s);
        assertTrue(instance.isIntersectedBy(l));
        // Test 3
        r = new V2D_Point(1, 1);
        s = new V2D_Point(1, 0);
        instance = new V2D_LineSegment(r, s);
        assertTrue(instance.isIntersectedBy(l));
        // Test 4
        r = new V2D_Point(1, 1);
        s = new V2D_Point(0, 1);
        instance = new V2D_LineSegment(r, s);
        assertFalse(instance.isIntersectedBy(l));
    }

    /**
     * Test of getIntersection method, of class V2D_Line.
     */
    @Test
    public void testGetIntersection_V2D_Line() {
        System.out.println("getIntersection");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(2, 0);
        V2D_Line l = new V2D_Line(p, q);
        V2D_Line instance = new V2D_Line(p, q);
        V2D_Line expResult = new V2D_Line(p, q);
        V2D_Geometry result = instance.getIntersection(l);
        assertTrue(expResult.equals((V2D_Line) result));
    }

    /**
     * Test of getIntersection method, of class V2D_Line.
     */
    @Test
    public void testGetIntersection_V2D_Line_V2D_Line() {
        System.out.println("getIntersection");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(2, 0);
        V2D_Line l0 = new V2D_Line(p, q);
        V2D_Point r = new V2D_Point(0, 0);
        V2D_Point s = new V2D_Point(0, 2);
        V2D_Line l1 = new V2D_Line(r, s);
        V2D_Geometry expResult = new V2D_Point(0, 0);
        V2D_Geometry result = V2D_Line.getIntersection(l0, l1);
        assertEquals(expResult, result);
        // Test 2
        r = new V2D_Point(1, 0);
        s = new V2D_Point(1, 2);
        l1 = new V2D_Line(r, s);
        expResult = new V2D_Point(1, 0);
        result = V2D_Line.getIntersection(l0, l1);
        assertEquals(expResult, result);
    }

    /**
     * Test of getDistance method, of class V2D_Line.
     */
    @Test
    public void testGetDistance() {
        System.out.println("getDistance");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(2, 0);
        V2D_Line l = new V2D_Line(p, q);
        int scale = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point r = new V2D_Point(0, 1);
        V2D_Point s = new V2D_Point(2, 1);
        V2D_Line instance = new V2D_Line(r, s);
        BigDecimal expResult = BigDecimal.ONE;
        BigDecimal result = instance.getDistance(l, scale, rm);
        assertThat(expResult, Matchers.comparesEqualTo(result));
        assertEquals(expResult, result);
    }

}
