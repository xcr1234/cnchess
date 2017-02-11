package edu.hubu.cnxq.walk;

import edu.hubu.cnxq.core.Move;

import java.util.List;

/**
 * 士的走法
 */
public class ShiWalker extends SlashWalker {
    public ShiWalker() {
        super(
                new int[][]{{17}, {15}, {-15}, {-17}},
                null
        );
    }

    @Override
    protected boolean filter(int x, int y, boolean red) {
        return isInNine(x, y, red);
    }
}
