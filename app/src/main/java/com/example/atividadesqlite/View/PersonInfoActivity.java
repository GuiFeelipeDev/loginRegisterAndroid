package com.example.atividadesqlite.View;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.atividadesqlite.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonInfoActivity extends AppCompatActivity {
    SearchView searchView;
    DatabaseReference databaseReference;
    LinearLayout linearLayout;
    TextView personName, personDoc, personStatus, personName2, personDoc2, personFindDate, personFeature;
    Button rollback;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        searchView = findViewById(R.id.find_by_name);
        rollback = findViewById(R.id.rollback_person);

        databaseReference = FirebaseDatabase.getInstance().getReference("victim");
        linearLayout = findViewById(R.id.infoCard);
        personName = findViewById(R.id.personName);
        personDoc = findViewById(R.id.personDocument);
        personStatus = findViewById(R.id.personStatus);
        personName2 = findViewById(R.id.personName2);
        personDoc2 = findViewById(R.id.personDoc2);
        personFindDate = findViewById(R.id.personFindDate);
        personFeature = findViewById(R.id.personFeature);
        imageView = findViewById(R.id.documentImage);


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != "") {
                    getData(query);
                }else{
                    Toast.makeText(PersonInfoActivity.this, "Insira um nome!", Toast.LENGTH_LONG).show();
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }
        });

        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void getData(String query) {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DataSnapshot sampleSnapshot = snapshot.child(query);
                if(sampleSnapshot.getValue() == null){
                    Toast.makeText(PersonInfoActivity.this, "Pessoa näo encontrada!", Toast.LENGTH_LONG).show();
                    return;
                }
                String name = sampleSnapshot.child("name").getValue().toString();
                String doc = sampleSnapshot.child("doc").getValue().toString();
                String feature = sampleSnapshot.child("feature").getValue().toString();
                String identification_status = sampleSnapshot.child("identification_status").getValue().toString();
                String rescue_date = sampleSnapshot.child("rescue_date").getValue().toString();
                String doc_img = sampleSnapshot.child("doc_img").getValue().toString();
                Toast.makeText(PersonInfoActivity.this, "Encontrado!", Toast.LENGTH_LONG).show();
                personName.setText(name);
                personDoc.setText(doc);
                personFeature.setText(feature);
                personStatus.setText(identification_status);
                personDoc2.setText(doc);
                personFindDate.setText(rescue_date);
                personName2.setText(name);
                Glide.with(PersonInfoActivity.this).load(doc_img).into(imageView);
                linearLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(PersonInfoActivity.this, "Pessoa näo encontrada!", Toast.LENGTH_LONG).show();
            }


        });


    }
}