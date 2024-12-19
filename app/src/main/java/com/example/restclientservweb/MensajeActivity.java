package com.example.restclientservweb;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MensajeActivity extends AppCompatActivity {
    private ApiService apiService;
    ListView listViewMensajes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mensaje);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
        GetMensajes();

        Button buttonBackToMenu = findViewById(R.id.btnGoMenu);
        buttonBackToMenu.setOnClickListener(v -> {
            Intent intent = new Intent(MensajeActivity.this, MenuActivity.class);
            startActivity(intent);
        });

        Button butonGetMensajes = findViewById(R.id.btnGetMensajes);
        butonGetMensajes.setOnClickListener(v -> {
            Toast.makeText(MensajeActivity.this, "Obteniendo mensajes...", Toast.LENGTH_SHORT).show();
            GetMensajes();
        });
    }

    private void GetMensajes()
    {
        Call<List<Mensaje>> call = apiService.getPosts();
        call.enqueue(new Callback<List<Mensaje>>() {
            @Override
            public void onResponse(Call<List<Mensaje>> call, Response<List<Mensaje>> response) {
                if (response.isSuccessful()) {
                    List<Mensaje> mensajes = response.body();
                    MensajeAdapter adapter = new MensajeAdapter(MensajeActivity.this, mensajes);
                    listViewMensajes = findViewById(R.id.listViewMensaje);
                    listViewMensajes.setAdapter(adapter);
                }
                else
                    Toast.makeText(MensajeActivity.this, "Error al obtener los mensajes", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<List<Mensaje>> call, Throwable throwable) {
                Toast.makeText(MensajeActivity.this, "Error al obtener respuesta", Toast.LENGTH_SHORT).show();
            }
        });
    }
}