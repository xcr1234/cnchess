package edu.hubu.cnxq.walk;

/**
 * 马的走法
 */
public class MaWalker extends SlashWalker{
    public MaWalker(){
        super(
                new int[][]{{-33, -31}, {-18, 14}, {-14, 18}, {31, 33}},
                new int[]{-16, -1, 1, 16}
                );
    }
}
