/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.geometry;

import java.io.IOException;
import java.math.BigDecimal;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;

/**
 *
 * @author geoagdt
 */
public class Vector_Envelope2DTest {

    Vector_Environment env;
    
    public Vector_Envelope2DTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
        try {
            env = new Vector_Environment();
        } catch (IOException ex) {
            ex.printStackTrace(System.err);
        }
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of toString method, of class Vector_Envelope2D.
     */
    @Test
    public void testToString() {
//        System.out.println("toString");
//        Vector_Envelope2D instance = new Vector_Envelope2D();
//        String expResult = "";
//        String result = instance.toString();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of envelope method, of class Vector_Envelope2D.
     */
    @Test
    public void testEnvelope_VectorEnvelope2D_VectorEnvelope2D() {
//        System.out.println("envelope");
//        Vector_Envelope2D a_Envelope2D = null;
//        Vector_Envelope2D b_Envelope2D = null;
//        Vector_Envelope2D expResult = null;
//        Vector_Envelope2D result = Vector_Envelope2D.envelope(a_Envelope2D, b_Envelope2D);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of envelope method, of class Vector_Envelope2D.
     */
    @Test
    public void testEnvelope_VectorEnvelope2D() {
//        System.out.println("envelope");
//        Vector_Envelope2D a_Envelope2D = null;
//        Vector_Envelope2D instance = new Vector_Envelope2D();
//        Vector_Envelope2D expResult = null;
//        Vector_Envelope2D result = instance.envelope(a_Envelope2D);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersectsFailFast method, of class Vector_Envelope2D.
     */
    @Test
    public void testGetIntersects_Envelope2D() {
        System.out.println("getIntersects");
        boolean expResult;
        boolean result;

        Vector_Point2D a;
        Vector_Point2D b = new Vector_Point2D(null, "1", "1");
        Vector_Envelope2D be = b.getEnvelope2D();
        Vector_Envelope2D abe;
        BigDecimal aX = new BigDecimal("1");
        BigDecimal aY = new BigDecimal("1");
        BigDecimal ten = new BigDecimal("10");
        for (int i = 0; i < 1000; i++) {
            aX = aX.divide(ten);
            aY = aY.divide(ten);
            a = new Vector_Point2D(env, aX, aY);
            System.out.println("a " + a.toString());
            abe = new Vector_Envelope2D(
                    a,
                    b);
            expResult = true;
            result = abe.getIntersects(a.getEnvelope2D());
            System.out.println("abe.getIntersects(a.getEnvelope2D()) " + result);
            assertEquals(expResult, result);
            result = abe.getIntersects(be);
            assertEquals(expResult, result);
        }
    }

    /**
     * Test of getIntersectsFailFast method, of class Vector_Envelope2D.
     */
    @Test
    public void testGetIntersects_VectorLineSegment2D() {
//        System.out.println("getIntersectsFailFast");
//        VectorLineSegment2D a_LineSegment2D = null;
//        Vector_Envelope2D instance = new Vector_Envelope2D();
//        int expResult = 0;
//        int result = instance.getIntersectsFailFast(a_LineSegment2D);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersectsFailFast method, of class Vector_Envelope2D.
     */
    @Test
    public void testGetIntersects_VectorPoint2D() {
//        System.out.println("getIntersectsFailFast");
//        Vector_Point2D aPoint = null;
//        Vector_Envelope2D instance = new Vector_Envelope2D();
//        boolean expResult = false;
//        boolean result = instance.getIntersectsFailFast(aPoint);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getIntersectsFailFast method, of class Vector_Envelope2D.
     */
    @Test
    public void testGetIntersects_BigDecimal_BigDecimal() {
//        System.out.println("getIntersectsFailFast");
//        BigDecimal x = null;
//        BigDecimal y = null;
//        Vector_Envelope2D instance = new Vector_Envelope2D();
//        boolean expResult = false;
//        boolean result = instance.getIntersectsFailFast(x, y);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getEnvelope2D method, of class Vector_Envelope2D.
     */
    @Test
    public void testGetEnvelope2D() {
//        System.out.println("getEnvelope2D");
//        Vector_Envelope2D instance = new Vector_Envelope2D();
//        Vector_Envelope2D expResult = null;
//        Vector_Envelope2D result = instance.getEnvelope2D();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of applyDecimalPlacePrecision method, of class Vector_Envelope2D.
     */
    @Test
    public void testApplyDecimalPlacePrecision() {
//        System.out.println("applyDecimalPlacePrecision");
//        Vector_Envelope2D instance = new Vector_Envelope2D();
//        instance.applyDecimalPlacePrecision();
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }
}
