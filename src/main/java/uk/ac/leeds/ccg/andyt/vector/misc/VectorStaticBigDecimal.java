/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.leeds.ccg.andyt.vector.misc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author geoagdt
 */
public class VectorStaticBigDecimal {

    public VectorStaticBigDecimal(){}

    public static BigDecimal getRounded_BigDecimal(
            BigDecimal toRoundBigDecimal,
            BigDecimal toRoundToBigDecimal){
        int scale = toRoundToBigDecimal.scale();
        BigDecimal result = toRoundBigDecimal.setScale(
                scale - 1,
                RoundingMode.FLOOR);
        result = result.setScale(
                scale);
        result = result.add(toRoundToBigDecimal);
        return result;
    }
}
