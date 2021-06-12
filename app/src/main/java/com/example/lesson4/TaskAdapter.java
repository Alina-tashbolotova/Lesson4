package com.example.lesson4;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    public List<TaskModel> list = new ArrayList<>();
    private LayoutInflater inflater;
    MainActivity activity;
    OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public TaskAdapter(Context context,MainActivity activity){
        this.inflater = LayoutInflater.from(context);
        this.activity = activity;

    }

    public void addData(TaskModel taskModel) {
        list.add(taskModel);
        notifyDataSetChanged();
    }

    public void updateTask(TaskModel model,int poss){
        list.set(poss,model);
        notifyDataSetChanged();
    }

    public void deleteTask(int position) {
        list.remove(position);
        notifyDataSetChanged();
    }


    @Override
    public TaskAdapter.TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(TaskAdapter.TaskHolder holder, int position) {
        holder.bind(list.get(position));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TaskHolder extends RecyclerView.ViewHolder {
        TextView txtTitle, txtDescription;

        public TaskHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txt_title);
            txtDescription = itemView.findViewById(R.id.txt_description);
        }

        public void bind(TaskModel taskModel) {
            txtTitle.setText(taskModel.getTitle());
            txtDescription.setText(taskModel.getDescription());
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(itemView.getContext()).create();
                    dialog.setTitle("ВНИМАНИЕ!!!");
                    dialog.setMessage("Вы точно хотите удалить?");
                    dialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Да", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            deleteTask(getAdapterPosition());

                        }
                    });
                    dialog.setButton(AlertDialog.BUTTON_POSITIVE, "Нет", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    dialog.show();
                    return false;
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onClickItem(getAdapterPosition());

                }
            });

        }
    }
}
