package com.example.androidapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidapi.interfaces.ProductoAPI;
import com.example.androidapi.models.Categoria;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private EditText txtCodigo;
    private TextView txtNombre;
    private Button btnBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtCodigo=findViewById(R.id.txtCodigo);
        txtNombre=findViewById(R.id.lbNombre);
        btnBuscar=findViewById(R.id.btnBuscar);

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                find(txtCodigo.getText().toString().trim());
            }
        });
    }

    private void find(String codigo){
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://tienda.mercadona.es/api/v1_1/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        ProductoAPI productoAPI = retrofit.create(ProductoAPI.class);
        Call<Categoria> call = productoAPI.find(Integer.parseInt(codigo));
        call.enqueue(new Callback<Categoria>() {
            @Override
            public void onResponse(Call<Categoria> call, Response<Categoria> response) {
                try{

                    if(response.isSuccessful()){
                        Categoria c = new Categoria();
                        c.setName("empty");
                        c = response.body();
                        txtNombre.setText(c.getName());
                    }

                }catch (Exception ex){
                    Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categoria> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}