package edu.hubu.cnxq.walk;


import edu.hubu.cnxq.Constants;
import edu.hubu.cnxq.core.Move;

import java.util.ArrayList;
import java.util.List;


public class PaoWalker extends AbstractWalker implements Constants {



    @Override
    public List<Move> getAllMove(boolean red, int x, int y) {
        List<Move> moves = new ArrayList<>();
        boolean cross = false;
        for(int i=x-1;inBoard(i,y);i--){
            int state = getState(i,y);
            if(state == EMPTY ){
                if(!cross) moves.add(new Move(x,y,i,y));
            }else if(cross){
                if(isEnemy(i,y,red)) moves.add(new Move(x,y,i,y));
                break;
            }else{
                cross = true;
            }
        }
        cross = false;
        for(int i=x+1;inBoard(i,y);i++){
            int state = getState(i,y);
            if(state == EMPTY ){
                if(!cross) moves.add(new Move(x,y,i,y));
            }else if(cross){
                if(isEnemy(i,y,red)) moves.add(new Move(x,y,i,y));
                break;
            }else{
                cross = true;
            }
        }
        cross = false;
        for(int i=y-1;inBoard(x,i);i--){
            int state = getState(x,i);
            if(state == EMPTY){
                if(!cross) moves.add(new Move(x,y,x,i));
            }else if(cross){
                if(isEnemy(x,i,red)) moves.add(new Move(x,y,x,i));
                break;
            }else{
                cross = true;
            }
        }
        cross = false;
        for(int i=y+1;inBoard(x,i);i++){
            int state = getState(x,i);
            if(state == EMPTY){
                if(!cross) moves.add(new Move(x,y,x,i));
            }else if(cross){
                if(isEnemy(x,i,red)) moves.add(new Move(x,y,x,i));
                break;
            }else{
                cross = true;
            }
        }
        return moves;
    }

    @Override
    protected boolean canMove(boolean red, int x, int y, int tx, int ty) {
        int s = getState(tx, ty);
        if (s == EMPTY) {
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
        if (x == tx) {
            int p = Math.min(y, ty);
            int q = Math.max(y, ty);
            int sum = 0;
            for (int i = p + 1; i < q; i++) {
                if (getState(x, i) != EMPTY) {
                    sum++;
                }
                if(sum > 1){
                    return false;
                }
            }
            return sum == 1;
        }
        if (y == ty) {
            int p = Math.min(x, tx);
            int q = Math.max(x, tx);
            int sum = 0;
            for (int i = p + 1; i < q; i++) {
                if (getState(i, y) != EMPTY) {
                    sum++;
                }
                if(sum > 1){
                    return false;
                }
            }
            return sum == 1;
        }
        return false;
    }
}
