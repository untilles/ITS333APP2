package com.example.billiblahblaa.its333app2;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;


public class CourseManage extends ActionBarActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, ActionMode.Callback {

    CourseDBHelper helper;
    SimpleCursorAdapter adapter;
    long selectedId;
    ActionMode actionMode;

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
            case R.id.btCourse:
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

        if (requestCode == 77) {
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
                long newId = db.update("course", r, "_id = ?",
                        new String[]{Long.toString(selectedId)});

                if (newId != -1) {
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "Successfully updated the course",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
                else {
                    Toast t = Toast.makeText(this.getApplicationContext(),
                            "Unable to update the course",
                            Toast.LENGTH_SHORT);
                    t.show();
                }
                Cursor cursor = db.rawQuery("SELECT _id, code, (day || ' (' || time || ')') AS x FROM course;;", null);
                adapter.changeCursor(cursor);
                db.close();
            }
        }


        Log.d("course", "onActivityResult");

        listCourse();
    }


    @Override
    public void onDestroyActionMode(ActionMode mode) {
        actionMode = null;
    }

    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.menu_actionmode, menu);
        return true;
    }

    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete:
                deleteClicked();
                mode.finish();
                break;
            case R.id.menu_edit:
                editClicked();
                mode.finish();
                break;
            default:
                return false;
        }
        return true;
    }

    private void deleteClicked() {
        SQLiteDatabase db = helper.getWritableDatabase();
        int rowCount = db.delete("course", "_id = ?",
                new String[]{Long.toString(selectedId)});
        if (rowCount == 1) {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Deleted one course", Toast.LENGTH_SHORT);
            t.show();
        }
        else {
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Unable to delete the course",
                    Toast.LENGTH_SHORT);
            t.show();
        }
        Cursor cursor = db.rawQuery("SELECT _id, code, (day || ' (' || time || ')') AS x FROM course;", null);
        adapter.changeCursor(cursor);
        db.close();
    }

    private void editClicked() {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM course WHERE _id=?;",
                new String[]{Long.toString(selectedId)});
        if (c.getCount() == 1) {
            // Retrieved exactly one row
            c.moveToFirst();
            String code = c.getString(c.getColumnIndex("code"));
            int credit = c.getInt(c.getColumnIndex("credit"));
            String day = c.getString(c.getColumnIndex("day"));
            String time = c.getString(c.getColumnIndex("time"));
            Intent i = new Intent(this, AddCourse.class);
            i.putExtra("code", code);
            i.putExtra("credit", credit);
            i.putExtra("day", day);
            i.putExtra("time", time);
            startActivityForResult(i, 77);
        }
        else {
            // Unable to get the selected id
            Toast t = Toast.makeText(this.getApplicationContext(),
                    "Unable to retrieve the selected course",
                    Toast.LENGTH_SHORT);
            t.show();
        }
    }


    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        Log.d("course", id + " is clicked");
    }

    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        view.setSelected(true);
        selectedId = id;
        actionMode = this.startActionMode(this);
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
