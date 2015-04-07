package com.myfriends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends ActionBarActivity {

    EditText et;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FriendDBHandler dbHandler = new FriendDBHandler(getApplicationContext(), "friendDB");
        List<Friend> friendsList = dbHandler.getAllFriends();

        lv = (ListView) findViewById(R.id.listView);
        et = (EditText) findViewById(R.id.editText);

        et.setText("friendDB");

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View view, int position, long id) {
                FriendDBHandler dbHandler = new FriendDBHandler(getApplicationContext(), "friendDB");
                List<Friend> friendsList = dbHandler.getAllFriends();

                Friend friend = friendsList.get(position);
                Intent intent = new Intent(getBaseContext(), DetailsActivity.class);
                intent.putExtra("friendName", friend.getName());
                intent.putExtra("friendEmail", friend.getEmail());
                intent.putExtra("friendPhone", friend.getPhoneNumber());

                startActivity(intent);
            }
        });


        et.addTextChangedListener(new TextWatcher(){
            public void afterTextChanged(Editable s) {
                if (et.getText().toString().equals("friendDB")) {
                    FriendDBHandler dbHandler = new FriendDBHandler(getApplicationContext(), "friendDB");
                    List<Friend> friendsList = dbHandler.getAllFriends();
                    ArrayAdapter<Friend> arrayAdapter = new ArrayAdapter<Friend>(getBaseContext(), android.R.layout.simple_list_item_1, friendsList );
                    lv.setAdapter(arrayAdapter);
                }
                else {
                    lv.setAdapter(null);
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });


        ArrayAdapter<Friend> arrayAdapter = new ArrayAdapter<Friend>(this, android.R.layout.simple_list_item_1, friendsList );
        lv.setAdapter(arrayAdapter);
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_help) {
            Intent intent = new Intent(getBaseContext(), Help.class);
            startActivity(intent);
        }

        if (id == R.id.action_about) {
            Intent intent = new Intent(getBaseContext(), About.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
