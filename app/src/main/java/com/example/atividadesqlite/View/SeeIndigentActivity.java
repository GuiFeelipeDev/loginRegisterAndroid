package com.example.atividadesqlite.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.atividadesqlite.Model.DisasterData;
import com.example.atividadesqlite.Model.IndigentData;
import com.example.atividadesqlite.R;
import com.example.atividadesqlite.controller.IndigentAdapter;
import com.example.atividadesqlite.controller.ListAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SeeIndigentActivity extends AppCompatActivity {
    Spinner filterIndigent;
    DatabaseReference databaseReference;
    ArrayList<DisasterData> disasterList = new ArrayList<>();
    ArrayList<IndigentData> indigentList = new ArrayList<>();
    ArrayList<String> spinnerDisasters = new ArrayList<>();
    Button rollback, infoButton, filterButton;
    IndigentAdapter listAdapter;
    ListView listViewIndigent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_indigent);
        filterIndigent = findViewById(R.id.seeIndigentSpinner);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        rollback = findViewById(R.id.seeIndigentRollback);
        infoButton = findViewById(R.id.infoButton);
        filterButton = findViewById(R.id.seeIndigentFilter);
        listViewIndigent = findViewById(R.id.listViewIndigent);
        databaseReference.child("disasters").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                disasterList.clear();
                spinnerDisasters.clear();
                for (DataSnapshot sampleSnapshot: snapshot.getChildren()){
                    String disasterId = sampleSnapshot.child("uuid").getValue().toString();
                    String disasterType = sampleSnapshot.child("type").getValue().toString();
                    String disasterLocal = sampleSnapshot.child("local").getValue().toString();
                    DisasterData disaster = new DisasterData(disasterId, disasterType, disasterLocal);
                    disasterList.add(disaster);
                    spinnerDisasters.add(disasterType + " - " + disasterLocal);
                }
                String Arr[];
                Arr = spinnerDisasters.toArray(new String[spinnerDisasters.size()]);
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SeeIndigentActivity.this, android.R.layout.simple_spinner_item, Arr);
                filterIndigent.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SeeIndigentActivity.this, "Ocorreu um erro!", Toast.LENGTH_LONG).show();
                return;
            }
        });

        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav = new Intent(SeeIndigentActivity.this, InfoActivity.class);
                startActivity(nav);
            }
        });

        listAdapter = new IndigentAdapter(SeeIndigentActivity.this, indigentList);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseReference.child("indigent").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        indigentList.clear();
                        listAdapter.clear();
                        Long disasterPos = filterIndigent.getSelectedItemId();
                        Integer position = Integer.valueOf(disasterPos.intValue());
                        String disasterIdSelected = disasterList.get(position).getUuid();
                        for (DataSnapshot sampleSnapshot: snapshot.getChildren()){
                            String uuid = sampleSnapshot.child("uuid").getValue().toString();
                            String gender = sampleSnapshot.child("gender").getValue().toString();
                            String rescueDate = sampleSnapshot.child("rescueDate").getValue().toString();
                            String features = sampleSnapshot.child("features").getValue().toString();
                            String disasterId = sampleSnapshot.child("disasterId").getValue().toString();


                            if(disasterId.equals(disasterIdSelected)){
                                IndigentData data = new IndigentData(features, gender, disasterId, uuid, rescueDate);

                                indigentList.add(data);
                            }
                        }

                        if(listAdapter.isEmpty()) {
                            Toast.makeText(SeeIndigentActivity.this, "Nenhum indigente nesse desastre!", Toast.LENGTH_LONG).show();
                            return;
                        }
                        listViewIndigent.setAdapter(listAdapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    return;
                    }
                });

                return;
            }
        });
    }
}