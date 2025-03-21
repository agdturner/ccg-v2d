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
package uk.ac.leeds.ccg.v2d.geometry.test;

import ch.obermuhlner.math.big.BigRational;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.v2d.geometry.V2D_AABB;
import uk.ac.leeds.ccg.v2d.geometry.V2D_ConvexArea;
import uk.ac.leeds.ccg.v2d.geometry.V2D_FiniteGeometry;
import uk.ac.leeds.ccg.v2d.geometry.V2D_LineSegment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Rectangle;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Triangle;

/**
 *
 * @author Andy Turner
 */
public class V2D_ConvexAreaTest extends V2D_Test {

    public V2D_ConvexAreaTest() {
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

//    /**
//     * Test of getPointsArray method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetPointsArray() {
//        System.out.println("getPointsArray");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        V2D_Point[] expResult = null;
//        V2D_Point[] result = instance.getPointsArray(oom, rm);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPoints method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetPoints() {
//        System.out.println("getPoints");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        HashMap<Integer, V2D_Point> expResult = null;
//        HashMap<Integer, V2D_Point> result = instance.getPoints(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    /**
     * Test of toString method, of class V2D_ConvexArea.
     */
    @Test
    public void testToString() {
        System.out.println("toString");
        int oom = -6;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_ConvexArea instance = new V2D_ConvexArea(oom, rm,
                pP0P0, pP0P1, pP1P1, pP1P0);
        String expResult = """
                           uk.ac.leeds.ccg.v2d.geometry.V2D_ConvexArea(
                           points  (
                            (0, V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=0, dy=0))),
                            (1, V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=1, dy=0))),
                            (2, V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=1, dy=1))),
                            (3, V2D_Point(offset=V2D_Vector(dx=0, dy=0), rel=V2D_Vector(dx=0, dy=1)))
                            )
                           )""";
        String result = instance.toString();
        //System.out.println(result);
        assertTrue(expResult.equalsIgnoreCase(result));
    }
//
//    /**
//     * Test of toStringSimple method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testToStringSimple() {
//        System.out.println("toStringSimple");
//        V2D_ConvexArea instance = null;
//        String expResult = "";
//        String result = instance.toStringSimple();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of equals method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testEquals() {
//        System.out.println("equals");
//        V2D_ConvexArea c = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.equals(c, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getAABB method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetAABB() {
//        System.out.println("getAABB");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        V2D_AABB expResult = null;
//        V2D_AABB result = instance.getAABB(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of simplify method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testSimplify() {
//        System.out.println("simplify");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        V2D_FiniteGeometry expResult = null;
//        V2D_FiniteGeometry result = instance.simplify(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_1() {
//        System.out.println("intersects");
//        V2D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(pt, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_1() {
//        System.out.println("intersects0");
//        V2D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(pt, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testContains_3args_1() {
//        System.out.println("contains");
//        V2D_Point pt = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(pt, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testContains_3args_2() {
//        System.out.println("contains");
//        V2D_LineSegment l = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(l, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testContains_3args_3() {
//        System.out.println("contains");
//        V2D_Triangle t = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testContains_3args_4() {
//        System.out.println("contains");
//        V2D_Rectangle r = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(r, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of contains method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testContains_3args_5() {
//        System.out.println("contains");
//        V2D_ConvexArea ch = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.contains(ch, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_2() {
//        System.out.println("intersects");
//        V2D_LineSegment l = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(l, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_2() {
//        System.out.println("intersects0");
//        V2D_LineSegment l = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(l, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_3() {
//        System.out.println("intersects");
//        V2D_Triangle t = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_3() {
//        System.out.println("intersects0");
//        V2D_Triangle t = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_4() {
//        System.out.println("intersects");
//        V2D_Rectangle r = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(r, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_4() {
//        System.out.println("intersects0");
//        V2D_Rectangle r = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(r, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_5() {
//        System.out.println("intersects");
//        V2D_ConvexArea ch = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(ch, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_5() {
//        System.out.println("intersects0");
//        V2D_ConvexArea ch = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(ch, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getArea method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetArea() {
//        System.out.println("getArea");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        BigRational expResult = null;
//        BigRational result = instance.getArea(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getIntersect method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetIntersect() {
//        System.out.println("getIntersect");
//        V2D_Triangle t = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        V2D_FiniteGeometry expResult = null;
//        V2D_FiniteGeometry result = instance.getIntersect(t, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotate method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testRotate() {
//        System.out.println("rotate");
//        V2D_Point pt = null;
//        BigRational theta = null;
//        Math_BigDecimal bd = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        V2D_ConvexArea expResult = null;
//        V2D_ConvexArea result = instance.rotate(pt, theta, bd, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of rotateN method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testRotateN() {
//        System.out.println("rotateN");
//        V2D_Point pt = null;
//        BigRational theta = null;
//        Math_BigDecimal bd = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        V2D_ConvexArea expResult = null;
//        V2D_ConvexArea result = instance.rotateN(pt, theta, bd, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects_3args_6() {
//        System.out.println("intersects");
//        V2D_AABB aabb = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects(aabb, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of intersects0 method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIntersects0_3args_6() {
//        System.out.println("intersects0");
//        V2D_AABB aabb = null;
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.intersects0(aabb, oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isTriangle method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIsTriangle() {
//        System.out.println("isTriangle");
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.isTriangle();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of isRectangle method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testIsRectangle() {
//        System.out.println("isRectangle");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        boolean expResult = false;
//        boolean result = instance.isRectangle(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeometry method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetGeometry_3args_1() {
//        System.out.println("getGeometry");
//        int oom = 0;
//        RoundingMode rm = null;
//        ArrayList<V2D_Point> pts = null;
//        V2D_FiniteGeometry expResult = null;
//        V2D_FiniteGeometry result = V2D_ConvexArea.getGeometry(oom, rm, pts);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeometry method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetGeometry_3args_2() {
//        System.out.println("getGeometry");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_Point[] pts = null;
//        V2D_FiniteGeometry expResult = null;
//        V2D_FiniteGeometry result = V2D_ConvexArea.getGeometry(oom, rm, pts);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getTriangles method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetTriangles() {
//        System.out.println("getTriangles");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        ArrayList<V2D_Triangle> expResult = null;
//        ArrayList<V2D_Triangle> result = instance.getTriangles(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getEdges method, of class V2D_ConvexArea.
//     */
//    @Test
//    public void testGetEdges() {
//        System.out.println("getEdges");
//        int oom = 0;
//        RoundingMode rm = null;
//        V2D_ConvexArea instance = null;
//        HashMap<Integer, V2D_LineSegment> expResult = null;
//        HashMap<Integer, V2D_LineSegment> result = instance.getEdges(oom, rm);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

}
