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
package uk.ac.leeds.ccg.v2d.geometry.d.test;

import uk.ac.leeds.ccg.v2d.core.d.V2D_Environment_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Point_d;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Vector_d;

/**
 * V2D_Test
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V2D_Test_d {
    
    public static final V2D_Environment_d env = new V2D_Environment_d(0.00000001);
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
    public static final V2D_Vector_d P2P2 = new V2D_Vector_d(2.0d, 2.0d);
    public static final V2D_Vector_d P2P1 = new V2D_Vector_d(2.0d, 1.0d);
    public static final V2D_Vector_d P2P0 = new V2D_Vector_d(2.0d, 0.0d);
    public static final V2D_Vector_d P2N1 = new V2D_Vector_d(2.0d, -1.0d);
    public static final V2D_Vector_d P2N2 = new V2D_Vector_d(2.0d, -2.0d);
    // P1x
    public static final V2D_Vector_d P1P2 = new V2D_Vector_d(1.0d, 2.0d);
    public static final V2D_Vector_d P1P1 = new V2D_Vector_d(1.0d, 1.0d);
    public static final V2D_Vector_d P1P0 = new V2D_Vector_d(1.0d, 0.0d);
    public static final V2D_Vector_d P1N1 = new V2D_Vector_d(1.0d, -1.0d);
    public static final V2D_Vector_d P1N2 = new V2D_Vector_d(1.0d, -2.0d);
    // P0x
    public static final V2D_Vector_d P0P2 = new V2D_Vector_d(0.0d, 2.0d);
    public static final V2D_Vector_d P0P1 = new V2D_Vector_d(0.0d, 1.0d);
    public static final V2D_Vector_d P0P0 = new V2D_Vector_d(0.0d, 0.0d);
    public static final V2D_Vector_d P0N1 = new V2D_Vector_d(0.0d, -1.0d);
    public static final V2D_Vector_d P0N2 = new V2D_Vector_d(0.0d, -2.0d);
    // N1x
    public static final V2D_Vector_d N1P2 = new V2D_Vector_d(-1.0d, 2.0d);
    public static final V2D_Vector_d N1P1 = new V2D_Vector_d(-1.0d, 1.0d);
    public static final V2D_Vector_d N1P0 = new V2D_Vector_d(-1.0d, 0.0d);
    public static final V2D_Vector_d N1N1 = new V2D_Vector_d(-1.0d, -1.0d);
    public static final V2D_Vector_d N1N2 = new V2D_Vector_d(-1.0d, -2.0d);
    // N2x
    public static final V2D_Vector_d N2P2 = new V2D_Vector_d(-2.0d, 2.0d);
    public static final V2D_Vector_d N2P1 = new V2D_Vector_d(-2.0d, 1.0d);
    public static final V2D_Vector_d N2P0 = new V2D_Vector_d(-2.0d, 0.0d);
    public static final V2D_Vector_d N2N1 = new V2D_Vector_d(-2.0d, -1.0);
    public static final V2D_Vector_d N2N2 = new V2D_Vector_d(-2.0d, -2.0d);
    // P2x
    public static final V2D_Point_d pP2P2 = new V2D_Point_d(env, 2.0d, 2.0d);
    public static final V2D_Point_d pP2P1 = new V2D_Point_d(env, 2.0d, 1.0d);
    public static final V2D_Point_d pP2P0 = new V2D_Point_d(env, 2.0d, 0.0d);
    public static final V2D_Point_d pP2N1 = new V2D_Point_d(env, 2.0d, -1.0d);
    public static final V2D_Point_d pP2N2 = new V2D_Point_d(env, 2.0d, -2.0d);
    // P1x
    public static final V2D_Point_d pP1P2 = new V2D_Point_d(env, 1.0d, 2.0d);
    public static final V2D_Point_d pP1P1 = new V2D_Point_d(env, 1.0d, 1.0d);
    public static final V2D_Point_d pP1P0 = new V2D_Point_d(env, 1.0d, 0.0d);
    public static final V2D_Point_d pP1N1 = new V2D_Point_d(env, 1.0d, -1.0d);
    public static final V2D_Point_d pP1N2 = new V2D_Point_d(env, 1.0d, -2.0d);
    // P0x
    public static final V2D_Point_d pP0P2 = new V2D_Point_d(env, 0.0d, 2.0d);
    public static final V2D_Point_d pP0P1 = new V2D_Point_d(env, 0.0d, 1.0d);
    public static final V2D_Point_d pP0P0 = new V2D_Point_d(env, 0.0d, 0.0d);
    public static final V2D_Point_d pP0N1 = new V2D_Point_d(env, 0.0d, -1.0d);
    public static final V2D_Point_d pP0N2 = new V2D_Point_d(env, 0.0d, -2.0d);
    // N1x
    public static final V2D_Point_d pN1P2 = new V2D_Point_d(env, -1.0d, 2.0d);
    public static final V2D_Point_d pN1P1 = new V2D_Point_d(env, -1.0d, 1.0d);
    public static final V2D_Point_d pN1P0 = new V2D_Point_d(env, -1.0d, 0.0d);
    public static final V2D_Point_d pN1N1 = new V2D_Point_d(env, -1.0d, -1.0d);
    public static final V2D_Point_d pN1N2 = new V2D_Point_d(env, -1.0d, -2.0d);
    // N2x
    public static final V2D_Point_d pN2P2 = new V2D_Point_d(env, -2.0d, 2.0d);
    public static final V2D_Point_d pN2P1 = new V2D_Point_d(env, -2.0d, 1.0d);
    public static final V2D_Point_d pN2P0 = new V2D_Point_d(env, -2.0d, 0.0d);
    public static final V2D_Point_d pN2N1 = new V2D_Point_d(env, -2.0d, -1.0);
    public static final V2D_Point_d pN2N2 = new V2D_Point_d(env, -2.0d, -2.0d);
}
