package com.example.billiblahblaa.its333app2;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.Button;


public class AddCourse extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);

        Intent i = this.getIntent();
        if (i.hasExtra("code")) {
            String code = i.getStringExtra("code");
            int credit = i.getIntExtra("credit", 0);
            String time = i.getStringExtra("time");
            String day = i.getStringExtra("day");

            RadioGroup rday = (RadioGroup)findViewById(R.id.rgDay);
            if (day.equals("Monday")) {
                rday.check(R.id.Monday);
            }
            else if (day.equals("Tuesday")) {
                rday.check(R.id.Tuesday);
            }
            else if (day.equals("Wednesday")) {
                rday.check(R.id.Wednesday);
            }
            else if (day.equals("Thursday")) {
                rday.check(R.id.Thursday);
            }
            else if (day.equals("Friday")) {
                rday.check(R.id.Friday);
            }
            else if (day.equals("Saturday")) {
                rday.check(R.id.Saturday);
            }

            RadioGroup rtime = (RadioGroup)findViewById(R.id.rgTime);
            if (time.equals("9.00-10.20")) {
                rtime.check(R.id.rb1);
            }
            else if (time.equals("10.40-12.00")) {
                rtime.check(R.id.rb2);
            }
            else if (time.equals("13.00-14.20")) {
                rtime.check(R.id.rb3);
            }
            else if (time.equals("14.40-16.00")) {
                rday.check(R.id.rb4);
            }

            EditText etCode = (EditText) findViewById(R.id.etCode);
            etCode.setText(code);

            EditText etCR = (EditText) findViewById(R.id.etCredit);
            etCR.setText(Integer.toString(credit));

            Button btAddCourse = (Button) findViewById(R.id.btAddCourse);
            btAddCourse.setText("Edit Course");
        }

    }

    public void addClicked(View v) {
        EditText etCode = (EditText)findViewById(R.id.etCode);
        EditText etCredit = (EditText)findViewById(R.id.etCredit);
        RadioGroup rgDay = (RadioGroup)findViewById(R.id.rgDay);
        RadioGroup rgTime = (RadioGroup)findViewById(R.id.rgTime);

        String sCode = etCode.getText().toString();
        String sCredit = etCredit.getText().toString();

        if (sCode.trim().length() == 0 || sCredit.trim().length() == 0) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Both course code and credit are necessary.",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            Intent result = new Intent();
            result.putExtra("code", sCode);
            result.putExtra("credit", Integer.valueOf(sCredit));
            int rDayID = rgDay.getCheckedRadioButtonId();
            int rTimeID = rgTime.getCheckedRadioButtonId();
            String Day = ((RadioButton)findViewById(rDayID)).getText().toString();
            String Time = ((RadioButton)findViewById(rTimeID)).getText().toString();
            result.putExtra("day", Day);
            result.putExtra("time", Time);
            this.setResult(RESULT_OK, result);
            this.finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_course, menu);
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
