package com.example.atividadesqlite.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.example.atividadesqlite.Model.ListData;
import com.example.atividadesqlite.R;
import com.example.atividadesqlite.controller.ListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewValuesActivity extends AppCompatActivity {
    //Criação dos componentes
    ListView listViewPerson;
    ListAdapter listAdapter;
    ArrayList<ListData> dataArrayList = new ArrayList<>();
    Button rollback, nameSearch;
    DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_values);


        //Botão de voltar
        rollback = findViewById(R.id.rollback_button);
        nameSearch = findViewById(R.id.filter_button);
        databaseReference = FirebaseDatabase.getInstance().getReference("victim");

        //Atribuição da listview
        listViewPerson = findViewById(R.id.listViewPerson);


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot sampleSnapshot: dataSnapshot.getChildren()) {
                    String name = sampleSnapshot.child("name").getValue().toString();
                    String doc = sampleSnapshot.child("doc").getValue().toString();
                    String feature = sampleSnapshot.child("feature").getValue().toString();
                    String condition = sampleSnapshot.child("condition").getValue().toString();
                    String disaster_id = sampleSnapshot.child("disaster_id").getValue().toString();
                    String identification_status = sampleSnapshot.child("identification_status").getValue().toString();
                    String rescue_date = sampleSnapshot.child("rescue_date").getValue().toString();
                    String id = sampleSnapshot.child("id").getValue().toString();
                    String doc_img = sampleSnapshot.child("doc_img").getValue().toString();
                    ListData item = new ListData(name, doc, condition, disaster_id, feature, id, identification_status, rescue_date, doc_img);
                    dataArrayList.add(item);
                }
                //Criação e consumo do adapter
                listAdapter = new ListAdapter(ViewValuesActivity.this, dataArrayList);
                listViewPerson.setAdapter(listAdapter);
                Log.d("firebase", dataArrayList.toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("firebase", "erro");
                throw databaseError.toException(); // don't ignore errors
            }
        });




        listViewPerson.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent nav = new Intent(getApplicationContext(), PersonInfoActivity.class);
//                startActivity(nav);
                Toast.makeText(ViewValuesActivity.this, "BANIDO!", Toast.LENGTH_LONG).show();
            }
        });



        nameSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav = new Intent(getApplicationContext(), PersonInfoActivity.class);
                startActivity(nav);

            }
        });


        //Botão para voltar as telas
        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    FirebaseAuth.getInstance().signOut();
                    finish();
                }catch (Error err){
                    Toast.makeText(ViewValuesActivity.this, "BANIDO!", Toast.LENGTH_LONG).show();
                }

            }
        });


    }
}
