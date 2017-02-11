package edu.hubu.cnxq.core;

import java.util.List;

/**
 * 判断走法的可行性
 */
public interface WalkState {
    //得到棋盘对象
    ChessBoard getChessBoard();
    //当前的玩家是红旗还是黑棋，true表示红旗
    //判断能否移动
    boolean canMove(boolean red,Move move);
    //判断能否选中.
    boolean canSelect(boolean red,int x,int y);
    //生成一颗棋子的所有走法
    List<Move> getAllMove(boolean red,int x,int y);
    //判断是否将军
    WinEnum hasWin(boolean red);
}
