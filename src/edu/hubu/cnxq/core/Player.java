package edu.hubu.cnxq.core;

import java.util.List;

/**
 * 玩家
 */
public interface Player {
    //获得棋盘对象
    ChessBoard getChessBoard();
    //判断玩家是否获胜
    boolean hasWin();
    //获取敌人玩家
    Player getEnemy();
    //获取我的走法
    List<Move> getMyMove();
}
