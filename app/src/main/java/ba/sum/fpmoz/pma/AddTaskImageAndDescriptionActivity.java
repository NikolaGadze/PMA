package ba.sum.fpmoz.pma;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.UUID;

public class AddTaskImageAndDescriptionActivity extends AppCompatActivity {

    private ImageView imageView;
    private EditText descriptionEditText;
    private Button uploadButton;
    private Uri imageUri;
    private String taskId;

    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task_image_and_description);

        mAuth = FirebaseAuth.getInstance();

        String taskName = getIntent().getStringExtra("taskName");
        taskId = getIntent().getStringExtra("taskId");
        getSupportActionBar().setTitle("Load " + taskName + " Data");

        imageView = findViewById(R.id.imageView);
        descriptionEditText = findViewById(R.id.descriptionEditText);
        uploadButton = findViewById(R.id.uploadButton);

        uploadButton.setEnabled(false);

        taskId = getIntent().getStringExtra("taskId");

        Log.d("AddTaskImageAndDescriptionActivity", "Task ID: " + taskId);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkInputs();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageToFirebaseStorage();
            }
        });
    }

    private void checkInputs() {
        if (imageUri != null && !descriptionEditText.getText().toString().isEmpty()) {
            uploadButton.setEnabled(true);
            uploadButton.setBackgroundTintList(ContextCompat.getColorStateList(AddTaskImageAndDescriptionActivity.this, R.color.dark_green));
        } else {
            uploadButton.setEnabled(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

            // Log the imageUri
            Log.d("AddTaskImageAndDescriptionActivity", "Image URI: " + imageUri);

            checkInputs();


        }
    }


    private void uploadImageToFirebaseStorage() {
        if (imageUri != null) {
            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + UUID.randomUUID().toString());
            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    // Get the current user
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String fullName = user.getDisplayName();

                                    HandleTaskImageInformation handleTaskImageInformation = new HandleTaskImageInformation(taskId, uri.toString(), descriptionEditText.getText().toString(), fullName);

                                    // Get a reference to your Firebase database
                                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("tasks").child(taskId);
                                    databaseReference.setValue(handleTaskImageInformation)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    // Clear the descriptionEditText and imageView
                                                    descriptionEditText.setText("");
                                                    imageView.setImageDrawable(null);

                                                    Log.d("AddTaskImageAndDescriptionActivity", "Database write successful");

                                                    // Show a toast message
                                                    Toast.makeText(AddTaskImageAndDescriptionActivity.this, "Upload successful", Toast.LENGTH_SHORT).show();

                                                    // Start the ViewSingleTaskInfoActivity
                                                    Intent intent = new Intent(AddTaskImageAndDescriptionActivity.this, SingleTaskViewActivity.class);
                                                    startActivity(intent);
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Log.e("AddTaskImageAndDescriptionActivity", "Database write failed", e);
                                                    // Show a toast message
                                                    Toast.makeText(AddTaskImageAndDescriptionActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle any errors
                            Log.e("Upload error", e.getMessage(), e);
                        }
                    });
        }
    }
}