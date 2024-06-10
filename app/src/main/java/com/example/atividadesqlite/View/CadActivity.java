package com.example.atividadesqlite.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atividadesqlite.Model.UserData;
import com.example.atividadesqlite.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class CadActivity extends AppCompatActivity {

    Button rollback, btnRegister;

    EditText cad_name, cad_phone, cad_cpf, cad_email, cad_password;

    FirebaseAuth mAuth;

    DatabaseReference databaseReference;

    int userIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad);

        rollback = findViewById(R.id.cad_rollback_button);
        btnRegister = findViewById(R.id.submit_cad_button);

        cad_name = findViewById(R.id.cad_name);
        cad_phone = findViewById(R.id.cad_phone);
        cad_cpf = findViewById(R.id.cad_cpf);
        cad_email = findViewById(R.id.cad_email);
        cad_password = findViewById(R.id.cad_pass);



        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("userData");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userIndex = (int) snapshot.getChildrenCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CadActivity.this, "Algo deu errado!", Toast.LENGTH_LONG).show();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = cad_name.getText().toString();
                String phone = cad_phone.getText().toString();
                String cpf = cad_cpf.getText().toString();
                String email = cad_email.getText().toString();
                String password = cad_password.getText().toString();

                if (email.isEmpty() || password.isEmpty() || name.isEmpty() || phone.isEmpty() || cpf.isEmpty()) {
                    Toast.makeText(CadActivity.this, "Preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else {
                    cadUsers();
                }
            }


            private void cadUsers() {
                String name = cad_name.getText().toString();
                String phone = cad_phone.getText().toString();
                String cpf = cad_cpf.getText().toString();
                String email = cad_email.getText().toString();
                String password = cad_password.getText().toString();



              try {
                  mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task) {
                          if (task.isSuccessful()) {
                              Toast.makeText(CadActivity.this, "CADASTRADO COM SUCESSO!!!", Toast.LENGTH_LONG).show();
                              String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                              final int index = userIndex + 1;

                              UserData userData = new UserData("user"+index, name, phone, cpf, uid);

                              databaseReference.child("user"+index).setValue(userData);

                              finish();
                          }

                          if(task.isCanceled()){
                              Toast.makeText(CadActivity.this, "BANIDO!", Toast.LENGTH_LONG).show();
                          }
                      }
                  });
              }catch (Error err){
                  Toast.makeText(CadActivity.this, "Falha ao realizar cadastro!", Toast.LENGTH_LONG).show();
              }

            }
        });
        rollback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}