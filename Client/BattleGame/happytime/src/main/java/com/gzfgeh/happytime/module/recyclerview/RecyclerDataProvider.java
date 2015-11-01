package com.gzfgeh.happytime.module.recyclerview;

import java.util.ArrayList;

/**
 * Created by guzhenfu on 15/10/31.
 */
public class RecyclerDataProvider {
    public static String[] titles = new String[]{
            "1",
            "2",
            "3",
            "4",
            "5",
    };

    public static String[] urls = new String[]{//640*360 360/640=0.5625
            "http://cdn.duitang.com/uploads/item/201311/20/20131120213622_mJCUy.thumb.600_0.gif",//伪装者:胡歌演绎"痞子特工"
            "http://cdn.duitang.com/uploads/item/201311/20/20131120213622_mJCUy.thumb.600_0.gif",//无心法师:生死离别!月牙遭虐杀
            "http://cdn.duitang.com/uploads/item/201311/20/20131120213622_mJCUy.thumb.600_0.gif",//花千骨:尊上沦为花千骨
            "http://cdn.duitang.com/uploads/item/201311/20/20131120213622_mJCUy.thumb.600_0.gif",//综艺饭:胖轩偷看夏天洗澡掀波澜
            "http://cdn.duitang.com/uploads/item/201311/20/20131120213622_mJCUy.thumb.600_0.gif",//碟中谍4:阿汤哥高塔命悬一线,超越不可能
    };

    public static ArrayList<RecyclerViewItem> getList() {
        ArrayList<RecyclerViewItem> list = new ArrayList<>();
        for (int i = 0; i < urls.length; i++) {
            RecyclerViewItem item = new RecyclerViewItem();
            item.mGifViewUrl = urls[i];
            item.mName = titles[i];
            list.add(item);
        }

        return list;
    }

}
