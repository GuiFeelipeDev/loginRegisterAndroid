package com.example.atividadesqlite.controller;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.atividadesqlite.Model.ListData;
import com.example.atividadesqlite.R;
import com.example.atividadesqlite.View.PersonInfoActivity;
import com.example.atividadesqlite.View.ViewValuesActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListAdapter extends ArrayAdapter<ListData> {
    DatabaseReference databaseReference;
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
        Button contactId = view.findViewById(R.id.contactId);
        Glide.with(getContext()).load(listData.doc_img).into(imageView);
        databaseReference = FirebaseDatabase.getInstance().getReference("userData").child(listData.identifiedBy);
        getData(listData, feature, disaster, contactId);




        Log.d("IDENTIFIED BY", listData.identifiedBy);

        name.setText(listData.name);
        doc.setText(listData.doc);
        feature.setText(listData.identification_status);




        return view;
    }

    private void getData(ListData listData, TextView feature, TextView disaster, Button contactId) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot sampleSnapshot = snapshot.child("name");


                if(sampleSnapshot.getValue() != null){
                    disaster.setText(sampleSnapshot.getValue().toString());
                }
                if(listData.identification_status.equals("Identificado")){
                    ViewGroup.LayoutParams params = contactId.getLayoutParams();
                    params.height = 120;
                    feature.setTextColor(Color.rgb(0, 128, 0));
                    contactId.setVisibility(View.VISIBLE);
                    contactId.setLayoutParams(params);
                } else {
                    feature.setTextColor(Color.rgb(128, 0, 0));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
               return;
            }


        });



    }
}
