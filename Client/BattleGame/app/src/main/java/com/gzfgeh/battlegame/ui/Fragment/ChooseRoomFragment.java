package com.gzfgeh.battlegame.ui.Fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.gzfgeh.battlegame.R;

/**
 * Created by guzhenf on 7/12/2015.
 */
public class ChooseRoomFragment extends Fragment {
    private TextView tvUser;
    private Button btnCreateRoom;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.choose_room, container,false);
        return rootView;
    }
}
