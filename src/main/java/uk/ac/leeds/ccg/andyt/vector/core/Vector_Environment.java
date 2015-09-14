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

import uk.ac.leeds.ccg.andyt.generic.math.Generic_BigDecimal;

/**
 *
 * @author geoagdt
 */
public class Vector_Environment {

    private Generic_BigDecimal _Generic_BigDecimal;

    /**
     * @return the _Generic_BigDecimal
     */
    public Generic_BigDecimal get_Generic_BigDecimal() {
        if (_Generic_BigDecimal == null) {
            init_Generic_BigDecimal();
        }
            return _Generic_BigDecimal;
    }

    /**
     * @param Generic_BigDecimal the _Generic_BigDecimal to set
     */
    public void set_Generic_BigDecimal(Generic_BigDecimal Generic_BigDecimal) {
        this._Generic_BigDecimal = Generic_BigDecimal;
    }

    /**
     */
    public void init_Generic_BigDecimal() {
        _Generic_BigDecimal = new Generic_BigDecimal();
    }

    /**
     * Creates a new Vector_Environment
     */
    public Vector_Environment(){}
}
