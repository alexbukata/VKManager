package vkmanager.model.photos;

import java.util.ArrayList;

public class VKPhotoAlbum{
    private int id;
    private String name;
    private String description;
    private String thumb;

    public VKPhotoAlbum(){}

    public VKPhotoAlbum(int id, String name, String description, String thumb){
        this.id = id;
        this.name = name;
        this.description = description;
        this.thumb = thumb;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getThumb(){
        return thumb;
    }
    public void setThumb(String thumb){
        this.thumb = thumb;
    }
}