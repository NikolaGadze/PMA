package ba.sum.fpmoz.pma;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fpmoz.pma.adapters.TaskImageAdapter;

public class ViewSingleTaskDataActivity extends AppCompatActivity {

    private String taskId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBarTaskData;
    private RecyclerView taskDataRecyclerView;
    private List<HandleTaskImageInformation> taskImageList = new ArrayList<>();
    private TaskImageAdapter taskImageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_single_task_data);

        progressBarTaskData = findViewById(R.id.progressBarTaskData);
        progressBarTaskData.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if (user == null) {
            //Log.d("TaskData", "User is not signed in");
            Toast.makeText(ViewSingleTaskDataActivity.this, "User not singed in!", Toast.LENGTH_LONG).show();
            return;
        }

        String taskName = getIntent().getStringExtra("taskName");
        taskId = getIntent().getStringExtra("taskId");
        getSupportActionBar().setTitle(taskName + " Data");

        taskDataRecyclerView = findViewById(R.id.taskDataRecyclerView);
        taskDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        taskImageAdapter = new TaskImageAdapter(taskImageList);
        taskDataRecyclerView.setAdapter(taskImageAdapter);

        loadTaskData();
    }

    private void loadTaskData() {
        DatabaseReference imagesReference = FirebaseDatabase.getInstance().getReference("images");
        imagesReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskImageList.clear();
                for (DataSnapshot imageSnapshot : dataSnapshot.getChildren()) {
                    HandleTaskImageInformation taskImage = imageSnapshot.getValue(HandleTaskImageInformation.class);
                    if (taskImage != null && taskImage.getTaskID().equals(taskId)) {
                        taskImageList.add(taskImage);
                    }
                }
                taskImageAdapter.notifyDataSetChanged();
                //Toast.makeText(ViewSingleTaskDataActivity.this, "Data fetched", Toast.LENGTH_LONG).show();
                progressBarTaskData.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressBarTaskData.setVisibility(View.GONE);
                //Toast.makeText(ViewSingleTaskDataActivity.this, "Error loading images: " + databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}