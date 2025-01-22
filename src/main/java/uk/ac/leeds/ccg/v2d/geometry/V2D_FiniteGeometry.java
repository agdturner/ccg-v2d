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

import uk.ac.leeds.ccg.v2d.geometry.envelope.V2D_Envelope;
/**
 * V2D_FiniteGeometry
 * 
 * @author Andy Turner
 * @version 1.1
 */
public interface V2D_FiniteGeometry {
    
    /**
     * For getting the envelope of the geometry
     * @return The V2D_Envelope. 
     */
    public abstract V2D_Envelope getEnvelope();

    /**
     * For identifying if the geometry is intersected by point
     * {@code p}.
     *
     * @param p The point to test for intersection with.
     * @return {@code true} iff the geometry is intersected by {@code p}.
     */
    public abstract boolean isIntersectedBy(V2D_Point p);
    
    /**
     * For identifying if the geometry is intersected by line
     * {@code l}.
     *
     * @param l The line to test for intersection with.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    public abstract boolean isIntersectedBy(V2D_Line l);
    
    /**
     * For identifying if the geometry is intersected by line segment
     * {@code l}.
     *
     * @param l The line segment to test for intersection with.
     * @param b To distinguish this method from
     * {@link #isIntersectedBy(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}.
     * @return {@code true} iff the geometry is intersected by {@code l}.
     */
    public abstract boolean isIntersectedBy(V2D_LineSegment l, boolean b);
    
    /**
     * For getting the intersection between the geometry and a {@code l}.
     * @param l The line to intersect with.
     * @return The V2D_Geometry. 
     */
    public abstract V2D_Geometry getIntersection(V2D_Line l);
    
    /**
     * For getting the intersection between the geometry and the line segment
     * {@code l}.
     *
     * @param l The line segment to intersect with.
     * @param b To distinguish this method from
     * {@link #getIntersection(uk.ac.leeds.ccg.v3d.geometry.V3D_Line)}.
     * @return The V2D_Geometry.
     */
    public abstract V2D_Geometry getIntersection(V2D_LineSegment l, boolean b);
}
