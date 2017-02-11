package edu.hubu.cnxq.walk;

import edu.hubu.cnxq.core.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * 走斜线的棋子，适用于马，象、士
 */
public class SlashWalker extends AbstractWalker {

    public SlashWalker(int[][] offset, int[] LegOffset) {
        this.offset = offset;
        this.LegOffset = LegOffset;
    }

    private int[][] offset;//偏移
    private int[] LegOffset;//腿的偏移（马，象有腿，士没有腿，该值为null）

    @Override
    protected boolean canMove(boolean red, int x, int y, int tx, int ty) {
        int srcIndex = indexFor(x, y);
        int dstIndex = indexFor(tx, ty);
        for (int i = 0; i < offset.length; i++) {
            for (int j = 0; j < offset[i].length; j++) {
                if (filter(pointForX(dstIndex), pointForY(dstIndex),red) && srcIndex + offset[i][j] == dstIndex) {
                    //判断腿上是否有子
                    return LegOffset == null || getState(srcIndex + LegOffset[i]) == 0;
                }
            }
        }
        return false;
    }

    @Override
    public List<Move> getAllMove(boolean red, int x, int y) {
        int srcIndex = indexFor(x, y);
        List<Move> moves = new ArrayList<>();
        for (int i = 0; i < offset.length; i++) {
            for (int j = 0; j < offset[i].length; j++) {
                //如果马腿上没有子，且目标不是自己的，就可以走
                int dstIndex = srcIndex + offset[i][j];
                if (filter(pointForX(dstIndex), pointForY(dstIndex),red) && inBoard(dstIndex) && !isSelf(dstIndex, red) && (LegOffset == null || getState(srcIndex + LegOffset[i]) == 0)) {
                    moves.add(new Move(x, y, pointForX(dstIndex), pointForY(dstIndex)));
                }
            }
        }
        return moves;
    }


    protected boolean filter(int x, int y,boolean red) {
        return true;
    }
}
