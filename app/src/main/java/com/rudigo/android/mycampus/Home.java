package com.rudigo.android.mycampus;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerAdapter adapter;
    Database database;
    ArrayList<Lecture> lectures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        database = DatabaseHelper.getDatabase(getApplicationContext(), DatabaseHelper.LECTURE_DATA);
        recyclerView2 = (RecyclerView)findViewById(R.id.recyclerUpComing);
        recyclerView2.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(Home.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView2.setLayoutManager(layoutManager);

        geAllNewLectures();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, NewLecture.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        geAllNewLectures();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_nav_drawer_drawer, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_lectures_timetable) {
            // Handle the camera action
        } else if (id == R.id.nav_lecture_notes) {
            // Handle the camera action
        } else if (id == R.id.nav_lecture_recording) {

        } else if (id == R.id.nav_invite_coursemates) {

        } else if (id == R.id.nav_notifications) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    private void geAllNewLectures() {

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
            Toast.makeText(Home.this, "Get customers info failed", Toast.LENGTH_SHORT).show();
        }
        adapter = new RecyclerAdapter(lectures,Home.this);
        recyclerView2.setAdapter(adapter);

    }
}
