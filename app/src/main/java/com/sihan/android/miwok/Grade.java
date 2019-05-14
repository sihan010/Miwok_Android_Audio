package com.sihan.android.miwok;

public class Grade implements java.io.Serializable {
    private String Name;
    private String English101;
    private String Math101;
    private String History101;
    private String Programming101;

    public Grade(String name, String english, String math, String history, String programming) {
        this.Name=name;
        this.English101=english;
        this.Math101=math;
        this.History101=history;
        this.Programming101=programming;
    }

    public String getName() {
        return Name;
    }

    public String getEnglish101() {
        return English101;
    }

    public String getMath101() {
        return Math101;
    }

    public String getHistory101() {
        return History101;
    }

    public String getProgramming101() {
        return Programming101;
    }

    public String getResultString(){
        return "Name: "+getName()+" ,English: "+getEnglish101()+" ,Math: "+getMath101()+" ,History: "+getHistory101()+" ,Programming: "+getProgramming101();
    }
}
