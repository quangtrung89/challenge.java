package com.aurasoftwareinc.java.challenge1;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;

public class MainActivity extends Activity
{

    private TextView text1;
    private TextView text2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        createLayout();
    }

    private void createLayout()
    {
        FrameLayout topFrame = new FrameLayout(this);
        topFrame.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        topFrame.setBackgroundColor(0x88880000);

        setContentView(topFrame);

        LinearLayout contentFrame = new LinearLayout(this);
        contentFrame.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        contentFrame.setOrientation(LinearLayout.VERTICAL);

        topFrame.addView(contentFrame);

        ScrollView scroll1 = new ScrollView(this);
        scroll1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
        scroll1.setBackgroundColor(0x88008800);

        contentFrame.addView(scroll1);

        text1 = new TextView(this);
        text1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        scroll1.addView(text1);

        ScrollView scroll2 = new ScrollView(this);
        scroll2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 0.5f));
        scroll2.setBackgroundColor(0x88000088);

        contentFrame.addView(scroll2);

        text2 = new TextView(this);
        text2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        scroll2.addView(text2);

        LinearLayout buttonFrame = new LinearLayout(this);
        buttonFrame.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        buttonFrame.setOrientation(LinearLayout.HORIZONTAL);
        buttonFrame.setGravity(Gravity.CENTER_HORIZONTAL);
        buttonFrame.setBackgroundColor(0x88888888);
        buttonFrame.setPadding(10, 10, 10, 10);

        contentFrame.addView(buttonFrame);

        TextView startButton = new TextView(this);
        startButton.setText("Start");
        startButton.setTextSize(36f);
        startButton.setBackgroundColor(0xffffffff);
        startButton.setPadding(20, 20, 20, 20);
        buttonFrame.addView(startButton);
    }
}
