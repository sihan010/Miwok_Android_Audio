package com.sihan.android.miwok;

public class Word {
    private String englishTranslation;
    private String mewokTranslation;
    private int src;
    private int mAudio;

    public Word(String english, String mewok, int audio){
        this.englishTranslation = english;
        this.mewokTranslation = mewok;
        this.mAudio=audio;
    }

    public Word(String english, String mewok, int src, int audio){
        this.englishTranslation = english;
        this.mewokTranslation = mewok;
        this.src = src;
        this.mAudio=audio;
    }

    public String getEnglish(){
        return englishTranslation;
    }

    public String getMewok(){
        return mewokTranslation;
    }

    public int getSrc(){
        return src;
    }

    public int getAudio(){return mAudio;}
}
