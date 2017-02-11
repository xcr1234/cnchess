package edu.hubu.cnxq.util;

import edu.hubu.cnxq.core.ChessBoard;

import java.io.Serializable;

/**
 * 象棋残局
 */
public class GameSave implements Serializable{
    private static final long serialVersionUID = 6553095750557437058L;
    private ChessBoard chessBoard;
    private boolean red;
    private boolean playing;

    public GameSave(ChessBoard chessBoard, boolean red, boolean hasWin) {
        this.chessBoard = chessBoard;
        this.red = red;
        this.playing = hasWin;
    }

    public ChessBoard getChessBoard() {
        return chessBoard;
    }

    public void setChessBoard(ChessBoard chessBoard) {
        this.chessBoard = chessBoard;
    }

    public boolean isRed() {
        return red;
    }

    public void setRed(boolean red) {
        this.red = red;
    }

    public boolean isPlaying() {
        return playing;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }
}
