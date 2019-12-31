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
package uk.ac.leeds.ccg.vector.geometry;

import java.math.RoundingMode;
import uk.ac.leeds.ccg.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.vector.core.Vector_Object;

/**
 * An abstract class defining a geometrical object and the methods it must
 * implement.
 */
public abstract class Vector_AbstractGeometry2D extends Vector_Object {

    protected int DecimalPlacePrecision = 0;
    protected RoundingMode _RoundingMode = RoundingMode.FLOOR;

    public Vector_AbstractGeometry2D(Vector_Environment e) {
        super(e);
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
