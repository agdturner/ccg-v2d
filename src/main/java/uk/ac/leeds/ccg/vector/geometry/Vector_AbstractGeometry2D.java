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
package uk.ac.leeds.ccg.vector.geometry;

import java.math.RoundingMode;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.vector.core.Vector_Object;

/**
 * An abstract class defining a geometrical object and the methods it must
 * implement.
 */
public abstract class Vector_AbstractGeometry2D extends Vector_Object {

    protected int DecimalPlacePrecision = 0;
    protected RoundingMode _RoundingMode = RoundingMode.FLOOR;

    protected Vector_AbstractGeometry2D() {    }

    public Vector_AbstractGeometry2D(Vector_Environment ve) {
        super(ve);
    }

    @Override
    public String toString() {
        return "DecimalPlacePrecision(" + DecimalPlacePrecision + "),"
                + "RoundingMode(" + _RoundingMode + "),";
    }

    public int getDecimalPlacePrecision() {
        return DecimalPlacePrecision;
    }

    /**
     * Default method probably best overridden to determine what setting
     * DecimalPlacePrecision_Integer involves
     *
     * @param precision The number of decimal places set for
     * DecimalPlacePrecision_Integer.
     * @return The current value of DecimalPlacePrecision_Integer.
     */
    protected int setDecimalPlacePrecision(int precision) {
        int result = getDecimalPlacePrecision();
        DecimalPlacePrecision = precision;
        applyDecimalPlacePrecision();
        return result;
    }

    public RoundingMode get_RoundingMode() {
        return _RoundingMode;
    }

    public void set_RoundingMode(RoundingMode r) {
        _RoundingMode = r;
    }
    
    public abstract Vector_Envelope2D getEnvelope2D();
    
    public abstract void applyDecimalPlacePrecision();
}
