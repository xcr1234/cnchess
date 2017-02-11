package edu.hubu.cnxq.core;


import java.io.Serializable;

public class Move implements Serializable{
    private static final long serialVersionUID = -1158771405035884820L;
    private Point from;
    private Point to;

    public Move(int fromX, int fromY, int toX, int toY) {
        from = new Point(fromX, fromY);
        to = new Point(toX, toY);
    }

    public Move(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Move move = (Move) o;

        if (from != null ? !from.equals(move.from) : move.from != null) return false;
        return to != null ? to.equals(move.to) : move.to == null;
    }

    @Override
    public int hashCode() {
        int result = from != null ? from.hashCode() : 0;
        result = 31 * result + (to != null ? to.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Move{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }
}
