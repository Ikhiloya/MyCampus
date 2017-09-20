package com.rudigo.android.mycampus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.rudigo.android.mycampus.Helper.DatabaseHelper;
import com.rudigo.android.mycampus.adapters.RecyclerAdapter;
import com.rudigo.android.mycampus.models.Lecture;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    RecyclerAdapter adapter;
    Database database;

    private ArrayList<Lecture> lectures;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        database = DatabaseHelper.getDatabase(getApplicationContext(), DatabaseHelper.LECTURE_DATA);

        geAllNewLectures();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewLecture.class);
                startActivity(intent);

            }
        });


        recyclerView1 = (RecyclerView)findViewById(R.id.recycler1);
        recyclerView1.setHasFixedSize(true);


    }

    private void geAllNewLectures() {

        recyclerView2 = (RecyclerView)findViewById(R.id.recycler2);
        recyclerView2.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager);


        if (database == null)
            return;

        Query query = database.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS); //ALL_DOCS by id, BY_SEQUENCE by last modified

        try {
            QueryEnumerator result = query.run();
            lectures = new ArrayList<>();

            for (; result.hasNext(); ) {
                QueryRow row = result.next();
                Lecture lecture = Lecture.fromDictionary(row.getDocument().getProperties());
                lectures.add(lecture);
            }
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "Get customers info failed", Toast.LENGTH_SHORT).show();
        }
        adapter = new RecyclerAdapter(lectures,MainActivity.this);
        recyclerView2.setAdapter(adapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
