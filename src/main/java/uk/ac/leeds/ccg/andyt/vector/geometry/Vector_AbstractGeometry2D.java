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
package uk.ac.leeds.ccg.andyt.vector.geometry;

import java.io.Serializable;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;

/**
 * An abstract class defining a geometrical object and the methods it must
 * implement.
 */
public abstract class Vector_AbstractGeometry2D
        implements Serializable {

    public Vector_Environment _Vector_Environment;

    protected Integer _DecimalPlacePrecision_Integer;
    private static final int DefaultDecimalPlacePrecision_int = 0;
    protected RoundingMode _RoundingMode;
    private static final RoundingMode Default_RoundingMode = RoundingMode.FLOOR;

    @Override
    public String toString() {
        return "_DecimalPlacePrecision(" + _DecimalPlacePrecision_Integer + ")," +
                "_RoundingMode(" + _RoundingMode + "),";
    }

    public abstract Vector_Envelope2D getEnvelope2D();

    public abstract void applyDecimalPlacePrecision();

    /**
     * @return 0 (a primitive copy of DefaultDecimalPlacePrecision_int)
     */
    public int getDefaultDecimalPlacePrecision_int(){
        return DefaultDecimalPlacePrecision_int;
    }

    public int get_DecimalPlacePrecision(){
        if (_DecimalPlacePrecision_Integer == null){
            _DecimalPlacePrecision_Integer = DefaultDecimalPlacePrecision_int;
        }
        return _DecimalPlacePrecision_Integer;
    }

    /**
     * Default method probably best overridden to determine what setting
     * _DecimalPlacePrecision_Integer involves
     * @param precision The number of decimal places set for 
     * _DecimalPlacePrecision_Integer. 
     * @return The current value of _DecimalPlacePrecision_Integer.
     */
    protected int set_DecimalPlacePrecision(int precision){
        int result = get_DecimalPlacePrecision();
        this._DecimalPlacePrecision_Integer = precision;
        return result;
    }

    public RoundingMode getDefault_RoundingMode(){
        return Default_RoundingMode;
    }

    public RoundingMode get_RoundingMode(){
        if (_RoundingMode == null){
            this._RoundingMode = Default_RoundingMode;
        }
        return this._RoundingMode;
    }
}
