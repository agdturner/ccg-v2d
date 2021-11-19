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

import ch.obermuhlner.math.big.BigRational;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Paths;
import uk.ac.leeds.ccg.generic.core.Generic_Environment;
import uk.ac.leeds.ccg.generic.io.Generic_Defaults;
import uk.ac.leeds.ccg.io.IO_Path;
import uk.ac.leeds.ccg.generic.memory.Generic_MemoryManager;
import uk.ac.leeds.ccg.math.arithmetic.Math_BigDecimal;
import uk.ac.leeds.ccg.v2d.geometry.V2D_Vector;
import uk.ac.leeds.ccg.v2d.io.V2D_Files;
import uk.ac.leeds.ccg.v2d.projection.V2D_OSGBtoLatLon;

/**
 * Vector Environment.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_Environment extends Generic_MemoryManager {

    private static final long serialVersionUID = 1L;

    /**
     * Generic Environment
     */
    public final Generic_Environment env;

    /**
     * zero Vector
     */
    public static final V2D_Vector ZERO_VECTOR = new V2D_Vector(BigRational.ZERO, BigRational.ZERO); 
    
    /**
     * For code brevity.
     */
    public final BigRational P0 = BigRational.ZERO;
    public final BigRational P1 = BigRational.ONE;
    public final BigRational P2 = BigRational.valueOf(2);
    public final BigRational P3 = BigRational.valueOf(3);
    public final BigRational N1 = BigRational.ONE.negate();

    public V2D_Files files;

    /**
     * Math_BigDecimal
     */
    public Math_BigDecimal bd;
    
    protected V2D_OSGBtoLatLon OSGBtoLatLon;

    public V2D_Environment(Generic_Environment e) throws IOException,
            Exception {
        this(e, e.files.getDir());
    }

    public V2D_Environment(Generic_Environment e, IO_Path dir)
            throws IOException, Exception {
        super();
        this.env = e;
        bd = new Math_BigDecimal();
        initMemoryReserve(Default_Memory_Threshold, env);
        files = new V2D_Files(new Generic_Defaults(Paths.get(dir.toString(),
                V2D_Strings.s_vector)));
        
    }

    public BigDecimal round(BigDecimal toRound, BigDecimal toRoundTo) {
        int scale = toRoundTo.scale();
        BigDecimal r = toRound.setScale(scale - 1, RoundingMode.FLOOR);
        r = r.setScale(scale);
        r = r.add(toRoundTo);
        return r;
    }

    public V2D_OSGBtoLatLon getOSGBtoLatLon() {
        if (OSGBtoLatLon == null) {
            OSGBtoLatLon = new V2D_OSGBtoLatLon();
        }
        return OSGBtoLatLon;
    }

    @Override
    public boolean swapSomeData() throws IOException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean swapSomeData(boolean hoome) throws IOException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean checkAndMaybeFreeMemory() throws IOException, Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
