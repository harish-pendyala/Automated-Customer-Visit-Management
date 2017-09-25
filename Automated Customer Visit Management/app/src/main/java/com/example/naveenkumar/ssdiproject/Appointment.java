package com.example.naveenkumar.ssdiproject;

/**
 * Created by naveenkumar on 11/29/16.
 */
public class Appointment {
    private String Appointment_Date, Appointment_Time, User_Id, Branch_Name;
    private int Branch_Id;

    public Appointment() {
    }

    public Appointment(String appointment_Date, String appointment_Time, String user_Id, String branch_Name, int branch_Id) {
        Appointment_Date = appointment_Date;
        Appointment_Time = appointment_Time;
        User_Id = user_Id;
        Branch_Name = branch_Name;
        Branch_Id = branch_Id;
    }

    public String getAppointment_Date() {
        return Appointment_Date;
    }

    public void setAppointment_Date(String appointment_Date) {
        Appointment_Date = appointment_Date;
    }

    public String getAppointment_Time() {
        return Appointment_Time;
    }

    public void setAppointment_Time(String appointment_Time) {
        Appointment_Time = appointment_Time;
    }

    public String getUser_Id() {
        return User_Id;
    }

    public void setUser_Id(String user_Id) {
        User_Id = user_Id;
    }

    public String getBranch_Name() {
        return Branch_Name;
    }

    public void setBranch_Name(String branch_Name) {
        Branch_Name = branch_Name;
    }

    public int getBranch_Id() {
        return Branch_Id;
    }

    public void setBranch_Id(int branch_Id) {
        Branch_Id = branch_Id;
    }
}
