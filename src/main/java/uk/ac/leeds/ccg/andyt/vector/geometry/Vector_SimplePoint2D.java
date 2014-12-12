/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.geometry;

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
     * @param x
     * @param y
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
