/*
 * Copyright 2020-2025 Andy Turner, University of Leeds.
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
import uk.ac.leeds.ccg.v2d.core.d.V2D_Environment_d;

/**
 * The simplest V2D_Area_d is a V2D_Triangle_d that represents a triangle in 2D.
 * V2D_ConvexArea_d extends V2D_Area and is for representing a convex area
 * comprising one or more triangles sharing internal edges and with a convex
 * external edge. All points of convex areas are on the external edge.
 * V2D_PolygonNoInternalHoles_d are defined by a V2D_ConvexArea_d and a
 * collection of non edge sharing V2D_PolygonNoInternalHoles_d areas each
 * defining an external hole or concavity. V2D_Polygon_d extends
 * V2D_PolygonNoInternalHoles_d and is also defined by a collection of non edge
 * sharing internal holes each represented also as a V2D_Polygon_d.
 *
 * @author Andy Turner
 * @version 2.0
 */
public abstract class V2D_Area_d extends V2D_FiniteGeometry_d {

    private static final long serialVersionUID = 1L;

    /**
     * The id of the shape.
     */
    protected final int id;

    /**
     * Creates a new instance with offset V2D_Vector.ZERO.
     *
     * @param env What {@link #env} is set to.
     * @param offset What {@link #offset} is set to.
     */
    public V2D_Area_d(V2D_Environment_d env, V2D_Vector_d offset) {
        super(env, offset);
        this.id = env.getNextID();
    }

    /**
     * For storing the points
     */
    protected HashMap<Integer, V2D_Point_d> points;

    /**
     * For storing the edges.
     */
    protected HashMap<Integer, V2D_LineSegment_d> edges;

    /**
     * For getting the points of a shape.
     *
     * @return A HashMap of the points with integer identifier keys.
     */
    public abstract HashMap<Integer, V2D_Point_d> getPoints();

    /**
     * For getting the edges of a shape.
     *
     * @return A HashMap of the edges with integer identifier keys.
     */
    public abstract HashMap<Integer, V2D_LineSegment_d> getEdges();

    /**
     * @return A copy of the points of the geometries gs.
     * @param ss The geometries.
     */
    public ArrayList<V2D_Point_d> getPoints(
            HashMap<Integer, V2D_Area_d> ss) {
        ArrayList<V2D_Point_d> list = new ArrayList<>();
        ss.values().forEach(x -> list.addAll(x.getPoints().values()));
        return list;
    }
}
