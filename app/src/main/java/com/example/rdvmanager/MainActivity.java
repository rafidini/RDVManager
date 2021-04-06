package com.example.rdvmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
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

        /* Set OnClick item event behaviour ListView */
        this.myLvRDVs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /* Extract attributes */
                String vId = ((TextView) view.findViewById(R.id.idRdv)).getText().toString();
                String vTitle = ((TextView) view.findViewById(R.id.titleRdv)).getText().toString();
                String vDate = ((TextView) view.findViewById(R.id.dateRdv)).getText().toString();
                String vTime = ((TextView) view.findViewById(R.id.timeRdv)).getText().toString();
                String vPerson = ((TextView) view.findViewById(R.id.personRdv)).getText().toString();
                String vAddress = ((TextView) view.findViewById(R.id.addressRdv)).getText().toString();
                String vPhoneNumber = ((TextView) view.findViewById(R.id.phoneNumberRdv)).getText().toString();
                String vDone = ((TextView) view.findViewById(R.id.doneRdv)).getText().toString();

                /* Define a Rdv */
                final Rdv vRdv = new Rdv(Long.parseLong(vId), vTitle, vDate, vTime, vPerson,
                        vAddress, vPhoneNumber, (vDone == "1"?true:false));

                /* Prepare to create window */
                Intent vIntent = new Intent(getApplicationContext(), RdvAddActivity.class);
                vIntent.putExtra("SelectedRdv", vRdv);
                vIntent.putExtra("fromAdd", false);

                /* Start new window */
                startActivity(vIntent);
            }
        });

        /* Load the data */
        chargeData();

        /* Register the ListView for a ContextMenu */
        registerForContextMenu(this.myLvRDVs);
    } // onCreate(.)

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

        if (item.getItemId() == R.id.delete){
            int vCount = myHelper.delete(info.id);
            Log.d("DB", vCount + " deleted!");
            chargeData();
            return true;
        }

        return super.onContextItemSelected(item);
    }

    /**
     * Load the data to the view by using the DatabaseHelper.
     */
    private void chargeData() {
        /* Get columns from database */
        final String[] vFrom = new String[] {
                myHelper._ID, myHelper.TITLE, myHelper.DATE, myHelper.TIME, myHelper.PERSON,
                myHelper.ADDRESS, myHelper.PHONE_NUMBER, myHelper.DONE
        };

        /* Get ids from Activity widgets */
        final int[] vTo = new int[] {
                R.id.idRdv, R.id.titleRdv, R.id.dateRdv, R.id.timeRdv, R.id.personRdv,
                R.id.addressRdv, R.id.phoneNumberRdv, R.id.doneRdv
        };

        /* Get data as Cursor */
        final Cursor vCursor = myHelper.getAllRdv();

        /* Put to adapter */
        SimpleCursorAdapter vAdapter = new SimpleCursorAdapter(this, R.layout.rdv_item_view,
                vCursor, vFrom, vTo, 0);
        vAdapter.notifyDataSetChanged();

        /* Set it to the ListView */
        this.myLvRDVs.setAdapter(vAdapter);
    }

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
                Intent intent = new Intent(this, RdvAddActivity.class);

                /* Switch to RdvAddActivity */
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