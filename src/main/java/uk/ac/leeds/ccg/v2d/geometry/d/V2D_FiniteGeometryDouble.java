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
package uk.ac.leeds.ccg.v2d.geometry.d;

import java.util.ArrayList;
import java.util.Arrays;
import uk.ac.leeds.ccg.v2d.core.d.V2D_EnvironmentDouble;

/**
 * V2D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V2D_FiniteGeometryDouble extends V2D_GeometryDouble {

    private static final long serialVersionUID = 1L;
    
    /**
     * For storing the envelope.
     */
    protected V2D_EnvelopeDouble en;
    
    /**
     * Creates a new instance with offset V2D_Vector.ZERO.
     * 
     * @param env The environment.
     */
    public V2D_FiniteGeometryDouble(V2D_EnvironmentDouble env) {
        this(env, V2D_VectorDouble.ZERO);
    }
    
    /**
     * Creates a new instance.
     *
     * @param env The environment.
     * @param offset What {@link #offset} is set to.
     */
    public V2D_FiniteGeometryDouble(V2D_EnvironmentDouble env, V2D_VectorDouble offset) {
        super(env, offset);
    }
    
    /**
     * For getting the envelope of the geometry
     *
     * @return The envelope.
     */
    public abstract V2D_EnvelopeDouble getEnvelope();
    
    /**
     * Translate (move relative to the origin).
     *
     * @param v The vector to translate.
     */
    @Override
    public void translate(V2D_VectorDouble v) {
        super.translate(v);
        if (en != null) {
            en.translate(v);
        }
        //en = null;
    }
    
    public abstract V2D_PointDouble[] getPointsArray();
    
    /**
     * @return A copy of the points of the geometries gs.
     * @param gs The geometries.
     */
    public static V2D_PointDouble[] getPoints(V2D_FiniteGeometryDouble... gs) {
        ArrayList<V2D_PointDouble> list = new ArrayList<>();
        for (var x: gs) {
            list.addAll(Arrays.asList(x.getPointsArray()));
        }
        return list.toArray(V2D_PointDouble[]::new);
    }
}
