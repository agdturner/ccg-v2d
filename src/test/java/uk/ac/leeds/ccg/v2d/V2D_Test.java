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
package uk.ac.leeds.ccg.v2d;

import uk.ac.leeds.ccg.math.number.Math_BigRational;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 * @author Andy Turner
 */
public class V2D_Test {
    
    public static final Math_BigRational P0 = Math_BigRational.ZERO;
    public static final Math_BigRational P1 = Math_BigRational.ONE;
    public static final Math_BigRational P2 = Math_BigRational.TWO;
    public static final Math_BigRational P3 = Math_BigRational.valueOf(3);
    public static final Math_BigRational P4 = Math_BigRational.valueOf(4);
    public static final Math_BigRational P5 = Math_BigRational.valueOf(5);
    public static final Math_BigRational P6 = Math_BigRational.valueOf(6);
    public static final Math_BigRational P7 = Math_BigRational.valueOf(7);
    public static final Math_BigRational P8 = Math_BigRational.valueOf(8);
    public static final Math_BigRational P9 = Math_BigRational.valueOf(9);
    public static final Math_BigRational P10 = Math_BigRational.TEN;
    public static final Math_BigRational P99 = Math_BigRational.valueOf(99);
    public static final Math_BigRational P100 = Math_BigRational.valueOf(100);
    public static final Math_BigRational P101 = Math_BigRational.valueOf(101);
    public static final Math_BigRational P999 = Math_BigRational.valueOf(999);
    public static final Math_BigRational P1000 = Math_BigRational.valueOf(1000);
    public static final Math_BigRational P1001 = Math_BigRational.valueOf(1001);
    public static final Math_BigRational P9999 = Math_BigRational.valueOf(9999);
    public static final Math_BigRational P10000 = Math_BigRational.valueOf(10000);
    public static final Math_BigRational P10001 = Math_BigRational.valueOf(10001);
    public static final Math_BigRational N0 = P0.negate();
    public static final Math_BigRational N1 = P1.negate();
    public static final Math_BigRational N2 = P2.negate();
    public static final Math_BigRational N3 = P3.negate();
    public static final Math_BigRational N4 = P4.negate();
    public static final Math_BigRational N5 = P5.negate();
    public static final Math_BigRational N6 = P6.negate();
    public static final Math_BigRational N7 = P7.negate();
    public static final Math_BigRational N8 = P8.negate();
    public static final Math_BigRational N9 = P9.negate();
    public static final Math_BigRational N10 = P10.negate();
    public static final Math_BigRational N99 = P99.negate();
    public static final Math_BigRational N100 = P100.negate();
    public static final Math_BigRational N101 = P101.negate();
    public static final Math_BigRational N999 = P999.negate();
    public static final Math_BigRational N1000 = P1000.negate();
    public static final Math_BigRational N1001 = P1001.negate();
    public static final Math_BigRational N9999 = P9999.negate();
    public static final Math_BigRational N10000 = P10000.negate();
    public static final Math_BigRational N10001 = P10001.negate();
    
    public final V2D_Point P1P1;
    public final V2D_Point P1P0;
    public final V2D_Point P1N1;
    public final V2D_Point P0P1;
    public final V2D_Point P0P0;
    public final V2D_Point P0N1;
    public final V2D_Point N1P1;
    public final V2D_Point N1P0;
    public final V2D_Point N1N1;


    public V2D_Test() {
        P1P1 = new V2D_Point(P1, P1);
        P1P0 = new V2D_Point(P1, P0);
        P1N1 = new V2D_Point(P1, N1);
        P0P1 = new V2D_Point(P0, P1);
        P0P0 = new V2D_Point(P0, P0);
        P0N1 = new V2D_Point(P0, N1);
        N1P1 = new V2D_Point(N1, P1);
        N1P0 = new V2D_Point(N1, P0);
        N1N1 = new V2D_Point(N1, N1);
    }
}
