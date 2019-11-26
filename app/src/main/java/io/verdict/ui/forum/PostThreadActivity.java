package io.verdict;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class PostThreadActivity extends AppCompatActivity {


    Spinner selectCategory;
    EditText threadTitle;
    EditText threadDescription;
    CheckBox isAnonymous;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_thread);

        selectCategory = (Spinner) findViewById(R.id.postThread_selectCategory);
        threadTitle = (EditText) findViewById(R.id.postThread_titleContent);
        threadDescription = (EditText) findViewById(R.id.postThread_descriptionContent);
        isAnonymous = (CheckBox) findViewById(R.id.postResponse_anonymous);
        submitButton = (Button) findViewById(R.id.postResponse_submitButton);

        Intent i = getIntent();
        ArrayList arrayList = i.getStringArrayListExtra("categories");
        arrayList.add(0, "Select Category");
        ArrayAdapter arrayAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, arrayList);
        selectCategory.setAdapter(arrayAdapter);
        selectCategory.setSelection(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (threadTitle.getText().toString() != "") {
                    Intent intent = new Intent(PostThreadActivity.this, ThreadsActivity.class);
                    intent.putExtra("category", selectCategory.getSelectedItem().toString());
                    intent.putExtra("title", threadTitle.getText().toString());
                    intent.putExtra("description", threadDescription.getText().toString());
                    intent.putExtra("anonymous", isAnonymous.isChecked());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
