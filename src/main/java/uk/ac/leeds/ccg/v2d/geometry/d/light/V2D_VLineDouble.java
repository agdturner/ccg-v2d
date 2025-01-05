/*
 * Copyright 2020 Andy Turner, University of Leeds.
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
package uk.ac.leeds.ccg.v2d.geometry.d.light;

import java.io.Serializable;

/**
 * 2D representation of a moveable finite length line. The line starts at 
 * {@link #p} and ends at {@link #q}.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_VLineDouble implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A point defining the line.
     */
    public final V2D_VDouble p;

    /**
     * A point defining the line.
     */
    public final V2D_VDouble q;

    /**
     * @param l Used to initialise this.
     */
    public V2D_VLineDouble(V2D_VLineDouble l) {
        this.p = new V2D_VDouble(l.p);
        this.q = new V2D_VDouble(l.q);
    }

    /**
     * @param p What {@link #p} is set to.
     * @param q What {@link #q} is set to.
     */
    public V2D_VLineDouble(V2D_VDouble p, V2D_VDouble q) {
        this.p = p;
        this.q = q;
    }

    @Override
    public String toString() {
        return toString("");
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of this.
     */
    public String toString(String pad) {
        return this.getClass().getSimpleName() + "\n"
                + pad + "(\n"
                + toStringFields(pad + " ") + "\n"
                + pad + ")";
    }

    /**
     * @param pad A padding of spaces.
     * @return A description of the fields.
     */
    protected String toStringFields(String pad) {
        return pad + "p=" + p.toString(pad) + "\n"
                + pad + ",\n"
                + pad + "q=" + q.toString(pad);
//               + pad + "q=" + q.toString(pad) + "\n"
//               + pad + ",\n"
//               + pad + "v=" + v.toString(pad);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof V2D_VLineDouble l) {
            return equals(l);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    /**
     * The lines are equal irrespective of the direction vector between the 
     * points.
     * 
     * @param l The line to test if it is the same as {@code this}.
     * @return {@code true} iff {@code l} is the same as {@code this}.
     */
    public boolean equals(V2D_VLineDouble l) {
        return (p.equals(l.p) && q.equals(l.q))
                || (p.equals(l.q) && q.equals(l.p));
    }

    /**
     * @return The centre of the line. 
     */
    public V2D_VDouble getC() {
        return new V2D_VDouble(
                    (p.x + q.x)/2D,
                    (p.y + q.y)/2D);
    }
}
