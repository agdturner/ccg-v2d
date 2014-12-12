/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.geometry;

import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author geoagdt
 */
public class VectorLineSegment2DTest {

    public VectorLineSegment2DTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of set_DecimalPlacePrecision method, of class Vector_LineSegment2D.
     */
    @Test
    public void testSet_DecimalPlacePrecision() {
//        System.out.println("set_DecimalPlacePrecision");
//        int _DecimalPlacePrecision = 0;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        int expResult = 0;
//        int result = instance.set_DecimalPlacePrecision(_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Vector_LineSegment2D.
     */
    @Test
    public void testToString() {
//        System.out.println("toString");
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Vector_LineSegment2D.
     */
    @Test
    public void testCompareTo() {
//        System.out.println("compareTo");
//        Object o = null;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        int expResult = 0;
//        int result = instance.compareTo(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getLength method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetLength() {
//        System.out.println("getLength");
//        int a_DecimalPlacePrecision = 0;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getLength(a_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnvelope2D method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetEnvelope2D() {
//        System.out.println("getEnvelope2D");
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        VectorEnvelope2D expResult = null;
//        VectorEnvelope2D result = instance.getEnvelope2D();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetIntersects_VectorLineSegment2D_int() {
//        System.out.println("getIntersects");
//        Vector_LineSegment2D a_LineSegment2D = null;
//        int a_DecimalPlacePrecision = 0;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        boolean expResult = false;
//        boolean result = instance.getIntersects(a_LineSegment2D, a_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetIntersects_3args() {
//
//
//        System.out.println("LineSegment2D " + a_LineSegment2D +
//                " intersection LineSegment2D " + b_LineSegment2D +
//                " " + a_LineSegment2D.getIntersection(b_LineSegment2D,a_DecimalPlacePrecision));
//
//
//
//        System.out.println("getIntersects");
//        Vector_LineSegment2D a_LineSegment2D = null;
//        boolean ignore_this_Start_Point2D = false;
//        int a_DecimalPlacePrecision = 0;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        boolean expResult = false;
//        boolean result = instance.getIntersects(a_LineSegment2D, ignore_this_Start_Point2D, a_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetIntersects_VectorPoint2D_int() {
        boolean result;
        Vector_Point2D a = new Vector_Point2D("0", "0");
        Vector_Point2D b = new Vector_Point2D("1", "1");
        Vector_LineSegment2D ab_VectorLineSegment2D = new Vector_LineSegment2D(
                a,
                b);
        System.out.println(
                "ab_VectorLineSegment2D " + ab_VectorLineSegment2D);
        boolean expResult = true;
        BigDecimal ten_BigDecimal = new BigDecimal("10");
        BigDecimal a_x = new BigDecimal("1");
        BigDecimal a_y = new BigDecimal("1");
        int a_DecimalPlacePrecision = 0;
        for (int i = 0; i < 1000; i++) {
            a_DecimalPlacePrecision++;
            System.out.println("a_DecimalPlacePrecision " + a_DecimalPlacePrecision);
            a_x = a_x.divide(ten_BigDecimal);
            a_y = a_y.divide(ten_BigDecimal);
            a = new Vector_Point2D(a_x, a_y);
            System.out.println("a " + a.toString());
            result = ab_VectorLineSegment2D.getIntersects(
                    a,
                    a_DecimalPlacePrecision);
            System.out.println(
                    "ab_VectorLineSegment2D.getIntersects(a,a_DecimalPlacePrecision)"
                    + result);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getGradient method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetGradient() {
//        System.out.println("getGradient");
//        int a_DecimalPlacePrecision = 0;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getGradient(a_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of isOnGradient method, of class Vector_LineSegment2D.
     */
    @Test
    public void testIsOnGradient() {
//        System.out.println("isOnGradient");
//        Vector_Point2D a_Point2D = null;
//        int a_DecimalPlacePrecision = 0;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        boolean expResult = false;
//        boolean result = instance.isOnGradient(a_Point2D, a_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrderedLineSegment2D method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetOrderedLineSegment2D() {
//        System.out.println("getOrderedLineSegment2D");
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        Vector_LineSegment2D expResult = null;
//        Vector_LineSegment2D result = instance.getOrderedLineSegment2D();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersection method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetIntersection() {
//        System.out.println("getIntersection");
//        Vector_LineSegment2D a_LineSegment2D = null;
//        int a_DecimalPlacePrecision = 0;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        VectorAbstractGeometry2D expResult = null;
//        VectorAbstractGeometry2D result = instance.getIntersection(a_LineSegment2D, a_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getAngleToX_double method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetAngleToX_double() {
//        System.out.println("getAngleToX_double");
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        double expResult = 0.0;
//        double result = instance.getAngleToX_double();
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getAngleToY_double method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetAngleToY_double() {
//        System.out.println("getAngleToY_double");
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        double expResult = 0.0;
//        double result = instance.getAngleToY_double();
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getScalarProduct method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetScalarProduct() {
//        System.out.println("getScalarProduct");
//        Vector_LineSegment2D a_LineSegment2D = null;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getScalarProduct(a_LineSegment2D);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getCrossProduct method, of class Vector_LineSegment2D.
     */
    @Test
    public void testGetCrossProduct() {
//        System.out.println("getCrossProduct");
//        Vector_LineSegment2D a_LineSegment2D = null;
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getCrossProduct(a_LineSegment2D);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of applyDecimalPlacePrecision method, of class Vector_LineSegment2D.
     */
    @Test
    public void testApplyDecimalPlacePrecision() {
//        System.out.println("applyDecimalPlacePrecision");
//        Vector_LineSegment2D instance = new Vector_LineSegment2D();
//        instance.applyDecimalPlacePrecision();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}
