package com.rudigo.android.mycampus.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;

import java.util.HashMap;
import java.util.Map;

public class Lecture extends BaseModel implements Parcelable {
    private String id;
    private String courseCode;
    private String courseTitle;
    private String venue;
    private String lecturer;
    private long time;

    public static Lecture fromDictionary(Object dictionary) {
        return fromDictionary(dictionary, Lecture.class);
    }


    //implements the constructor of the Parcelable Interface
    public Lecture() {
        // super();
    }

    //read: get from the fields
    public Lecture(Parcel parcel) {
        this.id = parcel.readString();
        this.courseCode = parcel.readString();
        this.courseTitle = parcel.readString();
        this.venue = parcel.readString();
        this.lecturer = parcel.readString();
        this.time = parcel.readLong();
    }

    public Lecture(String courseCode, String courseTitle, String venue, String lecturer, long time) {
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
        parcel.writeString(id);
        parcel.writeString(this.courseCode);
        parcel.writeString(this.courseTitle);
        parcel.writeString(this.venue);
        parcel.writeString(this.lecturer);
        parcel.writeLong(this.time);
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public void setLecturer(String lecturer) {
        this.lecturer = lecturer;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public void saveToDatabase(final AppCompatActivity activity, final Database database) {

        if (database == null) {
            Toast.makeText(activity, "Cannot to save to store. Database unavailable.", Toast.LENGTH_SHORT).show();
            return;
        }

        Document LectureDocument;
        Map<String, Object> properties;

        if (TextUtils.isEmpty(this.getId())) {
            //new lecture
            LectureDocument = database.createDocument();
            this.setId(LectureDocument.getId());
            properties = this.toDictionary();
        } else {
            LectureDocument = database.getDocument(this.getId());
            properties = new HashMap<>();
            properties.putAll(LectureDocument.getProperties());
            properties.putAll(this.toDictionary());
        }

        try {
            LectureDocument.putProperties(properties);
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
            Toast.makeText(activity, "Failed to save to store. Fatal error occurred.", Toast.LENGTH_SHORT).show();
        }
    }
}
