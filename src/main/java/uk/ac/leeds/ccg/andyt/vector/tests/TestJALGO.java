/**
 * Component of a library for handling spatial vector data.
 * Copyright (C) 2009 Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.vector.tests;

import java.math.BigDecimal;
import org.ojalgo.function.implementation.BigFunction;
import org.ojalgo.constant.BigMath;

/**
 *
 * @author geoagdt
 */
@Deprecated
public class TestJALGO {
public TestJALGO(){}

    public static void main(String[] args){
        new TestJALGO().run();
    }

    public void run(){

        BigDecimal rootThousand0 = BigFunction.POW.invoke(
                BigMath.THOUSAND,
                BigMath.HALF);
        System.out.println("" + BigMath.THOUSAND + " precision " + BigMath.THOUSAND.scale());
        System.out.println("" + BigMath.HALF + " precision " + BigMath.HALF.scale());
        System.out.println("" + rootThousand0 + " precision " + rootThousand0.scale());
        System.out.println("Math.pow(1000.0d,0.5d) " + Math.pow(1000.0d,0.5d));

        BigDecimal thousand = BigMath.THOUSAND.setScale(100);
        BigDecimal half = BigMath.HALF.setScale(100);
        BigDecimal rootThousand1 = BigFunction.POW.invoke(
                thousand,
                half);
        System.out.println("" + rootThousand1 + " precision " + rootThousand1.scale());
        
        BigDecimal rootTwo = BigFunction.POW.invoke(
                BigMath.TWO,
                BigMath.HALF);
        System.out.println("" + rootTwo + " precision " + rootTwo.scale());
        System.out.println("Math.pow(2.0d,0.5d) " + Math.pow(2.0d,0.5d));

    }
}
