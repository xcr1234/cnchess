package edu.hubu.cnxq;

import javax.swing.*;
import java.awt.*;

/**
 * 游戏主程序
 */
public class ChessGame {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (Exception e){}

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                ChessWindow chessWindow = new ChessWindow();
                chessWindow.setVisible(true);
            }
        });
    }
}
