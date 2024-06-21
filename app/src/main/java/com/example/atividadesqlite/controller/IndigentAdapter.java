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
import com.example.atividadesqlite.Model.IndigentData;
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

public class IndigentAdapter extends ArrayAdapter<IndigentData> {
    DatabaseReference databaseReference;
    public IndigentAdapter(@NonNull Context context, ArrayList<IndigentData> dataArrayList) {
        super(context, R.layout.list_indigent, dataArrayList);
    }
    //Criação do adapter no modelo a ser exibido
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        IndigentData listData = getItem(position);


        if(view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.list_indigent, parent, false);
        }
        TextView gender = view.findViewById(R.id.seeIndigentGender);
        TextView date = view.findViewById(R.id.seeIndigentDate);
        TextView features = view.findViewById(R.id.seeIndigentFeatures);

        gender.setText(listData.getGender());
        date.setText(listData.getRescueDate());
        features.setText(listData.getFeatures());
        return view;
    }
    }

