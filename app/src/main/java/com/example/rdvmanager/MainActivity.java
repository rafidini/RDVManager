package com.example.rdvmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /* Attributes */
    private DatabaseHelper myHelper; // Database helper
    public ListView myLvRDVs; // Display of RDVs in ListView

    /* Methods */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("test", "onCreateEvent");

        /* Open Database */
        this.myHelper = new DatabaseHelper(this);
        this.myHelper.open();
        Log.d("test", "Database opened");

        /* Initiate RDVs displayed */
        this.myLvRDVs = (ListView) findViewById(R.id.myLvRDVs);
        this.myLvRDVs.setEmptyView(findViewById(R.id.tvEmpty));
    } // onCreate(.)

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.rdv_menu,menu);
        return true;
    } // onCreateOptionsMenu(.)

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            /* Add a new RDV */
            case R.id.new_rdv: {
                Intent intent = new Intent(this, Rdv.class);
                startActivity(intent);
                return true;
            }

            /* Default case */
            default:
                return super.onOptionsItemSelected(item);
        }
    } // onOptionsItemSelected(.)

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test", "onResumeEvent");
    } // onResume()

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test", "onPauseEvent");
    } // onPause()

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test", "onStopEvent");
    } // onStop()

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("test", "onRestartEvent");
    } // onRestart()

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroyEvent");

        /* Close Database */
        this.myHelper.close();
    } // onDestroy()
}