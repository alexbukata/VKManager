package vkmanager.model.photos;

public class VKPhoto{
    private int id;
    private String description;
    private String link_s;  // small
    private String link_l;  // large

    public VKPhoto(){}
    
    public VKPhoto(int id, String description, String link_s, String link_l){
        this.id = id;
        this.description = description;
        this.link_s = link_s;
        this.link_l = link_l;
    }
    
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description = description;
    }

    public String getLink_s(){
        return link_s;
    }
    public void setLink_s(String link_s){
        this.link_s = link_s;
    }

    public String getLink_l(){
        return link_l;
    }
    public void setLink_l(String link_l){
        this.link_l = link_l;
    }
}