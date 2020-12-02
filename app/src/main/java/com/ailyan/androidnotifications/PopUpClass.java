package com.ailyan.androidnotifications;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class PopUpClass extends Activity {

    private Context controller;
    public void setController(Context cnts){ this.controller = cnts;}
    //PopupWindow display method

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_example);
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }


    /* private final View.OnClickListener mainListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          finish();
            Intent i=new Intent(PopUpClass.this,QuizzActivity.class);
            startActivity(i);
        }
    }; */

    @SuppressLint("ResourceType")
    public void showPopupWindow(final View view) {


        //Create a View object yourself through inflater
        final LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.popup_example, null);

        //Specify the length and width through constants
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;

        //Make Inactive Items Outside Of PopupWindow
        boolean focusable = true;

        //Create a window with our parameters
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        //Set the location of the window on the screen
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        //Initialize the elements of our window, install the handler

        TextView test2 = popupView.findViewById(R.id.titleText);
        //   test2.setText(R.id.titleText);

        Button buttonEdit = popupView.findViewById(R.id.messageButton);
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //As an example, display the message
                Toast.makeText(view.getContext(), "Accepter la partie", Toast.LENGTH_SHORT).show();
                // startActivity(new Intent(MainActivity.this, QuizzActivity.class));
                setController(PopUpClass.this);
                Intent i=new Intent(PopUpClass.this,QuizzActivity.class);
                controller.startActivity(i);

                //  popupWindow.getContentView()
                // inflater.inflate(R.layout.activity_quizz, null);
                //  Context.start

            }
        });
        //  buttonEdit.setOnClickListener(mainListener);


        Button buttonRefuser = popupView.findViewById(R.id.refuser);
        buttonRefuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //As an example, display the message
                Toast.makeText(view.getContext(), "Refuser la partie", Toast.LENGTH_SHORT).show();
                popupWindow.dismiss();

            }
        });



        //Handler for clicking on the inactive zone of the window

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });
    }


}
