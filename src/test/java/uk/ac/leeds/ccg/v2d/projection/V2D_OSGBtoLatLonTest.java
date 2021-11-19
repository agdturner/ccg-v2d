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
package uk.ac.leeds.ccg.v2d.projection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Andy Turner
 */
public class V2D_OSGBtoLatLonTest {
    
    public V2D_OSGBtoLatLonTest() {
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
     * Test of latlon2osgb method, of class V2D_OSGBtoLatLon.
     */
    @Test
    public void testLatlon2osgb_double_double() {
        System.out.println("latlon2osgb");
        double lat = 53.807882;
        double lon = -1.557103;
        double[] expResult = new double[2];
        expResult[0] = 429162.30399011535; // Easting
        expResult[1] = 434735.37573485856; // Northing
        double[] result = V2D_OSGBtoLatLon.latlon2osgb(lat, lon);
        for (int i = 0; i < expResult.length; i ++) {
            assertEquals(expResult[i], result[i]);
        }
    }

    /**
     * Test of latlon2osgb method, of class V2D_OSGBtoLatLon.
     */
    @Test
    public void testLatlon2osgb_4args() {
        System.out.println("latlon2osgb");
        BigDecimal lat = new BigDecimal("53.807882");
        BigDecimal lon = new BigDecimal("-1.557103");
        int oom = 0;
        RoundingMode rm = RoundingMode.HALF_UP;
        V2D_OSGBtoLatLon instance = new V2D_OSGBtoLatLon();
        BigDecimal[] expResult = new BigDecimal[2];
        expResult[0] = BigDecimal.valueOf(429162);
        expResult[1] = BigDecimal.valueOf(434735);
        BigDecimal[] result = instance.latlon2osgb(lat, lon, oom, rm);
        for (int i = 0; i < expResult.length; i ++) {
            assertTrue(expResult[i].compareTo(result[i]) == 0);
        }
    }

    /**
     * Test of osgb2latlon method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testOsgb2latlon_double_double() {
        System.out.println("osgb2latlon");
        double easting = 0.0;
        double northing = 0.0;
        double[] expResult = null;
        double[] result = V2D_OSGBtoLatLon.osgb2latlon(easting, northing);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of osgb2latlon method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testOsgb2latlon_3args() {
        System.out.println("osgb2latlon");
        double easting = 0.0;
        double northing = 0.0;
        boolean verbose = false;
        double[] expResult = null;
        double[] result = V2D_OSGBtoLatLon.osgb2latlon(easting, northing, verbose);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of osgb2latlon method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testOsgb2latlon_4args() {
        System.out.println("osgb2latlon");
        BigDecimal easting = null;
        BigDecimal northing = null;
        int dp = 0;
        RoundingMode rm = null;
        V2D_OSGBtoLatLon instance = new V2D_OSGBtoLatLon();
        BigDecimal[] expResult = null;
        BigDecimal[] result = instance.osgb2latlon(easting, northing, dp, rm);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of osgb2latlon method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testOsgb2latlon_5args() {
        System.out.println("osgb2latlon");
        BigDecimal easting = null;
        BigDecimal northing = null;
        int oom = 0;
        RoundingMode rm = null;
        boolean verbose = false;
        V2D_OSGBtoLatLon instance = new V2D_OSGBtoLatLon();
        BigDecimal[] expResult = null;
        BigDecimal[] result = instance.osgb2latlon(easting, northing, oom, rm, verbose);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toRadians method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testToRadians_double() {
        System.out.println("toRadians");
        double x = 0.0;
        double expResult = 0.0;
        double result = V2D_OSGBtoLatLon.toRadians(x);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toRadians method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testToRadians_3args() {
        System.out.println("toRadians");
        BigDecimal x = null;
        int dp = 0;
        RoundingMode rm = null;
        V2D_OSGBtoLatLon instance = new V2D_OSGBtoLatLon();
        BigDecimal expResult = null;
        BigDecimal result = instance.toRadians(x, dp, rm);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toDegrees method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testToDegrees_double() {
        System.out.println("toDegrees");
        double x = 0.0;
        double expResult = 0.0;
        double result = V2D_OSGBtoLatLon.toDegrees(x);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toDegrees method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testToDegrees_3args() {
        System.out.println("toDegrees");
        BigDecimal x = null;
        int dp = 0;
        RoundingMode rm = null;
        V2D_OSGBtoLatLon instance = new V2D_OSGBtoLatLon();
        BigDecimal expResult = null;
        BigDecimal result = instance.toDegrees(x, dp, rm);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
