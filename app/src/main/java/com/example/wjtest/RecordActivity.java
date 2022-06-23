package com.example.wjtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wjtest.Model.RecordInfo;
import com.example.wjtest.ViewHolder.RecordViewHolder;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class RecordActivity extends AppCompatActivity {

    private Button btn_add_record;
    private String userID;
    private TextView tv_empty;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference recordRef;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);

        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        btn_add_record = (Button) findViewById(R.id.btn_add_history);
        tv_empty = (TextView) findViewById(R.id.tv_record_empty);
        recyclerView = findViewById(R.id.record_recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        btn_add_record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecordActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

//        recordRef = (DatabaseReference) FirebaseDatabase.getInstance().getReference("Record").orderByChild("Record").equalTo(userID);
        recordRef = FirebaseDatabase.getInstance().getReference("Record");
        query = recordRef.orderByChild("userID").equalTo(userID);
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<RecordInfo> options =  new FirebaseRecyclerOptions.Builder<RecordInfo>().setQuery(query, RecordInfo.class).build();

        FirebaseRecyclerAdapter<RecordInfo, RecordViewHolder> adapter = new FirebaseRecyclerAdapter<RecordInfo, RecordViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecordViewHolder holder, int i, @NonNull final RecordInfo model) {
                holder.date.setText(model.getDate());
                holder.activity.setText(model.getActivity());
                holder.calories.setText(model.getCalories());
                holder.points.setText(model.getPoints());

                String id = getSnapshots().getSnapshot(i).getKey();

            }

            @NonNull
            @Override
            public RecordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_list, parent, false);
                RecordViewHolder holder = new RecordViewHolder(view);
                tv_empty.setVisibility(View.INVISIBLE);
                return holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }
}