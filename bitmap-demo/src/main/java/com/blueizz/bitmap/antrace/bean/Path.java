package com.blueizz.bitmap.antrace.bean;

public class Path {
    public int area;       /* area of the bitmap path */
    public int sign;       /* '+' or '-', depending on orientation */
    public Curve curve;    /* this path's vector data */

    public Path next;      /* linked list structure */

    public Path childlist; /* tree structure */
    public Path sibling;   /* tree structure */

    public PrivPath priv;  /* private state */

    public Curve getCurve() {
        return curve;
    }
}
