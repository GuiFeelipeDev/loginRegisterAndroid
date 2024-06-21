package com.example.atividadesqlite.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.atividadesqlite.Model.ListData;
import com.example.atividadesqlite.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class CadVictim extends AppCompatActivity {

    CheckBox indigent;
    EditText victimName, victimDoc, victimImage, victimFeatures, victimDate;
    LinearLayout fieldsLayout;
    Button rollback, submit, cadDisaster;
    Spinner victimGender, victimDisasters;
    DatabaseReference databaseReference;
    ArrayList<DisasterData> disasterList = new ArrayList<>();
    ArrayList<String> spinnerDisasters = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_victim);
        indigent = findViewById(R.id.cadVictimIndigent);
        victimName = findViewById(R.id.cadVictimName);
        victimDoc = findViewById(R.id.cadVictimDoc);
        victimImage = findViewById(R.id.cadVictimImageLink);
        victimDate = findViewById(R.id.cadVictimDate);
        fieldsLayout = findViewById(R.id.fieldsVictim);
        rollback = findViewById(R.id.victimRollback);
        submit = findViewById(R.id.submitVictimBtn);
        victimFeatures = findViewById(R.id.cadVictimFeature);
        victimGender = findViewById(R.id.cadVictimGender);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        victimDisasters = findViewById(R.id.cadVictimDisaster);
        cadDisaster = findViewById(R.id.cadDisaster);


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
                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CadVictim.this, android.R.layout.simple_spinner_item, Arr);
                victimDisasters.setAdapter(spinnerArrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CadVictim.this, "Ocorreu um erro!", Toast.LENGTH_LONG).show();
                return;
            }
        });


        indigent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup.LayoutParams params = fieldsLayout.getLayoutParams();
                if(indigent.isChecked()){
                    params.height = 0;
                    fieldsLayout.setVisibility(View.INVISIBLE);
                    fieldsLayout.setLayoutParams(params);
                }else {
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    fieldsLayout.setVisibility(View.VISIBLE);
                    fieldsLayout.setLayoutParams(params);

                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = victimName.getText().toString();
                String doc = victimDoc.getText().toString();
                String imgUrl = victimImage.getText().toString();
                String features = victimFeatures.getText().toString();
                String gender = victimGender.getSelectedItem().toString();
                String rescueDate = victimDate.getText().toString();
                Long disasterPos = victimDisasters.getSelectedItemId();
                Integer position = Integer.valueOf(disasterPos.intValue());
                String disasterId = disasterList.get(position).getUuid();
                Boolean isIndigent = indigent.isChecked();

                if(isIndigent && !features.isEmpty() && !gender.isEmpty() && !rescueDate.isEmpty()){
                    cadIndigent(features, gender, disasterId, rescueDate);
                    return;
                }

                if(name.isEmpty() || doc.isEmpty() || imgUrl.isEmpty() || features.isEmpty() || gender.isEmpty()){
                    Toast.makeText(CadVictim.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                    return;
                }


                cadVictim(name, doc, disasterId, features, doc.replaceAll("[\\s\\-()]", ""), rescueDate, imgUrl);

            }
        });

        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        cadDisaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav = new Intent(CadVictim.this, CadDisaster.class);
                startActivity(nav);
            }
        });

    }
    public void cadIndigent(String features, String gender, String disasterId, String rescueDate) {
        UUID uuid = UUID.randomUUID();
        IndigentData data = new IndigentData(features, gender, disasterId, uuid.toString(), rescueDate);
        databaseReference.child("indigent").child(uuid.toString()).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                victimFeatures.setText("");
                victimDate.setText("");
                victimGender.setSelection(0);
                Toast.makeText(CadVictim.this, "Indigente adicionado com sucesso!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadVictim.this, "Falha ao adicionar indigente, tente novamente!", Toast.LENGTH_LONG).show();
            }
        });
        return;
    };
    public void cadVictim(String name, String doc, String disasterId, String feature, String id, String rescueDate, String img) {
        ListData data = new ListData(name, doc, "Obito", disasterId, feature, id, "Não identificado", rescueDate, img, "");
        databaseReference.child("victim").child(id).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                victimFeatures.setText("");
                victimDoc.setText("");
                victimDate.setText("");
                victimName.setText("");
                victimImage.setText("");
                victimGender.setSelection(0);
                Toast.makeText(CadVictim.this, "Vitima adicionado com sucesso!", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadVictim.this, "Falha ao adicionar vitima!", Toast.LENGTH_LONG).show();
            }
        });
        return;
    };
}