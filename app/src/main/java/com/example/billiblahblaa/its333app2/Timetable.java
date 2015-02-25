package com.example.billiblahblaa.its333app2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class Timetable extends ActionBarActivity {

    CourseDBHelper helper;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timetable);

        helper = new CourseDBHelper(this);

        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM course;", null);
        cursor.moveToFirst();
        int i,tcr = 0;
        for(i=0; i<cursor.getCount(); i++) {
            String course = cursor.getString(1);
            int cr = cursor.getInt(2);
            String day = cursor.getString(3);
            String time = cursor.getString(4);

            /*TextView tvM1 = (TextView)findViewById(R.id.tvM1);
            TextView tvM2 = (TextView)findViewById(R.id.tvM2);
            TextView tvM3 = (TextView)findViewById(R.id.tvM3);
            tvM1.setText(course);
            tvM2.setText(day);
            tvM3.setText(time);*/
            tcr = tcr + cr;

            if(day.compareTo("Monday") == 0) {
                if(time.compareTo("9.00-10.20") == 0) {
                    TextView tvM1 = (TextView)findViewById(R.id.tvM1);
                    tvM1.setText(course);
                } else if(time.compareTo("10.40-12.00") == 0) {
                    TextView tvM2 = (TextView)findViewById(R.id.tvM2);
                    tvM2.setText(course);
                } else if(time.compareTo("13.00-14.20") == 0) {
                    TextView tvM3 = (TextView)findViewById(R.id.tvM3);
                    tvM3.setText(course);
                } else if(time.compareTo("14.40-16.00") == 0) {
                    TextView tvM4 = (TextView)findViewById(R.id.tvM4);
                    tvM4.setText(course);
                }
            } else if(day.compareTo("Tuesday") == 0) {
                if(time.compareTo("9.00-10.20") == 0) {
                    TextView tvT1 = (TextView)findViewById(R.id.tvT1);
                    tvT1.setText(course);
                } else if(time.compareTo("10.40-12.00") == 0) {
                    TextView tvT2 = (TextView)findViewById(R.id.tvT2);
                    tvT2.setText(course);
                } else if(time.compareTo("13.00-14.20") == 0) {
                    TextView tvT3 = (TextView)findViewById(R.id.tvT3);
                    tvT3.setText(course);
                } else if(time.compareTo("14.40-16.00") == 0) {
                    TextView tvT4 = (TextView)findViewById(R.id.tvT4);
                    tvT4.setText(course);
                }
            } else if(day.compareTo("Wednesday") == 0) {
                if(time.compareTo("9.00-10.20") == 0) {
                    TextView tvW1 = (TextView)findViewById(R.id.tvW1);
                    tvW1.setText(course);
                } else if(time.compareTo("10.40-12.00") == 0) {
                    TextView tvW2 = (TextView)findViewById(R.id.tvW2);
                    tvW2.setText(course);
                } else if(time.compareTo("13.00-14.20") == 0) {
                    TextView tvW3 = (TextView)findViewById(R.id.tvW3);
                    tvW3.setText(course);
                } else if(time.compareTo("14.40-16.00") == 0) {
                    TextView tvW4 = (TextView)findViewById(R.id.tvW4);
                    tvW4.setText(course);
                }
            } else if(day.compareTo("Thursday") == 0) {
                if(time.compareTo("9.00-10.20") == 0) {
                    TextView tvTH1 = (TextView)findViewById(R.id.tvTH1);
                    tvTH1.setText(course);
                } else if(time.compareTo("10.40-12.00") == 0) {
                    TextView tvTH2 = (TextView)findViewById(R.id.tvTH2);
                    tvTH2.setText(course);
                } else if(time.compareTo("13.00-14.20") == 0) {
                    TextView tvTH3 = (TextView)findViewById(R.id.tvTH3);
                    tvTH3.setText(course);
                } else if(time.compareTo("14.40-16.00") == 0) {
                    TextView tvTH4 = (TextView)findViewById(R.id.tvTH4);
                    tvTH4.setText(course);
                }
            } else if(day.compareTo("Friday") == 0) {
                if(time.compareTo("9.00-10.20") == 0) {
                    TextView tvF1 = (TextView)findViewById(R.id.tvF1);
                    tvF1.setText(course);
                } else if(time.compareTo("10.40-12.00") == 0) {
                    TextView tvF2 = (TextView)findViewById(R.id.tvF2);
                    tvF2.setText(course);
                } else if(time.compareTo("13.00-14.20") == 0) {
                    TextView tvF3 = (TextView)findViewById(R.id.tvF3);
                    tvF3.setText(course);
                } else if(time.compareTo("14.40-16.00") == 0) {
                    TextView tvF4 = (TextView)findViewById(R.id.tvF4);
                    tvF4.setText(course);
                }
            } else if(day.compareTo("Saturday") == 0) {
                if(time.compareTo("9.00-10.20") == 0) {
                    TextView tvS1 = (TextView)findViewById(R.id.tvS1);
                    tvS1.setText(course);
                } else if(time.compareTo("10.40-12.00") == 0) {
                    TextView tvS2 = (TextView)findViewById(R.id.tvS2);
                    tvS2.setText(course);
                } else if(time.compareTo("13.00-14.20") == 0) {
                    TextView tvS3 = (TextView)findViewById(R.id.tvS3);
                    tvS3.setText(course);
                } else if(time.compareTo("14.40-16.00") == 0) {
                    TextView tvS4 = (TextView)findViewById(R.id.tvS4);
                    tvS4.setText(course);
                }
            }
            cursor.moveToNext();
        }

        TextView tvCR = (TextView)findViewById(R.id.tvCR);
        tvCR.setText(Integer.toString(tcr));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_timetable, menu);
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
