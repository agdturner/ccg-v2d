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
import java.awt.Polygon;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
//import java.awt.geom.Point2D;
import java.awt.geom.Ellipse2D;
import javax.swing.JApplet;
import javax.swing.JFrame;

@Deprecated
public class VectorRender extends JApplet {

    final static Color bg = Color.white;
    final static Color fg = Color.black;
    final static Color red = Color.red;
    final static Color black = Color.black;
    final static Color white = Color.white;
    int width = 100;
    int height = 100;
    int scale = 50;
    protected JFrame _JFrame;
    protected Graphics2D _Graphics2D;
    // Board quadrants
    Polygon[] _Polygons;

    public VectorRender(JFrame _JFrame) {
        this._JFrame = _JFrame;
        this._JFrame.getContentPane().add("Center", this);
        this.init();
        _JFrame.pack();
        _JFrame.setSize(new Dimension(this.width, this.height));
        _JFrame.setVisible(true);
    }

    public VectorRender() {
        _JFrame = new JFrame("City");
        this._JFrame.getContentPane().add("Center", this);
        this.init();
        _JFrame.pack();
        _JFrame.setSize(new Dimension(this.width, this.height));
        _JFrame.setVisible(true);
        _JFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    @Override
    public void init() {
        _Polygons = new Polygon[128];
    }

    public static void main(String args[]) {
        JFrame _JFrame = new JFrame("City");
        _JFrame.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        JApplet aRender = new VectorRender(_JFrame);
    }

    @Override
    public void paint(Graphics _Graphics) {
        this._Graphics2D = (Graphics2D) _Graphics;
        // Circle
        Ellipse2D.Double circle = new Ellipse2D.Double(10,10,350,350);
//        this._Graphics2D.fill( circle);
        // Draw
        //draw();
    }

}
