package ba.sum.fpmoz.pma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

public class SingleTaskViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_task_view);

        Intent intent = getIntent();
        HandleTaskInformation task = (HandleTaskInformation) intent.getSerializableExtra("task");

        getSupportActionBar().setTitle(task.getName());

        String currentUserUUID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        ImageView creatorBadge = findViewById(R.id.creator_indicator);
        TextView creatorText = findViewById(R.id.creator_text);

        if (task.getCreatorUUID().equals(currentUserUUID)) {
            creatorBadge.setVisibility(View.VISIBLE);
            creatorText.setVisibility(View.VISIBLE);
        } else {
            creatorBadge.setVisibility(View.GONE);
            creatorText.setVisibility(View.GONE);
        }

        TextView description = findViewById(R.id.description);
        description.setText(task.getDescription());

        TextView dateOfCreation = findViewById(R.id.date_of_creation);
        dateOfCreation.setText(task.getDateOfCreation());

        TextView dateOfCompletion = findViewById(R.id.date_of_completion);
        dateOfCompletion.setText(task.getDateOfCompletion());

        TextView status = findViewById(R.id.status_value);
        status.setText(task.getStatus());

        FloatingActionButton fabViewMemberData = findViewById(R.id.fab_view_member_data);

        fabViewMemberData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskId = task.getTaskId();
                if (taskId != null) {
                    Intent intent = new Intent(SingleTaskViewActivity.this, ViewSingleTaskDataActivity.class);
                    intent.putExtra("taskName", task.getName()); // Pass the name of the task
                    intent.putExtra("taskId", taskId); // Pass the id of the task
                    intent.putExtra("taskFieldId", task.getTaskId()); //Pass the id of the task that is connected to Images node
                    startActivity(intent);
                } else {
                    Log.e("SingleTaskViewActivity", "Task ID is null");
                }
            }
        });





        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String taskId = task.getTaskId();
                if (taskId != null) {
                    Intent intent = new Intent(SingleTaskViewActivity.this, AddTaskImageAndDescriptionActivity.class);
                    intent.putExtra("taskName", task.getName()); // Pass the name of the task
                    intent.putExtra("taskId", taskId); // Pass the id of the task
                    startActivity(intent);
                } else {
                    Log.e("SingleTaskViewActivity", "Task ID is null");
                }
            }
        });
    }
}