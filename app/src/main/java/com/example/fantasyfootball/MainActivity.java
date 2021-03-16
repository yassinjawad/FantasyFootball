package com.example.fantasyfootball;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    // declare DBHandler
    DBHandler dbHandler;

    // declare EditText
    EditText nameEditText;

    // declare Spinners
    Spinner positionSpinner;
    Spinner teamSpinner;

    // declare Strings to store position and team selected in Spinners
    String position1;
    String team;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // initialize DBHandler
        dbHandler = new DBHandler(this, null);

        // initialize EditText
        nameEditText = (EditText) findViewById(R.id.nameEditText);

        // initialize Spinners
        positionSpinner = (Spinner) findViewById(R.id.positionSpinner);
        teamSpinner = (Spinner) findViewById(R.id.teamSpinner);

        // initialize ArrayAdapters with values in position and team string arrays
        // and stylize them with style defined by simple_spinner_item
        ArrayAdapter<CharSequence> positionAdapter = ArrayAdapter.createFromResource(this,
                R.array.positions, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> teamAdapter = ArrayAdapter.createFromResource(this,
                R.array.teams, android.R.layout.simple_spinner_item);

        // further stylize ArrayAdapters with style defined by simple_spinner_dropdown_item
        positionAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        teamAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        // set ArrayAdapters on Spinners
        positionSpinner.setAdapter(positionAdapter);
        teamSpinner.setAdapter(teamAdapter);

        // register On Item Selected Listener to Spinners
        positionSpinner.setOnItemSelectedListener(this);
        teamSpinner.setOnItemSelectedListener(this);


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
        switch (item.getItemId()){
            case R.id.action_get_count_eagles :
                Toast.makeText(this, getMessage("Eagles"), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_get_count_steelers :
                Toast.makeText(this, getMessage("Steelers"), Toast.LENGTH_LONG).show();
                return true;
            case R.id.action_get_count_cowboys :
                Toast.makeText(this, getMessage("Cowboys"), Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    /**
     * This method gets called when a menu-item in the overflow menu is selected.
     * @param team selected major (CIS, CIT, or CSM)
     * @return String that contains count of the number of students who have the selected major.
     */
    public String getMessage (String team) {
        int count = dbHandler.getCount(team);
        return (count == 1 ? count + " player." : count + " players.");
    }

    /**
     * This method gets called when the add button in the Action Bar gets clicked.
     * @param menuItem add player menu item
     */
    public void addPlayer(MenuItem menuItem) {
        // get data input in EditText and store it in String
        String name = nameEditText.getText().toString();

        // trim Strings and see if they're equal to empty Strings
        if (name.trim().equals("") || position1.trim().equals("") || team.trim().equals("")){
            // display "Please enter a position, name, and team!" Toast if any of the Strings are empty
            Toast.makeText(this, "Please enter a position, name, and team!", Toast.LENGTH_LONG).show();
        } else {
            // add item into database
            dbHandler.addPlayer(position1, name, team);

            // display "Player added!" Toast of none of the Strings are empty
            Toast.makeText(this, "Player added!", Toast.LENGTH_LONG).show();
        }
    }


    /**
     * This method gets called when an item in one of the Spinners is selected.
     * @param parent Spinner AdapterView
     * @param view MainActivity view
     * @param position position of item in Spinner that was selected
     * @param id database id of item in Spinner that was selected
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // get the id of the Spinner that called method
        switch (parent.getId()) {
            case R.id.teamSpinner:
                // get the item selected in the Spinner and store it in String
                team = parent.getItemAtPosition(position).toString();
                break;
            case R.id.positionSpinner:
                // get the item selected in the Spinner and store it in String
                position1 = parent.getItemAtPosition(position).toString();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}