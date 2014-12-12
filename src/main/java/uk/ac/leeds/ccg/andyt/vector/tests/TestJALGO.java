/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.leeds.ccg.andyt.vector.tests;
import java.math.BigDecimal;
import org.ojalgo.function.implementation.BigFunction;
import org.ojalgo.constant.BigMath;

/**
 *
 * @author geoagdt
 */
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
