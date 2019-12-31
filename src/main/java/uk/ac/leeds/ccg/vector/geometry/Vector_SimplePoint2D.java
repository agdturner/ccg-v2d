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

import java.math.BigDecimal;

/**
 *
 * @author geoagdt
 */
public class Vector_SimplePoint2D { //extends Vector_AbstractGeometry2D {
    
    /**
     * The x coordinate of the VectorPoint2D
     */
    public BigDecimal _x;

    /**
     * The y coordinate of the VectorPoint2D
     */
    public BigDecimal _y;

    /**
     * Creates a default VectorPoint2D with:
     * _x = null;
     * _y = null;
     */
    public Vector_SimplePoint2D() {
    }

    /**
     * Creates a default VectorPoint2D with:
     * _x = x;
     * _y = y;
     * @param x BigDecimal
     * @param y BigDecimal
     */
    public Vector_SimplePoint2D(
            BigDecimal x,
            BigDecimal y) {
        _x = x;
        _y = y;
    }

    public BigDecimal getX() {
        return _x;
    }

    public void setX(BigDecimal _x) {
        this._x = _x;
    }

    public BigDecimal getY() {
        return _y;
    }

    public void setY(BigDecimal _y) {
        this._y = _y;
    }
}
