package com.example.nick0.addyourgrades;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

public class AddGradeActivity extends AppCompatActivity {

    public EditText addCourseName;
    public EditText addGrade;
    public String mTitle;
    public String mGrade;
    public FloatingActionButton saveButton;

    public static final String EXTRA_TITLE = "a title";
    public static final String EXTRA_GRADE = "a grade";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grade);

        addCourseName = findViewById(R.id.add_coursetitle);
        addGrade = findViewById(R.id.add_grade);
        saveButton = findViewById(R.id.save_button);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mTitle = addCourseName.getText().toString();
                mGrade = addGrade.getText().toString();

                //Check if all the fields have been filled (else Snackbar).
                if(TextUtils.isEmpty(mTitle) || TextUtils.isEmpty(mGrade)){
                    Snackbar.make(getCurrentFocus(),"Fill in all fields.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                //Put the data which is added to the intent.
                Intent data = new Intent();
                data.putExtra(EXTRA_TITLE,mTitle);
                data.putExtra(EXTRA_GRADE,mGrade);
                setResult(RESULT_OK,data);
                finish();
            }
        });


    }
}
