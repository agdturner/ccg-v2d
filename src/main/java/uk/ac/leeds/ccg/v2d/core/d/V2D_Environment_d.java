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
package uk.ac.leeds.ccg.v2d.core.d;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import uk.ac.leeds.ccg.v2d.geometry.d.V2D_Area_d;

/**
 * V2D_Environment
 *
 * @author Andy Turner
 * @version 2.0
 */
public class V2D_Environment_d implements Serializable {
    
    private static final long serialVersionUID = 1L;

    /**
     * The epsilon for judging if two vectors are the same.
     */
    public double epsilon;
    
    /**
     * The shapes.
     */
    public HashMap<Integer, V2D_Area_d> shapes;
    
    /**
     * The ids of shapes.
     */
    HashSet<Integer> ids;
    
    /**
     * Creates a new instance.
     */
    public V2D_Environment_d(double epsilon){
        shapes = new HashMap<>();
        ids = new HashSet<>();
    }
    
    /**
     * @return The next id that has not yet been used; 
     */
    public int getNextID() {
        int id;
        if (!ids.isEmpty()) {
            id = ids.iterator().next();
            ids.remove(id);
        } else {
            id = shapes.size();
        }
        return id;
    }
    
    /**
     * @param shape The shape to be put in {@link #shapes}.
     * @return The id of the shape allocated. 
     */
    public int add(V2D_Area_d shape) {
        int id = getNextID();
        shapes.put(id, shape);
        return id;
    }
    
    /**
     * @param id The id of the shape to remove. 
     */
    public void remove(int id) {
        shapes.remove(id);
        ids.add(id);
    }
}
