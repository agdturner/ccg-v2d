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
import java.io.IOException;
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

    protected Vector_Files() throws IOException {
        this(getDefaultDir());
    }
    
    public Vector_Files(File dir) throws IOException {
        this(getDir(dir), new Grids_Files(Grids_Files.getDir(dir)));
    }

    public Vector_Files(File dir, Grids_Files gf) throws IOException {
        super(dir);
        this.gf = gf;
    }

    /**
     * @return {@code new File(getDefaultGenericDir(), Vector_Strings.s_vector)}.
     */
    public static File getDefaultDir() {
        return new File(getDefaultGenericDir(), Vector_Strings.s_vector);
    }
    
    /**
     * @param dataDir
     * @return A directory called {@link Vector_Strings#String_s_vector} 
     * in {@code dataDir}.
     */
    public static File getDir(File dataDir) {
        File r = new File(dataDir, Vector_Strings.s_vector);
        r.mkdir();
        return r;
    }
}
