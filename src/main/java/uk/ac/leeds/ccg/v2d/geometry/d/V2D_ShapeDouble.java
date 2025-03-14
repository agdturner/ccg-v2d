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
import java.util.HashMap;
import uk.ac.leeds.ccg.v2d.core.d.V2D_EnvironmentDouble;

/**
 * V2D_FiniteGeometry for representing finite geometries.
 *
 * @author Andy Turner
 * @version 1.0
 */
public abstract class V2D_ShapeDouble extends V2D_FiniteGeometryDouble {

    private static final long serialVersionUID = 1L;
            
    /**
     * The id of the shape.
     */
    protected final int id;
    
    /**
     * Creates a new instance with offset V2D_Vector.ZERO.
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     */
    public V2D_ShapeDouble(V2D_EnvironmentDouble env, V2D_VectorDouble offset) {
        super(env, offset);
        this.id = env.getNextID();
    }
    
    /**
     * For storing the points
     */
    protected HashMap<Integer, V2D_PointDouble> points;
    
    /**
    * For storing the edges.
     */
    protected HashMap<Integer, V2D_LineSegmentDouble> edges;
    
    /**
     * For getting the points of a shape.
     * @return A HashMap of the points with integer identifier keys.
     */
    public abstract HashMap<Integer, V2D_PointDouble> getPoints();
    
    /**
     * For getting the edges of a shape.
     * 
     * @return A HashMap of the edges with integer identifier keys.
     */
    public abstract HashMap<Integer, V2D_LineSegmentDouble> getEdges();
    
    /**
     * @return A copy of the points of the geometries gs.
     * @param ss The geometries.
     */
    public ArrayList<V2D_PointDouble> getPoints(
            HashMap<Integer, V2D_ShapeDouble> ss) {
        ArrayList<V2D_PointDouble> list = new ArrayList<>();
        ss.values().forEach(x -> list.addAll(x.getPoints().values()));
        return list;
    }
}
