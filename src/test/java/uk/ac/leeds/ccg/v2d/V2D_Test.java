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

import ch.obermuhlner.math.big.BigRational;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 * @author Andy Turner
 */
public class V2D_Test {
    
    public static final BigRational P0 = BigRational.ZERO;
    public static final BigRational P1 = BigRational.ONE;
    public static final BigRational P2 = BigRational.TWO;
    public static final BigRational P3 = BigRational.valueOf(3);
    public static final BigRational P4 = BigRational.valueOf(4);
    public static final BigRational P5 = BigRational.valueOf(5);
    public static final BigRational P6 = BigRational.valueOf(6);
    public static final BigRational P7 = BigRational.valueOf(7);
    public static final BigRational P8 = BigRational.valueOf(8);
    public static final BigRational P9 = BigRational.valueOf(9);
    public static final BigRational P10 = BigRational.TEN;
    public static final BigRational P99 = BigRational.valueOf(99);
    public static final BigRational P100 = BigRational.valueOf(100);
    public static final BigRational P101 = BigRational.valueOf(101);
    public static final BigRational P999 = BigRational.valueOf(999);
    public static final BigRational P1000 = BigRational.valueOf(1000);
    public static final BigRational P1001 = BigRational.valueOf(1001);
    public static final BigRational P9999 = BigRational.valueOf(9999);
    public static final BigRational P10000 = BigRational.valueOf(10000);
    public static final BigRational P10001 = BigRational.valueOf(10001);
    public static final BigRational N0 = P0.negate();
    public static final BigRational N1 = P1.negate();
    public static final BigRational N2 = P2.negate();
    public static final BigRational N3 = P3.negate();
    public static final BigRational N4 = P4.negate();
    public static final BigRational N5 = P5.negate();
    public static final BigRational N6 = P6.negate();
    public static final BigRational N7 = P7.negate();
    public static final BigRational N8 = P8.negate();
    public static final BigRational N9 = P9.negate();
    public static final BigRational N10 = P10.negate();
    public static final BigRational N99 = P99.negate();
    public static final BigRational N100 = P100.negate();
    public static final BigRational N101 = P101.negate();
    public static final BigRational N999 = P999.negate();
    public static final BigRational N1000 = P1000.negate();
    public static final BigRational N1001 = P1001.negate();
    public static final BigRational N9999 = P9999.negate();
    public static final BigRational N10000 = P10000.negate();
    public static final BigRational N10001 = P10001.negate();
    
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
