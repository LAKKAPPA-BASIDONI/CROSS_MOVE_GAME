package com.basidoni.backend.model;

public class MoveResponse {

    private int frow;
    private int fcol;
    private int trow;
    private int tcol;


    public MoveResponse(int frow, int fcol, int trow, int tcol) {
        this.frow = frow;
        this.fcol = fcol;
        this.trow = trow;
        this.tcol = tcol;
    }

    public MoveResponse(int frow, int fcol) {
        this.frow = frow;
        this.fcol = fcol;
    }



    public int getFrow() {
        return frow;
    }

    public void setFrow(int frow) {
        this.frow = frow;
    }

    public int getFcol() {
        return fcol;
    }

    public void setFcol(int fcol) {
        this.fcol = fcol;
    }

    public int getTrow() {
        return trow;
    }

    public void setTrow(int trow) {
        this.trow = trow;
    }

    public int getTcol() {
        return tcol;
    }

    public void setTcol(int tcol) {
        this.tcol = tcol;
    }


}
