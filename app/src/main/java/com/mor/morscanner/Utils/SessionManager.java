package com.mor.morscanner.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.mor.morscanner.Constant.Constant;
import com.mor.morscanner.Model.GetLoginResponse.GetLoginResponse;

public class SessionManager {

    private SharedPreferences sharedPreferences;


    private Context context;


    private static final String KEY_USERNAME = "username";
    private static final String KEY_USERID = "userid";

    public SessionManager(Context context) {

        this.context = context;
        sharedPreferences = context.getSharedPreferences(Constant.PREFERENCE_NAME, Context.MODE_PRIVATE);


    }

    public void createSession(GetLoginResponse getLoginResponse) {

        sharedPreferences.edit().putString(KEY_USERNAME, getLoginResponse.getUserID()).apply();
        sharedPreferences.edit().putInt(KEY_USERID, getLoginResponse.getId()).apply();

    }

    public void clearSessionData() {
        this.sharedPreferences.edit().clear().commit();
    }


    public void setKeyUserid(Integer userId) {
        sharedPreferences.edit().putInt(KEY_USERID, userId).apply();
    }

    public Integer getKeyUserid() {
        return sharedPreferences.getInt(KEY_USERID, 0);

    }


    public void setKeyUsername(String username) {
        sharedPreferences.edit().putString(KEY_USERNAME, username).apply();
    }

    public String getKeyUsername() {
        return sharedPreferences.getString(KEY_USERNAME, "");

    }
}
