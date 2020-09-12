package com.carlos.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.carlos.login.utils.EndPoints;
import com.carlos.login.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    Button loginButton, registerButton;
    static final int code_camera=999;
    private MainActivity root= this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        loadComponents();
    }

    private void loadComponents() {
        loginButton = this.findViewById(R.id.login);
        registerButton = this.findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent registerActivity= new Intent(root, RegisterUser.class);
                root.startActivity(registerActivity);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ENVIO AL API
                AsyncHttpClient client = new AsyncHttpClient();

                EditText email = root.findViewById(R.id.email_txt);
                EditText pass = root.findViewById(R.id.password_txt);

                RequestParams params = new RequestParams();
                params.add("nick", email.getText().toString());
                params.add("password", pass.getText().toString());

                    client.post(EndPoints.LOGIN_SERVICE, params, new JsonHttpResponseHandler(){
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                            try {
                                if (response.has("msn")){
                                    UserDataServer.MSM = response.getString("msn");
                                }
                                if (response.has("token")){
                                    UserDataServer.TOKEN = response.getString("token");
                                }
                                if (UserDataServer.TOKEN.length()>150){
                                    Intent intent = new Intent(root,MainDaschActivity.class);
                                    root.startActivity(intent);
                                }
                                else {
                                    Toast.makeText(root, response.getString("msn"), Toast.LENGTH_LONG).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
              /*  Intent intent = new Intent(root,MainDaschActivity.class);
                root.startActivity(intent);*/
                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("content://contacts/people/"));
                root.startActivity(intent);*/
                /*Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:67936855"));
                root.startActivity(intent);*/
                /*Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY,"Intents en andriod");
                root.startActivity(intent);*/
                //Intent intent = new Intent(Intent.ACTION_VIEW);
                //intent.setData(Uri.parse("https://www.twitch.tv"));
                //intent.putExtra(SearchManager.QUERY,"Intents en andriod");
                //root.startActivity(intent);
                /*Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if(camera.resolveActivity(root.getPackageManager())!= null){
                    root.startActivityForResult(camera, code_camera);
                }*/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==code_camera && resultCode == RESULT_OK){
            Bundle photo = data.getExtras();
            Bitmap imageBitmap = (Bitmap) photo.get("data");
            ImageView img = root.findViewById(R.id.imageView);
            img.setImageBitmap(imageBitmap);
        }



    }
}