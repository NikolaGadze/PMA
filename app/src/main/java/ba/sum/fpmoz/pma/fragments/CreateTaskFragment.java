package ba.sum.fpmoz.pma.fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ba.sum.fpmoz.pma.ChangePasswordActivity;
import ba.sum.fpmoz.pma.HandleTaskInformation;
import ba.sum.fpmoz.pma.R;


public class CreateTaskFragment extends Fragment {
    private EditText taskName, taskDescription, taskDateOfCreation, taskDateOfCompletion;
    private Button addMember, createTask;
    private ProgressBar progressBar;
    private FirebaseAuth authProfile;

    private String taskId;

    private boolean isMemberAdded = false;
    private List<String> members = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Create Task");
        }

        taskName = view.findViewById(R.id.editText_task_name);
        taskDescription = view.findViewById(R.id.editText_task_description);
        taskDateOfCreation = view.findViewById(R.id.editText_task_date_of_creation);
        taskDateOfCompletion = view.findViewById(R.id.editText_task_date_of_completion);
        addMember = view.findViewById(R.id.add_member_button);
        createTask = view.findViewById(R.id.create_task_button);
        progressBar = view.findViewById(R.id.create_task_progress_bar);
        authProfile = FirebaseAuth.getInstance();

        taskDateOfCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Date Picker Dialog
                DatePickerDialog picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        taskDateOfCreation.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });


        taskDateOfCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                // Date Picker Dialog
                DatePickerDialog picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        taskDateOfCompletion.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                picker.show();
            }
        });



        // Set up the "Add Members" button
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show a dialog to add members
                // ...
                isMemberAdded = true;
                checkFieldsForEmptyValues();
            }
        });

        // Set up the "Create Task" button
        createTask.setEnabled(false);
        // TextWatcher added to the fields to enable the "Create Task" button when all the fields are filled
        taskName.addTextChangedListener(textWatcher);
        taskDescription.addTextChangedListener(textWatcher);
        taskDateOfCreation.addTextChangedListener(textWatcher);
        taskDateOfCompletion.addTextChangedListener(textWatcher);


        // Set up the "Add Members" button
        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Add Member");

                final EditText input = new EditText(getContext());
                builder.setView(input);

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String memberEmail = input.getText().toString();
                        if (android.util.Patterns.EMAIL_ADDRESS.matcher(memberEmail).matches()) {
                            String encodedMemberEmail = memberEmail.replace('.', ',');
                            DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                            database.child("EmailToUUID").child(encodedMemberEmail).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String memberUUID = dataSnapshot.getValue(String.class);
                                    if (memberUUID != null) {
                                        members.add(memberUUID);
                                        isMemberAdded = true;
                                        checkFieldsForEmptyValues();
                                    } else {
                                        Toast.makeText(getContext(), "User not found", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(getContext(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(getContext(), "Invalid email format", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.lime));
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.red));
            }
        });

        //"Create Task" button logic
        createTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);

                try {

                    String name = taskName.getText().toString();
                    String description = taskDescription.getText().toString();
                    String dateOfCreation = taskDateOfCreation.getText().toString();
                    String dateOfCompletion = taskDateOfCompletion.getText().toString();
                    String status = "In Progress";
                    Map<String, Boolean> membersMap = new HashMap<>();
                    for (String member : members) {
                        membersMap.put(member, true);
                    }
                    List<Image> images = new ArrayList<>();


                    String creatorUUID = authProfile.getCurrentUser().getUid();

                    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    String taskId = database.child("tasks").push().getKey();

                    HandleTaskInformation task = new HandleTaskInformation(name, description, dateOfCreation, dateOfCompletion, status, membersMap, images, creatorUUID, taskId);

                    //DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                    database.child("tasks").push().setValue(task).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container, new HomeFragment());
                                transaction.commit();

                                BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNavigationView);
                                bottomNavigationView.setSelectedItemId(R.id.home);

                            } else {

                                Exception e = task.getException();
                                Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                            }

                            progressBar.setVisibility(View.GONE);
                        }
                    });

                    for (String member : members) {
                        sendNotification(member);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });

    }



    private void sendNotification(String member) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "channelId")
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("New Task")
                .setContentText("You have been added to a new task.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(member.hashCode(), builder.build());
    }




    private final TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    void checkFieldsForEmptyValues(){
        String s1 = taskName.getText().toString();
        String s2 = taskDescription.getText().toString();
        String s3 = taskDateOfCreation.getText().toString();
        String s4 = taskDateOfCompletion.getText().toString();

        if(s1.isEmpty() || s2.isEmpty() || s3.isEmpty() || s4.isEmpty() || !isMemberAdded){
            createTask.setEnabled(false);
            createTask.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.gray));
        } else {
            createTask.setEnabled(true);
            createTask.setBackgroundTintList(ContextCompat.getColorStateList(getActivity(), R.color.dark_green));
        }
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_create_task, container, false);
    }

}