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

import java.io.File;
import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.andyt.vector.io.Vector_Files;

/**
 *
 * @author geoagdt
 */
public class Vector_Environment {

    protected File directory;
    
    protected Vector_Files vf;
    
    protected Grids_Environment ge;
    
    protected Generic_BigDecimal bd;

    /**
     * @return the _Generic_BigDecimal
     */
    public Generic_BigDecimal get_Generic_BigDecimal() {
        if (bd == null) {
            bd = new Generic_BigDecimal();
        }
        return bd;
    }

    /**
     * @return the _Generic_BigDecimal
     */
    public Grids_Environment getGrids_Environment() {
        if (ge == null) {
            ge = new Grids_Environment(vf.getGridsDirectory());
        }
        return ge;
    }

    public Vector_Environment(){}

    public Vector_Environment(Grids_Environment ge){
        this.ge = ge;
    }
}
