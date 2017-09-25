package com.example.naveenkumar.ssdiproject;

import java.io.Serializable;

/**
 * Created by naveenkumar on 11/29/16.
 */
public class Bank implements Serializable{
    private String branchname,branchaddress1,branchaddress2,branchcity,branchstate;
    private int branchid,branchzipcode,noofrepresentatives;

    public Bank() {
    }

    public Bank(String branchname, String branchaddress1, String branchaddress2, String branchcity, String branchstate, int branchid, int branchzipcode, int noofrepresentatives) {
        this.branchname = branchname;
        this.branchaddress1 = branchaddress1;
        this.branchaddress2 = branchaddress2;
        this.branchcity = branchcity;
        this.branchstate = branchstate;
        this.branchid = branchid;
        this.branchzipcode = branchzipcode;
        this.noofrepresentatives = noofrepresentatives;
    }

    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    public String getBranchaddress1() {
        return branchaddress1;
    }

    public void setBranchaddress1(String branchaddress1) {
        this.branchaddress1 = branchaddress1;
    }

    public String getBranchaddress2() {
        return branchaddress2;
    }

    public void setBranchaddress2(String branchaddress2) {
        this.branchaddress2 = branchaddress2;
    }

    public String getBranchcity() {
        return branchcity;
    }

    public void setBranchcity(String branchcity) {
        this.branchcity = branchcity;
    }

    public String getBranchstate() {
        return branchstate;
    }

    public void setBranchstate(String branchstate) {
        this.branchstate = branchstate;
    }

    public int getBranchid() {
        return branchid;
    }

    public void setBranchid(int branchid) {
        this.branchid = branchid;
    }

    public int getBranchzipcode() {
        return branchzipcode;
    }

    public void setBranchzipcode(int branchzipcode) {
        this.branchzipcode = branchzipcode;
    }

    public int getNoofrepresentatives() {
        return noofrepresentatives;
    }

    public void setNoofrepresentatives(int noofrepresentatives) {
        this.noofrepresentatives = noofrepresentatives;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "branchname='" + branchname + '\'' +
                ", branchaddress1='" + branchaddress1 + '\'' +
                ", branchaddress2='" + branchaddress2 + '\'' +
                ", branchcity='" + branchcity + '\'' +
                ", branchstate='" + branchstate + '\'' +
                ", branchid=" + branchid +
                ", branchzipcode=" + branchzipcode +
                ", noofrepresentatives=" + noofrepresentatives +
                '}';
    }
}
