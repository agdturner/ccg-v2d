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
public class VectorPoint2DTest {

    public VectorPoint2DTest() {
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

//    /**
//     * Test of set_DecimalPlacePrecision method, of class Vector_Point2D.
//     */
//    @Test
//    public void testSet_DecimalPlacePrecision() {
////        System.out.println("set_DecimalPlacePrecision");
////        int _DecimalPlacePrecision = 0;
////        Vector_Point2D instance = new Vector_Point2D();
////        int expResult = 0;
////        int result = instance.set_DecimalPlacePrecision(_DecimalPlacePrecision);
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
//    }

    /**
     * Test of roundTo method, of class Vector_Point2D.
     */
    @Test
    public void testRoundTo() {
//        System.out.println("roundTo");
//        BigDecimal toRoundTo_BigDecimal = null;
//        Vector_Point2D instance = new Vector_Point2D();
//        instance.roundTo(toRoundTo_BigDecimal);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Vector_Point2D.
     */
    @Test
    public void testToString() {
//        System.out.println("toString");
//        Vector_Point2D instance = new Vector_Point2D();
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of equals method, of class Vector_Point2D.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        Vector_Point2D a;
        Vector_Point2D b;
        Vector_Point2D c;
        Vector_Point2D d;
boolean result;
boolean expResult;
        a = new Vector_Point2D(0.1d, 0.3d);
        System.out.println(
                "a = new VectorPoint2D(0.1d,0.3d); "
                + a.toString());

        b = new Vector_Point2D("0.1", "0.3");
        System.out.println(
                "b = new VectorPoint2D(\"0.1\",\"0.3\"); "
                + b.toString());
        
        result = a.equals(b);
        System.out.println(
                "a.equals(b); "
                + result);
        expResult = false;
        //try{
        assertEquals(expResult, result);
//        } catch (AssertionError a_AssertionError){
//            System.err.println(
//        }

        c = new Vector_Point2D(a, 1);
        System.out.println(
                "c = new VectorPoint2D(a,1); "
                + c.toString());
        result = a.equals(c);
        System.out.println(
                "a.equals(c); "
                + result);
        expResult = false;
        assertEquals(expResult, result);

        d = new Vector_Point2D(b, 1);
        System.out.println(
                "d = new VectorPoint2D(b,1); "
                + d.toString());

        result = b.equals(d);
        System.out.println(
                "b.equals(d); "
                + result);
        expResult = true;
        assertEquals(expResult, result);
        
        result = c.equals(d);
        System.out.println(
                "c.equals(d); "
                + result);
        expResult = false;
        assertEquals(expResult, result);
    }

    /**
     * Test of hashCode method, of class Vector_Point2D.
     */
    @Test
    public void testHashCode() {
//        System.out.println("hashCode");
//        Vector_Point2D instance = new Vector_Point2D();
//        int expResult = 0;
//        int result = instance.hashCode();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of compareTo method, of class Vector_Point2D.
     */
    @Test
    public void testCompareTo() {
//        System.out.println("compareTo");
//        Object o = null;
//        Vector_Point2D instance = new Vector_Point2D();
//        int expResult = 0;
//        int result = instance.compareTo(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersects method, of class Vector_Point2D.
     */
    @Test
    public void testGetIntersects() {
//        System.out.println("getIntersects");
//        VectorLineSegment2D a_LineSegment2D = null;
//        int a_DecimalPlacePrecisionForCalculations = 0;
//        Vector_Point2D instance = new Vector_Point2D();
//        boolean expResult = false;
//        boolean result = instance.getIntersects(a_LineSegment2D, a_DecimalPlacePrecisionForCalculations);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getDistance method, of class Vector_Point2D.
     */
    @Test
    public void testGetDistance() {
//        System.out.println("getDistance");
//        Vector_Point2D a_VectorPoint2D = null;
//        int a_DecimalPlacePrecision = 0;
//        Vector_Point2D instance = new Vector_Point2D();
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getDistance(a_VectorPoint2D, a_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getAngle_double method, of class Vector_Point2D.
     */
    @Test
    public void testGetAngle_double() {
//        System.out.println("getAngle_double");
//        Vector_Point2D a_Point2D = null;
//        Vector_Point2D instance = new Vector_Point2D();
//        double expResult = 0.0;
//        double result = instance.getAngle_double(a_Point2D);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getAngle_BigDecimal method, of class Vector_Point2D.
     */
    @Test
    public void testGetAngle_BigDecimal() {
//        System.out.println("getAngle_BigDecimal");
//        Vector_Point2D a_Point2D = null;
//        Vector_Point2D instance = new Vector_Point2D();
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getAngle_BigDecimal(a_Point2D);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getGradient method, of class Vector_Point2D.
     */
    @Test
    public void testGetGradient() {
//        System.out.println("getGradient");
//        Vector_Point2D a_Point2D = null;
//        int a_DecimalPlacePrecision = 0;
//        Vector_Point2D instance = new Vector_Point2D();
//        BigDecimal expResult = null;
//        BigDecimal result = instance.getGradient(a_Point2D, a_DecimalPlacePrecision);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnvelope2D method, of class Vector_Point2D.
     */
    @Test
    public void testGetEnvelope2D() {
//        System.out.println("getEnvelope2D");
//        Vector_Point2D instance = new Vector_Point2D();
//        VectorEnvelope2D expResult = null;
//        VectorEnvelope2D result = instance.getEnvelope2D();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of applyDecimalPlacePrecision method, of class Vector_Point2D.
     */
    @Test
    public void testApplyDecimalPlacePrecision() {
//        System.out.println("applyDecimalPlacePrecision");
//        Vector_Point2D instance = new Vector_Point2D();
//        instance.applyDecimalPlacePrecision();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of to_doubleArray method, of class Vector_Point2D.
     */
    @Test
    public void testTo_doubleArray() {
//        System.out.println("to_doubleArray");
//        Vector_Point2D instance = new Vector_Point2D();
//        double[] expResult = null;
//        double[] result = instance.to_doubleArray();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

}