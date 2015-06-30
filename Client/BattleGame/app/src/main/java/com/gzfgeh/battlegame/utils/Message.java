package com.gzfgeh.battlegame.utils;

import java.io.Serializable;

/**
 * Created by guzhenf on 6/30/2015.
 */
public class Message implements Serializable{
    private String msg;

    public Message(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Message{" +
                "msg='" + msg + '\'' +
                '}';
    }
}
