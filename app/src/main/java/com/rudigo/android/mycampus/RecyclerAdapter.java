package com.rudigo.android.mycampus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 9/20/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private List<Lecture> lectures;

    private Context context;

    public RecyclerAdapter(List<Lecture> lectures, Context context) {
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
        public TextView courseCode;
        public TextView courseTitle;
        public TextView venue;
        public TextView time;

        public ViewHolder(View itemView) {
            super(itemView);

            courseCode = (TextView) itemView.findViewById(R.id.courseCode);
            courseTitle = (TextView) itemView.findViewById(R.id.courseTitle);
            venue = (TextView) itemView.findViewById(R.id.venue);
            time = (TextView) itemView.findViewById(R.id.lectureTime);


        }
    }
}
