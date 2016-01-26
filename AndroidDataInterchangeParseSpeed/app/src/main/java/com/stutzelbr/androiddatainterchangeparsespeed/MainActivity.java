package com.stutzelbr.androiddatainterchangeparsespeed;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
    }

    @Override
    protected void onResume() {
        super.onResume();
        text.setText("performing test");
        new Thread(new Runnable() {
            @Override
            public void run() {
                ParseTests pTests = new ParseTests(ctx);

                Person p = new Person(100, 100);

        //pTests.serializeObject(p);

        //pTests.jsonParserAsGson(p);
        //pTests.jsonParserAsJackson(p);
//          pTests.jsonParserAsBoon(p); //não é compatível
        pTests.xmlParserAsXpp3XStream(p);

                h.post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("1/3 test");
                    }
                });
        pTests.xmlParserAsDomDriverXStream(p);

                h.post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("2/3 test");
                    }
                });
        pTests.xmlParserAsStaxDriverXStream(p);

                h.post(new Runnable() {
                    @Override
                    public void run() {
                        text.setText("completed test");
                    }
                });

            }
        }).start();
    }
}
