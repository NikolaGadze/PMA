package ba.sum.fpmoz.pma.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ba.sum.fpmoz.pma.HandleTaskInformation;
import ba.sum.fpmoz.pma.R;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<HandleTaskInformation> tasks;
    private String currentUserUUID;

    public TaskAdapter(List<HandleTaskInformation> tasks, String currentUserUUID) {
        this.tasks = tasks;
        this.currentUserUUID = currentUserUUID;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_row, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        HandleTaskInformation task = tasks.get(position);
        holder.nameTextView.setText(task.name);
        holder.dateOfCreationTextView.setText(task.dateOfCreation);
        holder.dateOfCompletionTextView.setText(task.dateOfCompletion);
        if (task.creatorUUID.equals(currentUserUUID)) {
            holder.ownerBadge.setVisibility(View.VISIBLE);
        } else {
            holder.ownerBadge.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView dateOfCreationTextView;
        TextView dateOfCompletionTextView;
        View ownerBadge;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.textView_card_view_task_name);
            dateOfCreationTextView = itemView.findViewById(R.id.date1);
            dateOfCompletionTextView = itemView.findViewById(R.id.date2);
            ownerBadge = itemView.findViewById(R.id.ownerBadge);
        }
    }
}