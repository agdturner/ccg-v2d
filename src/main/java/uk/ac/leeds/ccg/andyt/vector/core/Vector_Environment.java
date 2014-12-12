/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
