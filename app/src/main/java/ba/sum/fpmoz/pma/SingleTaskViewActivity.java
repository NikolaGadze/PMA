package ba.sum.fpmoz.pma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    }
}