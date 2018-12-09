package com.blueizz.bitmap.antrace.bean;

public class PrivPath {
    public int len;
    public Point[] pt;        /* pt[len]: path as extracted from bitmap */
    public int[] lon;         /* lon[len]: (i,lon[i]) = longest straight line from i */

    public int x0, y0;        /* origin for sums */
    public Sums[] sums;       /* sums[len+1]: cache for fast summing */

    public int m;             /* length of optimal polygon */
    public int[] po;          /* po[m]: optimal polygon */

    public PrivCurve curve;   /* curve[m]: array of curve elements */
    public PrivCurve ocurve;  /* ocurve[om]: array of curve elements */

    /**
     * final curve: this points to either curve or
     * ocurve. Do not free this separately.
     */
    public PrivCurve fcurve;
}
