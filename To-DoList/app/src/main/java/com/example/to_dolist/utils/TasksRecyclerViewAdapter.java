package com.example.to_dolist.utils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.to_dolist.R;
import com.example.to_dolist.data.model.Task;

import org.jetbrains.annotations.NotNull;

public class TasksRecyclerViewAdapter
        extends RecyclerView.Adapter<TasksRecyclerViewAdapter.MyViewHolder> {
    private final List<Task> mDataset;
    private final ContextProvider mContextProvider;
    private static MyClickListener myClickListener;

    public static class MyViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        TextView tvTitle;
        TextView tvDescription;
        TextView tvStatus;
        TextView tvStatusDeleted;
        ImageButton ibShare;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.item_task_tv_title);
            tvDescription = (TextView) itemView.findViewById(R.id.item_task_tv_description);
            tvStatus = (TextView) itemView.findViewById(R.id.item_task_tv_status);
            tvStatusDeleted = (TextView) itemView.findViewById(R.id.item_task_tv_status_deleted);
            ibShare = (ImageButton) itemView.findViewById(R.id.item_task_ib_share);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();

            myClickListener.onItemClick(position, view);
        }
    }

    public TasksRecyclerViewAdapter(ContextProvider myContextProvider, List<Task> myDataset) {
        mDataset = myDataset;
        mContextProvider = myContextProvider;
    }

    @NotNull
    @Override
    public TasksRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent,
                                                                    int viewType) {
        try {
            View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.cardview_item_task, parent, false);

            return new MyViewHolder(view);
        } catch (Exception e) {
            Log.e("AppDebug", "onCreateViewHolder", e);
            throw e;
        }
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, final int position) {
        holder.tvTitle.setText(mDataset.get(position).getTitle());
        holder.tvDescription.setText(mDataset.get(position).getDescription());
        if (mDataset.get(position).isCompleted()) {
            holder.tvStatus.setText(R.string.item_task_tv_status_completed_text);
            holder.tvStatus.setTextColor(
                    ContextCompat.getColor(mContextProvider.getContext(), R.color.goldyPrimary)
            );
        } else {
            holder.tvStatus.setText(R.string.item_task_tv_status_uncompleted_text);
            holder.tvStatus.setTextColor(
                    ContextCompat.getColor(mContextProvider.getContext(), R.color.goldyAccent)
            );
        }
        if (mDataset.get(position).getDeleted_at() == null) {
            holder.tvStatusDeleted.setVisibility(View.INVISIBLE);
            holder.ibShare.setVisibility(View.VISIBLE);
            holder.ibShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnIbShareClick(position);
                }
            });
        } else {
            holder.tvStatusDeleted.setVisibility(View.VISIBLE);
            holder.ibShare.setVisibility(View.GONE);
        }
    }

    private void setOnIbShareClick(int position) {
        Task task = mDataset.get(position);
        String status = task.isCompleted() ? "Completed" : "Not Completed Yet";
        String taskPlainText = "Task Title: \n"
                                + "-> " + task.getTitle() + "\n\n"
                                + "Task Description: \n"
                                + "-> " + task.getDescription() +"\n\n"
                                + "Status: \n"
                                + "-> " + status;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        Context context = mContextProvider.getContext();

        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, taskPlainText);
        try {
            context.startActivity(shareIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        TasksRecyclerViewAdapter.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    public interface ContextProvider {
        Context getContext();
    }
}