/*
 * Copyright 2020 Andy Turner, University of Leeds.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.ac.leeds.ccg.v2d.geometry;

import java.math.RoundingMode;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * V2D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V2D_FiniteGeometry extends V2D_Geometry {

    private static final long serialVersionUID = 1L;

    /**
     * For storing the envelope.
     */
    protected V2D_Envelope en;
    
    /**
     * For getting the envelope of the geometry
     *
     * @return The envelope.
     */
    public abstract V2D_Envelope getEnvelope(int oom);
    
    /**
     * Creates a new instance with offset V2D_Vector.ZERO.
     */
    public V2D_FiniteGeometry() {
        this(V2D_Vector.ZERO);
    }
    
    /**
     * Creates a new instance.
     *
     * @param offset What {@link #offset} is set to.
     */
    public V2D_FiniteGeometry(V2D_Vector offset) {
        super(offset);
    }
    
    /**
     * For evaluating if the geometry is intersected by the Axis Aligned 
     * Bounding Box aabb.
     *
     * @param aabb The Axis Aligned Bounding Box to test for intersection.
     * @param epsilon The tolerance within which two vector components are
     * considered equal.
     * @return {@code true} iff the geometry intersects aabb at the given precision.
     */
    public abstract boolean isIntersectedBy(V2D_Envelope aabb, int oom, 
            RoundingMode rm);
    
    /**
     * @return A copy of the points of the geometry.
     */
    public abstract V2D_Point[] getPoints();
    
    /**
     * @return A copy of the points of the geometries gs.
     * @param gs The geometries.
     */
    public static V2D_Point[] getPoints(V2D_FiniteGeometry... gs) {
        ArrayList<V2D_Point> list = new ArrayList<>();
        for (var x: gs) {
            list.addAll(Arrays.asList(x.getPoints()));
        }
        return list.toArray(V2D_Point[]::new);
    }
    
    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     */
    @Override
    public void translate(V2D_Vector v, int oom, RoundingMode rm) {
        super.translate(v, oom, rm);
        if (en != null) {
            en.translate(v, oom, rm);
        }
        //en = null;
    }
}
