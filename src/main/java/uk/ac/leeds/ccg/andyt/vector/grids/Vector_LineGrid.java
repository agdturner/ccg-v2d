/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.grids;

import java.io.File;
import java.math.BigDecimal;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_2D_ID_long;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Dimensions;
import uk.ac.leeds.ccg.andyt.grids.core.grid.Grids_GridDouble;
import uk.ac.leeds.ccg.andyt.grids.core.grid.chunk.Grids_GridChunkDoubleArrayFactory;
import uk.ac.leeds.ccg.andyt.grids.core.grid.Grids_GridDoubleFactory;
import uk.ac.leeds.ccg.andyt.grids.core.Grids_Environment;
import uk.ac.leeds.ccg.andyt.grids.core.grid.stats.Grids_GridDoubleStats;
import uk.ac.leeds.ccg.andyt.grids.io.Grids_ImageExporter;
import uk.ac.leeds.ccg.andyt.grids.process.Grids_Processor;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Object;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_LineSegment2D;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_Point2D;

/**
 *
 * @author geoagdt
 */
public class Vector_LineGrid extends Vector_Object {

    protected Vector_LineGrid() {
    }

    protected Vector_LineGrid(Vector_Environment ve) {
        super(ve);
    }

    public static void main(String[] args) {
        Vector_Environment ve;
        ve = new Vector_Environment();
        new Vector_LineGrid(ve).run();
    }

    public void run() {
        // Grids set up
        BigDecimal tollerance;
        tollerance = new BigDecimal("0.0000001");
        double factor;
        factor = 1;
        File dir;
        dir = new File(
                "/scratch02/DigitalWelfare/Output/LCC/SHBE/");
        double noDataValue;
        noDataValue = -9999.0d;
        boolean handleNoDataValue;
        handleNoDataValue = true;
        Grids_Environment ge;
        ge = ve.getGrids_Environment();
        Grids_GridChunkDoubleArrayFactory gcaf;
        gcaf = new Grids_GridChunkDoubleArrayFactory();
        Grids_GridDouble g;
        long nRows;
        nRows = 100;
        long nCols;
        nCols = 100;
        Grids_Dimensions dimensions; // The cellsize, xmin, ymin, xmax and ymax.
        dimensions = new Grids_Dimensions(
                new BigDecimal(0.0d),
                new BigDecimal(0.0d),
                new BigDecimal(100.0d),
                new BigDecimal(100.0d),
                new BigDecimal(1.0d));
        Grids_GridDoubleStats gs;
        gs = new Grids_GridDoubleStats(ge);

        Grids_GridDoubleFactory gf;
        gf = new Grids_GridDoubleFactory(
                ge,
                dir,
                ge.getProcessor().GridChunkDoubleFactory, gcaf, noDataValue,
                (int) nRows, (int) nCols, dimensions, gs);
        g = gf.create(gs, dir, gcaf, nRows, nCols, dimensions, handleNoDataValue);
        // Vector set up
        Vector_Point2D p0;
        p0 = new Vector_Point2D(
                ve,
                new BigDecimal(30.2d),
                new BigDecimal(30.5d));
        Vector_Point2D p1;
        p1 = new Vector_Point2D(ve, new BigDecimal(40.5d),
                new BigDecimal(20.5d));
        Vector_LineSegment2D l;
        l = new Vector_LineSegment2D(p0, p1);
        System.out.println("line " + l);
        addToGrid(g, l, factor, tollerance, handleNoDataValue);
        System.out.println(g.toString());
        Grids_ImageExporter ie;
        ie = new Grids_ImageExporter(ge);
        Grids_Processor gp;
        gp = new Grids_Processor(ge, dir);
        File fout = new File(dir, "test.PNG");
        ie.toGreyScaleImage(g, gp, fout, "PNG");
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
            Grids_GridDouble g,
            Vector_LineSegment2D l,
            double factor,
            BigDecimal tollerance,
            boolean handleOutOfMemoryError) {
        int decimalPlacePrecision;
        decimalPlacePrecision = 10;
//        int chunkNRows;
//        chunkNRows = g.getChunkNRows();
//        int chunkNCols;
//        chunkNCols = g.getChunkNCols();
        Grids_Dimensions dimensions;
        dimensions = g.getDimensions();
//        BigDecimal cellsize;
//        cellsize = dimensions[0];
        BigDecimal xmin;
        xmin = dimensions.getXMin();
        BigDecimal ymin;
        ymin = dimensions.getYMin();
        BigDecimal xmax;
        xmax = dimensions.getXMax();
        BigDecimal ymax;
        ymax = dimensions.getYMax();
        BigDecimal halfCellsize;
        halfCellsize = dimensions.getHalfCellsize();
        Integer directionIn;
        directionIn = null;
        //System.out.println("line length " + l.getLength(decimalPlacePrecision));
        long nrows = g.getNRows();
        long ncols = g.getNCols();
        // Check if line intersect grid and if not return fast.
        if (Vector_LineSegment2D.getIntersects(xmin, ymin, xmax, ymax, l,
                tollerance, decimalPlacePrecision, handleOutOfMemoryError)) {
            long row;
            row = g.getRow(l.Start.Y);
            long col;
            col = g.getCol(l.Start.X);
            Grids_2D_ID_long cellID = g.getCellID(row, col);
            Grids_Dimensions cellBounds;
            cellBounds = g.getCellDimensions(halfCellsize, row, col);
            Object[] lineToIntersectIntersectPointDirection;
            lineToIntersectIntersectPointDirection = Vector_LineSegment2D.getLineToIntersectLineRemainingDirection(
                    tollerance, xmin, ymin, xmax, ymax, l, directionIn, decimalPlacePrecision,
                    handleOutOfMemoryError);
            Vector_LineSegment2D lineToIntersect;
            lineToIntersect = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[0];
            //System.out.println("lineToIntersect " + lineToIntersect);
            double length;
            length = lineToIntersect.getLength(decimalPlacePrecision).doubleValue();
            double v;
            v = length * factor;
            //System.out.println("lineToIntersect length " + length);
            g.addToCell(cellID, v);
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
                    row++;
                }
                if (directionOut == 1) {
                    row++;
                    col++;
                }
                if (directionOut == 2) {
                    col++;
                }
                if (directionOut == 3) {
                    col++;
                    row--;
                }
                if (directionOut == 4) {
                    row--;
                }
                if (directionOut == 5) {
                    col--;
                    row--;
                }
                if (directionOut == 6) {
                    col--;
                }
                if (directionOut == 7) {
                    col--;
                    row++;
                }
                if (row < 0 || row >= nrows
                        || col < 0 || col >= ncols) {
                    return;
                }
                //System.out.println("cellRowIndex " + cellRowIndex + ", cellColIndex " + cellColIndex);
                cellID = g.getCellID(row, col);
                cellBounds = g.getCellDimensions(halfCellsize, row, col);
                lineToIntersectIntersectPointDirection = Vector_LineSegment2D.getLineToIntersectLineRemainingDirection(
                        tollerance, xmin, ymin, xmax, ymax, remainingLine, directionOut, decimalPlacePrecision, handleOutOfMemoryError);
                lineToIntersect = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[0];
                //System.out.println("lineToIntersect " + lineToIntersect);
                length = lineToIntersect.getLength(decimalPlacePrecision).doubleValue();
                //System.out.println("lineToIntersect length " + length);
                v = length * factor;
                g.addToCell(cellID, v);
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
