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

import uk.ac.leeds.ccg.andyt.grids.core.Grids_Grid2DSquareCellDouble;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Grid2DSquareCellDoubleFactory;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Network2D;

/**
 * Class for tests and example uses of Network2D.
 */
public class TestNetwork2D {

    public static void main(String[] args) {
        new TestNetwork2D().run();
    }

    public void run() {
        boolean _HandleOutOfMemoryError = false;
        long _NRows = 10;
        long _NCols = 20;
        Grids_Grid2DSquareCellDoubleFactory aGrid2DSquareCellDoubleFactory =
                new Grids_Grid2DSquareCellDoubleFactory(
                new Grids_Environment(),
                _HandleOutOfMemoryError);
        Grids_Grid2DSquareCellDouble aGrid2DSquareCellDouble =
                (Grids_Grid2DSquareCellDouble) aGrid2DSquareCellDoubleFactory.create(_NRows, _NCols);
        Vector_Network2D aNetwork2D = new Vector_Network2D(
null, 
                aGrid2DSquareCellDouble);
        System.out.println(aNetwork2D.toString());

        for (int i = 2; i < 10; i++) {
            double x = Math.atan(1.0d / (double) i);
            System.out.println(x);
            double z = Math.PI / 4.0d;
            System.out.println(z);
            double y = z - x;
            System.out.println(
                    "x>y " + (x > y) +
                    ", x " + x +
                    ", y " + y +
                    ", z " + z +
                    ", x+y+z " + (x + y + z) +
                    ", PI/2 " + Math.PI / 2.0d);
        }
        System.out.println(Integer.MAX_VALUE);
        System.out.println(1000000L * 6500L);
    }
}
