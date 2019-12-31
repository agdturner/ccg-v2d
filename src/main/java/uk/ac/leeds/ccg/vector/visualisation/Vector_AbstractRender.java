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
package uk.ac.leeds.ccg.vector.visualisation;

import java.awt.Graphics2D;
import javax.swing.JApplet;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;

/**
 * Abstract render class.
 */
public abstract class Vector_AbstractRender extends JApplet {

    public Vector_Environment ve;
   
    public int Width = 512;
    public int Height = 256;
    //public int scale = 50;

    public JFrame _JFrame;
    public Graphics2D _Graphics2D;
    
    protected Vector_AbstractRender(){}

    public Vector_AbstractRender(
            Vector_Environment ve){
        this.ve = ve;
    }

}
