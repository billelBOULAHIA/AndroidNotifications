package com.ailyan.androidnotifications;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class QuizzActivity extends Activity {

    private WebView mywebView;
    private String name;


    // @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        //  MediaPlayer mp = MediaPlayer.create(QuizzActivity.this,R.raw.v1);
        //  mp.start();
        requestWindowFeature ( Window.FEATURE_NO_TITLE );//for full screen
        getWindow ().setFlags ( WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView ( R.layout.activity_quizz );
        mywebView =(WebView)findViewById ( R.id.webView );

        // Get Email from Shared Preference
        name=SharedPrefManager.getInstance(this).getEmail();

        WebSettings webSettings = mywebView.getSettings ();
        webSettings.setJavaScriptEnabled ( true );
        mywebView.loadUrl ( "https://www.ailyan.fr/Quizz/etab_ailyan/Dashboard/Jeux/QuizzB/?user="+name);
        mywebView.setWebViewClient ( new WebViewClient() );//prevents our url to open in another browser
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setLoadsImagesAutomatically(true);
        mywebView.clearCache(true);
        mywebView.clearHistory();
        mywebView.clearFormData();

        // webSettings.setCacheMode(webSettings.load);
        //  mywebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);

        // mywebView.clearCache(false);  // <-- DO THIS FIRST
        // mywebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

       /* try {
            Method m = WebSettings.class.getMethod("setMixedContentMode", int.class);
            if ( m == null ) {
                Log.e("WebSettings", "Error getting setMixedContentMode method");
            }
            else {
                m.invoke( mywebView.getSettings(), 2); // 2 = MIXED_CONTENT_COMPATIBILITY_MODE
                Log.i("WebSettings", "Successfully set MIXED_CONTENT_COMPATIBILITY_MODE");
            }
        }
        catch (Exception ex) {
            Log.e("WebSettings", "Error calling setMixedContentMode: " + ex.getMessage(), ex);
        } */

    }

    @Override
    public void onBackPressed() {
        if(mywebView.canGoBack ()){
            mywebView.goBack ();
        }else{
            super.onBackPressed ();

        }

    }
}