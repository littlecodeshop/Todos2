package com.littlecodeshop.todos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TodoListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //database
        databaseReference = FirebaseDatabase.getInstance().getReference();

        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create a new todo !
                Todo newTodo = new Todo();
                newTodo.setText("Bonjour les mecs");
                newTodo.setChecked(false);
                // Write a message to the database
                String key = databaseReference.child("todos").push().getKey();

                databaseReference.child("todos").child(key).setValue(newTodo);

                Snackbar.make(view, "Todo created !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        String[] data = new String[]{"Hello","world","youhou","pipipi","couocou"};
        //should I load the data here ??
        this.recyclerView = (RecyclerView) findViewById(R.id.todorecycler);
        this.recyclerView.setAdapter(new TodoAdapter(data));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_list, menu);
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
