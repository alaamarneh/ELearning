package com.ala.elearning.Beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alaam on 12/28/2017.
 */

public class Course implements Parcelable {
    private int id;
    private String name,summary;
    private User instructor;


    public Course() {
    }


    protected Course(Parcel in) {
        id = in.readInt();
        name = in.readString();
        summary = in.readString();
        instructor = in.readParcelable(User.class.getClassLoader());
    }

    public static final Creator<Course> CREATOR = new Creator<Course>() {
        @Override
        public Course createFromParcel(Parcel in) {
            return new Course(in);
        }

        @Override
        public Course[] newArray(int size) {
            return new Course[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(summary);
        parcel.writeParcelable(instructor, i);
    }
}
