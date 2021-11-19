/*
 * Copyright 2019 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.v2d.core;

import java.io.Serializable;

/**
 * Vector Object
 *
 * @author Andy Turner
 * @version 1.0.0
 */
public class V2D_Object implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * V2D_Environment
     */
    public V2D_Environment e;

    /**
     * @param e What {@link e} is set to.
     */
    public V2D_Object(V2D_Environment e) {
        this.e = e;
    }

}
