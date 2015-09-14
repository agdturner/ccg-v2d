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
package uk.ac.leeds.ccg.andyt.vector.visualisation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.event.WindowAdapter;
//import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
//import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
//import javax.swing.JApplet;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Envelope2D;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Network2D;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Network2D.Connection;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Point2D;
//import uk.ac.leeds.ccg.andyt.;

/**
 * Class for rendering Vector_Network2D instances.
 */
public class VectorRenderNetwork2D extends VectorAbstractRender {

    public HashSet<Vector_Network2D> _VectorNetwork2D_HashSet;
    public Vector_Envelope2D _VectorEnvelope2D;
    public BigDecimal _YRange_BigDecimal;
    public BigDecimal _XRange_BigDecimal;

    public VectorRenderNetwork2D(){}

    public VectorRenderNetwork2D(
            JFrame a_JFrame) {
        _JFrame = a_JFrame;
        _JFrame.getContentPane().add("Center", this);
        init();
        _JFrame.pack();
        _JFrame.setSize(new Dimension(this._width_int, this._height_int));
        _JFrame.setVisible(true);
    }

    public VectorRenderNetwork2D(
            JFrame a_JFrame,
            int width,
            int height,
            HashSet<Vector_Network2D> _Network2D_HashSet) {
        this._width_int = width;
        this._height_int = height;
        _JFrame = a_JFrame;
        this._JFrame.getContentPane().add("Center", this);
        this.init();
        _JFrame.pack();
        _JFrame.setSize(new Dimension(this._width_int, this._height_int));
        //_JFrame.pack();
        _JFrame.setVisible(true);
        this._VectorNetwork2D_HashSet = _Network2D_HashSet;
    }

    public VectorRenderNetwork2D(
            JFrame a_JFrame,
            int width,
            int height,
            HashSet<Vector_Network2D> _Network2D_HashSet,
            Vector_Envelope2D a_VectorEnvelope2D) {
        this._width_int = width;
        this._height_int = height;
        _JFrame = a_JFrame;
        this._JFrame.getContentPane().add("Center", this);
        this.init();
        _JFrame.pack();
        _JFrame.setSize(new Dimension(this._width_int, this._height_int));
        //_JFrame.pack();
        _JFrame.setVisible(true);
        this._VectorNetwork2D_HashSet = _Network2D_HashSet;
        this._VectorEnvelope2D = a_VectorEnvelope2D;
    }

    @Override
    public void paint(Graphics _Graphics) {
        super.paint(_Graphics);
        this._Graphics2D = (Graphics2D) _Graphics;
        // Draw
        draw();
        //drawNetwork2D();
    }

    public void draw(Graphics g) {
        this._Graphics2D = (Graphics2D) g;
        draw();
    }

    public void draw() {
        Iterator a_Iterator = _VectorNetwork2D_HashSet.iterator();
        Vector_Network2D a_Network2D;
        while (a_Iterator.hasNext()) {
            a_Network2D = (Vector_Network2D) a_Iterator.next();
            Set<Vector_Point2D> keys = a_Network2D._Connection_HashMap.keySet();
            Iterator bIterator;
            bIterator = keys.iterator();
            Vector_Point2D a_Point2D;
            HashSet<Connection> a_Connection_HashSet;
            while (bIterator.hasNext()) {
                a_Point2D = (Vector_Point2D) bIterator.next();
                a_Connection_HashSet = (HashSet<Connection>) a_Network2D._Connection_HashMap.get(a_Point2D);
                draw(
                        a_Point2D,
                        a_Connection_HashSet);
            }
        }
    }

    public void init_Envelope2D(){
        if (_VectorNetwork2D_HashSet != null){
            Iterator<Vector_Network2D> a_Network2D_HashSet_Iterator = _VectorNetwork2D_HashSet.iterator();
            Vector_Network2D a_Network2D;
            _VectorEnvelope2D = new Vector_Envelope2D();
            while (a_Network2D_HashSet_Iterator.hasNext()) {
                a_Network2D = a_Network2D_HashSet_Iterator.next();
                _VectorEnvelope2D = _VectorEnvelope2D.envelope(a_Network2D.getEnvelope2D());
            }
        }
    }



        public void draw(
            Vector_Point2D a_Point2D,
            HashSet<Connection> a_Connection_HashSet) {
            if (_VectorEnvelope2D == null){
                init_Envelope2D();
            }
            _YRange_BigDecimal = _VectorEnvelope2D._ymax.subtract(_VectorEnvelope2D._ymin);
            _XRange_BigDecimal = _VectorEnvelope2D._xmax.subtract(_VectorEnvelope2D._xmin);
//        boolean handleOutOfMemoryError = _Environment._HandleOutOfMemoryError;
//        BigDecimal[] reportingDimensions = _Environment._reportingPopulationDensityAggregate_Grid2DSquareCellDouble.get_Dimensions(handleOutOfMemoryError);
//        BigDecimal[] networkDimensions = _Environment._network_Grid2DSquareCellDouble.get_Dimensions(handleOutOfMemoryError);
//        int scale = reportingDimensions[0].divide(networkDimensions[0]).intValue();
        BigDecimal width_BigDecimal = new BigDecimal(_width_int);
        BigDecimal height_BigDecimal = new BigDecimal(_height_int);

            this._Graphics2D.setPaint(Color.RED);
        int ax = ((a_Point2D._x.subtract(_VectorEnvelope2D._xmin)).divide
                (_XRange_BigDecimal,10,RoundingMode.DOWN).multiply
                (width_BigDecimal)).intValue();
        int ay = ((a_Point2D._y.subtract(_VectorEnvelope2D._ymin)).divide
                (_YRange_BigDecimal,10,RoundingMode.DOWN).multiply
                (height_BigDecimal)).intValue();
        int bx;
        int by;
        Iterator aIterator;
        aIterator = a_Connection_HashSet.iterator();
        while (aIterator.hasNext()) {
            Connection a_Connection = (Connection) aIterator.next();
            //bx = (int) a_Connection._Point2D._x.intValue() * _Scale;
            //by = (int) a_Connection._Point2D._y.intValue() * _Scale;
            //bx = (int) (a_Connection._Point2D._x.doubleValue() * _Scale);
            //by = (int) (a_Connection._Point2D._y.doubleValue() * _Scale);
            bx = ((a_Connection._Point2D._x.subtract(_VectorEnvelope2D._xmin)).divide
                    (_XRange_BigDecimal,10,RoundingMode.DOWN).multiply
                (width_BigDecimal)).intValue();
        by = ((a_Connection._Point2D._y.subtract(_VectorEnvelope2D._ymin)).divide
                (_YRange_BigDecimal,10,RoundingMode.DOWN).multiply
                (height_BigDecimal)).intValue();
            //this._Graphics2D.drawLine(ax, ay, bx, by);
        System.out.println(
                " ax " + ax +
                " ay " + ay +
                " bx " + bx +
                " by " + by);
            if (!(ax == bx && ay == by)){
                this._Graphics2D.drawLine(
                        ax,
                        _height_int - ay,
                        bx,
                        _height_int - by);
            }
        }
    }
}
