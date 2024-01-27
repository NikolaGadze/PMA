package ba.sum.fpmoz.pma.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ba.sum.fpmoz.pma.HandleTaskImageInformation;
import ba.sum.fpmoz.pma.R;

public class TaskImageAdapter extends RecyclerView.Adapter<TaskImageAdapter.TaskImageViewHolder> {

    private List<HandleTaskImageInformation> taskImageList;

    public TaskImageAdapter(List<HandleTaskImageInformation> taskImageList) {
        this.taskImageList = taskImageList;
    }

    @NonNull
    @Override
    public TaskImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_recycler_view_block, parent, false);
        return new TaskImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskImageViewHolder holder, int position) {
        HandleTaskImageInformation taskImage = taskImageList.get(position);

        holder.taskNameTextView.setText(taskImage.getUsersFullName());
        Picasso.get()
                .load(taskImage.getUrl())
                .into(holder.taskImageView);
        holder.taskImageDescriptionTextView.setText(taskImage.getDescription());
        Log.d("TaskImageAdapter", "Binding data for position: " + position + ", data: " + taskImageList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskImageList.size();
    }

    public static class TaskImageViewHolder extends RecyclerView.ViewHolder {
        TextView taskNameTextView;
        ImageView taskImageView;
        TextView taskImageDescriptionTextView;

        public TaskImageViewHolder(@NonNull View itemView) {
            super(itemView);
            taskNameTextView = itemView.findViewById(R.id.textview_name);
            taskImageView = itemView.findViewById(R.id.imageView_task);
            taskImageDescriptionTextView = itemView.findViewById(R.id.textview_image_description);
        }
    }
}