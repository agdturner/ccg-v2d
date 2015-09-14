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
package uk.ac.leeds.ccg.andyt.vector.tests;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.andyt.grids.core.Grid2DSquareCellDouble;
import uk.ac.leeds.ccg.andyt.grids.core.Grid2DSquareCellDoubleFactory;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.andyt.vector.visualisation.VectorRenderNetwork2D;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Network2D;

/**
 * Class for tests and example uses of RenderNetwork2D.
 */
public class TestRenderNetwork2D {

    public static void main(String[] args) {
        //new TestRenderNetwork2D().run1();
        new TestRenderNetwork2D().run0();
    }

    public void run1() {
        JFrame _JFrame = new JFrame("City");
        _JFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        long rows = 200;
        long cols = 200;
        new VectorRenderNetwork2D(
                _JFrame,
                (int) cols,
                (int) rows,
                getNetwork2D_HashSet(rows,cols));
    }

    public void run0() {
        JFrame _JFrame = new JFrame("City");
        _JFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        new VectorRenderNetwork2D(_JFrame);
    }


    public HashSet<Vector_Network2D> getNetwork2D_HashSet(
            long rows,
            long cols) {
        HashSet<Vector_Network2D> tNetwork2D_HashSet =
                new HashSet<Vector_Network2D>();
        boolean handleOutOfMemoryError = false;
        Grid2DSquareCellDoubleFactory aGrid2DSquareCellDoubleFactory =
                new Grid2DSquareCellDoubleFactory(
                new Grids_Environment(),
                handleOutOfMemoryError);
        Grid2DSquareCellDouble aGrid2DSquareCellDouble =
                (Grid2DSquareCellDouble)
                aGrid2DSquareCellDoubleFactory.create(
                rows,
                cols);
        Vector_Network2D aNetwork2D = new Vector_Network2D(
                aGrid2DSquareCellDouble);
        tNetwork2D_HashSet.add(aNetwork2D);
        return tNetwork2D_HashSet;
    }
}
