package com.example.kohendakanne;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kohendakanne.Models.TestModel;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Test extends AppCompatActivity {

    private RecyclerView firestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firestoreList = findViewById(R.id.firestore_list);

        Query query = firebaseFirestore.collection("Users");
        FirestoreRecyclerOptions<TestModel> options = new FirestoreRecyclerOptions.Builder<TestModel>()
                .setQuery(query, TestModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<TestModel, TestViewHolder>(options) {
            @NonNull
            @Override
            public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_single_item, parent, false);

                return new TestViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TestViewHolder holder, int position, @NonNull TestModel model) {
                holder.list_name.setText(model.getName());

            }
        };

        firestoreList.setHasFixedSize(true);
        firestoreList.setLayoutManager(new LinearLayoutManager(this));
        firestoreList.setAdapter(adapter);
    }

    private class TestViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);

            list_name = itemView.findViewById(R.id.list_name);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
