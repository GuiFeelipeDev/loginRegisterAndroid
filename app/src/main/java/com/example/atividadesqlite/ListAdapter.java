package com.example.atividadesqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.atividadesqlite.Model.ListData;

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

        name.setText(listData.name);
        doc.setText(listData.doc);
        feature.setText(listData.feature);
        disaster.setText(listData.disaster_id);



        return view;
    }
}
