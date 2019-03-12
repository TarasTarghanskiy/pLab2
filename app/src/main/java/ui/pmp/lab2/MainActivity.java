package ui.pmp.lab2;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SQLiteDatabase database;

    private final String[] names = new String[] {// activity_main
            "Simple List", "add to S.L.", "Scroll View", "Image View",
            "test", "test", "test", "test", "test",
            "test", "test", "test", "test", "test"
    };

    private ArrayList<String> simpleList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); // activity_main
        menu();
    }

    private void menu() {
        database = new DBHelper(this).getWritableDatabase();

        ListView listView = (ListView)findViewById(R.id.listMenu); // activity_main

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, // activity_main
                android.R.layout.simple_list_item_1, names);

        listView.setAdapter(adapter); // activity_main

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
                                    long id) { // activity_main
                switch ((int)id) {
                    case  0:
                        setSimpleList(); // simple list
                        break;
                    case 1:
                        setContentView(R.layout.enter); // add to S.L.
                        break;
                    case 2: // Scroll View
                        setContentView(R.layout.more_text);
                        TextView text = (TextView)findViewById(R.id.textview);
                        StringBuilder s = new StringBuilder();
                        for (int i = 0; i < 1000; i++) {
                            String m = "test number "+ i +" \n";
                            s.append(m);
                        }
                        text.setText(s);
                        break;
                    case 3: // Image View
                        setContentView(R.layout.picture);
                        break;
                        default: // test
                            Toast.makeText(getApplicationContext(), "empty",
                                    Toast.LENGTH_SHORT).show();

                }
            }
        });
    }

    public void onEnterTextButtonClick(View view) { //SEND
        EditText editText = (EditText) findViewById(R.id.editText); // поле вводу

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBHelper.KEY_NAME, editText.getText().toString());

        database.insert(DBHelper.TEST_TABLE, null, contentValues);

        Toast.makeText(getApplicationContext(), "text was added",
                Toast.LENGTH_SHORT).show();
    }

    private void setSimpleList() {
        setContentView(R.layout.list);

        Cursor cursor = database.query(DBHelper.TEST_TABLE, null,null, null, null, null, null);

        if (!cursor.moveToFirst()) {
            Toast.makeText(getApplicationContext(), "simple list is empty",
                    Toast.LENGTH_SHORT).show();
        }   else {
            ListView listView = (ListView) findViewById(R.id.simpleList);
            ArrayList<String> arrayList = new ArrayList<>();
            do {
                arrayList.add(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME)));
            } while (cursor.moveToNext());

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, arrayList);

            listView.setAdapter(adapter);
        }

        cursor.close();

//        if (simpleList.isEmpty()){
//            Toast.makeText(getApplicationContext(), "simple list is empty",
//                    Toast.LENGTH_SHORT).show();
//        } else {
//            ListView listView = (ListView) findViewById(R.id.simpleList);
//
//            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
//                    android.R.layout.simple_list_item_1, simpleList);
//
//            listView.setAdapter(adapter);
//        }
    }

    public void onBackButtonClick(View view)
    {
        setContentView(R.layout.activity_main);
        menu();
    }
}
