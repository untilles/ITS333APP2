package com.example.billiblahblaa.its333app2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class CourseManage extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    CourseDBHelper helper;
    SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manage);

        helper = new CourseDBHelper(this);

        listCourse();

    }

    public void buttonClicked(View v) {
        int id = v.getId();
        Intent i;

        switch(id) {
            case R.id.btAdd:
                i = new Intent(this, AddCourse.class);
                startActivityForResult(i, 88);
                break;

            case R.id.btConfirm:
                this.finish();
                break;

            case R.id.btClear:
                SQLiteDatabase db = helper.getWritableDatabase();
                int n_rows = db.delete("course", "", null);
                listCourse();
                break;
        }
    }

    public void listCourse() {

        SQLiteDatabase db2 = helper.getReadableDatabase();
        Cursor cursor = db2.rawQuery("SELECT _id, code, (day || ' (' || time || ')') AS x FROM course;", null);

        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2, cursor,
                new String[] {"code", "x"}, new int[] {android.R.id.text1,android.R.id.text2},0);

        ListView lv = (ListView)findViewById(R.id.listView);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 88) {
            if (resultCode == RESULT_OK) {
                String code = data.getStringExtra("code");
                int credit = data.getIntExtra("credit", 0);
                String day = data.getStringExtra("day");
                String time = data.getStringExtra("time");

                SQLiteDatabase db = helper.getWritableDatabase();
                ContentValues r = new ContentValues();
                r.put("code", code);
                r.put("credit", credit);
                r.put("day", day);
                r.put("time", time);
                long new_id = db.insert("course", null, r);
            }
        }

        Log.d("course", "onActivityResult");

        listCourse();
    }

    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Log.d("course", id + " is clicked");
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();

        int n = db.delete("course", "_id = ?", new String[] {Long.toString(id)});

        if (n == 1) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Successfully deleted the selected item.", Toast.LENGTH_SHORT);

            t.show();

            Cursor cursor = db.rawQuery("SELECT _id, code, (day || ' (' || time || ')') AS x FROM course;", null);

            adapter.changeCursor(cursor);
        }

        db.close();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_manage, menu);
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
