package com.gzfgeh.battlegame.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.gzfgeh.battlegame.R;

import java.util.List;



/**
 * Created by guzhenfu on 15/8/16.
 */
public class GridViewDialog extends Dialog implements AdapterView.OnItemClickListener {
    private Context context;
    private List datas;
    private GirdViewDialogItem item;
    private GridView gridView;

    public GridViewDialog(Context context, List datas) {
        super(context, R.style.dialog_fullscreen);
        setContentView(R.layout.girdview_dialog);

        this.context = context;
        this.datas = datas;
        setProperty();
        gridView = (GridView) this.findViewById(R.id.GridView_toolbar);
        SimpleAdapter adapter = new SimpleAdapter(context, datas, R.layout.grid_view_item,
                new String[] { "itemImage", "itemText" },
                new int[] { R.id.item_image, R.id.item_text });

        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        View view = findViewById(R.id.mainlayout);
        view.getBackground().setAlpha(0);
        view.setBackgroundResource(R.drawable.tool_box_bkg_wood);
        this.setCanceledOnTouchOutside(true);
    }

    private void setProperty() {
        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.alpha = 1.0f;
        w.setAttributes(lp);

    }

    public void setItem(GirdViewDialogItem item) {
        this.item = item;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (item != null)
            item.ItemClickListener(position);

        this.dismiss();
    }

    public interface GirdViewDialogItem {
        void ItemClickListener(int position);
    }

}
