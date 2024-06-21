package com.example.atividadesqlite.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.atividadesqlite.Model.DisasterData;
import com.example.atividadesqlite.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class CadDisaster extends AppCompatActivity {

    EditText cadType, cadLocal;
    Button rollback, submitDisaster;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_disaster);
        rollback = findViewById(R.id.disasterRollback);
        submitDisaster = findViewById(R.id.submitDisaster);
        cadType = findViewById(R.id.cadDisasterType);
        cadLocal = findViewById(R.id.cadDisasterLocal);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        submitDisaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = cadType.getText().toString();
                String local = cadLocal.getText().toString();
                if(type.isEmpty() || local.isEmpty()){
                    Toast.makeText(CadDisaster.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                    return;
                }

                cadDisaster(local, type);
            }
        });

        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void cadDisaster(String local, String type) {
        UUID uuid = UUID.randomUUID();
        DisasterData data = new DisasterData(uuid.toString(), type, local);
        databaseReference.child("disasters").child(uuid.toString()).setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                cadLocal.setText("");
                cadType.setText("");
                Toast.makeText(CadDisaster.this, "Desastre cadastrado com sucesso!", Toast.LENGTH_LONG).show();
                return;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CadDisaster.this, "Falha ao adicinar desastre, tente novamente!", Toast.LENGTH_LONG).show();
                return;
            }
        });
    }
}