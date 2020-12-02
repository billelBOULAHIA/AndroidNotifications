package com.ailyan.androidnotifications;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button buttonSendPush;
    private Button buttonRegister;
    private EditText editTextEmail;
    private ProgressDialog progressDialog;

    private Button buttonDisplayToken;
    private Button buttonQuizz;
    private TextView textViewToken;
    private String user;

    String token;
    String refreshedToken;

    private String titre;
    private String message;

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTitre() {
        return titre;
    }

    public String getMessage() {
        return message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        token=SharedPrefManager.getInstance(this).getDeviceToken();



      /* MediaPlayer mp = MediaPlayer.create(this,R.raw.v1);
       mp.start();*/

     //   Toast.makeText(MainActivity.this, "Token"+token, Toast.LENGTH_LONG).show();
         if(token!=null) {
            popUpDisplay("Notification","Voulez-vous commencez le jeu ?");
        }

         //  popUpDisplay(titre,message);
      //  Notifications notif=new Notifications();
      //  notif.infoDialog(MainActivity.this,"notification","message");



  /*      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, importance);
            mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mNotificationManager.createNotificationChannel(mChannel);

        }

        MyNotificationManagerTest.getInstance(this).displayNotification("Greetings", "Hello how are you?"); */

        //getting views from xml
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
      //  buttonSendPush = (Button) findViewById(R.id.buttonSendNotification);

        textViewToken = (TextView) findViewById(R.id.textViewToken);

        buttonDisplayToken = (Button) findViewById(R.id.buttonDisplayToken);
        buttonDisplayToken.setOnClickListener(this);

       // buttonQuizz = (Button) findViewById(R.id.diplayQuizz);
      //  buttonQuizz.setOnClickListener(this);

        //adding listener to view
        buttonRegister.setOnClickListener(this);
      //  buttonSendPush.setOnClickListener(this);

  /*
        Button popupButton = findViewById(R.id.buttonPopup);
        popupButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                PopUpClass popUpClass = new PopUpClass();
                popUpClass.showPopupWindow(v);
            }
        });  */

    }

    public void popUpDisplay(String titre,String message){

        AlertDialog.Builder alertDialogBuilder=new AlertDialog.Builder(MainActivity.this);
     //   AlertDialog.Builder alertDialogBuilder= new AlertDialog.Builder(new ContextThemeWrapper(MainActivity.this, android.R.style.Theme_DeviceDefault_Wallpaper));
     //   alertDialogBuilder.setTitle("Notification");
     //   alertDialogBuilder.setMessage("Voulez-vous commencez le jeu ?");

        alertDialogBuilder.setTitle(titre);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setIcon(R.mipmap.logoail);


        alertDialogBuilder.setPositiveButton("Oui", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(MainActivity.this, "jouer le jeu", Toast.LENGTH_LONG).show();
                 // Toast.makeText(MainActivity.this, "Token"+token, Toast.LENGTH_LONG).show();
                if(token!=null){

                   // MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.v1);
                   // mp.start();

                    Intent i=new Intent(MainActivity.this,QuizzActivity.class);
                    startActivity(i);

                }
                else{
                    Toast.makeText(MainActivity.this, "Veuillez vous authentifier", Toast.LENGTH_LONG).show();
                    MediaPlayer mp = MediaPlayer.create(MainActivity.this,R.raw.v1);
                    mp.start();
                }
            }
        });

        alertDialogBuilder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Toast.makeText(MainActivity.this, "refuser de jouer", Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alert=alertDialogBuilder.create();
        alert.show();
        Button positiveButton=alert.getButton(DialogInterface.BUTTON1);
        Button negativeButton=alert.getButton(DialogInterface.BUTTON2);
        positiveButton.setBackgroundColor(Color.GREEN);
       // positiveButton.setPadding(40,10,40,10);

        /* Window viewW=alert.getWindow();
        LinearLayoutCompat.LayoutParams params=new LinearLayoutCompat.LayoutParams(
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                LinearLayoutCompat.LayoutParams.WRAP_CONTENT
        ); */

       // params.setMargins(20,0,0,0);
        positiveButton.getCompoundPaddingLeft();
        positiveButton.setTextSize(40);
        negativeButton.setTextSize(40);
        negativeButton.setBackgroundColor(Color.RED);
        positiveButton.setTextColor(Color.WHITE);
        negativeButton.setTextColor(Color.WHITE);
        alert.getWindow().setLayout(1500,600);
        negativeButton.setPadding(40,0,40,0);
        positiveButton.setPadding(40,0,40,0);
        TextView textViewMessage=alert.findViewById(android.R.id.message);
        textViewMessage.setTextSize(40);
        textViewMessage.setGravity(Gravity.CENTER);
        textViewMessage.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        textViewMessage.setPaddingRelative(10,70,70,80);
       // textViewMessage.setHeight(15);

       /* TextView textViewTitre=alert.findViewById(android.R.id.title);
        textViewTitre.setText("Notification1");
        textViewTitre.setTextSize(40); */
        // textViewTitre.setTextSize(202);



    }

    //storing token to mysql server
    private void sendTokenToServer() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering Device...");
        progressDialog.show();

        final String token = SharedPrefManager.getInstance(this).getDeviceToken();
        final String email = editTextEmail.getText().toString();
        final String etab_id = "31400";

        SharedPrefManager.getInstance(getApplicationContext()).saveEmail(email);

            //   final String name=SharedPrefManager.getInstance(this).getEmail();

           // Log.i("token",token);
          // Toast.makeText(this, "Token not generated"+token, Toast.LENGTH_LONG).show();

        if (token == null) {
             progressDialog.dismiss();
            //   Toast.makeText(this, "Token not generated", Toast.LENGTH_LONG).show();
            return;
        }


        StringRequest stringRequest = new StringRequest(Request.Method.POST, EndPoints.URL_REGISTER_DEVICE,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject obj = new JSONObject(response);
                    Toast.makeText(MainActivity.this, obj.getString("message"), Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("token", token);
                params.put("etab_id", etab_id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    public void onClick(View view) {
        if (view == buttonRegister) {
            sendTokenToServer();
            startActivity(new Intent(this, QuizzActivity.class));
        }

     /*   if (view == buttonQuizz) {
         //   Toast.makeText(this, "Quizz Display", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, QuizzActivity.class));
        }  */

        if (view == buttonDisplayToken) {
            //getting token from shared preferences
             token = SharedPrefManager.getInstance(this).getDeviceToken();
             refreshedToken = FirebaseInstanceId.getInstance().getToken();

              //   Toast.makeText(this, "Token generated :"+refreshedToken, Toast.LENGTH_LONG).show();

           //      Toast.makeText(this, "Name :"+SharedPrefManager.getInstance(this).getEmail(), Toast.LENGTH_LONG).show();
              user= SharedPrefManager.getInstance(this).getEmail();

         //   PopUpClass popUpClass = new PopUpClass();
         //   popUpClass.showPopupWindow(view);
         //   popUpDisplay();
           // FireMissilesDialogFragment dialog=new FireMissilesDialogFragment();
           // dialog.show;



            //if token is not null
            if (token != null) {
                //displaying the token
                textViewToken.setText(token);
            } else {
                //if token is null that means something wrong
                textViewToken.setText("Token not generated");
            }
        }

        //starting send notification activity
        if(view == buttonSendPush){
            //Intent sendNotificationActivity = new Intent(MainActivity.this, ActivitySendPushNotification.class);
           // startActivity(sendNotificationActivity);


          //  Toast toast = Toast.makeText(MainActivity.this, "Hello toast!", Toast.LENGTH_SHORT);
           // toast.show();
             startActivity(new Intent(this, ActivitySendPushNotification.class));
        }
    }
}
