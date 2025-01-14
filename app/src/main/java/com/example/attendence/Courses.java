package com.example.attendence;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Courses extends BaseObservable {
    @PrimaryKey(autoGenerate=true)
    public int id;
    public String name;
    public int attendance;
    public int tnc;   //total no. of classes

    public Courses(int id,String name,int attendance,int tnc){
        this.id=id;
        this.name=name;
        this.attendance=attendance;
        this.tnc=tnc;
    }

    public Courses(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    @Bindable
    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    @Bindable
    public int getTnc() {
        return tnc;
    }

    public void setTnc(int tnc) {
        this.tnc = tnc;
    }
}
