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
package uk.ac.leeds.ccg.v2d.geometry.d;

import java.io.Serializable;
import uk.ac.leeds.ccg.v2d.core.d.V2D_Environment_d;

/**
 * For 2D Euclidean geometrical objects. The two dimensions have
 * orthogonal axes X, and Y that meet at the origin point {@code<0, 0>}
 * where {@code x=0 and y=0}. The following depicts the origin and
 * dimensions. {@code
 *                          y
 *                          +
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 * x - ---------------------|------------------------ + x
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          |
 *                          -
 *                          y
 * }
 *
 * @author Andy Turner
 * @version 2.0
 */
public abstract class V2D_Geometry_d implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * The environment.
     */
    public final V2D_Environment_d env;
    
    /**
     * The offset used to position a geometry object relative to the
     * {@link V2D_Point_d#ORIGIN}.
     */
    protected V2D_Vector_d offset;

    /**
     * Creates a new instance.
     * 
     * @param env The environment.
     */
    public V2D_Geometry_d(V2D_Environment_d env) {
        this(env, V2D_Vector_d.ZERO);
    }

    /**
     * Creates a new instance.
     *
     * @param env The environment.
     * @param offset What {@link #offset} is set to.
     */
    public V2D_Geometry_d(V2D_Environment_d env, V2D_Vector_d offset) {
        this.env = env;
        this.offset = offset;
    }

    /**
     * @param pad The padding.
     * @return A padded description.
     */
    protected String toStringFields(String pad) {
        return pad + "offset=" + offset.toString(pad);
    }

    /**
     * @param pad The padding.
     * @return A padded description.
     */
    protected String toStringFieldsSimple(String pad) {
        return pad + "offset=" + offset.toStringSimple("");
    }

    /**
     * Translate (move relative to the origin).
     *
     * @param v The translation vector.
     */
    public void translate(V2D_Vector_d v) {
        offset = offset.add(v);
    }

    /**
     * Returns the geometry rotated by the angle theta about the point pt. If 
     * theta is positive the angle is clockwise.
     * 
     * @param pt The point about which the geometry is rotated.
     * @param theta The angle of rotation about pt in radians.
     * @return The rotated geometry.
     */
    public abstract V2D_Geometry_d rotate(V2D_Point_d pt, double theta);
    
    /**
     * Returns the geometry rotated by a normalised angle theta about pt. 
     * theta &gt; 0 && theta &lt; 2Pi.
     * 
     * @param pt The point about which the geometry is rotated.
     * @param theta The angle of rotation around pt in radians.
     * @return The rotated geometry.
     */
    public abstract V2D_Geometry_d rotateN(V2D_Point_d pt, double theta);
    
}
