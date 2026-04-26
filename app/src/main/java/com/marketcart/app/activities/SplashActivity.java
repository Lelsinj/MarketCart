package com.marketcart.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.marketcart.app.R;

public class SplashActivity extends AppCompatActivity {

    // Tempo de exibição da splash em milissegundos
    private static final int SPLASH_DELAY = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Após 2 segundos, navega para a tela principal
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Fecha a Splash para não voltar a ela com o botão "Voltar"
        }, SPLASH_DELAY);
    }
}
