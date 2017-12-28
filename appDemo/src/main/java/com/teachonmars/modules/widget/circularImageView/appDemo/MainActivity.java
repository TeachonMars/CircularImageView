package com.teachonmars.modules.widget.circularImageView.appDemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.teachonmars.modules.widget.circularImageView.CircularImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDynamic();
    }

    private void initDynamic() {
        final CircularImageView dynamicChanges = findViewById(R.id.dynamicChanges);
        EditText gapSize = findViewById(R.id.gapSize);
        gapSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Integer size;
                try {
                    size = Integer.valueOf(charSequence.toString());
                } catch (NumberFormatException e) {
                    size = 0;
                }
                dynamicChanges.setGapSize(dpOf(size));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        EditText gapColor = findViewById(R.id.gapColor);
        gapColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Integer color;
                try {
                    color = Color.parseColor(charSequence.toString());
                } catch (Exception e) {
                    color = Color.WHITE;
                }
                dynamicChanges.setGapColor(color);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        EditText borderSize = findViewById(R.id.borderSize);
        borderSize.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Integer size;
                try {
                    size = Integer.valueOf(charSequence.toString());
                } catch (Exception e) {
                    size = 0;
                }
                dynamicChanges.setBorderSize(dpOf(size));
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        EditText borderColor = findViewById(R.id.borderColor);
        borderColor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                Integer color;
                try {
                    color = Color.parseColor(charSequence.toString());
                } catch (Exception e) {
                    color = Color.BLACK;
                }
                dynamicChanges.setBorderColor(color);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        ToggleButton needInset = findViewById(R.id.needInset);
        needInset.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                dynamicChanges.setNeedInsetDrawable(isChecked);
            }
        });


        gapColor.setText("#FF445566");
        gapSize.setText("7");
        borderColor.setText("#0000FF");
        borderSize.setText("3");
        needInset.setChecked(true);
    }

    private int dpOf(Integer size) {
        return (int) (getResources().getDisplayMetrics().density * size);
    }
}
