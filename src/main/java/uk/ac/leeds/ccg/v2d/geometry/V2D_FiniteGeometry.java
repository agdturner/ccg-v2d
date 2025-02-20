/*
 * Copyright 2025 Andy Turner, University of Leeds.
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;

/**
 * V2D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 2.0
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
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return The envelope.
     */
    public abstract V2D_Envelope getEnvelope(int oom, RoundingMode rm);
    
    /**
     * Creates a new instance with offset V2D_Vector.ZERO.
     * 
     * @param env The environment.
     */
    public V2D_FiniteGeometry(V2D_Environment env) {
        this(env, V2D_Vector.ZERO);
    }
    
    /**
     * Creates a new instance.
     *
     * @param env The environment.
     * @param offset What {@link #offset} is set to.
     */
    public V2D_FiniteGeometry(V2D_Environment env, V2D_Vector offset) {
        super(env, offset);
    }
    
    /**
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @return A copy of the points of the geometry.
     */
    public abstract V2D_Point[] getPointsArray(int oom, RoundingMode rm);
    
    /**
     * @return A copy of the points of the geometries gs.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode for any rounding.
     * @param gs The geometries.
     */
    public static V2D_Point[] getPoints(int oom, RoundingMode rm, V2D_FiniteGeometry... gs) {
        ArrayList<V2D_Point> list = new ArrayList<>();
        for (var x: gs) {
            list.addAll(Arrays.asList(x.getPointsArray(oom, rm)));
        }
        return list.toArray(V2D_Point[]::new);
    }
    
    /**
     * For collecting and returning all the points.
     *
     * @param gs The input.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
     * @return A Set of points that are the corners of the triangles.
     */
    public static V2D_Point[] getPoints(V2D_FiniteGeometry[] gs, int oom, RoundingMode rm) {
        List<V2D_Point> s = new ArrayList<>();
        for (var g : gs) {
            V2D_Point[] gps = g.getPointsArray(oom, rm);
            s.addAll(Arrays.asList(gps));
        }
        ArrayList<V2D_Point> points = V2D_Point.getUnique(s, oom, rm);
        return points.toArray(V2D_Point[]::new);
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     * @param oom The Order of Magnitude for the precision.
     * @param rm The RoundingMode if rounding is needed.
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
