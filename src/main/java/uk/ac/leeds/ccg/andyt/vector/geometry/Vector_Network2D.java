/**
 * Component of a library for handling spatial vector data. Copyright (C) 2009
 * Andy Turner, CCG, University of Leeds, UK.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.
 */
package uk.ac.leeds.ccg.andyt.vector.geometry;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import uk.ac.leeds.ccg.andyt.grids.core.AbstractGrid2DSquareCell;
import uk.ac.leeds.ccg.andyt.vector.core.Vector_Environment;

/**
 * Class for networks. These comprise a HashMap of Vector_Point2D instances each
 * linked to a HashSet of Connections. Each Connection comprises of a
 * Vector_Point2D instance and a value.
 */
public class Vector_Network2D
        extends Vector_AbstractGeometry2D
        implements Serializable {

    public static double DefaultValue = 1;
    public static int DefaultType = 0;
    private static boolean HandleOutOfMemoryError = false;
    public HashMap<Vector_Point2D, HashSet<Connection>> _Connection_HashMap;

    public Vector_Network2D(
            Vector_Environment ve) {
        _Vector_Environment = ve;
        _Connection_HashMap = new HashMap<Vector_Point2D, HashSet<Connection>>();
    }

    public Vector_Network2D(
            Vector_Environment ve,
            AbstractGrid2DSquareCell aGrid2DSquareCell) {
        _Vector_Environment = ve;
        _Connection_HashMap = new HashMap<Vector_Point2D, HashSet<Connection>>();
        Vector_Point2D a_Point2D;
        HashSet<Vector_Point2D> neighbouringPoints;
        for (long row = 0; row < aGrid2DSquareCell.get_NRows(HandleOutOfMemoryError); row++) {
            for (long col = 0; col < aGrid2DSquareCell.get_NCols(HandleOutOfMemoryError); col++) {
                a_Point2D = new Vector_Point2D(
                        ve,
                        aGrid2DSquareCell.getCellXBigDecimal(col, HandleOutOfMemoryError),
                        aGrid2DSquareCell.getCellYBigDecimal(row, HandleOutOfMemoryError));
                HashSet a_Connection_HashSet = new HashSet<Connection>();
                neighbouringPoints = getNeighbouringPoints(
                        ve,
                        aGrid2DSquareCell,
                        row,
                        col);
                for (Vector_Point2D b_Point2D : neighbouringPoints) {
                    Connection a_Connection = new Connection(
                            b_Point2D,
                            DefaultType,
                            DefaultValue);
                    a_Connection_HashSet.add(a_Connection);
                }
                _Connection_HashMap.put(
                        a_Point2D,
                        a_Connection_HashSet);
            }
        }
    }

    /**
     * Adds the points and connections from a_Network into this. If lowest is
     * true then for connections between the same start and end points of the
     * same type, the lowest value of these is maintained, otherwise the highest
     * is.
     *
     * @param a_Network Vector_Network2D
     * @param operator If operator == 1, then min; operator == 2, then max;
     * operator == 3, then increment; operator == 4, then sum.
     */
    public void addToNetwork(
            Vector_Network2D a_Network,
            int operator) {
        //_Connection_HashMap.putAll(a_Network._Connection_HashMap);
        Iterator a_Iterator = a_Network._Connection_HashMap.keySet().iterator();
        Vector_Point2D a_Point2D;
        while (a_Iterator.hasNext()) {
            a_Point2D = (Vector_Point2D) a_Iterator.next();
            if (_Connection_HashMap.containsKey(a_Point2D)) {
                HashSet<Connection> t_Connection_HashSet = _Connection_HashMap.get(a_Point2D);
                HashSet<Connection> a_Connection_HashSet = a_Network._Connection_HashMap.get(a_Point2D);
                for (Connection a_Connection : a_Connection_HashSet) {
                    // See if this breaks out as expected... May be going through all records unnecessarily!
                    // No work needed for checking type due to the nature of Connection.equals(Object)
                    if (t_Connection_HashSet.contains(a_Connection)) {
                        for (Connection t_Connection : t_Connection_HashSet) {
                            if (t_Connection.equals(a_Connection)) {
                                // See if this breaks out as desired... May be going through all records unnecessarily!
                                switch (operator) {
                                    case 0:
                                        if (t_Connection._Value > a_Connection._Value) {
                                            t_Connection_HashSet.add(a_Connection);
                                        }
                                        break;
                                    case 1:
                                        if (t_Connection._Value < a_Connection._Value) {
                                            t_Connection_HashSet.add(a_Connection);
                                        }
                                        break;
                                    default:
                                        t_Connection.set_Value(t_Connection._Value + a_Connection._Value);
                                        break;
                                }
                            }
                        }
                    } else {
                        t_Connection_HashSet.add(a_Connection);
                        break;
                    }
                }
            } else {
                _Connection_HashMap.put(a_Point2D, a_Network._Connection_HashMap.get(a_Point2D));
            }
        }
    }

    public void addToNetwork(
            Vector_Point2D toConnect_Point2D,
            HashSet<Connection> a_Connections_HashSet) {
        if (_Connection_HashMap.containsKey(toConnect_Point2D)) {
            ((HashSet) _Connection_HashMap.get(toConnect_Point2D)).addAll(a_Connections_HashSet);
        } else {
            _Connection_HashMap.put(
                    toConnect_Point2D,
                    a_Connections_HashSet);
        }
    }

    /**
     *
     * @param toConnect_Point2D Vector_Point2D
     * @param a_Connection Connection
     * @param operator: operator 0, min; operator 1, max; operator default, sum.
     */
    public void addToNetwork(
            Vector_Point2D toConnect_Point2D,
            Connection a_Connection,
            int operator) {
        //if (!toConnect_Point2D.equals(a_Connection._Point2D)){
        if (_Connection_HashMap.containsKey(toConnect_Point2D)) {
            HashSet<Connection> t_Connection_HashSet = (HashSet<Connection>) _Connection_HashMap.get(toConnect_Point2D);
            if (t_Connection_HashSet.contains(a_Connection)) {
                for (Connection t_Connection : t_Connection_HashSet) {
                    if (t_Connection.equals(a_Connection)) {
                        // See if this breaks out as desired... May be going through all records unnecessarily!
                        switch (operator) {
                            case 0:
                                if (t_Connection._Value > a_Connection._Value) {
                                    t_Connection_HashSet.add(a_Connection);
                                }
                                break;
                            case 1:
                                if (t_Connection._Value < a_Connection._Value) {
                                    t_Connection_HashSet.add(a_Connection);
                                }
                                break;
                            default:
                                t_Connection.set_Value(t_Connection._Value + a_Connection._Value);
                        }
                    }
                }
            }
        } else {
            HashSet<Connection> a_Connection_HashSet = new HashSet<Connection>();
            a_Connection_HashSet.add(a_Connection);
            _Connection_HashMap.put(
                    toConnect_Point2D,
                    a_Connection_HashSet);
        }
        //}
    }

    /**
     * Adds a connection with a Default Type and Value using the operator.
     *
     * @param toConnect_Point2D Vector_Point2D
     * @param toConnectTo_Point2D Vector_Point2D
     * @param operator: operator 0, min; operator 1, max; operator default, sum.
     */
    public void addToNetwork(
            Vector_Point2D toConnect_Point2D,
            Vector_Point2D toConnectTo_Point2D,
            int operator) {
        if (!toConnect_Point2D.equals(toConnectTo_Point2D)) {
            Connection a_Connection = new Connection(
                    toConnectTo_Point2D,
                    DefaultType,
                    DefaultValue);
            addToNetwork(
                    toConnect_Point2D,
                    a_Connection,
                    operator);
        }
    }

    /**
     * Adds a connection with a Default Type and Value using the operator.
     *
     * @param toConnect_Point2D Vector_Point2D
     * @param toConnectTo_Point2D Vector_Point2D
     */
    public void addToNetwork(
            Vector_Point2D toConnect_Point2D,
            Vector_Point2D toConnectTo_Point2D) {
        if (!toConnect_Point2D.equals(toConnectTo_Point2D)) {
            addToNetwork(
                    toConnect_Point2D,
                    toConnectTo_Point2D,
                    2);
        }
    }

    @Override
    public String toString() {
        return "_Connection_HashMap.keySet().size() " + _Connection_HashMap.keySet().size();
    }

    public static HashSet<Vector_Point2D> getNeighbouringPoints(
         Vector_Environment ve,
            AbstractGrid2DSquareCell aGrid2DSquareCell,
            long row,
            long col) {
        boolean _HandleOutOfMemoryError = false;
        HashSet<Vector_Point2D> result = new HashSet<Vector_Point2D>();
        long arow;
        long acol;
        arow = row - 1;
        acol = col - 1;
        if (aGrid2DSquareCell.isInGrid(arow, acol, _HandleOutOfMemoryError)) {
            result.add(
                    new Vector_Point2D(
                            ve,
                            aGrid2DSquareCell.getCellXBigDecimal(acol, _HandleOutOfMemoryError),
                            aGrid2DSquareCell.getCellYBigDecimal(arow, _HandleOutOfMemoryError)));
        }
        arow = row - 1;
        acol = col;
        if (aGrid2DSquareCell.isInGrid(arow, acol, _HandleOutOfMemoryError)) {
            result.add(
                    new Vector_Point2D(
                            ve,
                            aGrid2DSquareCell.getCellXBigDecimal(acol, _HandleOutOfMemoryError),
                            aGrid2DSquareCell.getCellYBigDecimal(arow, _HandleOutOfMemoryError)));

        }
        arow = row - 1;
        acol = col + 1;
        if (aGrid2DSquareCell.isInGrid(arow, acol, _HandleOutOfMemoryError)) {
            result.add(
                    new Vector_Point2D(
                            ve,
                            aGrid2DSquareCell.getCellXBigDecimal(acol, _HandleOutOfMemoryError),
                            aGrid2DSquareCell.getCellYBigDecimal(arow, _HandleOutOfMemoryError)));

        }
        arow = row;
        acol = col - 1;
        if (aGrid2DSquareCell.isInGrid(arow, acol, _HandleOutOfMemoryError)) {
            result.add(
                    new Vector_Point2D(
                            ve,
                            aGrid2DSquareCell.getCellXBigDecimal(acol, _HandleOutOfMemoryError),
                            aGrid2DSquareCell.getCellYBigDecimal(arow, _HandleOutOfMemoryError)));

        }
        arow = row;
        acol = col + 1;
        if (aGrid2DSquareCell.isInGrid(arow, acol, _HandleOutOfMemoryError)) {
            result.add(
                    new Vector_Point2D(
                            ve,
                            aGrid2DSquareCell.getCellXBigDecimal(acol, _HandleOutOfMemoryError),
                            aGrid2DSquareCell.getCellYBigDecimal(arow, _HandleOutOfMemoryError)));

        }
        arow = row + 1;
        acol = col - 1;
        if (aGrid2DSquareCell.isInGrid(arow, acol, _HandleOutOfMemoryError)) {
            result.add(
                    new Vector_Point2D(
                            ve,
                            aGrid2DSquareCell.getCellXBigDecimal(acol, _HandleOutOfMemoryError),
                            aGrid2DSquareCell.getCellYBigDecimal(arow, _HandleOutOfMemoryError)));

        }
        arow = row + 1;
        acol = col;
        if (aGrid2DSquareCell.isInGrid(arow, acol, _HandleOutOfMemoryError)) {
            result.add(
                    new Vector_Point2D(
                            ve,
                            aGrid2DSquareCell.getCellXBigDecimal(acol, _HandleOutOfMemoryError),
                            aGrid2DSquareCell.getCellYBigDecimal(arow, _HandleOutOfMemoryError)));

        }
        arow = row + 1;
        acol = col + 1;
        if (aGrid2DSquareCell.isInGrid(arow, acol, _HandleOutOfMemoryError)) {
            result.add(
                    new Vector_Point2D(
                            ve,
                            aGrid2DSquareCell.getCellXBigDecimal(acol, _HandleOutOfMemoryError),
                            aGrid2DSquareCell.getCellYBigDecimal(arow, _HandleOutOfMemoryError)));

        }
        return result;
    }

    @Override
    public Vector_Envelope2D getEnvelope2D() {
        Vector_Envelope2D result = new Vector_Envelope2D();
        Vector_Point2D a_Point2D;
        Vector_Point2D b_Point2D;
        Set<Vector_Point2D> a_Connection_HashMap_Key_Set = _Connection_HashMap.keySet();
        Iterator<Vector_Point2D> a_Connection_HashMap_Key_Set_Iterator = a_Connection_HashMap_Key_Set.iterator();
        HashSet<Connection> a_Connection_HashMap_HashSet;
        Iterator<Connection> a_Connection_HashMap_HashSet_Iterator;
        Connection a_Connection;
        while (a_Connection_HashMap_Key_Set_Iterator.hasNext()) {
            a_Point2D = a_Connection_HashMap_Key_Set_Iterator.next();
            result = result.envelope(a_Point2D.getEnvelope2D());
            a_Connection_HashMap_HashSet = _Connection_HashMap.get(a_Point2D);
            a_Connection_HashMap_HashSet_Iterator = a_Connection_HashMap_HashSet.iterator();
            while (a_Connection_HashMap_HashSet_Iterator.hasNext()) {
                a_Connection = a_Connection_HashMap_HashSet_Iterator.next();
                b_Point2D = a_Connection._Point2D;
                result = result.envelope(b_Point2D.getEnvelope2D());
            }
        }
        return result;
    }

    @Override
    public void applyDecimalPlacePrecision() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public class Connection implements Serializable {

        public Vector_Point2D _Point2D;
        /**
         * Intended for capacity or other condition, or store of information
         * (e.g. number of flows)...
         */
        public double _Value;
        /**
         * To distinguish between different types of connection e.g. On foot, by
         * car etc...
         */
        public int _Type;

        public Connection() {
        }

        public Connection(
                Vector_Point2D _Point2D,
                int _Type,
                double _Value) {
            this._Point2D = _Point2D;
            this._Type = _Type;
            this._Value = _Value;
        }

        public void set_Value(double _Value) {
            this._Value = _Value;
        }

        /**
         * This ignores _Value, so two connections are the same irrespective of
         * _Value.
         *
         * @param o Object
         * @return boolean
         */
        @Override
        public boolean equals(Object o) {
            if (o instanceof Connection) {
                Connection a_Connection = (Connection) o;
                return _Point2D.equals(a_Connection._Point2D)
                        && _Type == a_Connection._Type;
            }
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 73 * hash + (this._Point2D != null ? this._Point2D.hashCode() : 0);
            hash = 73 * hash + this._Type;
            return hash;
        }
    }
}
