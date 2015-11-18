package com.fostergem;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fostergem.home.HomeActivity;
import com.fostergem.services.Constants;
import com.fostergem.services.LoginObjects;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;

/**
 * Created by Neelkamal on 02/11/15.
 */
public class LoginActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button login = (Button) findViewById(R.id.loginbutton);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Do something in response to button
                EditText email = (EditText) findViewById(R.id.user_email);
                EditText password = (EditText) findViewById(R.id.user_password);
                //Toast.makeText(LoginActivity.this, email.getText().toString(), Toast.LENGTH_LONG).show();
                //Toast.makeText(LoginActivity.this, password.getText().toString(), Toast.LENGTH_LONG).show();
                FosterGemService obj = Constants.retrofit.create(FosterGemService.class);
                HashMap<String, String> user = new HashMap<String, String>();
                user.put("EmailAddress",email.getText().toString());
                user.put("Password",password.getText().toString());
                obj.authenticateUser(user, new Callback<LoginObjects>() {
                    @Override
                    public void success(LoginObjects loginObjects, Response response) {
                        if(loginObjects.Status.equals("Success")) {
                            SharedPreferences sharedpreferences;
                            sharedpreferences = getSharedPreferences(Constants.mypreferecne, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString("fname", loginObjects.fname);
                            editor.putString("lname", loginObjects.lname);
                            editor.putString("profile_id", loginObjects.profile_id);
                            editor.putString("access_key", loginObjects.access_key);
                            editor.putString("secret_key", loginObjects.secret_key);
                            editor.putString("gender", loginObjects.gender);
                            editor.putString("profile_pic", loginObjects.profile_pic);

                            editor.commit();
                            Toast.makeText(LoginActivity.this, loginObjects.profile_id, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, loginObjects.Message, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    public interface FosterGemService {
        @POST("/login")
        public void authenticateUser(@Body HashMap<String, String> map,Callback<LoginObjects> callback);
    }


}
