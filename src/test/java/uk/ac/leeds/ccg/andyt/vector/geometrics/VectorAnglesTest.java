/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.leeds.ccg.andyt.vector.geometrics;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Point2D;

/**
 *
 * @author geoagdt
 */
public class VectorAnglesTest {

    public VectorAnglesTest() {
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
//     * Test of main method, of class Vector_Angles.
//     */
//    @Test
//    public void testMain() {
//        System.out.println("main");
//        String[] args = null;
//        Vector_Angles.main(args);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of run method, of class Vector_Angles.
//     */
//    @Test
//    public void testRun() {
//        System.out.println("run");
//        Vector_Angles instance = new Vector_Angles();
//        instance.run();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of getAnglesToYAxisClockwise_doubleArray method, of class Vector_Angles.
//     */
//    @Test
//    public void testGetAnglesToYAxisClockwise_doubleArray() {
//        System.out.println("getAnglesToYAxisClockwise_doubleArray");
//        Vector_Point2D[] a_LineRing = null;
//        double[] expResult = null;
//        double[] result = Vector_Angles.getAnglesToYAxisClockwise_doubleArray(a_LineRing);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

//    /**
//     * Test of getInsideAnglesClockwise_doubleArray method, of class Vector_Angles.
//     */
//    @Test
//    public void testGetInsideAnglesClockwise_doubleArray() {
//        System.out.println("getInsideAnglesClockwise_doubleArray");
//        Vector_Point2D[] a_LineRing = null;
//        double[] expResult = null;
//        double[] result = Vector_Angles.getInsideAnglesClockwise_doubleArray(a_LineRing);
//        //assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }

//    /**
//     * Test of getInsideAngleClockwise_doubleArray method, of class Vector_Angles.
//     */
//    @Test
//    public void testGetInsideAngleClockwise_doubleArray() {
//        System.out.println("getInsideAngleClockwise_doubleArray");
//        Vector_Point2D a_Point2D = null;
//        Vector_Point2D b_Point2D = null;
//        Vector_Point2D c_Point2D = null;
//        double expResult = 0.0;
//        double result = Vector_Angles.getInsideAngleClockwise_doubleArray(a_Point2D, b_Point2D, c_Point2D);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }

    /**
     * Test of getRadToDeg_double method, of class Vector_Angles.
     */
    @Test
    public void testGetRadToDeg_double() {
        System.out.println("getRadToDeg_double");
        double angleInRadians = 0.0;
        double expResult = 0.0;
        double result = Vector_Angles.getRadToDeg_double(angleInRadians);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

}