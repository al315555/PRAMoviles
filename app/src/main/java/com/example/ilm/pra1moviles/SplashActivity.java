package com.example.ilm.pra1moviles;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        Intent intent;
        boolean registered = sharedPref.getBoolean("registered", false);
        if (!registered) {
            intent = new Intent(this, LoginActivity.class);

        } else {
            //intent = new Intent(this, MainActivity.class);
            intent = new Intent(this, MainDrawerActivity.class);
            //intent = new Intent(this, ProductListActivity.class);
            String username = sharedPref.getString("username", "user");
            intent.putExtra("LoggedName", username);
        }
        startActivity(intent);
        finish();
    }
}
