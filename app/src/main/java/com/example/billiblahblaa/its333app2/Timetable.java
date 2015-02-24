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
        Cursor cursor = db.rawQuery("SELECT _id, code, day, time FROM course WHERE day = 'Monday' AND time = '9.00-10.20';", null);

        String code = cursor.getString(1);

        TextView tvM1 = (TextView)findViewById(R.id.tvM1);
        tvM1.setText(code);

        /*adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, cursor,
                new String[] {"code"}, new int[] {android.R.id.text1},0);

        ListView lvM1 = (ListView)findViewById(R.id.lvM1);
        lvM1.setAdapter(adapter);*/
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
