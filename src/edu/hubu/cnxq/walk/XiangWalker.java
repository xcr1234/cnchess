package edu.hubu.cnxq.walk;


public class XiangWalker extends SlashWalker {
    public XiangWalker() {
        super(
                new int[][]{{-34}, {-30}, {30}, {34}},
                new int[]{-17, -15, 15, 17}
        );
    }

    @Override
    protected boolean filter(int x, int y, boolean red) {
        return !isCrossRiver(x, y, red);
    }
}
