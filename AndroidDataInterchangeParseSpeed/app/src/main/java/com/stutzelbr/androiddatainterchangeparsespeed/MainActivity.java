package com.stutzelbr.androiddatainterchangeparsespeed;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import Domain.Person;
import javaxjparsespeed.ParseTests;

public class MainActivity extends AppCompatActivity {

    private Handler h;
    private TextView text;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        h = new Handler();
        text  = ((TextView) findViewById(R.id.txt));
        ctx = this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                ParseTests pTests = new ParseTests(ctx);

                Person p = new Person(100, 100);

                //pTests.serializeObject(p);

                //pTests.jsonParserAsGson(p);
                //pTests.jsonParserAsJackson(p);
//          pTests.jsonParserAsBoon(p); //não é compatível
                pTests.jsonParserAsJacksonParallel(p);

            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        text.setText("performing test");
    }
}
