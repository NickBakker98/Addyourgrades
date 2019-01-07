package com.example.nick0.addyourgrades;

import android.content.Intent;
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

    List<Grades> mGradeObjects = new ArrayList<>();
    private FloatingActionButton add_Button;
    GradeAdapter mAdapter = new GradeAdapter(this, mGradeObjects);
    private TextView mQuoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.LayoutManager mLayoutManager = new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
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
                //new GameObjectAsyncTask(TASK_DELETE_GAMES).execute(gameObject);
                mGradeObjects.remove(position);
                mAdapter.notifyItemRemoved(position);
                Toast.makeText(MainActivity.this, "Deleted: " + grades.getCourseName(), Toast.LENGTH_LONG).show();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        
        this.add_Button = findViewById(R.id.add_button);
        mQuoteTextView = findViewById(R.id.dayquote);

        add_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddGradeActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        requestData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String tmpTitle, tmpGrade;
        if (resultCode == RESULT_OK) {
            tmpTitle = data.getStringExtra(AddGradeActivity.EXTRA_TITLE);
            tmpGrade = data.getStringExtra(AddGradeActivity.EXTRA_GRADE);
            Grades newItem = new Grades(tmpTitle, tmpGrade);
            mGradeObjects.add(newItem);
            mAdapter.notifyDataSetChanged();
        }
    }


    public void setQuoteTextView(String quoteMessage) {
        mQuoteTextView.setText(quoteMessage);
    }

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
