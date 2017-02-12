package edu.hubu.cnxq;


import edu.hubu.cnxq.core.ChessBoardItem;
import edu.hubu.cnxq.core.WinEnum;
import edu.hubu.cnxq.core.Move;
import edu.hubu.cnxq.util.GameSave;
import edu.hubu.cnxq.util.MP3Player;
import edu.hubu.cnxq.util.MyOptionPane;
import edu.hubu.cnxq.util.Resource;

import edu.hubu.cnxq.core.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * 象棋游戏的界面，提供如下功能：
 * A、棋盘的绘制
 * B、棋子的绘制、事件的触发；走棋后，事件传递给GameState去处理
 * C、获胜后的UI
 */
public class ChessPanel extends JPanel implements Constants {
    private static final long serialVersionUID = -2643934396670394106L;

    private BufferedImage image; //panel的image,所有的画图操作都在这个image上进行，然后调用repaint方法重会。

    private boolean red = true;

    private boolean playing; //游戏是否正在进行


    private XqChessBoard chessBoard = new XqChessBoard();
    private XqWalkState walkState = new XqWalkState(chessBoard);


    public ChessPanel() {
        super();
        setSize(558, 620);

        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //添加鼠标事件
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    int x = floorDiv(e.getX() - x0, lineHeight);
                    int y = floorDiv(e.getY() - y0, lineHeight);
                    if (x >= 0 && x <= 8 && y >= 0 && y <= 9) {
                        onClick(x, y);
                    }
                }
            }
        });
        gameStartFirst();

    }


    //四舍五入除法，在涉及到点击点时，使用四舍五入能有效提高用户体验！
    private static int floorDiv(int x, int y) {
        float div = x * 1f / y;
        int z = (int) div; //整数部分
        float point = div - z; //小数部分
        return point < 0.5 ? z : z + 1;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    private static final BufferedImage imageChessBoard = Resource.getImage("main.gif");

    //把背景图片画在棋盘上
    private Graphics2D resetBackground() {
        image = new BufferedImage(imageChessBoard.getWidth(), imageChessBoard.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Graphics2D g = image.createGraphics();
        g.drawImage(imageChessBoard, 0, 0, null);
        return g;
    }


    /**
     * 在指定位置处画棋子
     *
     * @param num 棋子编号，取Constants中的常量
     * @param x   横坐标，取值0-8
     * @param y   纵坐标，取值0-9
     */
    private void drawChess(Graphics g, int num, int x, int y) {
        int px = x0 + x * lineHeight - chessSize / 2;
        int py = y0 + y * lineHeight - chessSize / 2;
        Image image = null;
        switch (num) {
            case redJiang:
                image = redJiangImg;
                break;
            case redShi:
                image = redShiImg;
                break;
            case redXiang:
                image = redXiangImg;
                break;
            case redMa:
                image = redMaImg;
                break;
            case redChe:
                image = redCheImg;
                break;
            case redPao:
                image = redPaoImg;
                break;
            case redZu:
                image = redZuImg;
                break;
            case blackJiang:
                image = blackJiangImg;
                break;
            case blackShi:
                image = blackShiImg;
                break;
            case blackXiang:
                image = blackXiangImg;
                break;
            case blackMa:
                image = blackMaImg;
                break;
            case blackChe:
                image = blackCheImg;
                break;
            case blackPao:
                image = blackPaoImg;
                break;
            case blackZu:
                image = blackZuImg;
                break;
            case EMPTY:
                break;
            case select:
                image = selectedImg;
                break;
            default:
                throw new IllegalStateException();

        }
        g.drawImage(image, px, py, null);
    }

    private void drawSelect(Graphics g, int x, int y) {
        int px = x0 + x * lineHeight - chessSize / 2;
        int py = y0 + y * lineHeight - chessSize / 2;
        g.drawImage(selectedImg, px, py, null);
    }

    private Point selectPoint; //选中的棋子


    //事件

    /**
     * 当用户在棋盘上点击时，触发
     *
     * @param x 横坐标，取值0-8
     * @param y 纵坐标，取值0-9
     */
    private void onClick(int x, int y) {
        if (!playing) {
            return;
        }
        if (walkState.canSelect(red, x, y)) { //如果可以选中这个棋子的话，则选中之
            Graphics2D g = resetBackground();
            drawChessBoard(g);
            drawSelect(g, x, y);
            g.dispose();
            repaint();
            selectPoint = new Point(x, y);
        } else if (selectPoint != null) {    //走棋了
            int tx = selectPoint.getX();
            int ty = selectPoint.getY();
            Move move = new Move(tx, ty, x, y);
            if (walkState.canMove(red, move)) { //判断可否走棋
                int state = chessBoard.getState(tx, ty);
                chessBoard.setState(tx, ty, EMPTY);
                int old = chessBoard.setState(x, y, state); //更新棋盘上的状态
                repaintBoard();
                selectPoint = null;
                hasWin(old);
                red = !red;
            } else {
                System.out.println("不可走棋:" + move);
            }
        }
    }

    private void hasWin(int state){
        if(state == redJiang){
            playing = false;
            MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "黑胜！", "提示");
        }
        if(state == blackJiang){
            playing = false;
            MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "红胜！", "提示");
        }
        WinEnum winEnum = walkState.hasWin(red);
        switch (winEnum){
            case RED:
                if(red){
                    playing = false;
                    MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "黑胜！", "提示");
                }else{
                    System.out.println("红棋被将军！");
                }
                break;
            case BLACK:
                if(!red){
                    playing = false;
                    MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "红胜！", "提示");
                }else{
                    System.out.println("黑棋被将军！");
                }
                break;
            case RED_KILL:
                playing = false;
                MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "绝杀！黑胜！", "提示");
                break;
            case BLACK_KILL:
                playing = false;
                MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "绝杀！红胜！", "提示");
                break;
            case DRAW:
                playing = false;
                MyOptionPane.showMessageDialog(JOptionPane.getFrameForComponent(this), "和棋！", "提示");
                break;
        }
    }




    private void repaintBoard(){
        Graphics2D g = resetBackground();
        drawChessBoard(g);
        g.dispose();
        repaint();
    }

    private void drawChessBoard(Graphics2D g) {
        for (ChessBoardItem chessBoardItem : chessBoard) {
            drawChess(g, chessBoardItem.getState(), chessBoardItem.getX(), chessBoardItem.getY());
        }
    }


    //游戏开局（先手）
    public void gameStartFirst() {
        playing = true;
        red = true;
        chessBoard.clear();
        chessBoard.setDefault();
        Graphics2D g = resetBackground();
        drawChessBoard(g);
        g.dispose();
    }

    //游戏开局 后手
    public void gameStartLast(){
        gameStartFirst();
        red = false;
    }

    //保存棋局
    public void save(OutputStream outputStream) throws Exception{
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        GameSave gameSave = new GameSave(chessBoard,red,playing);
        objectOutputStream.writeObject(gameSave);
    }

    //加载棋局
    public void load(InputStream inputStream) throws Exception{
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        GameSave gameSave = (GameSave) objectInputStream.readObject();
        chessBoard = (XqChessBoard) gameSave.getChessBoard();
        red = gameSave.isRed();
        playing = gameSave.isPlaying();
        selectPoint = null;
        walkState.setChessBoard(chessBoard);
        repaintBoard();
    }





    //声音资源:
    private static final MP3Player jjPlayer = new MP3Player(Resource.getStream("ding.mp3"));


    //资源图片：

    private static final BufferedImage redCheImg = Resource.getImage("红车.GIF");
    private static final BufferedImage redJiangImg = Resource.getImage("红将.gif");
    private static final BufferedImage redMaImg = Resource.getImage("红马.gif");
    private static final BufferedImage redPaoImg = Resource.getImage("红炮.gif");
    private static final BufferedImage redShiImg = Resource.getImage("红士.gif");
    private static final BufferedImage redXiangImg = Resource.getImage("红象.gif");
    private static final BufferedImage redZuImg = Resource.getImage("红卒.gif");
    private static final BufferedImage blackCheImg = Resource.getImage("黑车.gif");
    private static final BufferedImage blackJiangImg = Resource.getImage("黑将.gif");
    private static final BufferedImage blackMaImg = Resource.getImage("黑马.gif");
    private static final BufferedImage blackPaoImg = Resource.getImage("黑炮.gif");
    private static final BufferedImage blackShiImg = Resource.getImage("黑士.gif");
    private static final BufferedImage blackXiangImg = Resource.getImage("黑象.gif");
    private static final BufferedImage blackZuImg = Resource.getImage("黑卒.gif");

    private static final BufferedImage selectedImg = Resource.getImage("select.gif");


}
