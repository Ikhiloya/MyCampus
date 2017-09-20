package com.rudigo.android.mycampus.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rudigo.android.mycampus.models.Lecture;
import com.rudigo.android.mycampus.R;

import java.util.ArrayList;


public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private ArrayList<Lecture> lectures;

    private Context context;

    public RecyclerAdapter(ArrayList<Lecture> lectures, Context context) {
        this.lectures = lectures;
        this.context = context;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_upcoming, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
        Lecture lecture = lectures.get(position);

        holder.courseCode.setText(lecture.getCourseCode());
        holder.courseTitle.setText(lecture.getCourseTitle());
        holder.venue.setText(lecture.getVenue());
        holder.time.setText(String.valueOf(lecture.getTime()));
    }

    @Override
    public int getItemCount() {
        return lectures.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView courseCode;
        private TextView courseTitle;
        private TextView venue;
        private TextView time;

        public ViewHolder(View itemView) {
            super(itemView);

            courseCode = (TextView) itemView.findViewById(R.id.courseCode);
            courseTitle = (TextView) itemView.findViewById(R.id.courseTitle);
            venue = (TextView) itemView.findViewById(R.id.venue);
            time = (TextView) itemView.findViewById(R.id.lectureTime);


        }
    }
}
