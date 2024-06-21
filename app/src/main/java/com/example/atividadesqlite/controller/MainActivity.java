package com.example.atividadesqlite.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atividadesqlite.R;
import com.example.atividadesqlite.View.CadActivity;
import com.example.atividadesqlite.View.PersonInfoActivity;
import com.example.atividadesqlite.View.ViewValuesActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    //Criação dos componentes
    EditText name, pass;
    Button login, cad_page;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Atribuição dos componentes
        name = findViewById(R.id.login_name);
        pass = findViewById(R.id.login_pass);
        login = findViewById(R.id.submit_button);
        cad_page = findViewById(R.id.cad_button);
        mAuth = FirebaseAuth.getInstance();


        //Botão de login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Coleta dos dados dos campos
                String loginMail = name.getText().toString();
                String loginPass = pass.getText().toString();
                //Tenta logar
               try{
                   mAuth.signInWithEmailAndPassword(loginMail, loginPass)
                           .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                               @Override
                               public void onComplete(@NonNull Task<AuthResult> task) {
                                   if (task.isSuccessful()) {
                                       FirebaseUser currentUser = mAuth.getCurrentUser();
                                       if(currentUser != null){
                                           if(currentUser.getUid().equals("yz73JcIYUaVQOqMsfU18Nc3ue062")){
                                               Intent nav = new Intent(MainActivity.this, ViewValuesActivity.class);
                                               startActivity(nav);
                                           }else{
                                               Intent nav = new Intent(MainActivity.this, PersonInfoActivity.class);
                                               startActivity(nav);
                                           }

                                       }
                                   } else {
                                       // Se falhar da um toas avisando.
                                       Toast.makeText(MainActivity.this, "Usuário ou senha incorretos.",
                                               Toast.LENGTH_SHORT).show();
                                   }
                               }
                           });
               }catch(OutOfMemoryError err){
                   Toast.makeText(MainActivity.this, "Falha, tente novamente!",
                           Toast.LENGTH_SHORT).show();
               }
               }

        });

        cad_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nav = new Intent(MainActivity.this, CadActivity.class);
                startActivity(nav);
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        // Verifica se o usuário ja logou e o redireciona.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            if(currentUser.getUid().equals("yz73JcIYUaVQOqMsfU18Nc3ue062")){
                Intent nav = new Intent(MainActivity.this, ViewValuesActivity.class);
                startActivity(nav);
            }else{
                Intent nav = new Intent(MainActivity.this, PersonInfoActivity.class);
                startActivity(nav);
            }

        }


    }
}