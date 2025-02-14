/*
 * Copyright 2020 Andy Turner, University of Leeds.
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

import java.math.RoundingMode;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 * V2D_Test
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V2D_Test {
    public static final V2D_Environment env = new V2D_Environment(-6, RoundingMode.UP); 
    public static final double P1E5 = 100000d;
    public static final double P1E6 = 1000000d;
    public static final double P1E7 = 10000000d;
    public static final double P1E8 = 100000000d;
    public static final double P1E9 = 1000000000d;
    public static final double P1E10 = 10000000000d;
    public static final double P1E11 = 100000000000d;
    public static final double P1E12 = 1000000000000d;
    public static final double P1E13 = 10000000000000d;
    public static final double N1E5 = -100000d;
    public static final double N1E6 = -1000000d;
    public static final double N1E7 = -10000000d;
    public static final double N1E8 = -100000000d;
    public static final double N1E9 = -1000000000d;
    public static final double N1E10 = -10000000000d;
    public static final double N1E11 = -100000000000d;
    public static final double N1E12 = -1000000000000d;
    public static final double N1E13 = -10000000000000d;
    public static final double P0_1E2 = 0.01d;
    public static final double P0_1E3 = 0.001d;
    public static final double P0_1E4 = 0.0001d;
    public static final double P0_1E5 = 0.00001d;
    public static final double P0_1E6 = 0.000001d;
    public static final double P0_1E7 = 0.0000001d;
    public static final double P0_1E8 = 0.00000001d;
    public static final double P0_1E9 = 0.000000001d;
    public static final double P0_1E10 = 0.0000000001d;
    public static final double P0_1E11 = 0.00000000001d;
    public static final double P0_1E12 = 0.000000000001d;
    public static final double P0_1E13 = 0.0000000000001d;
    public static final double P0_1E14 = 0.00000000000001d;
    public static final double N0_1E2 = -0.01d;
    public static final double N0_1E3 = -0.001d;
    public static final double N0_1E4 = -0.0001d;
    public static final double N0_1E5 = -0.00001d;
    public static final double N0_1E6 = -0.000001d;
    public static final double N0_1E7 = -0.0000001d;
    public static final double N0_1E8 = -0.00000001d;
    public static final double N0_1E9 = -0.000000001d;
    public static final double N0_1E10 = -0.0000000001d;
    public static final double N0_1E11 = -0.00000000001d;
    public static final double N0_1E12 = -0.000000000001d;
    public static final double N0_1E13 = -0.0000000000001d;
    public static final double N0_1E14 = -0.00000000000001d;
    // P2x
    public static final V2D_Vector P2P2 = new V2D_Vector(2.0d, 2.0d);
    public static final V2D_Vector P2P1 = new V2D_Vector(2.0d, 1.0d);
    public static final V2D_Vector P2P0 = new V2D_Vector(2.0d, 0.0d);
    public static final V2D_Vector P2N1 = new V2D_Vector(2.0d, -1.0d);
    public static final V2D_Vector P2N2 = new V2D_Vector(2.0d, -2.0d);
    // P1x
    public static final V2D_Vector P1P2 = new V2D_Vector(1.0d, 2.0d);
    public static final V2D_Vector P1P1 = new V2D_Vector(1.0d, 1.0d);
    public static final V2D_Vector P1P0 = new V2D_Vector(1.0d, 0.0d);
    public static final V2D_Vector P1N1 = new V2D_Vector(1.0d, -1.0d);
    public static final V2D_Vector P1N2 = new V2D_Vector(1.0d, -2.0d);
    // P0x
    public static final V2D_Vector P0P2 = new V2D_Vector(0.0d, 2.0d);
    public static final V2D_Vector P0P1 = new V2D_Vector(0.0d, 1.0d);
    public static final V2D_Vector P0P0 = new V2D_Vector(0.0d, 0.0d);
    public static final V2D_Vector P0N1 = new V2D_Vector(0.0d, -1.0d);
    public static final V2D_Vector P0N2 = new V2D_Vector(0.0d, -2.0d);
    // N1x
    public static final V2D_Vector N1P2 = new V2D_Vector(-1.0d, 2.0d);
    public static final V2D_Vector N1P1 = new V2D_Vector(-1.0d, 1.0d);
    public static final V2D_Vector N1P0 = new V2D_Vector(-1.0d, 0.0d);
    public static final V2D_Vector N1N1 = new V2D_Vector(-1.0d, -1.0d);
    public static final V2D_Vector N1N2 = new V2D_Vector(-1.0d, -2.0d);
    // N2x
    public static final V2D_Vector N2P2 = new V2D_Vector(-2.0d, 2.0d);
    public static final V2D_Vector N2P1 = new V2D_Vector(-2.0d, 1.0d);
    public static final V2D_Vector N2P0 = new V2D_Vector(-2.0d, 0.0d);
    public static final V2D_Vector N2N1 = new V2D_Vector(-2.0d, -1.0);
    public static final V2D_Vector N2N2 = new V2D_Vector(-2.0d, -2.0d);
    // P2x
    public static final V2D_Point pP2P2 = new V2D_Point(env, 2.0d, 2.0d);
    public static final V2D_Point pP2P1 = new V2D_Point(env, 2.0d, 1.0d);
    public static final V2D_Point pP2P0 = new V2D_Point(env, 2.0d, 0.0d);
    public static final V2D_Point pP2N1 = new V2D_Point(env, 2.0d, -1.0d);
    public static final V2D_Point pP2N2 = new V2D_Point(env, 2.0d, -2.0d);
    // P1x
    public static final V2D_Point pP1P2 = new V2D_Point(env, 1.0d, 2.0d);
    public static final V2D_Point pP1P1 = new V2D_Point(env, 1.0d, 1.0d);
    public static final V2D_Point pP1P0 = new V2D_Point(env, 1.0d, 0.0d);
    public static final V2D_Point pP1N1 = new V2D_Point(env, 1.0d, -1.0d);
    public static final V2D_Point pP1N2 = new V2D_Point(env, 1.0d, -2.0d);
    // P0x
    public static final V2D_Point pP0P2 = new V2D_Point(env, 0.0d, 2.0d);
    public static final V2D_Point pP0P1 = new V2D_Point(env, 0.0d, 1.0d);
    public static final V2D_Point pP0P0 = new V2D_Point(env, 0.0d, 0.0d);
    public static final V2D_Point pP0N1 = new V2D_Point(env, 0.0d, -1.0d);
    public static final V2D_Point pP0N2 = new V2D_Point(env, 0.0d, -2.0d);
    // N1x
    public static final V2D_Point pN1P2 = new V2D_Point(env, -1.0d, 2.0d);
    public static final V2D_Point pN1P1 = new V2D_Point(env, -1.0d, 1.0d);
    public static final V2D_Point pN1P0 = new V2D_Point(env, -1.0d, 0.0d);
    public static final V2D_Point pN1N1 = new V2D_Point(env, -1.0d, -1.0d);
    public static final V2D_Point pN1N2 = new V2D_Point(env, -1.0d, -2.0d);
    // N2x
    public static final V2D_Point pN2P2 = new V2D_Point(env, -2.0d, 2.0d);
    public static final V2D_Point pN2P1 = new V2D_Point(env, -2.0d, 1.0d);
    public static final V2D_Point pN2P0 = new V2D_Point(env, -2.0d, 0.0d);
    public static final V2D_Point pN2N1 = new V2D_Point(env, -2.0d, -1.0);
    public static final V2D_Point pN2N2 = new V2D_Point(env, -2.0d, -2.0d);
}
