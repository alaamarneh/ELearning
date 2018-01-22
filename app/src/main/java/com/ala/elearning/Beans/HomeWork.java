package com.ala.elearning.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by alaam on 12/30/2017.
 */

public class HomeWork implements Parcelable{
    private int id;
    private Course course;
    private String title, text;
    private Date date,endDate;

    public HomeWork() {
    }

    protected HomeWork(Parcel in) {
        id = in.readInt();
        course = in.readParcelable(Course.class.getClassLoader());
        title = in.readString();
        text = in.readString();
        date = new Date(in.readLong());
        endDate = new Date(in.readLong());
    }

    public static final Creator<HomeWork> CREATOR = new Creator<HomeWork>() {
        @Override
        public HomeWork createFromParcel(Parcel in) {
            return new HomeWork(in);
        }

        @Override
        public HomeWork[] newArray(int size) {
            return new HomeWork[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeParcelable(course, i);
        parcel.writeString(title);
        parcel.writeString(text);
        parcel.writeLong(date.getTime());
        parcel.writeLong(endDate.getTime());
    }
}
