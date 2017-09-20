package com.rudigo.android.mycampus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class RecordActivity extends AppCompatActivity {

    private ImageButton recordButton;
    private Button previousLectureButton;
    private Button convertButton;
    private Button listenButton;


    private TextView recordText;
    private TextView timeText;
    private TextView minuteText;

    boolean isClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        isClicked = false;

        recordButton = (ImageButton) findViewById(R.id.recordButton);

        convertButton = (Button) findViewById(R.id.convertBtn);
        listenButton = (Button)findViewById(R.id.listenBtn);


        previousLectureButton = (Button) findViewById(R.id.previousLecturesBtn);
        recordText = (TextView) findViewById(R.id.record_started);
        timeText = (TextView) findViewById(R.id.time_text);
        minuteText = (TextView) findViewById(R.id.minutes_text);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isClicked == false){

                    convertButton.setVisibility(View.GONE);
                    listenButton.setVisibility(View.GONE);

                    //change color backgrouund to red
                    recordButton.setBackground(getResources().getDrawable(R.drawable.button_background_red));

                    //change the drawable image
                    recordButton.setImageResource(R.drawable.ic_settings_voice_black_24dp);

                    previousLectureButton.setVisibility(View.GONE);
                    recordText.setVisibility(View.VISIBLE);
                    timeText.setVisibility(View.VISIBLE);
                    minuteText.setVisibility(View.VISIBLE);
                    isClicked = true;
                }else{
                    recordText.setText("Recording Stopped");
                    timeText.setVisibility(View.GONE);
                    minuteText.setVisibility(View.GONE);
                    convertButton.setVisibility(View.VISIBLE);
                    listenButton.setVisibility(View.VISIBLE);

                    //change color backgrouund to blue
                    recordButton.setBackground(getResources().getDrawable(R.drawable.button_background_blue));

                    //change the drawable image
                    recordButton.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);
                    isClicked = false;


                }



            }
        });


    }
}
