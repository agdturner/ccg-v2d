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
package uk.ac.leeds.ccg.andyt.vector.core;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import uk.ac.leeds.ccg.andyt.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.andyt.math.Math_BigDecimal;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.andyt.vector.io.Vector_Files;
import uk.ac.leeds.ccg.andyt.vector.projection.Vector_OSGBtoLatLon;

/**
 *
 * @author geoagdt
 */
public class Vector_Environment {

    public Vector_Files files;
    
    public final Generic_Environment env;

    public final Grids_Environment ge;
    
    public Math_BigDecimal bd;
    
    protected Vector_OSGBtoLatLon OSGBtoLatLon;

    public Vector_Environment() throws IOException {
        this(new Grids_Environment(new Generic_Environment()));
    }

    public Vector_Environment(Grids_Environment ge){
        super();
        this.ge = ge;
        this.env = ge.env;
        this.files = new Vector_Files(env.files.getDir());
        bd = new Math_BigDecimal();
    }
    
    public BigDecimal getRounded_BigDecimal(
            BigDecimal toRoundBigDecimal,
            BigDecimal toRoundToBigDecimal) {
        int scale = toRoundToBigDecimal.scale();
        BigDecimal r = toRoundBigDecimal.setScale(scale - 1, RoundingMode.FLOOR);
        r = r.setScale(scale);
        r = r.add(toRoundToBigDecimal);
        return r;
    }

    public Vector_OSGBtoLatLon getOSGBtoLatLon() {
        if (OSGBtoLatLon == null) {
            OSGBtoLatLon = new Vector_OSGBtoLatLon();
        }
        return OSGBtoLatLon;
    }

}
