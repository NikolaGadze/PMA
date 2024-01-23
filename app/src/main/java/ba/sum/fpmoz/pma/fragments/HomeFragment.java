package ba.sum.fpmoz.pma.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ba.sum.fpmoz.pma.HandleTaskInformation;
import ba.sum.fpmoz.pma.R;
import ba.sum.fpmoz.pma.adapters.TaskAdapter;


public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setHasOptionsMenu(true);

        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Home");
        }

    }

    /*
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.mRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference();
        database.child("tasks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<HandleTaskInformation> tasks = new ArrayList<>();
                for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                    HandleTaskInformation task = taskSnapshot.getValue(HandleTaskInformation.class);
                    if (task.creatorUUID.equals(currentUser.getUid()) || task.members.containsKey(currentUser.getUid())) {
                        tasks.add(task);
                    }
                }
                TaskAdapter adapter = new TaskAdapter(tasks, currentUser.getUid());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error...
            }
        });

        return view;
    }




}