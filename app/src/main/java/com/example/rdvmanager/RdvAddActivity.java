package com.example.rdvmanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class RdvAddActivity extends AppCompatActivity {
    /* Attributes */
    private DatePickerDialog.OnDateSetListener onDate;
    private DatabaseHelper myHelper; // Database helper
    private Button addButton; // Add Button
    private Button dateButton; // RDV date dialog Button
    private EditText aTitle; // RDV Title
    private EditText aDate; // RDV Date
    private EditText aTime; // RDV Time
    private EditText aPerson; // RDV Person
    private EditText aAddress; // RDV Address
    private EditText aPhoneNumber; // RDV Phone Number
    private Rdv aSelectedRdv = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        /* Open Database */
        this.myHelper = new DatabaseHelper(this);
        this.myHelper.open();
        Log.d("test", "Database opened");

        /* Get Intent parameters */
        final Intent vIntent = getIntent();
        final boolean vFromAdd = vIntent.getBooleanExtra("fromAdd", true);

        /* Extract Activity elements */
        this.addButton = (Button) findViewById(R.id.addButton); // Add/Submit Button
        this.dateButton = (Button) findViewById(R.id.dateButton); // Add/Submit Button
        this.aTitle = (EditText) findViewById(R.id.inputTitle); // RDV title input
        this.aDate = (EditText) findViewById(R.id.inputDate); // RDV date input
        this.aTime = (EditText) findViewById(R.id.inputTime); // RDV date input
        this.aPerson = (EditText) findViewById(R.id.inputPerson); // RDV date input
        this.aAddress = (EditText) findViewById(R.id.inputAddress); // RDV date input
        this.aPhoneNumber = (EditText) findViewById(R.id.inputPhoneNumber); // RDV date input
        this.onDate = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay)
            {
                int year = selectedYear;
                int month = selectedMonth;
                int day = selectedDay;
                aDate.setText(new StringBuilder().append(month +1).
                        append("-").append(day).append("-").append(year).append(" "));
            }
        }; // Data Picker Dialog

        /* Check if from selected Rdv */
        if(!vFromAdd) {
            /* Get arguments from Intent & Bundle */
            final Bundle vBundle = vIntent.getExtras();
            this.aSelectedRdv = vBundle.getParcelable("SelectedRdv");

            /* Give value to fields */
            this.aTitle.setText(aSelectedRdv.getTitle());
            this.aDate.setText(aSelectedRdv.getDate());
            this.aTime.setText(aSelectedRdv.getTime());
            this.aPerson.setText(aSelectedRdv.getPerson());
            this.aAddress.setText(aSelectedRdv.getAddress());
        }

        /* TODO - Fix onClick dateButton behaviour */
        this.dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(RdvAddActivity.this, "dateButton Clicked", Toast.LENGTH_LONG).show();
                showDatePicker();
            } // onClick(.)
        }); // setOnClickListener(.)

        /* Define onClick addButton behaviour */
        this.addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                /* Create a Rdv with attributes */
                final Rdv vNewRdv = new Rdv(
                        aTitle.getText().toString(),
                        aDate.getText().toString(),
                        aTime.getText().toString(),
                        aPerson.getText().toString(),
                        aAddress.getText().toString(),
                        aPhoneNumber.getText().toString()
                );

                /* Add a new Rdv */
                if (vFromAdd) {
                    /* Add Rdv to Database */
                    Log.d("DB", "Before adding");
                    myHelper.add(vNewRdv);
                    Log.d("DB", "After adding");
                } // if

                /* TODO - Update a Rdv */
                else {
                    vNewRdv.setId(aSelectedRdv.getId());
                     int vCount = myHelper.update(vNewRdv);
                    Log.d("DB", "Updated!");
                } // else

                /* Toast message */
                Toast.makeText(RdvAddActivity.this, vFromAdd?"New RDV added!":"RDV updated!", Toast.LENGTH_LONG).show();

                /* Quit this Intent */
                final Intent vIntent = new Intent(v.getContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(vIntent);
            } // onClick(.)
        }); // setOnClickListener(.)
    } // onCreate(.)

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test", "onDestroyEvent");

        /* Close Database */
        this.myHelper.close();
    } // onDestroy()

    public void pickDate(View pView) {
        showDatePicker();
    }

    public void showDatePicker() {
        DatePickerFragment vDate = new DatePickerFragment();

        final Calendar vCalendar = Calendar.getInstance();
        int vYear = vCalendar.get(Calendar.YEAR);
        int vMonth = vCalendar.get(Calendar.MONTH);
        int vDay = vCalendar.get(Calendar.DAY_OF_MONTH);

        Bundle args = new Bundle();
        args.putInt("year", vYear);
        args.putInt("month", vMonth);
        args.putInt("day", vDay);

        vDate.setArguments(args);
        vDate.setCallBack(this.onDate);
        vDate.show(getSupportFragmentManager(), "Date Picker");
    }
}

class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    /* Attributes */
    DatePickerDialog.OnDateSetListener onDateSet;
    public StringBuilder res;
    private int aDay;
    private int aMonth;
    private int aYear;

    public DatePickerFragment() {};

    /* Methods */
    public void setCallBack(DatePickerDialog.OnDateSetListener onDate) {
        this.onDateSet = onDate;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        aYear = args.getInt("year");
        aMonth = args.getInt("month");
        aDay = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), onDateSet, aYear, aMonth, aDay);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        this.res = new StringBuilder().append(month+1).append("-").append(day).append("-").append(year).append(" ");
    }
}
