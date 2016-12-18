package com.example.jenis.tabbedactivityapp;

/**
 * Created by Jenis on 8/22/2016.
 */
public class UserInfo {
    public String userName, birthday, joinDate, firstName, lastName, phone, linkToProfile;

    public UserInfo(){}
    public UserInfo(String birthday, String firstname, String joinDate, String lastName,
                    String linkToProfile, String phone){
        this.birthday = birthday;
        this.firstName = firstname;
        this.joinDate = joinDate;
        this.lastName = lastName;
        this.linkToProfile = linkToProfile;
        this.phone = phone;
    }
    public String getBirthday(){return birthday;}
    public String getfirstName(){return firstName;}
    public String getjoinDate(){return joinDate;}
    public String getlastName(){return lastName;}
    public String getLinkToProfile(){return linkToProfile;}
    public String getPhone(){return phone;}
    public String getUserName(){return userName;}

    public void setBirthday(String birthday){this.birthday = birthday;}
    public void setFirstName(String firstName){this.firstName = firstName;}
    public void setJoinDate(String joinDate){this.joinDate = joinDate;}
    public void setLastName(String lastName){this.lastName = lastName;}
    public void setlinkToProfile(String linkToProfile){this.linkToProfile = linkToProfile;}
    public void setPhone(String phoneNumber){this.phone = phoneNumber;}
    public void setUserName(String userName){this.userName = userName;}
}