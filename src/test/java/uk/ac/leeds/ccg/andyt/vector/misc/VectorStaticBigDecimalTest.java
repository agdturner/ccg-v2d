/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.leeds.ccg.andyt.vector.misc;

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
public class VectorStaticBigDecimalTest {

    public VectorStaticBigDecimalTest() {
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
     * Test of getMin method, of class VectorStaticBigDecimal.
     */
    @Test
    public void testGetMin() {
//        System.out.println("getMin");
//        BigDecimal a_BigDecimal = null;
//        BigDecimal b_BigDecimal = null;
//        BigDecimal expResult = null;
//        BigDecimal result = VectorStaticBigDecimal.getMin(a_BigDecimal, b_BigDecimal);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getMax method, of class VectorStaticBigDecimal.
     */
    @Test
    public void testGetMax() {
//        System.out.println("getMax");
//        BigDecimal a_BigDecimal = null;
//        BigDecimal b_BigDecimal = null;
//        BigDecimal expResult = null;
//        BigDecimal result = VectorStaticBigDecimal.getMax(a_BigDecimal, b_BigDecimal);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
    }

    /**
     * Test of getRounded_BigDecimal method, of class VectorStaticBigDecimal.
     */
    @Test
    public void testGetRounded_BigDecimal() {
        System.out.println("getRounded_BigDecimal");
        boolean expResult;
        boolean result;
        BigDecimal toRound_BigDecimal = new BigDecimal("53.0092");
        System.out.println("toRound_BigDecimal " + toRound_BigDecimal.toPlainString());

        BigDecimal toRoundTo_BigDecimal = new BigDecimal("0.0005");
        System.out.println("toRoundTo_BigDecimal " + toRoundTo_BigDecimal.toPlainString());
        
        BigDecimal rounded_BigDecimal = VectorStaticBigDecimal.getRounded_BigDecimal(
             toRound_BigDecimal,
             toRoundTo_BigDecimal);
        
        System.out.println(
                "rounded_BigDecimal; VectorStaticBigDecimal.getRounded_BigDecimal(toRound_BigDecimal,toRoundTo_BigDecimal)" +
                rounded_BigDecimal);
        
        expResult = true;
        result = rounded_BigDecimal.scale() == toRoundTo_BigDecimal.scale();
        assertEquals(expResult, result);

//        expResult = true;
//        result = rounded_BigDecimal.ulp() == toRoundTo_BigDecimal.ulp();
//        assertEquals(expResult, result);
    }

}