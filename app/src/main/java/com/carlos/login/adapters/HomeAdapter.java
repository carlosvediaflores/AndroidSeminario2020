package com.carlos.login.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.carlos.login.R;

import java.util.ArrayList;

public class HomeAdapter extends BaseAdapter {
    private ArrayList<StructListxxx> LISTDATA;
    private Context context;
    public HomeAdapter(ArrayList<StructListxxx>data, Context context){
        LISTDATA = data;
        this.context = context;

    }
    @Override
    public int getCount() {
        return LISTDATA.size();
    }

    @Override
    public Object getItem(int i) {
        return LISTDATA.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i ;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view= LayoutInflater.from(context).inflate(R.layout.item_listxxx, null);
            TextView title= view.findViewById(R.id.title);
            title.setText(LISTDATA.get(i).getTitle());
            TextView albun= view.findViewById(R.id.subtitle);
            ImageView imageView = view.findViewById(R.id.albun);
            albun.setText(LISTDATA.get(i).getTitle());
            if (LISTDATA.get(i).getImage().equals("No IMAGE")){
                Glide.with(context).load("https://www.scdn.co/i/_global/open-graph-default.png").centerCrop().into(imageView);
            }
        }
        return view;
    }
}
