package com.example.atividadesqlite.controller;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.atividadesqlite.Model.ListData;
import com.example.atividadesqlite.R;
import com.example.atividadesqlite.View.PersonInfoActivity;
import com.example.atividadesqlite.View.ViewValuesActivity;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListData> {
    public ListAdapter(@NonNull Context context, ArrayList<ListData> dataArrayList) {
        super(context, R.layout.list_item, dataArrayList);
    }
    //Criação do adapter no modelo a ser exibido
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ListData listData = getItem(position);


        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        TextView name = view.findViewById(R.id.victim_name);
        TextView doc = view.findViewById(R.id.victim_doc);
        TextView feature = view.findViewById(R.id.victim_feature);
        TextView disaster = view.findViewById(R.id.disaster);
        ImageView imageView = view.findViewById(R.id.listImage);
        Glide.with(getContext()).load(listData.doc_img).into(imageView);

        name.setText(listData.name);
        doc.setText(listData.doc);
        feature.setText(listData.feature);
        disaster.setText(listData.disaster_id);



        return view;
    }
}
