package com.example.lab5_aiot;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import com.example.lab5_aiot.databinding.ActivityMainBinding;
import com.example.lab5_aiot.entitites.Usuario;
import com.firebase.ui.auth.AuthMethodPickerLayout;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    DatabaseReference db;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db = FirebaseDatabase.getInstance().getReference();
        Intent intent = new Intent(this, RegistroActivity.class);
        Intent intentDoctores = new Intent(this, DoctoresActivity.class);

        //Boton de Login with GOOGLE
        binding.buttonGoogle.setOnClickListener(view -> {

            Intent intent2 = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(Arrays.asList(
                            new AuthUI.IdpConfig.GoogleBuilder().build()
                    )).build();

            signInLauncher.launch(intent2);
        });

        //Boton de Registro
        binding.textRegistro.setOnClickListener(view -> {
            startActivity(intent);
        });

        //Boton Iniciar sesion
        binding.buttonIngresar.setOnClickListener(view -> {
            //Validaciones de campos
            if(TextUtils.isEmpty(binding.editTextTextPersonName.getText().toString().trim())){
                Toast.makeText(this, "Ingresar Correo", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(binding.editTextTextPersonName.getText().toString())) {
                Toast.makeText(this, "Ingresar Contraseña", Toast.LENGTH_SHORT).show();
            }else{
                //Ingresar a la APP
                db.child("usuarios").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        Boolean valido = false;
                        for(DataSnapshot snapshot : datasnapshot.getChildren()){
                            Usuario usuario = snapshot.getValue(Usuario.class);
                            if(binding.editTextTextPersonName.getText().toString().equals(usuario.getCorreo())){
                                if(binding.editTextTextPassword.getText().toString().equals(usuario.getContrasenha())){
                                    startActivity(intentDoctores);
                                    valido = true;
                                    finish();
                                    break;
                                }
                            }
                        }
                        if(valido == false){
                            Toast.makeText(MainActivity.this, "Error en correo o contraseña", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                if (result.getResultCode() == RESULT_OK) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    Log.d("msg-test", "Firebase uid: " + user.getUid());
                    Intent intent = new Intent(this, DoctoresActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Log.d("msg-test", "Canceló el Log-in");
                }
            }
    );
}