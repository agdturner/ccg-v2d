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

import java.math.BigDecimal;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import uk.ac.leeds.ccg.math.number.Math_BigRational;

/**
 * Unit tests for the V2D_Point class.
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_PointTest {

    public V2D_PointTest() {
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUp() {
    }

    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of equals method, of class V2D_Point.
     */
    @Test
    public void testEquals() {
        System.out.println("equals");
        V2D_Point a = new V2D_Point(0.1d, 0.3d);
        V2D_Point b = new V2D_Point(new BigDecimal("0.1"), new BigDecimal("0.3"));
        Assertions.assertTrue(a.equals(b));
        // Test 2
        V2D_Point c = new V2D_Point(a);
        Assertions.assertTrue(a.equals(c));
        // Test 3
        V2D_Point d = new V2D_Point(0.1d, 0.4d);
        Assertions.assertFalse(b.equals(d));
        // Test 4
        d = new V2D_Point(0.2d, 0.3d);
        Assertions.assertFalse(c.equals(d));
        // Test 5
        Math_BigRational third = Math_BigRational.valueOf(1, 3);
        double dthird = 1.0d/3.0d;
        a = new V2D_Point(dthird, dthird);        
        b = new V2D_Point(third, third);
        Assertions.assertFalse(a.equals(b));
    }

}
