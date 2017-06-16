package com.example.prachi.movieapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.prachi.movieapp.Network.ApiCLient;
import com.example.prachi.movieapp.Network.ApiInterface;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    String request_token;
    EditText username;
    EditText password;
    String session_id="";
    Intent i=new Intent();
    HashMap<String,String> data=new HashMap<>();
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ProgressDialog progressdialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sp =getSharedPreferences("MovieApp",MODE_PRIVATE);
        editor=sp.edit();
        if(sp.getString("session_id","")!=""){
            Intent i=new Intent();
            i.setClass(this,startingactivity.class);
            i.putExtra("sessionID",sp.getString("session_id",""));
            startActivity(i);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        ImageView logo = (ImageView) findViewById(R.id.logo);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.animation);
        fadeIn.setRepeatMode(Animation.RESTART);
        //fadeIn.setRepeatCount(2);
        logo.startAnimation(fadeIn);
        Button login=(Button) findViewById(R.id.login);
        Button signup=(Button)findViewById(R.id.signup);
        TextView skip=(TextView) findViewById(R.id.skip);
        skip.setPaintFlags(skip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.setClass(LoginActivity.this,startingactivity.class);
                i.putExtra("sessionID",sp.getString("session_id",""));
                startActivity(i);
            }
        });
        LinearLayout layout=(LinearLayout) findViewById(R.id.linearlayout);
        layout.startAnimation(fadeIn);
      //  login.startAnimation(AnimationUtils.loadAnimation(this,android.R.anim.fade_in));
        //signup.startAnimation(AnimationUtils.loadAnimation(this,R.anim.button_pop));
          username=(EditText) findViewById(R.id.username);
         password=(EditText) findViewById(R.id.password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               progressdialog=new ProgressDialog(LoginActivity.this);
                progressdialog.setMessage("loging in");
                progressdialog.show();
                gettoken();
               ;
            }


        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signupintent=new Intent(Intent.ACTION_VIEW).setData(Uri.parse("https://www.themoviedb.org/account/signup"));
                startActivity(signupintent);
            }
        });
    }

    private void gettoken() {
        ApiInterface apiinterface = ApiCLient.getApiinterface();
        Call<requestToken> call = apiinterface.getRequestoken();
        call.enqueue(new Callback<requestToken>() {
            @Override
            public void onResponse(Call<requestToken> call, Response<requestToken> response) {
                if (response.isSuccessful()) {
                    request_token = response.body().getRequest_token();
                    Log.i("request_token", request_token);
                }
                data.put("username", username.getText().toString());
                data.put("password",password.getText().toString());
                data.put("request_token", request_token);
                getvalidation();
            }


            @Override
            public void onFailure(Call<requestToken> call, Throwable t) {

            }
        });
    }

    private void getvalidation() {

        ApiInterface apiinterface = ApiCLient.getApiinterface();
        final Call<RequestValidation> requestvalidationcall = apiinterface.validaterequesttoken(data);
        requestvalidationcall.enqueue(new Callback<RequestValidation>() {
            @Override
            public void onResponse(Call<RequestValidation> call, Response<RequestValidation> response) {

                if (response.isSuccessful()) {
                    request_token=response.body().getRequest_token();
                    Log.i("validation","success");
                }
                getsessionId();

            }

            @Override
            public void onFailure(Call<RequestValidation> call, Throwable t) {
                Log.i("validtionresponse", t.getMessage());
            }
        });
    }

    private void getsessionId() {
        ApiInterface apiinterface=ApiCLient.getApiinterface();
        Call<sessionId> call=apiinterface.getsessionId(request_token);
        call.enqueue(new Callback<sessionId>() {
            @Override
            public void onResponse(Call<sessionId> call, Response<sessionId> response) {
                if(response.isSuccessful()){
                    session_id=response.body().getSession_id();
                    Log.i("sessionId",session_id);
                    editor.putString("session_id",session_id);
                    editor.commit();
                    i.setClass(LoginActivity.this,startingactivity.class);
                    i.putExtra("sessionID",session_id);
                    progressdialog.cancel();
                    startActivity(i);
                }

            }

            @Override
            public void onFailure(Call<sessionId> call, Throwable t) {

            }
        });
    }

}
