package com.rudigo.android.mycampus;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by user on 9/20/2017.
 */

public class Lecture implements Parcelable {
    private String courseCode;
    private String courseTitle;
    private String venue;
    private String lecturer;
    private long time;


    //implements the constructor of the Parcelable Interface
    public Lecture() {
        super();

    }

    //read: get from the fields
    public Lecture(Parcel parcel) {
        this.courseCode = parcel.readString();
        this.courseTitle = parcel.readString();
        this.venue = parcel.readString();
        this.lecturer = parcel.readString();
        this.time = parcel.readLong();
    }

    public Lecture(String courseCode,String courseTitle, String venue, String lecturer, long time) {
        this.courseCode = courseCode;
        this.courseCode = courseTitle;
        this.venue = venue;
        this.lecturer = lecturer;
        this.time = time;

    }


    public static final Creator<Lecture> CREATOR = new Creator<Lecture>() {
        @Override
        public Lecture createFromParcel(Parcel in) {
            return new Lecture(in);
        }

        @Override
        public Lecture[] newArray(int size) {
            return new Lecture[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.courseCode);
        parcel.writeString(this.courseTitle);
        parcel.writeString(this.venue);
        parcel.writeString(this.lecturer);
        parcel.writeLong(this.time);
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public String getVenue() {
        return venue;
    }

    public String getLecturer() {
        return lecturer;
    }

    public long getTime() {
        return time;
    }
}
