package edu.hubu.cnxq.walk;


import edu.hubu.cnxq.Constants;
import edu.hubu.cnxq.core.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * 车的走法
 */
public class CheWalker extends AbstractWalker implements Constants {
    @Override
    public List<Move> getAllMove(boolean red, int x, int y) {
        List<Move> moves = new ArrayList<>();
        //从4个方向所有情况判断一遍.
        for(int i=x-1;inBoard(i,y);i--){
            int state = getState(i,y);
            if(state == EMPTY){
                moves.add(new Move(x,y,i,y));
            }else if(isSelf(i,y,red)){
                break;
            }else{
                moves.add(new Move(x,y,i,y));
                break;
            }
        }
        for(int i=x+1;inBoard(i,y);i++){
            int state = getState(i,y);
            if(state == EMPTY){
                moves.add(new Move(x,y,i,y));
            }else if(isSelf(i,y,red)){
                break;
            }else{
                moves.add(new Move(x,y,i,y));
                break;
            }
        }
        for(int i=y-1;inBoard(x,i);i--){
            int state = getState(x,i);
            if(state==EMPTY){
                moves.add(new Move(x,y,x,i));
            }else if(isSelf(x,i,red)){
                break;
            }else{
                moves.add(new Move(x,y,x,i));
                break;
            }
        }
        for(int i=y+1;inBoard(x,i);i++){
            int state = getState(x,i);
            if(state==EMPTY){
                moves.add(new Move(x,y,x,i));
            }else if(isSelf(x,i,red)){
                break;
            }else{
                moves.add(new Move(x,y,x,i));
                break;
            }
        }
        return moves;
    }

    @Override
    protected boolean canMove(boolean red, int x, int y, int tx, int ty) {
        if (x == tx) {
            int p = Math.min(y, ty);
            int q = Math.max(y, ty);
            for (int i = p + 1; i < q; i++) {
                int state = getState(x, i);
                if (state != EMPTY) {
                    return false;
                }
            }
            return true;
        }
        if (y == ty) {
            int p = Math.min(x, tx);
            int q = Math.max(x, tx);
            for (int i = p + 1; i < q; i++) {
                int state = getState(i, y);
                if (state != EMPTY) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
