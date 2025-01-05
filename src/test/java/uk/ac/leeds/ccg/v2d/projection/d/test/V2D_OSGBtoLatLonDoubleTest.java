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
package uk.ac.leeds.ccg.v2d.projection.d.test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import uk.ac.leeds.ccg.v2d.projection.d.V2D_OSGBtoLatLonDouble;

/**
 *
 * @author Andy Turner
 */
public class V2D_OSGBtoLatLonDoubleTest {
    
    public V2D_OSGBtoLatLonDoubleTest() {
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
        double[] result = V2D_OSGBtoLatLonDouble.latlon2osgb(lat, lon);
        for (int i = 0; i < expResult.length; i ++) {
            assertEquals(expResult[i], result[i]);
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
        double[] result = V2D_OSGBtoLatLonDouble.osgb2latlon(easting, northing);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of osgb2latlon method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testOsgb2latlon() {
        System.out.println("osgb2latlon");
        double easting = 0.0;
        double northing = 0.0;
        boolean verbose = false;
        double[] expResult = null;
        double[] result = V2D_OSGBtoLatLonDouble.osgb2latlon(easting, northing, verbose);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toRadians method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testToRadians() {
        System.out.println("toRadians");
        double x = 0.0;
        double expResult = 0.0;
        double result = V2D_OSGBtoLatLonDouble.toRadians(x);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toDegrees method, of class V2D_OSGBtoLatLon.
     */
    @Test
    @Disabled
    public void testToDegree() {
        System.out.println("toDegrees");
        double x = 0.0;
        double expResult = 0.0;
        double result = V2D_OSGBtoLatLonDouble.toDegrees(x);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
