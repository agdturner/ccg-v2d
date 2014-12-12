/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.tests;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
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
