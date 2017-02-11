package edu.hubu.cnxq.util;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 * @author Brandon B. Lin
 *         2013-1-25
 */
public class MP3Player {

    private byte mp3Bytes[];


    public MP3Player(InputStream in) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            int l = 0;
            byte[] bytes = new byte[1024];
            while ((l = in.read(bytes)) != -1) {
                byteArrayOutputStream.write(bytes, 0, l);
            }
            mp3Bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSync() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                play();
            }
        }).start();
    }


    public Player play() {
        Player player = null;
        try {
            player= new Player(new ByteArrayInputStream(mp3Bytes));
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
             if(player!=null){
                 player.close();
             }
        }
        return player;
    }

    public LoopPlay loopPlay(){
        try {
            LoopPlay loopPlay = new LoopPlay();
            loopPlay.start();
            return loopPlay;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



    public final class LoopPlay extends Thread{


        private boolean play = true;
        private int playCount = 0;
        private Player player;

        public void stopPlay(){
            play = false;
            stop();
        }

        @Override
        public void run() {
            while (true){
                synchronized (this){
                    if(!play){
                        break;
                    }
                }
                try {
                    player = play();
                    synchronized (this){
                        playCount++;
                    }
                } catch (Exception e) {}
            }
        }

        public synchronized int getPlayCount() {
            return playCount;
        }
    }




}