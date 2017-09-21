package com.rudigo.android.mycampus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.couchbase.lite.Database;
import com.rudigo.android.mycampus.Helper.DatabaseHelper;
import com.rudigo.android.mycampus.models.Lecture;
import com.sleepbot.datetimepicker.time.RadialPickerLayout;
import com.sleepbot.datetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class NewLecture extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, View.OnClickListener {

    public static final String TIMEPICKER_TAG = "timepicker";
    private LinearLayout mon,tue,wed,thurs,fri;
    private TextView alarmMon,alarmTues,alarmWed,alarmThur,alarmFri;
    private EditText courseCode,courseTitle,lecturer,venue;
    TimePickerDialog timePickerDialog;
    Button cancel,save;
    String hour;
    String minuteOfHour;
    Lecture lecture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_lecture);
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = TimePickerDialog.newInstance(this, calendar.get(Calendar.HOUR_OF_DAY) ,calendar.get(Calendar.MINUTE), false, false);

        if (savedInstanceState != null) {
            TimePickerDialog tpd = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag(TIMEPICKER_TAG);
            if (tpd != null) {
                tpd.setOnTimeSetListener(this);
            }
        }

        // buttons
        save = (Button)findViewById(R.id.btn_save);
        cancel = (Button)findViewById(R.id.btn_cancel);
        //edit text
        courseCode = (EditText)findViewById(R.id.course_code);
        courseTitle = (EditText)findViewById(R.id.course_title);
        venue = (EditText)findViewById(R.id.venue);
        lecturer = (EditText)findViewById(R.id.course_lecturer);
        // linearLayouts
        mon = (LinearLayout)findViewById(R.id.linMon);
        tue = (LinearLayout)findViewById(R.id.linTues);
        wed = (LinearLayout)findViewById(R.id.linWed);
        thurs = (LinearLayout)findViewById(R.id.linThur);
        fri = (LinearLayout)findViewById(R.id.linFri);

        // TextViews
        alarmMon = (TextView)findViewById(R.id.tv_alarmTimeMon);
        alarmTues = (TextView)findViewById(R.id.tv_alarmTimeTue);
        alarmWed = (TextView)findViewById(R.id.tv_alarmTimeWed);
        alarmThur = (TextView)findViewById(R.id.tv_alarmTimeThur);
        alarmFri = (TextView)findViewById(R.id.tv_alarmTimeFri);

        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thurs.setOnClickListener(this);
        fri.setOnClickListener(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savetoDataBase();

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(NewLecture.this,Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
            }
        });

    }

    private void savetoDataBase() {
        String cCode = courseCode.getText().toString().trim();
        String cTitle = courseTitle.getText().toString().trim();
        String cVenue = venue.getText().toString().trim();
        String cLecturer= lecturer.getText().toString().trim();
        long time = 360000;

        if (TextUtils.isEmpty(cCode) || TextUtils.isEmpty(cTitle) || TextUtils.isEmpty(cVenue)||TextUtils.isEmpty(cLecturer)){
            Toast.makeText(NewLecture.this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return ;
        }

        // saving to student database
        Database database = DatabaseHelper.getDatabase(getApplicationContext(), DatabaseHelper.LECTURE_DATA);

        lecture = new Lecture();
       lecture.setCourseCode(cCode);
        lecture.setCourseTitle(cTitle);
        lecture.setVenue(cVenue);
        lecture.setLecturer(cLecturer);
        lecture.setTime(time);

        lecture.saveToDatabase(NewLecture.this,database);
        Toast.makeText(NewLecture.this,"data Saved",Toast.LENGTH_SHORT).show();

        finish();
        Intent intent = new Intent(NewLecture.this,Home.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);


    }

    private void openTimePicker() {
        timePickerDialog.setCloseOnSingleTapMinute(true);
        timePickerDialog.show(getSupportFragmentManager(), TIMEPICKER_TAG);
    }


    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute) {

        Calendar c = Calendar.getInstance();
        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), hourOfDay, minute);
        c.getTimeInMillis();

        hour = String.valueOf(hourOfDay);
        minuteOfHour = String.valueOf(minute);

    }

    @Override
    public void onClick(View view) {
        if (view==mon){
            openTimePicker();
            alarmMon.setVisibility(View.VISIBLE);
            alarmMon.setText(hour+":"+minuteOfHour);

        }

        if (view==tue){
            openTimePicker();
            alarmTues.setVisibility(View.VISIBLE);
            alarmTues.setText(hour+":"+minuteOfHour);
        }

        if (view==wed){
            openTimePicker();
            alarmWed.setVisibility(View.VISIBLE);
            alarmWed.setText(hour+":"+minuteOfHour);
        }

        if (view==thurs){
            openTimePicker();
            alarmThur.setVisibility(View.VISIBLE);
            alarmThur.setText(hour+":"+minuteOfHour);
        }

        if (view==fri){
            openTimePicker();
            alarmFri.setVisibility(View.VISIBLE);
            alarmFri.setText(hour+":"+minuteOfHour);
        }

    }


    private void setAlarm() {

    }


}
