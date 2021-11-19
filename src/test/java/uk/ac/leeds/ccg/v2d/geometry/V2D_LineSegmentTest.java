/*
 * Copyright 2019 Andy Turner, University of Leeds.
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

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;

/**
 * Unit tests for the V2D_LineSegment class.
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_LineSegmentTest {

    V2D_Environment env;

    public V2D_LineSegmentTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() {
        try {
            env = new V2D_Environment(new Generic_Environment(new Generic_Defaults()));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of equals method, of class V2D_LineSegment.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        Object o = new V2D_LineSegment(p, q);
        V2D_LineSegment instance = new V2D_LineSegment(p, q);
        assertTrue(instance.equals(o));
        // Test 2
        instance = new V2D_LineSegment(q, p);
        assertTrue(instance.equals(o));
    }

    /**
     * Test of equals method, of class V2D_LineSegment.
     */
    @Test
    public void testEquals_V2D_LineSegment() {
        System.out.println("equals");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        V2D_LineSegment l = new V2D_LineSegment(p, q);
        V2D_LineSegment instance = new V2D_LineSegment(p, q);
        assertTrue(instance.equals(l));
        // Test 2
        instance = new V2D_LineSegment(q, p);
        assertTrue(instance.equals(l));
    }

    /**
     * Test of equalsIgnoreDirection method, of class V2D_LineSegment.
     */
    @Test
    public void testEqualsIgnoreDirection() {
        System.out.println("equalsIgnoreDirection");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        V2D_LineSegment l = new V2D_LineSegment(p, q);
        V2D_LineSegment instance = new V2D_LineSegment(p, q);
        assertTrue(instance.equalsIgnoreDirection(l));
        // Test 2
        instance = new V2D_LineSegment(q, p);
        assertTrue(instance.equalsIgnoreDirection(l));
    }

    /**
     * Test of getLength method, of class V2D_LineSegment.
     */
    @Test
    public void testGetLength() {
        System.out.println("getLength");
        int oom = -1;
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        V2D_LineSegment instance = new V2D_LineSegment(p, q);
        Math_BigRational expResult = Math_BigRational.valueOf("1.4");
        Math_BigRational result = instance.getLength(oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        oom = -2;
        expResult = Math_BigRational.valueOf("1.41");
        result = instance.getLength(oom);
        assertTrue(expResult.compareTo(result) == 0);
        // Test 2
        oom = -10;
        expResult = Math_BigRational.valueOf("1.4142135624");
        result = instance.getLength(oom);
        assertTrue(expResult.compareTo(result) == 0);
    }

    /**
     * Test of getEnvelope method, of class V2D_LineSegment.
     */
    @Test
    public void testGetEnvelope() {
        System.out.println("getEnvelope");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        V2D_LineSegment instance = new V2D_LineSegment(p, q);
        V2D_Envelope expResult = new V2D_Envelope(p, q);
        V2D_Envelope result = instance.getEnvelope();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V2D_LineSegment() {
        System.out.println("isIntersectedBy");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(2, 2);
        V2D_LineSegment l = new V2D_LineSegment(p, q);
        V2D_Point r = new V2D_Point(0, 0);
        V2D_Point s = new V2D_Point(1, 1);
        V2D_LineSegment instance = new V2D_LineSegment(r, s);
        assertTrue(l.isIntersectedBy(instance, true));
        // Test 2
        r = new V2D_Point(4, 4);
        s = new V2D_Point(3, 3);
        instance = new V2D_LineSegment(r, s);
        assertFalse(l.isIntersectedBy(instance, true));
        // Test 3
        r = new V2D_Point(0, 2);
        s = new V2D_Point(2, 0);
        instance = new V2D_LineSegment(r, s);
        assertTrue(l.isIntersectedBy(instance, true));
    }

    /**
     * Test of isIntersectedBy method, of class V2D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V2D_Point() {
        System.out.println("isIntersectedBy");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        V2D_LineSegment instance = new V2D_LineSegment(p, q);
        V2D_Point pt = new V2D_Point(0, 0);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 2
        pt = new V2D_Point(0, 0);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 3
        pt = new V2D_Point(1, 1);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 4
        pt = new V2D_Point(0.5, 0.5);
        assertTrue(instance.isIntersectedBy(pt));
        // Test 5
        pt = new V2D_Point(2, 2);
        assertFalse(instance.isIntersectedBy(pt));
    }

    /**
     * Test of getIntersection method, of class V2D_LineSegment.
     */
    @Test
    public void testGetIntersection() {
        System.out.println("getIntersection");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        V2D_LineSegment instance = new V2D_LineSegment(p, q);
        V2D_LineSegment l = new V2D_LineSegment(p, q);
        V2D_Geometry expResult = new V2D_LineSegment(p, q);
        V2D_Geometry result = instance.getIntersection(l);
        assertTrue(expResult.equals(result));
        // Test 2
        l = new V2D_LineSegment(q, p);
        result = instance.getIntersection(l);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment)result));
        // Test 3
        V2D_Point r = new V2D_Point(0, 0);
        V2D_Point s = new V2D_Point(2, 2);
        l = new V2D_LineSegment(r, s);
        expResult = new V2D_LineSegment(p, q);
        result = instance.getIntersection(l);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment)result));
        // Test 4
        r = new V2D_Point(0.5, 0.5);
        s = new V2D_Point(2, 2);
        l = new V2D_LineSegment(r, s);
        expResult = new V2D_LineSegment(r, q);
        result = instance.getIntersection(l);
        assertTrue(((V2D_LineSegment) expResult).equalsIgnoreDirection(
                (V2D_LineSegment)result));
        // Test 5
        r = new V2D_Point(1, 0);
        s = new V2D_Point(0, 1);
        l = new V2D_LineSegment(r, s);
        expResult = new V2D_Point(0.5, 0.5);
        result = instance.getIntersection(l);
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of hashCode method, of class V2D_LineSegment.
     */
    @Test
    public void testHashCode() {
        System.out.println("hashCode");
        // No test.
    }

}
