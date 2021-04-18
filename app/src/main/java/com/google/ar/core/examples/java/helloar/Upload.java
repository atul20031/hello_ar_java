package com.google.ar.core.examples.java.helloar;

public class Upload {
    private  String mName;
    private String imageUrl;
    private String upload_category;
    public Upload()
    {
        // Empty constructor
    }

    public String getUpload_category() {
        return upload_category;
    }

    public void setUpload_category(String upload_category) {
        this.upload_category = upload_category;
    }

    public  Upload(String name, String imageUrl, String upload_category)
    {
        if(name.trim().equals(""))
        {
            name = "No name";
        }
        mName = name;
        this.imageUrl = imageUrl;
        this.upload_category = upload_category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }
    public  String getmName()
    {
        return  this.mName;
    }
}
