package com.skilrock.retailapp.utils;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.skilrock.retailapp.R;

public class SnwCell extends LinearLayout implements View.OnClickListener {

    private Context context;

    public SnwCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setOrientation(LinearLayout.VERTICAL);
        LayoutInflater.from(context).inflate(R.layout.snw_cell, this, true);

        this.context = context;
        String title;
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomView, 0, 0);

        try {
            title = a.getString(R.styleable.CustomView_customViewTitle);
        } finally {
            a.recycle();
        }

        // Throw an exception if required attributes are not set
        if (title == null) {
            throw new RuntimeException("No title provided");
        }

        init(title);
    }

    // Setup views
    private void init(String title) {
        TextView tvCenter       = findViewById(R.id.tvNumber);
        TextView tvTopLeft      = findViewById(R.id.tvTopLeft);
        TextView tvTop          = findViewById(R.id.tvTop);
        TextView tvTopRight     = findViewById(R.id.tvTopRight);
        TextView tvLeft         = findViewById(R.id.tvLeft);
        TextView tvRight        = findViewById(R.id.tvRight);
        TextView tvBottomLeft   = findViewById(R.id.tvBottomLeft);
        TextView tvBottom       = findViewById(R.id.tvBottom);
        TextView tvBottomRight  = findViewById(R.id.tvBottomRight);

        tvCenter.setText(title);
        tvCenter.setOnClickListener(this);
        tvTop.setOnClickListener(this);
        tvTopLeft.setOnClickListener(this);
        tvTopRight.setOnClickListener(this);
        tvLeft.setOnClickListener(this);
        tvRight.setOnClickListener(this);
        tvBottomLeft.setOnClickListener(this);
        tvBottom.setOnClickListener(this);
        tvBottomRight.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvNumber:
                Toast.makeText(context, "Center", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvTopLeft:
                Toast.makeText(context, "Top Left", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvTop:
                Toast.makeText(context, "Top", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvTopRight:
                Toast.makeText(context, "Top Right", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvLeft:
                Toast.makeText(context, "Left", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvRight:
                Toast.makeText(context, "Right", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvBottomLeft:
                Toast.makeText(context, "Bottom Left", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvBottom:
                Toast.makeText(context, "Bottom", Toast.LENGTH_SHORT).show();
                break;

            case R.id.tvBottomRight:
                Toast.makeText(context, "Bottom Right", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
