package com.example.shirley.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Message;
import android.os.Handler;

import android.app.Activity;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private TextView sysTime;
    private TextView nextRemindTime;
    private TextView timeRemaining;
    long remind = System.currentTimeMillis() + 30*60*1000;

    private static final int msgKey1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sysTime = (TextView) findViewById(R.id.textView);
        nextRemindTime = (TextView) findViewById(R.id.textView3);
        timeRemaining = (TextView) findViewById(R.id.textView4);
        new TimeThread().start();
    }

    public class TimeThread extends  Thread{
        @Override
        public void run() {
            super.run();
            do{
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = msgKey1;
                    mHandler.sendMessage(msg);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }while (true);
        }
    }

    private Handler mHandler;

    {
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case msgKey1:
                        long time = System.currentTimeMillis();
                        long remaining = remind - time;
                        Date date = new Date(time);
                        Date remindDate = new Date(remind);
                        Date remainingDate = new Date(remaining);
                        SimpleDateFormat format = new SimpleDateFormat("HH时mm分ss秒");
                        sysTime.setText("当前系统时间： " + format.format(date));
                        nextRemindTime.setText("下次提示时间：" +  format.format(remindDate));
                        format.setTimeZone(TimeZone.getTimeZone("UTC"));
                        timeRemaining.setText("剩余时间：" + format.format(remainingDate));
       //                 format.setTimeZone(TimeZone.getTimeZone("CNT"));
                        break;
                    default:
                        break;
                }
            }
        };
    }


}
