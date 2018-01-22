package com.ala.elearning.Beans;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by alaam on 12/30/2017.
 */

public class Exam implements Parcelable{
    public static final int STATUS_NEW = 1;
    public static final int STATUS_COMPLETED = 2;

    public Exam() {
    }

    private int id;
    private Date date;
    private Date endDate;
    private String paragraph;

    public String getParagraph() {
        return paragraph;
    }

    public void setParagraph(String paragraph) {
        this.paragraph = paragraph;
    }

    protected Exam(Parcel in) {
        id = in.readInt();
        name = in.readString();
        mark = in.readDouble();
        maxMark = in.readInt();
        course = in.readParcelable(Course.class.getClassLoader());
        status = in.readInt();
        date = new Date(in.readLong());
        endDate = new Date(in.readLong());
        paragraph = in.readString();
    }

    public static final Creator<Exam> CREATOR = new Creator<Exam>() {
        @Override
        public Exam createFromParcel(Parcel in) {
            return new Exam(in);
        }

        @Override
        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private String name;
    private double mark;
    private int maxMark;
    private Course course;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public int getMaxMark() {
        return maxMark;
    }

    public void setMaxMark(int maxMark) {
        this.maxMark = maxMark;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getStatusName(){
        switch (status){
            case STATUS_NEW:
                return "NEW";
            case STATUS_COMPLETED:
                return "COMPLETED";
        }

        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeDouble(mark);
        parcel.writeInt(maxMark);
        parcel.writeParcelable(course, i);
        parcel.writeInt(status);
        parcel.writeLong(date.getTime());
        parcel.writeLong(endDate.getTime());
        parcel.writeString(paragraph);
    }
}
