package com.sihan.android.miwok;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class GradeActivity extends AppCompatActivity {

    final ArrayList<Grade> gradeArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade);

        Button button_save = findViewById(R.id.button_save);
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText_name = findViewById(R.id.editText_name);
                EditText editText_english = findViewById(R.id.editText_english);
                EditText editText_math = findViewById(R.id.editText_math);
                EditText editText_history = findViewById(R.id.editText_history);
                EditText editText_programming = findViewById(R.id.editText_programming);

                String name = editText_name.getText().toString();
                String english = editText_english.getText().toString();
                String math = editText_math.getText().toString();
                String history = editText_history.getText().toString();
                String programming = editText_programming.getText().toString();

                Grade grade = new Grade(name,english,math,history,programming);
                gradeArrayList.add(grade);

                final GradeAdapter gradeGradeAdapter = new GradeAdapter(GradeActivity.this,R.layout.grade_list_item,gradeArrayList);

                ListView listView = findViewById(R.id.grade_list_view);
                listView.setAdapter(gradeGradeAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Grade grade1 = gradeGradeAdapter.getItem(position);
                        gradeGradeAdapter.remove(grade1);
                        Toast.makeText(GradeActivity.this,"Grade of "+grade1.getName()+" removed",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
