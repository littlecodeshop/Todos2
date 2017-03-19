package com.littlecodeshop.todos;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TodoListActivity extends AppCompatActivity {

    private static final String TAG = "TodoListActivity";
    private RecyclerView recyclerView;
    private DatabaseReference todoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //database
        todoRef = FirebaseDatabase.getInstance().getReference("todos");


        setContentView(R.layout.activity_todo_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //scroll the recycler
                recyclerView.scrollToPosition(recyclerView.getAdapter().getItemCount());



                // Write a message to the database
                String key = todoRef.push().getKey();


                //est ce que ca va rajouter ??


                final EditText input = new EditText(view.getContext());
                final String thekey = key;

                AlertDialog.Builder builder = new AlertDialog.Builder(TodoListActivity.this);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String value = input.getText().toString();
                        Log.d(TAG, "onClick: " + value);
                        //create a new todo !
                        Todo newTodo = new Todo();
                        newTodo.setText(value);
                        newTodo.setChecked(false);

                        //here I update the text of todo
                        todoRef.child(thekey).setValue(newTodo);
                        Log.d(TAG, "onClick TODO : " + newTodo);


                    }
                });
                AlertDialog alert = builder.create();
                alert.setTitle("Train plan name");
                alert.setMessage("enter a name:");
                alert.setView(input);


                alert.show();



                Snackbar.make(view, "Todo created !", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //should I load the data here ??
        this.recyclerView = (RecyclerView) findViewById(R.id.todorecycler);
        this.recyclerView.setAdapter(new TodoAdapter(todoRef));
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
