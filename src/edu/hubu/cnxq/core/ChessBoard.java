package edu.hubu.cnxq.core;

/**
 * 表示整个棋盘信息的接口
 */
public interface ChessBoard extends Iterable<ChessBoardItem> {
    //获取最大横坐标、纵坐标 （ 0 <= x <maxX, 0<= y < maxY )
    int getMaxX();

    int getMaxY();

    //获得指定位置处的棋子状态码；比如在五子棋中，棋子状态可能是黑棋、白棋、或者空白
    int getState(int x, int y);

    //设置状态码
    int setState(int x, int y, int state);

    //初始化棋盘
    void clear();
    void setDefault();

    boolean inBoard(int x,int y);

    //获得将帅的坐标
    Point getJiang();
    Point getShuai();



}
