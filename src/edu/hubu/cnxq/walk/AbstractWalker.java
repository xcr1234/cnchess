package edu.hubu.cnxq.walk;

import edu.hubu.cnxq.Constants;
import edu.hubu.cnxq.XqWalkState;
import edu.hubu.cnxq.core.Move;
import edu.hubu.cnxq.core.Point;
import edu.hubu.cnxq.core.WalkState;


/**
 * 抽象的Walker，提供了一些非常方便的方法
 */
public abstract class AbstractWalker implements Walker {

    protected XqWalkState walkState;

    protected int state;

    @Override
    public int getState() {
        return state;
    }

    @Override
    public final void setState(WalkState walkState) {
        this.walkState = (XqWalkState) walkState;
    }

    public boolean inBoard(int x, int y) {
        return walkState.getChessBoard().inBoard(x, y);
    }

    public final int getState(int x, int y) {
        return walkState.getChessBoard().getState(x, y);
    }

    public final boolean isEmpty(int x, int y) {
        return getState(x, y) == 0;
    }

    public final int setState(int x, int y, int s) {
        return walkState.getChessBoard().setState(x, y, s);
    }

    public final int indexFor(int x, int y) {
        if (x < 0 || x >= walkState.getChessBoard().getMaxX() || y < 0 || y >= walkState.getChessBoard().getMaxY()) {
            throw new IndexOutOfBoundsException("x:" + x + ",y:" + y);
        }
        return 16 * (y + 3) + x + 3;
    }

    public static Point pointFor(int index) {
        return new Point(index & 16 - 3, index / 16 - 3);
    }

    public static  int pointForX(int index) {
        return index % 16 - 3;
    }

    public static  int pointForY(int index) {
        return index / 16 - 3;
    }


    public final int getState(int index) {
        return walkState.getUcpcSquares()[index];
    }

    public boolean inBoard(int index) {
        return walkState.getChessBoard().inBoard(index);
    }


    //是否已经过河
    public boolean isCrossRiver(int x, int y, boolean red) {
        if (red) {
            return y < Constants.riverYR;
        } else {
            return y > Constants.riverYb;
        }
    }

    public final boolean isSelf(int x, int y, boolean red) {
        int state = getState(x, y);
        return state != Constants.EMPTY && (red ? isRed(state) : isBlack(state));
    }

    public final boolean isSelf(int x, boolean red) {
        int state = getState(x);
        return state != Constants.EMPTY && (red ? isRed(state) : isBlack(state));
    }

    public final boolean isEnemy(int x, int y, boolean red) {
        int state = getState(x, y);
        return state != Constants.EMPTY && (red ? isBlack(state) : isRed(state));
    }

    public final boolean isEnemy(int x, boolean red) {
        int state = getState(x);
        return state != Constants.EMPTY && (red ? isBlack(state) : isRed(state));
    }

    //是否在自己的九宫格内.
    public final boolean isInNine(int x, int y, boolean red) {
        if (red) {
            return inBoard(x, y) && x >= 3 && x <= 5 && y >= 7;
        } else {
            return inBoard(x, y) && x >= 3 && x <= 5 && y <= 2;
        }
    }

    @Override
    public final boolean canMove(boolean red, Move move) {
        int x = move.getFrom().getX();
        int y = move.getFrom().getY();
        int tx = move.getTo().getX();
        int ty = move.getTo().getY();
        return canMove(red, x, y, tx, ty);
    }

    protected abstract boolean canMove(boolean red, int x, int y, int tx, int ty);

    public static boolean isRed(int state) {
        return (state & 8) != 0;
    }

    public static boolean isBlack(int state) {
        return (state & 16) != 0;
    }

}
