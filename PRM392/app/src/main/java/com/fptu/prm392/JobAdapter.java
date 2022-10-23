package com.fptu.prm392;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {

    private List<Job> mListJob;

    @SuppressLint("NotifyDataSetChanged")
    public void setData(List<Job> list) {
        this.mListJob = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new JobViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = mListJob.get(position);
        if(job == null) return;
        holder.tv_job_name.setText("name: " + job.getName());
        holder.tv_job_id.setText("id: " + job.getId());
        holder.tv_job_desc.setText("description: " + job.getDescription());
        holder.tv_job_status.setText("status: " + job.getStatus());
    }

    @Override
    public int getItemCount() {
        if (mListJob != null) return mListJob.size();
        return 0;
    }

    public class JobViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_job_name;
        private TextView tv_job_id;
        private TextView tv_job_desc;
        private TextView tv_job_status;
        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_job_name = itemView.findViewById(R.id.tv_name);
            tv_job_id = itemView.findViewById(R.id.tv_id);
            tv_job_desc = itemView.findViewById(R.id.tv_desc);
            tv_job_status = itemView.findViewById(R.id.tv_status);
        }
    }
}
