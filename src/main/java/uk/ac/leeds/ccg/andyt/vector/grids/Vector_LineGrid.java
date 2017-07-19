/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.grids;

import java.io.File;
import java.math.BigDecimal;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_AbstractGrid2DSquareCell.CellID;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Grid2DSquareCellDouble;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Grid2DSquareCellDoubleChunkArrayFactory;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Grid2DSquareCellDoubleFactory;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_GridStatistics0;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.andyt.grids.exchange.Grids_ImageExporter;
import uk.ac.leeds.ccg.andyt.grids.process.Grid2DSquareCellProcessor;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_LineSegment2D;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Point2D;

/**
 *
 * @author geoagdt
 */
public class Vector_LineGrid {

    public Vector_LineGrid() {
    }

    public static void main(String[] args) {
        new Vector_LineGrid().run();
    }

    public void run() {
        // Grids set up
        BigDecimal tollerance;
        tollerance = new BigDecimal("0.0000001");
        double factor;
        factor = 1;

        Grids_Environment ge;
        ge = new Grids_Environment();
        File dir;
        dir = new File(
                "/scratch02/DigitalWelfare/Output/LCC/SHBE/");
        double noDataValue;
        noDataValue = -9999.0d;
        boolean handleNoDataValue;
        handleNoDataValue = true;
        Grids_Grid2DSquareCellDoubleFactory gf;
        gf = new Grids_Grid2DSquareCellDoubleFactory(dir, noDataValue, ge, handleNoDataValue);
        Grids_Grid2DSquareCellDoubleChunkArrayFactory gcaf;
        gcaf = new Grids_Grid2DSquareCellDoubleChunkArrayFactory();
        Grids_Grid2DSquareCellDouble g;
        long _NRows;
        _NRows = 100;
        long _NCols;
        _NCols = 100;
        BigDecimal[] _Dimensions; // The cellsize, xmin, ymin, xmax and ymax.
        _Dimensions = new BigDecimal[5];
        _Dimensions[0] = new BigDecimal(1.0d);
        _Dimensions[1] = new BigDecimal(0.0d);
        _Dimensions[2] = new BigDecimal(0.0d);
        _Dimensions[3] = new BigDecimal(100.0d);
        _Dimensions[4] = new BigDecimal(100.0d);
        Grids_GridStatistics0 gs;
        gs = new Grids_GridStatistics0();
        g = gf.create(gs, dir, gcaf, _NRows, _NCols, _Dimensions, ge, handleNoDataValue);
        // Vector set up
        Vector_Environment ve;
        ve = new Vector_Environment();
        Vector_Point2D p0;
        p0 = new Vector_Point2D(
                ve,
                new BigDecimal(30.2d),
                new BigDecimal(30.5d));
        Vector_Point2D p1;
        p1 = new Vector_Point2D(
                ve,
                new BigDecimal(40.5d),
                new BigDecimal(20.5d));
        Vector_LineSegment2D l;
        l = new Vector_LineSegment2D(
                p0,
                p1);
        System.out.println("line " + l);
        addToGrid(g, l, factor, tollerance, handleNoDataValue);

        System.out.println(g.toString(handleNoDataValue));

        Grids_ImageExporter ie;
        ie = new Grids_ImageExporter(ge);

        Grid2DSquareCellProcessor gp;
        gp = new Grid2DSquareCellProcessor(ge, dir);

        File fout = new File(
                dir,
                "test.PNG");
        ie.toGreyScaleImage(g, gp, fout, "PNG", handleNoDataValue);

    }

