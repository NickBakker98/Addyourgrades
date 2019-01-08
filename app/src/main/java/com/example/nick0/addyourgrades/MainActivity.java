package com.example.nick0.addyourgrades;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //Declare the variables
    List<Grades> mGradeObjects = new ArrayList<>();
    private FloatingActionButton add_Button;
    GradeAdapter mAdapter = new GradeAdapter(this, mGradeObjects);
    private TextView mQuoteTextView;
    RecyclerView recyclerView;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This is the MainViewModel which handles the database.
        mMainViewModel = new MainViewModel(getApplicationContext());
        mMainViewModel.getGrades().observe(this, new Observer<List<Grades>>() {
            @Override
            public void onChanged(@Nullable List<Grades> reminders) {
                mGradeObjects = reminders;
                mAdapter.notifyDataSetChanged();
                mAdapter.swapList(mGradeObjects);
            }
        });

        //Setting the recyclerView.
        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            //Deleting the grades by swiping.
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                int position = (viewHolder.getAdapterPosition());
                final Grades grades = mGradeObjects.get(position);
                mMainViewModel.delete(mGradeObjects.get(position));
                Toast.makeText(MainActivity.this, "Deleted: " + grades.getCourseName(), Toast.LENGTH_LONG).show();
            }
        };

        //Attach the swipe to the recyclerView.
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        //Linking button and textview to xml file.
        this.add_Button = findViewById(R.id.add_button);
        mQuoteTextView = findViewById(R.id.dayquote);

        //Create an OnClickListener to the add_button which navigates to the AddGradeActivity.
        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGradeActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        //Calling the requestData method.
        requestData();
    }

    //Getting the data from the AddGradeActivity and add this data to the list.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String tmpTitle, tmpGrade;
        if (resultCode == RESULT_OK) {
            tmpTitle = data.getStringExtra(AddGradeActivity.EXTRA_TITLE);
            tmpGrade = data.getStringExtra(AddGradeActivity.EXTRA_GRADE);
            Grades newGrade = new Grades(tmpTitle, tmpGrade);
            mGradeObjects.add(newGrade);
            mMainViewModel.insert(newGrade);
        }
    }

    //Setting the text for the textView with the quote of the day.
    public void setQuoteTextView(String quoteMessage) {
        mQuoteTextView.setText(quoteMessage);
    }

    //Request the data from the API server.
    private void requestData()
    {
        NumbersApiService service = NumbersApiService.retrofit.create(NumbersApiService.class);
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1; //Calendar.MONTH starts at zero
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        /**
         * Make an a-synchronous call by enqueing and definition of callbacks.
         */
        Call<DayQuoteItem> call = service.getTodaysQuote(month, dayOfMonth);
        call.enqueue(new Callback<DayQuoteItem>() {
            @Override
            public void onResponse(Call<DayQuoteItem> call, Response<DayQuoteItem> response) {
                DayQuoteItem dayQuoteItem = response.body();
                setQuoteTextView(dayQuoteItem.getText());
            }
            @Override
            public void onFailure(Call<DayQuoteItem> call, Throwable t) {
                Log.d("error",t.toString());
            }
        });
    }
}
