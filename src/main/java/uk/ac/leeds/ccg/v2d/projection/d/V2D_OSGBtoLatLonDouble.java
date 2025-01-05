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
package uk.ac.leeds.ccg.v2d.projection.d;

/**
 * Adapted from http://www.movable-type.co.uk/scripts/latlong-gridref.html.
 * Ordnance Survey Grid Reference functions (c) Chris Veness 2005-2012
 * http://www.movable-type.co.uk/scripts/gridref.js
 *
 * @author Andy Turner
 */
public class V2D_OSGBtoLatLonDouble {

    /**
     * Create a new instance.
     */
    public V2D_OSGBtoLatLonDouble() {
    }

    /**
     * Converts WGS84 latitude/longitude coordinate to an Ordnance Survey
     * easting/northing coordinate
     *
     * @param lat latitude to be converted
     * @param lon longitude to be converted
     * @return double[2] r: where r[0] is easting, r[1] is northing.
     */
    public static double[] latlon2osgb(double lat, double lon) {
        double[] result = new double[2];
        double latr = toRadians(lat);
        double lonr = toRadians(lon);
        double a = 6377563.396;
        double b = 6356256.910;            // Airy 1830 major & minor semi-axes
        double F0 = 0.9996012717;          // NatGrid scale factor on central meridian
        //System.out.println("F0 " + F0);
        double lat0 = toRadians(49);
        double lon0 = toRadians(-2);       // NatGrid true origin is 49ºN,2ºW
        double N0 = -100000;
        double E0 = 400000;                // northing & easting of true origin, metres
        double e2 = 1 - (b * b) / (a * a); // eccentricity squared
        //System.out.println("Eccentricity squared " + e2);
        double n = (a - b) / (a + b);
        double n2 = n * n;
        double n3 = n * n * n;

        double cosLat = Math.cos(latr);
        double sinLat = Math.sin(latr);
        double nu = a * F0 / Math.sqrt(1 - e2 * sinLat * sinLat); // transverse radius of curvature
        //System.out.println("Transverse radius of curvature " + nu);
        double rho = a * F0 * (1 - e2) / Math.pow(1 - e2 * sinLat * sinLat, 1.5); // meridional radius of curvature
        double eta2 = nu / rho - 1;

        double Ma = (1 + n + (5 / 4) * n2 + (5 / 4) * n3) * (latr - lat0);
        double Mb = (3 * n + 3 * n * n + (21D / 8D) * n3) * Math.sin(latr - lat0) * Math.cos(latr + lat0);
        double Mc = ((15D / 8D) * n2 + (15D / 8D) * n3) * Math.sin(2D * (latr - lat0)) * Math.cos(2D * (latr + lat0));
        double Md = (35D / 24D) * n3 * Math.sin(3 * (latr - lat0)) * Math.cos(3D * (latr + lat0));
        double M = b * F0 * (Ma - Mb + Mc - Md); // meridional arc

        double cos3lat = cosLat * cosLat * cosLat;
        double cos5lat = cos3lat * cosLat * cosLat;
        double tan2lat = Math.tan(latr) * Math.tan(latr);
        double tan4lat = tan2lat * tan2lat;

        double I = M + N0;
        double II = (nu / 2D) * sinLat * cosLat;
        double III = (nu / 24D) * sinLat * cos3lat * (5 - tan2lat + 9 * eta2);
        double IIIA = (nu / 720D) * sinLat * cos5lat * (61 - 58 * tan2lat + tan4lat);
        double IV = nu * cosLat;
        double V = (nu / 6D) * cos3lat * (nu / rho - tan2lat);
        double VI = (nu / 120D) * cos5lat * (5 - 18 * tan2lat + tan4lat + 14 * eta2 - 58 * tan2lat * eta2);

        double dLon = lonr - lon0;
        double dLon2 = dLon * dLon;
        double dLon3 = dLon2 * dLon;
        double dLon4 = dLon3 * dLon;
        double dLon5 = dLon4 * dLon;
        double dLon6 = dLon5 * dLon;

        double N = I + II * dLon2 + III * dLon4 + IIIA * dLon6;
        double E = E0 + IV * dLon + V * dLon3 + VI * dLon5;

//        System.out.println("latr=" + latr);
//        System.out.println("lonr=" + lonr);
//        System.out.println("lat0=" + lat0);
//        System.out.println("lon0=" + lon0);
//        System.out.println("e2=" + e2);
//        System.out.println("n=" + n);
//        System.out.println("n2=" + n2);
//        System.out.println("n3=" + n3);
//        System.out.println("cosLat=" + cosLat);
//        System.out.println("sinLat=" + sinLat);
//        System.out.println("nu=" + nu);
//        System.out.println("rho=" + rho);
//        System.out.println("eta2=" + eta2);
//        System.out.println("Ma=" + Ma);
//        System.out.println("Mb=" + Mb);
//        System.out.println("Mc=" + Mc);
//        System.out.println("Md=" + Md);
//        System.out.println("M=" + M);
//        System.out.println("cos3lat=" + cos3lat);
//        System.out.println("cos5lat=" + cos5lat);
//        System.out.println("tan2lat=" + tan2lat);
//        System.out.println("tan4lat=" + tan4lat);
//        System.out.println("I=" + I);
//        System.out.println("II=" + II);
//        System.out.println("III=" + III);
//        System.out.println("IIIA=" + IIIA);
//        System.out.println("IV=" + IV);
//        System.out.println("V=" + V);
//        System.out.println("VI=" + VI);
//        System.out.println("dLon=" + dLon);
//        System.out.println("dLon2=" + dLon2);
//        System.out.println("dLon3=" + dLon3);
//        System.out.println("dLon4=" + dLon4);
//        System.out.println("dLon5=" + dLon5);
//        System.out.println("dLon6=" + dLon6);
//        System.out.println("N=" + N);
//        System.out.println("E=" + E);
        //System.out.println("E " + E + ", N " + N);
        result[0] = E;
        result[1] = N;
        return result;
        //return new OsGridRef(E, N);
    }

