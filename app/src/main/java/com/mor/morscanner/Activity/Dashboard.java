package com.mor.morscanner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mor.morscanner.R;
import com.mor.morscanner.Utils.SessionManager;
import com.mor.morscanner.databinding.ActivityDashboardBinding;

public class Dashboard extends AppCompatActivity {

    ActivityDashboardBinding binding;

    Dashboard objDashboard;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        objDashboard = this;
        sessionManager = new SessionManager(objDashboard);

        setAction();



    }

    private void setAction() {


        binding.cardAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent imain = new Intent(objDashboard, MainActivity.class);
                imain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                imain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(imain);

            }
        });

        binding.cardSummery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent imain = new Intent(objDashboard, OrderSummery.class);
                imain.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                imain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(imain);

            }
        });

        binding.ivLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sessionManager.clearSessionData();

                Intent iLogin = new Intent(objDashboard, Login.class);
                iLogin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(iLogin);
                finish();

            }
        });
    }
}