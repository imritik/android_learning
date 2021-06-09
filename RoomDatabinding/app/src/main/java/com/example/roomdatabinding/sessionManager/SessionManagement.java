package com.example.roomdatabinding.sessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.roomdatabinding.login.LoginActivity;

import java.util.HashMap;

public class SessionManagement {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context _context;

    int PRIVATE_MODE=0;

    private  static final String PREF_NAME="AndroidPref";
    private static final String IS_LOGIN="IsLoggedIn";
    public static final String KEY_NAME="name";
    public static final String KEY_EMAIL = "email";

    public SessionManagement(Context context){
        this._context=context;
        pref=_context.getSharedPreferences(PREF_NAME,PRIVATE_MODE);
        editor=pref.edit();
    }

    public void createLoginSession(String name,String email){
        editor.putBoolean(IS_LOGIN,true);
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN,false);
    }

    public void checkLogin(){
        if(!this.isLoggedIn()){
            //user is not logged in ,redirect to login activity
            Intent i=new Intent(_context,LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            _context.startActivity(i);
        }
    }

    //Get stored data
    public HashMap<String,String> getUserDetails(){
        HashMap<String,String> user= new HashMap<String, String>();
        user.put(KEY_NAME,pref.getString(KEY_NAME,null));
        user.put(KEY_EMAIL,pref.getString(KEY_EMAIL,null));
        return user;
    }

    //Clear session details
    public void logoutUser(){
        editor.clear();
        editor.commit();

        //after logout, redirect to login activity
        Intent i=new Intent(_context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(i);
    }
}
