package com.example.jaygold.examproject;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.jaygold.examproject.dataBase.SQLiteHeplper;
import com.example.jaygold.examproject.dataBase.TableManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteHeplper sqlHelper;
    private ListView listView;
    private ArrayAdapter<String> adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sqlHelper = new SQLiteHeplper(this);
        listView = (ListView) findViewById(R.id.listView);

        update();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_record:
                final EditText taskEditText = new EditText(this);
                AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Добавить новую запись")
                        .setMessage("Что ты хочешь делать дальше?")
                        .setView(taskEditText)
                        .setPositiveButton("Добавить", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = sqlHelper.getWritableDatabase();
                                ContentValues values = new ContentValues();
                                values.put(TableManager.RecordEntry.COL_RECORD_TITLE, task);
                                db.insertWithOnConflict(TableManager.RecordEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                update();
                            }
                        })
                        .setNegativeButton("Отмена", null)
                        .create();
                dialog.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void deleteRecord(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.record_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = sqlHelper.getWritableDatabase();
        db.delete(TableManager.RecordEntry.TABLE,
                TableManager.RecordEntry.COL_RECORD_TITLE + " = ?",
                new String[]{task});
        db.close();
        update();
    }

    private void update() {
        ArrayList<String> recordList = new ArrayList<>();
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        Cursor cursor = db.query(TableManager.RecordEntry.TABLE,
                new String[]{TableManager.RecordEntry._ID, TableManager.RecordEntry.COL_RECORD_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TableManager.RecordEntry.COL_RECORD_TITLE);
            recordList.add(cursor.getString(idx));
        }

        if (adapters == null) {
            adapters = new ArrayAdapter<>(this,
                    R.layout.items,
                    R.id.record_title,
                    recordList);
            listView.setAdapter(adapters);
        } else {
            adapters.clear();
            adapters.addAll(recordList);
            adapters.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }
}
