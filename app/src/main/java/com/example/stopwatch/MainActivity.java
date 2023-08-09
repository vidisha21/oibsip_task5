package com.example.stopwatch;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends AppCompatActivity {

    private Chronometer stopwatchChronometer;
    private Button startButton, stopButton, resetButton, holdButton;
    private boolean running = false;
    private boolean holdActive = false;
    private long pauseOffset = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stopwatchChronometer = findViewById(R.id.stopwatchChronometer);
        startButton = findViewById(R.id.startButton);
        stopButton = findViewById(R.id.stopButton);
        resetButton = findViewById(R.id.resetButton);
        holdButton = findViewById(R.id.holdButton);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startStopwatch();
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopStopwatch();
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetStopwatch();
            }
        });

        holdButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    holdActive = true;
                    holdStartStopwatch();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    holdActive = false;
                    holdStopStopwatch();
                }
                return true;
            }
        });
    }

    private void startStopwatch() {
        if (!running) {
            stopwatchChronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            stopwatchChronometer.start();
            running = true;
        }
    }

    private void stopStopwatch() {
        if (running) {
            stopwatchChronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - stopwatchChronometer.getBase();
            running = false;
        }
    }

    private void resetStopwatch() {
        stopwatchChronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
        if (running) {
            stopwatchChronometer.stop();
            running = false;
        }
    }

    private void holdStartStopwatch() {
        if (!running && holdActive) {
            stopwatchChronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            stopwatchChronometer.start();
            running = true;
        }
    }

    private void holdStopStopwatch() {
        if (running && !holdActive) {
            stopwatchChronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - stopwatchChronometer.getBase();
            running = false;
        }
    }
}
