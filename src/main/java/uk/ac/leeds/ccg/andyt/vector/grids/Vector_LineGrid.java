/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.leeds.ccg.andyt.vector.grids;

import java.math.BigDecimal;
import uk.ac.leeds.ccg.andyt.grids.core.AbstractGrid2DSquareCell.CellID;
import uk.ac.leeds.ccg.andyt.grids.core.Grid2DSquareCellDouble;
import uk.ac.leeds.ccg.andyt.vector.geometry.Vector_LineSegment2D;

/**
 *
 * @author geoagdt
 */
public class Vector_LineGrid {

    /**
     * Adds the length of l to each cell of g
     *
     * @param g
     * @param l
     */
    public static void addToGrid(
            Grid2DSquareCellDouble g,
            Vector_LineSegment2D l,
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
            
        long nrows = g.get_NRows(handleOutOfMemoryError);
        long ncols = g.get_NCols(handleOutOfMemoryError);
        // Check if line intersect grid and if not return fast.
        if (Vector_LineSegment2D.getIntersects(
                xmin, ymin, xmax, ymax,
                l, decimalPlacePrecision, handleOutOfMemoryError)) {
            long cellRowIndex;
            cellRowIndex = g.getCellRowIndex(l._Start_Point2D._y, handleOutOfMemoryError);
            long cellColIndex;
            cellColIndex = g.getCellColIndex(l._Start_Point2D._x, handleOutOfMemoryError);
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
            lineToIntersectIntersectPointDirection = Vector_LineSegment2D.getLineToIntersectIntersectRemainingLineDirection(
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
            double length;
            length = lineToIntersect.getLength(decimalPlacePrecision).doubleValue();
            g.addToCell(cellID, length, handleOutOfMemoryError);
            Vector_LineSegment2D remainingLine;
            remainingLine = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[1];
            Integer directionOut;
            directionOut = (Integer) lineToIntersectIntersectPointDirection[2];
            
            BigDecimal remainingLineLength;
            remainingLineLength = remainingLine.getLength(decimalPlacePrecision);
            while (remainingLineLength.compareTo(BigDecimal.ZERO) == 1) {
                if (directionOut == 0) {
                    cellRowIndex ++;
                }
                if (directionOut == 1) {
                    cellRowIndex ++;
                    cellColIndex ++;
                }
                if (directionOut == 2) {
                    cellColIndex ++;
                }
                if (directionOut == 3) {
                    cellColIndex ++;
                    cellRowIndex --;
                }
                if (directionOut == 4) {
                    cellRowIndex --;
                }
                if (directionOut == 5) {
                    cellColIndex --;
                    cellRowIndex --;
                }
                if (directionOut == 6) {
                    cellColIndex --;
                }
                if (directionOut == 7) {
                    cellColIndex --;
                    cellRowIndex ++;
                }
                if (cellRowIndex < 0 || cellRowIndex >= nrows ||
                        cellColIndex < 0 || cellColIndex >= ncols) {
                    return;
                }
                cellID = g.getCellID(
                    cellRowIndex, 
                    cellColIndex, 
                    handleOutOfMemoryError);
                cellBounds = g.getCellBounds_BigDecimalArray(
                        cellRowIndex, 
                        cellColIndex, 
                        handleOutOfMemoryError);
                lineToIntersectIntersectPointDirection = Vector_LineSegment2D.getLineToIntersectIntersectRemainingLineDirection(
                    cellBounds[0],
                    cellBounds[1],
                    cellBounds[2],
                    cellBounds[3],
                    remainingLine,
                    directionOut,
                    decimalPlacePrecision,
                    handleOutOfMemoryError);
                lineToIntersect = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[0];
                length = lineToIntersect.getLength(decimalPlacePrecision).doubleValue();
                g.addToCell(cellID, length, handleOutOfMemoryError);
                if (lineToIntersectIntersectPointDirection[2] == null) {
                    return;
                }
                remainingLine = (Vector_LineSegment2D) lineToIntersectIntersectPointDirection[1];
                remainingLineLength = remainingLine.getLength(decimalPlacePrecision);
                directionOut = (Integer) lineToIntersectIntersectPointDirection[2];
            }
        }
    }
}
