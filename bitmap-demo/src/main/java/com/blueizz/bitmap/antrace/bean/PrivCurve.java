package com.blueizz.bitmap.antrace.bean;

public class PrivCurve {
    public int n;            /* number of segments */
    public int[] tag;        /* tag[n]: POTRACE_CORNER or POTRACE_CURVETO */

    /**
     * c[n][i]: control points.
     * c[n][0] is unused for tag[n]=POTRACE_CORNER
     * the remainder of this structure is special to privcurve, and is
     * used in EPS debug output and special EPS "short coding". These
     * fields are valid only if "alphacurve" is set.
     */
    public Dpoint[][] c;
    public int alphacurve;    /* have the following fields been initialized? */
    public Dpoint[] vertex;   /* for POTRACE_CORNER, this equals c[1] */
    public double[] alpha;    /* only for POTRACE_CURVETO */
    public double[] alpha0;   /* "uncropped" alpha parameter - for debug output only */
    public double[] beta;
}
