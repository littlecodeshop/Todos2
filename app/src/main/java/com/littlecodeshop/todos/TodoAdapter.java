package com.littlecodeshop.todos;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by rribier on 17/03/2017.
 */

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.ViewHolder>{

    private static final String TAG = "TodoAdapter";
    private DatabaseReference mTodosRef;

    //we store the todos in a map
    private HashMap<String, Todo> mTodos;
    private ArrayList<String> mTodoKeys;

    public TodoAdapter(DatabaseReference todosRef) {
        mTodos = new HashMap<>();
        mTodoKeys = new ArrayList<>();
        this.mTodosRef = todosRef;
        ChildEventListener todosListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildAdded() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
                Todo todo = dataSnapshot.getValue(Todo.class);
                mTodos.put(dataSnapshot.getKey(), todo);
                mTodoKeys.add(dataSnapshot.getKey());
                Log.d(TAG, "onChildAdded: " + mTodos.toString());
                notifyItemInserted(mTodoKeys.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildChanged() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
                Todo todo = dataSnapshot.getValue(Todo.class);
                mTodos.put(dataSnapshot.getKey(), todo);
                //should update the data
                notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onChildRemoved() called with: dataSnapshot = [" + dataSnapshot + "]");
                //retirer de la liste
                Todo todo = dataSnapshot.getValue(Todo.class);
                mTodos.remove(dataSnapshot.getKey());
                mTodoKeys.remove(dataSnapshot.getKey());
                notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Log.d(TAG, "onChildMoved() called with: dataSnapshot = [" + dataSnapshot + "], s = [" + s + "]");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled() called with: databaseError = [" + databaseError + "]");
            }
        };

        mTodosRef.addChildEventListener(todosListener);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        CardView v = (CardView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_item_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String key = mTodoKeys.get(position);
        holder.getTextView().setText(mTodos.get(key).getText());
        holder.getImageView().setImageResource(R.drawable.france_paris_eiffel_tower);
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private final TextView textView;
        private final ImageView imageView;

        public ViewHolder(View v) {
            super(v);
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "Element " + getAdapterPosition() + " clicked.");
                }
            });
            textView = (TextView) v.findViewById(R.id.textView);
            imageView = (ImageView) v.findViewById(R.id.imageView);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }

    }
}
