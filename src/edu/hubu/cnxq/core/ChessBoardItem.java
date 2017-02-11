package edu.hubu.cnxq.core;

import java.io.Serializable;

/**
 * 棋子的逻辑模型
 */
public abstract class ChessBoardItem implements Serializable {
    private static final long serialVersionUID = 3082487677031917998L;
    private int x;
    private int y;

    public ChessBoardItem(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChessBoardItem that = (ChessBoardItem) o;

        if (x != that.x) return false;
        if (y != that.y) return false;
        return getState() == that.getState();
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + getState();
        return result;
    }

    public final int getX() {
        return x;
    }

    public final int getY() {
        return y;
    }

    public abstract int getState();

    public abstract void setState(int state) ;

    @Override
    public String toString() {
        return "ChessBoardItem{" +
                "x=" + x +
                ", y=" + y +
                ", state=" + getState() +
                '}';
    }


}
