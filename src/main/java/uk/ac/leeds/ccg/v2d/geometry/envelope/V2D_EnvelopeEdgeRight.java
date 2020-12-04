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
package uk.ac.leeds.ccg.v2d.geometry.envelope;

import uk.ac.leeds.ccg.v2d.geometry.V2D_Point;

/**
 * For the right edge of an envelope. For the two points that define the line
 * segment, {@link #p} is the top-right point (tr) and {@link q} is the
 * bottom-right point (br). {@code tr.y} should equal {@code br.y}. No checking
 * of these conditions is done for efficiency reasons.
 *
 * @author Andy Turner
 * @version 1.0
 */
public class V2D_EnvelopeEdgeRight extends V2D_EnvelopeEdge {

    private static final long serialVersionUID = 1L;

    /**
     * @param tr The point in the top-right of an envelope.
     * @param br The point in the bottom-right of an envelope.
     */
    public V2D_EnvelopeEdgeRight(V2D_Point tr, V2D_Point br) {
        super(tr, br);
    }

    /**
     * @param e A V2D_Envelope.
     */
    public V2D_EnvelopeEdgeRight(V2D_Envelope e) {
        super(new V2D_Point(e.getxMax(), e.getyMin()), new V2D_Point(e.getxMax(), e.getyMax()));
    }

    public V2D_Point getTr() {
        return start;
    }

    public V2D_Point getBr() {
        return end;
    }

}
