package io.verdict.ui.forum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import io.verdict.R;

public class PostResponseActivity extends AppCompatActivity {

    Button submitButton;
    EditText threadThumbs;
    EditText threadTitle;
    EditText response;
    CheckBox isAnonymous;
    int thumbs;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_response);

        threadThumbs = (EditText) findViewById(R.id.postResponse_titleNumThumbs);
        threadTitle = (EditText) findViewById(R.id.postResponse_titleName);
        response = (EditText) findViewById(R.id.postResponse_responseContent);
        isAnonymous = (CheckBox) findViewById(R.id.postResponse_anonymous);
        submitButton = (Button) findViewById(R.id.postResponse_submitButton);

        Intent i = getIntent();
        thumbs = i.getExtras().getInt("threadThumbs", 0);
        title = i.getExtras().getString("threadTitle", "Error");

        if (title != "Error") {
            threadThumbs.setText(String.valueOf(thumbs));
            threadTitle.setText(title);
        }
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
                Intent intent = new Intent(PostResponseActivity.this, SingleThreadActivity.class);
                intent.putExtra("threadThumbs", thumbs);
                intent.putExtra("threadTitle", title);
                intent.putExtra("response", response.getText().toString());
                intent.putExtra("anonymous", isAnonymous.isChecked());
                startActivity(intent);
                finish();
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
    protected void onDestroy() { super.onDestroy();
    }

}
