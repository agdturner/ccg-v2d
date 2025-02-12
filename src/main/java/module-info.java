/**
 * A module for 2D Geometry
 */
module uk.ac.leeds.ccg.v2d {
    
    /**
     * Requirements.
     */
    //requires ch.obermuhlner.math.big;
    //requires transitive uk.ac.leeds.ccg.generic;
    requires transitive uk.ac.leeds.ccg.math;
    //requires java.desktop;
    
    /**
     * Exports.
     */
    exports uk.ac.leeds.ccg.v2d.core;
    exports uk.ac.leeds.ccg.v2d.geometrics;
    exports uk.ac.leeds.ccg.v2d.geometry;
    exports uk.ac.leeds.ccg.v2d.geometry.light;
    
    exports uk.ac.leeds.ccg.v2d.core.d;
    exports uk.ac.leeds.ccg.v2d.geometrics.d;
    exports uk.ac.leeds.ccg.v2d.geometry.d;
    exports uk.ac.leeds.ccg.v2d.geometry.d.light;
    exports uk.ac.leeds.ccg.v2d.projection.d;
}