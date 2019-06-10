package com.example.handlerdmeo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ChangeTextViewListener {

    private TextView mClickTextView;
    private TextView mChangeTextView;

    private int mNum = 0;
    private MyHandler mHandler = new MyHandler(this);
    private Timer mTimer = new Timer();
    private TimerTask mTask = new TimerTask() {
        @Override
        public void run() {
            mNum ++;
            Message message = new Message();
            message.what = 1;
            message.arg1 = mNum;
            message.obj = "倒计时: ";
            mHandler.sendMessage(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mClickTextView = findViewById(R.id.clickTextView);
        mChangeTextView = findViewById(R.id.changeTextView);
        mClickTextView.setOnClickListener(this);
        mTimer.schedule(mTask,0,1000);
    }


    @Override
    public void onClick(View v) {
        mTask.cancel();
        mTimer.cancel();
    }

    @Override
    public void changeTextView(String text) {
        mChangeTextView.setText(text);
    }

    @Override
    public void changeTextViewComplete() {
        mChangeTextView.setText("完成");
        mClickTextView.setClickable(true);
    }

}


class MyHandler extends Handler {


    private ChangeTextViewListener mListenter;


    MyHandler(ChangeTextViewListener listener) {
        mListenter = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        int arg = msg.arg1;
        String string = (String) msg.obj;
        if (msg.what == 1) {
            if (mListenter != null) {
                mListenter.changeTextView(string + arg);
            }
        }
        if (arg == 0) {
            if (mListenter != null) {
                mListenter.changeTextViewComplete();
            }
        }

    }
}

interface ChangeTextViewListener {
    void changeTextView(String text);

    void changeTextViewComplete();
}

