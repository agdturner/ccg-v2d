/**
 * Component of a library for handling spatial vector data.
 * Copyright (C) 2017 Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA.
 */
package uk.ac.leeds.ccg.andyt.vector.io;

import java.io.File;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.andyt.generic.io.Generic_Files;
import uk.ac.leeds.ccg.andyt.grids.io.Grids_Files;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Strings;

/**
 *
 * @author geoagdt
 */
public class Vector_Files extends Generic_Files {

    protected Grids_Files gf;

    protected Vector_Files() {
        this(getDefaultDir(), new Grids_Files());        
    }
    
    /**
     * @return A default directory called {@link Vector_Strings#s_Vector} 
     * in the Generic_Files.getDefaultDir().
     */
    public static File getDefaultDir() {
        return new File(Generic_Defaults.getDefaultDir(), Vector_Strings.s_Vector);
    }
    
    /**
     * @param dataDir
     * @return A directory called {@link Vector_Strings#String_s_Vector} 
     * in {@code dataDir}.
     */
    public static File getDir(File dataDir) {
        File r = new File(dataDir, Vector_Strings.s_Vector);
        r.mkdir();
        return r;
    }
    
    public Vector_Files(File dir) {
        this(getDir(dir), new Grids_Files(dir));
    }

    public Vector_Files(File dir, Grids_Files gf) {
        super(dir);
    }

}
