package com.ailyan.androidnotifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.net.Uri;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("NEW_TOKEN",s);
        SharedPrefManager.getInstance(getApplicationContext()).saveDeviceToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data Payload: " + remoteMessage.getData().toString());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
                Log.e(TAG, "Exception: " + e.getMessage());
            }
        }
    }

    //this method will display the notification
    //We are passing the JSONObject that is received from
    //firebase cloud messaging
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log
        Log.e(TAG, "Notification JSON " + json.toString());
        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //parsing json data
            String title = data.getString("title");
            String message = data.getString("message");
            String imageUrl = data.getString("image");
           // String imageUrl= "https://ailyan.fr/upload/1344253554";

            //creating MyNotificationManager object
            MyNotificationManager mNotificationManager = new MyNotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);

            //if there is no image

            if(imageUrl.equals("null")){
                //displaying small notification
                mNotificationManager.showSmallNotification(title, message, intent);
            }else{
                //if there is an image
                //displaying a big notification
                mNotificationManager.showBigNotification(title, message, imageUrl, intent);
            }

          //  mNotificationManager.showBigNotification(title, message, imageUrl, intent);


            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager mNotificationManager1 =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME,importance);
                mChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
                mChannel.enableLights(true);
                mChannel.setLightColor(Color.RED);

           //     mChannel.enableVibration(true);
         //       mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});


                // Generate Sound For Notifications
                Uri soundUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://"+ getApplicationContext().getPackageName() + "/" + R.raw.v1);
          //      mChannel.setSound(null,null);
                AudioAttributes audioAttributes = new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .build();
                mChannel.setSound(soundUri, audioAttributes);
                mNotificationManager1.createNotificationChannel(mChannel);
            }
      //      Intent notificationIntent =new Intent(getApplicationContext(),MainActivity.class);

            MyNotificationManagerTest.getInstance(getApplicationContext()).displayNotification(title, message);


         /*   Notifications notif=new Notifications();
            notif.infoDialog(getApplicationContext(),title,message);  */

         //   MainActivity mainActivity= new MainActivity();
         //   Notifications notif=new Notifications();
         //  notif.infoDialog(getApplicationContext(),title,message);


        } catch (JSONException e) {
            Log.e(TAG, "Json Exception: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
    }



}