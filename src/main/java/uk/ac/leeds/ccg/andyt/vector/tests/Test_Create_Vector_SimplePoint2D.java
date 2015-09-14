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

import java.math.BigDecimal;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_SimplePoint2D;

/**
 *
 * @author geoagdt
 */
public class Test_Create_Vector_SimplePoint2D {

    public Test_Create_Vector_SimplePoint2D() {
    }

    public static void main(String[] args) {
        new Test_Create_Vector_SimplePoint2D().run();
    }

    public void run() {
        BigDecimal x = new BigDecimal("12.456");
        BigDecimal y = new BigDecimal("42.456");
        
        //int capacity = Integer.MAX_VALUE / 140;
        int capacity = 50000000;
        System.out.println(capacity);
        //List<Vector_SimplePoint2D> points = new ArrayList<Vector_SimplePoint2D>(capacity);
        
        Vector_SimplePoint2D[] pointsArray = new Vector_SimplePoint2D[capacity];
        for (int i = 0; i < capacity; i++) {
            Vector_SimplePoint2D point = new Vector_SimplePoint2D(
                    x,
                    y);
//            points.add(point);
//            pointsArray[i] = point = new Vector_SimplePoint2D(
//                    x,
//                    y);
        }

//        System.out.println(capacity);
//        List<Vector_SimplePoint2D> points2 = new ArrayList<Vector_SimplePoint2D>(capacity);
//        for (int i = 0; i < capacity; i++) {
//            Vector_SimplePoint2D point2 = new Vector_SimplePoint2D(
//                    x,
//                    y);
//            points.add(point2);
//            
//            
//        }
    }
}