    /**
     * Adds the length of l to each cell of g
     *
     * @param g
     * @param l
     * @param factor Value to multiply length by
     * @param tollerance
     * @param handleOutOfMemoryError
     */
    public static void addToGrid(
            Grids_Grid2DSquareCellDouble g,
            Vector_LineSegment2D l,
            double factor,
            BigDecimal tollerance,
            boolean handleOutOfMemoryError) {
        int decimalPlacePrecision;
        decimalPlacePrecision = 10;
//        int chunkNRows;
//        chunkNRows = g.get_ChunkNRows(handleOutOfMemoryError);
//        int chunkNCols;
//        chunkNCols = g.get_ChunkNCols(handleOutOfMemoryError);
        BigDecimal[] dimensions;
        dimensions = g.get_Dimensions(handleOutOfMemoryError);
//        BigDecimal cellsize;
//        cellsize = dimensions[0];
        BigDecimal xmin;
        xmin = dimensions[1];
        BigDecimal ymin;
        ymin = dimensions[2];
        BigDecimal xmax;
        xmax = dimensions[3];
        BigDecimal ymax;
        ymax = dimensions[4];
        Integer directionIn;
        directionIn = null;
        //System.out.println("line length " + l.getLength(decimalPlacePrecision));
        long nrows = g.get_NRows(handleOutOfMemoryError);
        long ncols = g.get_NCols(handleOutOfMemoryError);
        // Check if line intersect grid and if not return fast.
        if (Vector_LineSegment2D.getIntersects(
                xmin, ymin, xmax, ymax,
                l, tollerance,
                decimalPlacePrecision, handleOutOfMemoryError)) {
            long cellRowIndex;
            cellRowIndex = g.getCellRowIndex(l._Start_Point2D._y, handleOutOfMemoryError);
            long cellColIndex;
            cellColIndex = g.getCellColIndex(l._Start_Point2D._x, handleOutOfMemoryError);
            //System.out.println("cellRowIndex " + cellRowIndex + ", cellColIndex " + cellColIndex);
            CellID cellID = g.getCellID(
                    cellRowIndex,
                    cellColIndex,
                    handleOutOfMemoryError);
//            CellID cellID = g.getCellID(
//                    l._Start_Point2D._x,
//                    l._Start_Point2D._y,
//                    handleOutOfMemoryError);
            BigDecimal[] cellBounds;
            cellBounds = g.getCellBounds_BigDecimalArray(
                    cellRowIndex,
                    cellColIndex,
                    handleOutOfMemoryError);
//            cellBounds = g.getCellBounds_BigDecimalArray(
//                    l._Start_Point2D._x,
//                    l._Start_Point2D._y,
//                    handleOutOfMemoryError);
            Object[] lineToIntersectIntersectPointDirection;
            lineToIntersectIntersectPointDirection = Vector_LineSegment2D.getLineToIntersectLineRemainingDirection(
                    tollerance,
                    cellBounds[0],
                    cellBounds[1],
                    cellBounds[2],
                    cellBounds[3],
                    l,
                    directionIn,
                    decimalPlacePrecision,
                    handleOutOfMemoryError);
            Vector_LineSegment2D lineToIntersect;
            lineToIntersect = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[0];
            //System.out.println("lineToIntersect " + lineToIntersect);
            double length;
            length = lineToIntersect.getLength(decimalPlacePrecision).doubleValue();
            double v;
            v = length * factor;
            //System.out.println("lineToIntersect length " + length);
            g.addToCell(cellID, v , handleOutOfMemoryError);
            if (lineToIntersectIntersectPointDirection[2] == null) {
                return;
            }
            Vector_LineSegment2D remainingLine;
            remainingLine = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[1];
            //System.out.println("remainingLine " + remainingLine);
            BigDecimal remainingLineLength;
            remainingLineLength = remainingLine.getLength(decimalPlacePrecision);
            //System.out.println("remainingLine length " + remainingLineLength);
            Integer directionOut;
            directionOut = (Integer) lineToIntersectIntersectPointDirection[2];
            //System.out.println("directionOut " + directionOut);
            //while (remainingLineLength.compareTo(BigDecimal.ZERO) == 1) { // Change for a tollerance.
            while (!(remainingLineLength.compareTo(tollerance) == -1 && remainingLineLength.compareTo(tollerance.negate()) == 1)) {
                //System.out.println("remainingLineLength " + remainingLineLength);
                if (directionOut == 0) {
                    cellRowIndex++;
                }
                if (directionOut == 1) {
                    cellRowIndex++;
                    cellColIndex++;
                }
                if (directionOut == 2) {
                    cellColIndex++;
                }
                if (directionOut == 3) {
                    cellColIndex++;
                    cellRowIndex--;
                }
                if (directionOut == 4) {
                    cellRowIndex--;
                }
                if (directionOut == 5) {
                    cellColIndex--;
                    cellRowIndex--;
                }
                if (directionOut == 6) {
                    cellColIndex--;
                }
                if (directionOut == 7) {
                    cellColIndex--;
                    cellRowIndex++;
                }
                if (cellRowIndex < 0 || cellRowIndex >= nrows
                        || cellColIndex < 0 || cellColIndex >= ncols) {
                    return;
                }
                //System.out.println("cellRowIndex " + cellRowIndex + ", cellColIndex " + cellColIndex);
                cellID = g.getCellID(
                        cellRowIndex,
                        cellColIndex,
                        handleOutOfMemoryError);
                cellBounds = g.getCellBounds_BigDecimalArray(
                        cellRowIndex,
                        cellColIndex,
                        handleOutOfMemoryError);
                lineToIntersectIntersectPointDirection = Vector_LineSegment2D.getLineToIntersectLineRemainingDirection(
                        tollerance,
                        cellBounds[0],
                        cellBounds[1],
                        cellBounds[2],
                        cellBounds[3],
                        remainingLine,
                        directionOut,
                        decimalPlacePrecision,
                        handleOutOfMemoryError);
                lineToIntersect = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[0];
                //System.out.println("lineToIntersect " + lineToIntersect);
                length = lineToIntersect.getLength(decimalPlacePrecision).doubleValue();
                //System.out.println("lineToIntersect length " + length);
                v = length * factor;
                g.addToCell(cellID, v, handleOutOfMemoryError);
                if (lineToIntersectIntersectPointDirection[2] == null) {
                    return;
                }
                remainingLine = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[1];
                //System.out.println("remainingLine " + remainingLine);
                remainingLineLength = remainingLine.getLength(decimalPlacePrecision);
                //System.out.println("remainingLine length " + remainingLineLength);
                directionOut = (Integer) lineToIntersectIntersectPointDirection[2];
                //System.out.println("directionOut " + directionOut);
            }
        }
    }
}
