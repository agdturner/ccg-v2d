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

import ch.obermuhlner.math.big.BigRational;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import org.hamcrest.Matchers;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.math.Math_BigDecimal;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 *
 * @author geoagdt
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
        assertFalse(instance.equals(o));
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
        assertFalse(instance.equals(l));
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
        int dp = 1;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        V2D_LineSegment instance = new V2D_LineSegment(p, q);
        BigDecimal expResult = new BigDecimal("1.4");
        BigDecimal result = instance.getLength(dp, rm);
        assertThat(expResult, Matchers.comparesEqualTo(result));
        // Test 2
        dp = 2;
        expResult = new BigDecimal("1.41");
        result = instance.getLength(dp, rm);
        assertThat(expResult, Matchers.comparesEqualTo(result));
        // Test 2
        dp = 10;
        expResult = new BigDecimal("1.4142135624");
        result = instance.getLength(dp, rm);
        assertThat(expResult, Matchers.comparesEqualTo(result));
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
        assertEquals(expResult, result);
    }

    /**
     * Test of isIntersectedBy method, of class V2D_LineSegment.
     */
    @Test
    public void testIsIntersectedBy_V2D_LineSegment() {
        System.out.println("isIntersectedBy");
        V2D_Point p = new V2D_Point(0, 0);
        V2D_Point q = new V2D_Point(1, 1);
        V2D_LineSegment l = new V2D_LineSegment(p, q);
        V2D_Point r = new V2D_Point(0, 0);
        V2D_Point s = new V2D_Point(1, 1);
        V2D_LineSegment instance = new V2D_LineSegment(r, s);
        assertTrue(l.isIntersectedBy(instance));
        // Test 2
        r = new V2D_Point(2, 2);
        s = new V2D_Point(3, 3);
        instance = new V2D_LineSegment(r, s);
        assertFalse(l.isIntersectedBy(instance));
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
        V2D_Geometry expResult = new V2D_LineSegment(p, q);;
        V2D_Geometry result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 2
        V2D_Point r = new V2D_Point(0, 0);
        V2D_Point s = new V2D_Point(2, 2);
        l = new V2D_LineSegment(r, s);
        expResult = new V2D_LineSegment(p, q);;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
        // Test 3
        r = new V2D_Point(0.5, 0.5);
        s = new V2D_Point(2, 2);
        l = new V2D_LineSegment(r, s);
        expResult = new V2D_LineSegment(r, q);;
        result = instance.getIntersection(l);
        assertEquals(expResult, result);
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
