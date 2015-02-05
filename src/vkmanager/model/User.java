package vkmanager.model;

public class User{
    public static User user;
    
    private int id;
    private String name;
    private String lastname;
    private String photo;
    private String token;

    private User(){}

    private User(int id, String name, String lastname, String photo, String token){
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.photo = photo;
        this.token = token;
    }
    
    public static User createUser(int id, String name, String lastname, String photo, String token){
        if(user == null)
            user = new User(id, name, lastname, photo, token);
        return user;
    }
    
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public String getLastname(){
        return lastname;
    }
    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public String getPhoto(){
        return photo;
    }
    public void setPhoto(String photo){
        this.photo = photo;
    }

    public String getToken(){
        return token;
    }
    public void setToken(String token){
        this.token = token;
    }

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }
}