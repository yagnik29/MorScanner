package com.mor.morscanner.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mor.morscanner.Model.GetLoginResponse.GetLoginResponse;
import com.mor.morscanner.Model.GetLoginResponse.SetLoginJson;
import com.mor.morscanner.R;
import com.mor.morscanner.Utils.SessionManager;
import com.mor.morscanner.Utils.Utils;
import com.mor.morscanner.Webservice.ApiHandler;
import com.mor.morscanner.databinding.ActivityLoginBinding;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class Login extends AppCompatActivity {

    ActivityLoginBinding binding;

    Login objLogin;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        objLogin = this;

        sessionManager = new SessionManager(objLogin);

        findView();
        setaction();

    }

    private void findView() {




    }

    private void setaction() {


        binding.edtPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (TextUtils.isEmpty(charSequence)) {
                    binding.edtPassword.setErrorEnabled(true);
                    binding.edtPassword.setError("Please Enter Password");
                } else {
                    binding.edtPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        binding.edtUserName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (TextUtils.isEmpty(charSequence)) {
                    binding.edtUserName.setErrorEnabled(true);
                    binding.edtUserName.setError("Please Enter Username");
                } else {
                    binding.edtUserName.setErrorEnabled(false);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String strUsername = null, strPassword = null;
                strUsername = binding.edtUserName.getEditText().getText().toString().trim();

                strPassword = binding.edtPassword.getEditText().getText().toString().trim();

                if (TextUtils.isEmpty(strUsername)) {
                    binding.edtUserName.setErrorEnabled(true);
                    binding.edtUserName.setError("Please Enter Username");

                } else if (TextUtils.isEmpty(strPassword)) {
                    binding.edtPassword.setErrorEnabled(true);
                    binding.edtPassword.setError("Please Enter Password");

                } else {

                    login(strUsername, strPassword);
                    Timber.e("str %s  %s", strUsername, strPassword);
                    Log.e("Test", strUsername + strPassword);

                }


            }
        });


    }

    private void login(String strUsername, String strPassword) {

        if (Utils.getInternetConnection(objLogin)) {

            Utils.showProgressDialoug(objLogin);


            ApiHandler.getApiService().getLoginResponse(new SetLoginJson(strUsername, strPassword))
                    .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<GetLoginResponse>() {
                        @Override
                        public void onNext(GetLoginResponse getLoginResponse) {
                            Utils.dismissDialoug();

                            if (!getLoginResponse.getMessage().equalsIgnoreCase("success")) {

                                Toast.makeText(objLogin, getLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();


                            } else if (getLoginResponse.getMessage().equalsIgnoreCase("success")) {

                                sessionManager.createSession(getLoginResponse);

                                Timber.tag("ApiKey").e( sessionManager.getKeyUserid().toString());


                                Intent i_Home = new Intent(objLogin, Dashboard.class);
                                i_Home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(i_Home);
                                finish();


                            } else {


                                Toast.makeText(objLogin, getLoginResponse.getMessage(), Toast.LENGTH_SHORT).show();

                            }


                        }

                        @Override
                        public void onError(@NotNull Throwable e) {

                            Utils.dismissDialoug();
                            Toast.makeText(objLogin, e.getMessage(), Toast.LENGTH_SHORT).show();

                            e.printStackTrace();

                        }

                        @Override
                        public void onComplete() {
                            Utils.dismissDialoug();
                        }
                    });


        } else {

            Toast.makeText(objLogin, getResources().getString(R.string.internet_alert), Toast.LENGTH_SHORT).show();
        }


    }

}