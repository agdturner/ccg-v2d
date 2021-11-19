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
package uk.ac.leeds.ccg.v2d.visualisation;

import java.awt.Graphics2D;
import javax.swing.JFrame;
import uk.ac.leeds.ccg.v2d.core.V2D_Environment;

/**
 * Abstract render class.
 */
public abstract class V2D_Render {

    private static final long serialVersionUID = 1L;

    /**
     * V2D_Environment
     */
    public V2D_Environment ve;
   
    /**
     * Width
     */
    public int Width = 512;
    
    /**
     * Height
     */
    public int Height = 256;
    //public int scale = 50;

    /**
     * JFrame
     */
    public JFrame frame;
    
    /**
     * Graphics
     */
    public Graphics2D g;
    
    /**
     * Create a new instance.
     */
    protected V2D_Render(){}

    /**
     * Create a new instance.
     * 
     * @param ve V2D_Environment
     */
    public V2D_Render(V2D_Environment ve){
        this.ve = ve;
    }

}