    /**
     * Convert Ordnance Survey easting-northing coordinate to a WGS84
     * latitude-longitude coordinate
     *
     * @param easting The easting to be converted
     * @param northing The northing to be converted
     * @return result double[2] where result[0] is latitude and result[1] is
     * longitude
     */
    public static double[] osgb2latlon(double easting, double northing) {
        return osgb2latlon(easting, northing, false);
    }

    /**
     * Convert Ordnance Survey easting-northing coordinate to a WGS84
     * latitude-longitude coordinate
     *
     * @param easting The easting to be converted
     * @param northing The northing to be converted
     * @param verbose If verbose is true then intermediate calculations are
     * printed to std.out.
     * @return result double[2] where result[0] is latitude and result[1] is
     * longitude
     */
    public static double[] osgb2latlon(double easting, double northing,
            boolean verbose) {
        double[] result = new double[2];
        double a = 6377563.396;
        double b = 6356256.910;            // Airy 1830 major & minor semi-axes
        double F0 = 0.9996012717;          // NatGrid scale factor on central meridian
        double lat0 = toRadians(49);
        double lon0 = toRadians(-2);       // NatGrid true origin is 49ºN,2ºW
        double N0 = -100000;
        double E0 = 400000;                // northing & easting of true origin, metres
        double e2 = 1 - (b * b) / (a * a); // eccentricity squared
        //System.out.println("e2 " + e2);
        double n = (a - b) / (a + b);
        //System.out.println("n " + n);
        double n2 = n * n;
        double n3 = n * n * n;

        double lat = lat0;
        double M = 0;
        do {
            lat = (northing - N0 - M) / (a * F0) + lat;
            //System.out.println("lat " + lat);
            double Ma = (1 + n + (5 / 4) * n2 + (5 / 4) * n3) * (lat - lat0);
            //System.out.println("Ma " + Ma);
            double Mb = (3 * n + 3 * n * n + (21 / 8) * n3) * Math.sin(lat - lat0) * Math.cos(lat + lat0);
            //System.out.println("Mb " + Mb);
            double Mc = ((15 / 8) * n2 + (15 / 8) * n3) * Math.sin(2 * (lat - lat0)) * Math.cos(2 * (lat + lat0));
            //System.out.println("Mc " + Mc);
            double Md = (35 / 24) * n3 * Math.sin(3 * (lat - lat0)) * Math.cos(3 * (lat + lat0));
            //System.out.println("Md " + Md);
            M = b * F0 * (Ma - Mb + Mc - Md);    // meridional arc
            //System.out.println("M " + M);
            //break;
            //System.out.println("northing - N0 - M " + (northing - N0 - M));

        } while (northing - N0 - M >= 0.0001);  // ie until < 0.01mm

        double cosLat = Math.cos(lat);
        double sinLat = Math.sin(lat);
        double sin2Lat = sinLat * sinLat;
        double nu = a * F0 / Math.sqrt(1 - e2 * sin2Lat);                 // transverse radius of curvature
        double rho = a * F0 * (1 - e2) / Math.pow(1 - e2 * sin2Lat, 1.5); // meridional radius of curvature
        double eta2 = nu / rho - 1;

        double tanLat = Math.tan(lat);
        double tan2lat = tanLat * tanLat;
        double tan4lat = tan2lat * tan2lat;
        double tan6lat = tan4lat * tan2lat;
        double secLat = 1 / cosLat;
        double nu3 = nu * nu * nu;
        double nu5 = nu3 * nu * nu;
        double nu7 = nu5 * nu * nu;
        double VII = tanLat / (2 * rho * nu);
        double VIII = tanLat / (24 * rho * nu3) * (5 + 3 * tan2lat + eta2 - 9 * tan2lat * eta2);
        double IX = tanLat / (720 * rho * nu5) * (61 + 90 * tan2lat + 45 * tan4lat);
        double X = secLat / nu;
        double XI = secLat / (6 * nu3) * (nu / rho + 2 * tan2lat);
        double XII = secLat / (120 * nu5) * (5 + 28 * tan2lat + 24 * tan4lat);
        double XIIA = secLat / (5040 * nu7) * (61 + 662 * tan2lat + 1320 * tan4lat + 720 * tan6lat);
        double dE = (easting - E0);
        double dE2 = dE * dE;
        double dE3 = dE2 * dE;
        double dE4 = dE2 * dE2;
        double dE5 = dE3 * dE2;
        double dE6 = dE4 * dE2;
        double dE7 = dE5 * dE2;
        lat = lat - VII * dE2 + VIII * dE4 - IX * dE6;
        double lon = lon0 + X * dE - XI * dE3 + XII * dE5 - XIIA * dE7;
        result[0] = toDegrees(lat);
        result[1] = toDegrees(lon);
        if (verbose) {
            System.out.println("lat " + lat);
            System.out.println("cosLat " + cosLat);
            System.out.println("sinLat " + sinLat);
            System.out.println("sin2Lat " + sin2Lat);
            System.out.println("nu " + nu);
            System.out.println("rho " + rho);
            System.out.println("eta2 " + eta2);
            System.out.println("tanLat " + tanLat);
            System.out.println("tan6lat " + tan6lat);
            System.out.println("VII " + VII);
            System.out.println("VIII " + VIII);
            System.out.println("IX " + IX);
            System.out.println("X " + X);
            System.out.println("XI " + XI);
            System.out.println("XII " + XII);
            System.out.println("XIIA " + XIIA);
        }
        //System.out.println("lat " + lat + ", lon " + lon);
        return result;
    }

    /**
     * Calculate at return the x*Math.PI/180.
     *
     * @param x The angle in decimal degrees to convert to radians.
     * @return The angle of x in radians.
     */
    public static double toRadians(double x) {
        return x * Math.PI / 180D;
    }

    /**
     * Calculate at return the x*180/Pi.
     *
     * @param x The angle in radians to convert to decimal degrees.
     * @return The angle of x in decimal degrees.
     */
    public static double toDegrees(double x) {
        return x * 180D / Math.PI;
    }

}
