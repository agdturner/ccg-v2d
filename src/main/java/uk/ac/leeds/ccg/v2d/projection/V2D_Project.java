/*
 * Copyright 2020 Centre for Computational Geography.
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
package uk.ac.leeds.ccg.v2d.projection;

import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;

/**
 * For projecting geometries using vectors.
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_Project {
    
    /**
     * @param p The point to project.
     * @param v The vector of projection.
     * @return A new {@link V2D_Point} which is the point {@code p} move by the 
     * vector {@code v}.
     */
    public static V2D_Point project(V2D_Point p, V2D_Vector v) {
        return new V2D_Point(p.x.add(v.dx), p.y.add(v.dy));
    }
    
}
