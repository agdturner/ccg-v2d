/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.vector.projection;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author geoagdt
 */
public class Vector_OSBGtoLatLonTest {

    public Vector_OSBGtoLatLonTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }
    
//    public void run() {
//        //double lat = 53.807882;
//        //double lon = -1.557103;
//        //run1(lat,lon);
//        double easting = 429162;
//        double northing = 434735;
//        run2(easting,northing);
//    }
//        
//    public void run1(
//            double lat,
//            double lon) {
//        System.out.println("lat " + lat + ", lon " + lon);
//        double[] eastingNorthing = latlon2osgb(lat, lon);
//        System.out.println(
//                "easting " + eastingNorthing[0] + ", "
//                + "northing " + eastingNorthing[1]);
//        double[] latLon = osgb2latlon(eastingNorthing[0], eastingNorthing[1]);
//        System.out.println("lat " + latLon[0] + ", lon " + latLon[1]);
//
//        RoundingMode aRoundingMode = RoundingMode.HALF_UP;
//        //RoundingMode aRoundingMode = RoundingMode.HALF_EVEN;
//        //RoundingMode aRoundingMode = RoundingMode.HALF_DOWN;
//        //RoundingMode aRoundingMode = RoundingMode.DOWN;
//
//        BigDecimal latBigDecimal = BigDecimal.valueOf(lat);
//        BigDecimal lonBigDecimal = BigDecimal.valueOf(lon);
//        System.out.println("latBigDecimal " + latBigDecimal + ", lonBigDecimal " + lonBigDecimal);
//        //int decimalPlaces = 10;
//        //for (int decimalPlaces = 10; decimalPlaces < 100; decimalPlaces += 10) {
//        for (int decimalPlaces = 4; decimalPlaces < 32; decimalPlaces += 4) {
//            System.out.println("decimalPlaces " + decimalPlaces);
//            BigDecimal[] eastingNorthingBigDecimal = latlon2osgb(
//                    latBigDecimal,
//                    lonBigDecimal,
//                    decimalPlaces,
//                    aRoundingMode);
//            System.out.println(
//                    "easting " + eastingNorthingBigDecimal[0] + ", "
//                    + "northing " + eastingNorthingBigDecimal[1]);
//            BigDecimal[] latLonBigDecimal = osgb2latlon(
//                    eastingNorthingBigDecimal[0],
//                    eastingNorthingBigDecimal[1],
//                    decimalPlaces,
//                    aRoundingMode);
//            System.out.println("lat " + latLonBigDecimal[0] + ", lon " + latLonBigDecimal[1]);
//
//        }
//    }
//
//    public void run2(
//            double easting,
//            double northing) {
//        System.out.println(
//                    "easting " + easting + ", "
//                    + "northing " + northing);
//        double[] latLon = osgb2latlon(easting, northing);
//        System.out.println(
//                    "lat " + latLon[0] + ", "
//                    + "lon " + latLon[1]);
//            
//        RoundingMode aRoundingMode = RoundingMode.HALF_UP;
//        //RoundingMode aRoundingMode = RoundingMode.HALF_EVEN;
//        //RoundingMode aRoundingMode = RoundingMode.HALF_DOWN;
//        //RoundingMode aRoundingMode = RoundingMode.DOWN;
//
//        BigDecimal[] eastingNorthingBigDecimal = new BigDecimal[2];
//        eastingNorthingBigDecimal[0] = BigDecimal.valueOf(easting);
//        eastingNorthingBigDecimal[1] = BigDecimal.valueOf(northing);
//        
//        /**
//         * http://www.ordnancesurvey.co.uk/gps/transformation
//         * 53.808111 lat
//         * -1.558647 lon
//         * 429162.016 easting
//         * 434735.009 northing
//         * 53.808111 lat
//         * -1.558647 lon
//        */
//        for (int decimalPlaces = 4; decimalPlaces < 32; decimalPlaces += 4) {
//            System.out.println("decimalPlaces " + decimalPlaces);
//            BigDecimal[] latLonBigDecimal = osgb2latlon(
//                    eastingNorthingBigDecimal[0],
//                    eastingNorthingBigDecimal[1],
//                    decimalPlaces,
//                    aRoundingMode);
//            System.out.println("lat " + latLonBigDecimal[0] + ", lon " + latLonBigDecimal[1]);
//            BigDecimal[] eastingNorthingBigDecimal2 = latlon2osgb(
//                    latLonBigDecimal[0],
//                    latLonBigDecimal[1],
//                    decimalPlaces,
//                    aRoundingMode);
//            System.out.println(
//                    "easting " + eastingNorthingBigDecimal2[0] + ", "
//                    + "northing " + eastingNorthingBigDecimal2[1]);
//
//        }
//    }
//    

}
