package com.ailyan.androidnotifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;


/**
 * Created by Ravi on 31/03/15.
 */

public class MyNotificationManager {

    public static final int ID_BIG_NOTIFICATION = 234;
    public static final int ID_SMALL_NOTIFICATION = 235;

    private Context mCtx;

    public MyNotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    //the method will show a big notification with an image
    //parameters are title for message title, message for message text, url of the big image and an intent that will open
    //when you will tap on the notification
    public void showBigNotification(String title, String message, String url, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_BIG_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        bigPictureStyle.setBigContentTitle(title);
        bigPictureStyle.setSummaryText(Html.fromHtml(message).toString());
        bigPictureStyle.bigPicture(getBitmapFromURL(url));
       // Notification.Builder
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);

      //  Notification.Builder mBuilder = new Notification.Builder(mCtx);
        Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.logoail).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                 .setStyle(bigPictureStyle)
                .setSmallIcon(R.mipmap.logoail)
               // .setLargeIcon(bigPictureStyle.bigPicture(getBitmapFromURL(url))))
             //   .setLargeIcon(R.mipmap.ic_launcher)
                .setContentText(message)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_BIG_NOTIFICATION, notification);
    }

    //the method will show a small notification
    //parameters are title for message title, message for message text and an intent that will open
    //when you will tap on the notification
    public void showSmallNotification(String title, String message, Intent intent) {
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;

      //  Notification.Builder mBuilder = new Notification.Builder(mCtx);
      //  Notification notification;
        notification = mBuilder.setSmallIcon(R.mipmap.logoail).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setContentText(message)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        NotificationManager notificationManager = (NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
    }

    //The method will return Bitmap from an image URL
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void displayNotification(String title, String body) {

    //    Intent resultIntent = new Intent(mCtx, MainActivity.class);
    //    PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(mCtx, Constants.CHANNEL_ID)
                            .setSmallIcon(R.mipmap.logoail)
                            .setContentTitle(title)
                            .setContentText(body);
                             //.setLargeIcon(BitmapFactory.decodeResource(getRessources(),R.mipmap.logoail));


            /*
             *  Clicking on the notification will take us to this intent
             *  Right now we are using the MainActivity as this is the only activity we have in our application
             *  But for your project you can customize it as you want
             * */

            Intent resultIntent = new Intent(mCtx, MainActivity.class);

            /*
             *  Now we will create a pending intent
             *  The method getActivity is taking 4 parameters
             *  All paramters are describing themselves
             *  0 is the request code (the second parameter)
             *  We can detect this code in the activity that will open by this we can get
             *  Which notification opened the activity
             * */
            PendingIntent pendingIntent = PendingIntent.getActivity(mCtx, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            /*
             *  Setting the pending intent to notification builder
             * */

            mBuilder.setContentIntent(pendingIntent);

            NotificationManager mNotifyMgr =
                    (NotificationManager) mCtx.getSystemService(NOTIFICATION_SERVICE);


            /*
             * The first parameter is the notification id
             * better don't give a literal here (right now we are giving a int literal)
             * because using this id we can modify it later
             * */
            if (mNotifyMgr != null) {
                mNotifyMgr.notify(1, mBuilder.build());

        }

    //    Notifications notif=new Notifications();
    //    notif.infoDialog(mCtx,title,body);

    }
}
