package com.rudigo.android.mycampus;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class RecordActivity extends AppCompatActivity {

    private Chronometer myChronometer;
    private MediaPlayer mediaPlayer;
    private MediaRecorder recorder;
    // private String OUTPUT_FILE;

    static final String TAG = "MediaRecording";

    File audiofile = null;
    File dir;


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

        myChronometer = (Chronometer) findViewById(R.id.chronometer);

        isClicked = false;

        recordButton = (ImageButton) findViewById(R.id.recordButton);

        convertButton = (Button) findViewById(R.id.convertBtn);
        listenButton = (Button) findViewById(R.id.listenBtn);


        previousLectureButton = (Button) findViewById(R.id.previousLecturesBtn);
        recordText = (TextView) findViewById(R.id.record_started);
        //timeText = (TextView) findViewById(R.id.time_text);
        minuteText = (TextView) findViewById(R.id.minutes_text);

        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isClicked) {

                    convertButton.setVisibility(View.GONE);
                    listenButton.setVisibility(View.GONE);

                    //change color backgrouund to red
                    recordButton.setBackground(getResources().getDrawable(R.drawable.button_background_red));

                    //change the drawable image
                    recordButton.setImageResource(R.drawable.ic_settings_voice_black_24dp);

                    previousLectureButton.setVisibility(View.GONE);
                    recordText.setVisibility(View.VISIBLE);
                    //timeText.setVisibility(View.VISIBLE);
                    myChronometer.setVisibility(View.VISIBLE);
                    minuteText.setVisibility(View.VISIBLE);

                    //TODO:start recording audio
                    try {
                        beginRecording();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isClicked = true;
                } else {

                    //TODO: stop recording audio
                    try {
                        stopRecording();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    recordText.setText(R.string.recording_stopped);
                    //timeText.setVisibility(View.GONE);
                    myChronometer.setVisibility(View.GONE);
                    minuteText.setVisibility(View.GONE);
                    convertButton.setVisibility(View.VISIBLE);
                    listenButton.setVisibility(View.VISIBLE);

                    //change color backgrouund to blue
                    recordButton.setBackground(getResources().getDrawable(R.drawable.button_background_blue));

                    //change the drawable image
                    recordButton.setImageResource(R.drawable.ic_keyboard_voice_black_24dp);
                    // isClicked = false;
                    recordButton.setEnabled(false);

                }
            }
        });

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: convert audio to text
                Toast.makeText(RecordActivity.this, R.string.loading, Toast.LENGTH_SHORT).show();
            }
        });

        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: play audio
                try {
                    playRecording();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    private void beginRecording() throws IOException {

        ditchMediaRecorder();
        //starts the counter
        myChronometer.start();
        //Creating file
        dir = Environment.getExternalStorageDirectory();
        try {
            audiofile = File.createTempFile("recording", ".3gp", dir);
        } catch (IOException e) {
            Log.e(TAG, "external storage access error");
            return;
        }
        //Creating MediaRecorder and specifying audio source, output format, encoder & output format
        recorder = new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setOutputFile(audiofile.getAbsolutePath());
        recorder.prepare();
        recorder.start();

    }

    private void stopRecording() {
        //stop time counter
        myChronometer.stop();

        if (recorder != null) {
            recorder.stop();
            recorder.release();
        }

        //after stopping the recorder, create the sound file and add it to media library.
        addRecordingToMediaLibrary();
    }

    private void ditchMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void stopPlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }

    private void ditchMediaRecorder() {
        if (recorder != null) {
            recorder.release();
        }
    }


    private void playRecording() throws Exception {
        ditchMediaPlayer();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setDataSource(String.valueOf(audiofile));
        mediaPlayer.prepare();
        mediaPlayer.start();
    }

    protected void addRecordingToMediaLibrary() {
        //creating content values of size 4
        ContentValues values = new ContentValues(4);
        long current = System.currentTimeMillis();
        values.put(MediaStore.Audio.Media.TITLE, "audio" + audiofile.getName());
        values.put(MediaStore.Audio.Media.DATE_ADDED, (int) (current / 1000));
        values.put(MediaStore.Audio.Media.MIME_TYPE, "audio/3gpp");
        values.put(MediaStore.Audio.Media.DATA, audiofile.getAbsolutePath());

        //creating content resolver and storing it in the external content uri
        ContentResolver contentResolver = getContentResolver();
        Uri base = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Uri newUri = contentResolver.insert(base, values);

        //sending broadcast message to scan the media file so that it can be available
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, newUri));
        Toast.makeText(this, "Added File " + newUri, Toast.LENGTH_LONG).show();
    }


}
