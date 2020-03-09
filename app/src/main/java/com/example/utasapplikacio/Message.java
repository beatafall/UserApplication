package com.example.utasapplikacio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Message extends AppCompatActivity {

    Button btn_lerobbanas, btn_lopas, btn_baleset, btn_forgalom, btn_tulzsofoltsag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        btn_baleset = findViewById(R.id.baleset);
        btn_forgalom = findViewById(R.id.forgalom);
        btn_lerobbanas = findViewById(R.id.lerobbanas);
        btn_lopas = findViewById(R.id.lopas);
        btn_tulzsofoltsag = findViewById(R.id.tulzsufoltsag);

            btn_baleset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MessageForm.class);
                    intent.putExtra("uzenet", "baleset");
                    startActivity(intent);
                }
            });

            btn_tulzsofoltsag.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MessageForm.class);
                    intent.putExtra("uzenet", "tulzsufoltsag a buszon");
                    startActivity(intent);
                }
            });

            btn_lopas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MessageForm.class);
                    intent.putExtra("uzenet", "lopas");
                    startActivity(intent);
                }
            });

            btn_lerobbanas.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MessageForm.class);
                    intent.putExtra("uzenet", "lerobbanas");
                    startActivity(intent);
                }
            });

            btn_forgalom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MessageForm.class);
                    intent.putExtra("uzenet", "forgalom");
                    startActivity(intent);
                }
            });

    }
}
