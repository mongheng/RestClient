package com.earmongheng.restclient.adapterlistview;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.earmongheng.restclient.R;

/**
 * Created by earmongheng on 5/13/2016.
 */
public class AdapterListViewHouse {

    private Context context;
    private View view;
    private ListView listView;

    public AdapterListViewHouse(Context context, View view, ListView listView) {
        this.context = context;
        this.view = view;
        this.listView = listView;
    }

    public void viewImage() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "Position:" + parent.getItemIdAtPosition(position), Toast.LENGTH_LONG).show();
                String price = (String)((TextView)view.findViewById(R.id.txtPrice)).getText();
                Toast.makeText(context, "Price : " + price, Toast.LENGTH_LONG).show();
            }
        });
    }
}
