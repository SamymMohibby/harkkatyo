package com.example.harkkatyprojekti;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    //  TODO: tee se toiminta, joka mahdollista nähää kuntien väkiluku, video tutoriaali siitä löytyyy
    // TODO: tästä linkistä    https://www.youtube.com/watch?v=Y2M1m12a-ks   . Sitten kun oot kattonut ton-
    // TODO: niin tämän linkin avulla, näet miten se voidaan implementoida android studiolle , https://www.youtube.com/watch?v=ElyOB3Wa0dg"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    public void switchToTabactivity(View view) {
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
    }





}