package vkmanager.model;

import vkmanager.model.music.VKTrack;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import vkmanager.model.photos.VKPhoto;
import vkmanager.model.photos.VKPhotoAlbum;

public class VKApi{
    private static int user_id;   //id пользователя
    private static String token;
    private static VKApi vkApi = null;
    private static int musicOffset = 0;
    
    private VKApi(){}
    
    private VKApi(User user){
        this.user_id = user.getId();
        this.token = user.getToken();
    }
    
    public static VKApi createVKApi(User user){
        if(vkApi == null) vkApi = new VKApi(user);
        return vkApi;
    }
    
    public static VKApi getVKApi(){
        return vkApi;
    }
    
    public String createURL(String method, String parameters){
        String url = "https://api.vk.com/method/" + 
                method +
                "?fields=" + parameters + "&out=0" +
                "&access_token=" + token;
        return url;
    }
    
    public int getIntUserId(String stringId){
        int id = 0;
        BufferedReader reader = null;
        String userIdJSON = "";
        String url = createURL("users.get", "&user_ids="+stringId);
        //System.out.println(url);
        /*Receive albums list in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream(), "utf-8"));
            userIdJSON = reader.readLine();
            System.out.println(userIdJSON);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                reader.close();
            }catch(IOException ex){
                Logger.getLogger(VKApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonResp = (JSONObject) parser.parse(userIdJSON.toString());
            JSONArray postsList = (JSONArray) jsonResp.get("response");
            JSONObject idRes = null;
            for (int i=0; i < postsList.size(); i++){
                idRes = (JSONObject) postsList.get(i);
                id = Integer.parseInt(idRes.get("uid").toString());
            }
        }catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return id;
    }
    
    public int getIdFromHref(String href){
        href = href.substring(href.lastIndexOf("vk.com") + 7);
        System.out.println(href);
        int id = 0;
        /*If id is integer*/
        if((href.substring(0, 2).equals("id"))){
            for(int i = 2; i < href.length(); i++){
                if(i == href.length()-1 || href.charAt(i) == '?' || href.charAt(i) == '&'){
                    if(i == href.length()-1){
                        id = Integer.parseInt(href.substring(2, i+1));
                    }else{
                        id = Integer.parseInt(href.substring(2, i));
                    }
                    System.out.println(id);
                    break;
                }
            }
        /*if id is string*/
        }else{
            for(int i = 0; i < href.length(); i++){
                if(i == href.length()-1 || href.charAt(i) == '?' || href.charAt(i) == '&'){
                    if(i == href.length()-1){
                        href = href.substring(0, i+1);
                    }else{
                        href = href.substring(0, i);
                    }
                    System.out.println(href);
                    break;
                }
            }
            id = getIntUserId(href);
        }
        return id;
    }
    
    public static User getUserInfo(String newToken){
        int userId = 0;
        String name = "";
        String lastname = "";
        String photo = "";
        
        String url = "https://api.vk.com/method/" + 
                "users.get" +
                "?fields=photo_50" + "&out=0" +
                "&access_token=" + newToken;
        
        BufferedReader reader = null;
        String userIdJSON = "";
        //System.out.println(url);
        /*Receive albums list in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream(), "utf-8"));
            userIdJSON = reader.readLine();
            //System.out.println(userIdJSON);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                reader.close();
            }catch(IOException ex){
                Logger.getLogger(VKApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonResp = (JSONObject) parser.parse(userIdJSON.toString());
            JSONArray postsList = (JSONArray) jsonResp.get("response");
            JSONObject userInfo = null;
            for (int i=0; i < postsList.size(); i++){
                userInfo = (JSONObject) postsList.get(i);
                userId = Integer.parseInt(userInfo.get("uid").toString());
                name = userInfo.get("first_name").toString();
                lastname = userInfo.get("last_name").toString();
                photo = userInfo.get("photo_50").toString();
            }
        }catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        
        return User.createUser(userId, name, lastname, photo, newToken);
    }
    
    public int getNumberOfAlbums(int userId){        
        String url = "https://api.vk.com/method/" + 
                "photos.getAlbumsCount" +
                "?user_id=" + userId + "&out=0" +
                "&access_token=" + token;
        
        int numberOfAlbums = 0;
        BufferedReader reader = null;
        String albumsNumberJSON = "";
        System.out.println(url);
        /*Receive albums list in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream(), "utf-8"));
            albumsNumberJSON = reader.readLine();
            //System.out.println(albumsNumberJSON);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                reader.close();
            }catch(IOException ex){
                Logger.getLogger(VKApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonResp = (JSONObject) parser.parse(albumsNumberJSON.toString());
            numberOfAlbums = Integer.parseInt(jsonResp.get("response").toString());
        }catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }        
        return numberOfAlbums;
    }
    
    public ArrayList<VKTrack> getAllUserMusic(){
        BufferedReader reader = null;
        String tracksJSON = "";
        String url = createURL("audio.get","&count=10&offset="+musicOffset);
        System.out.println(url);
        musicOffset+=10;
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream(), "utf-8"));
            tracksJSON = reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print(tracksJSON);
        ArrayList<VKTrack> tracks = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonResp = (JSONObject)parser.parse(tracksJSON.toString());
            JSONArray trackList = (JSONArray) jsonResp.get("response");
            JSONObject track = null;
            String titleArtist;
            for(int i=0; i<trackList.size(); i++){
                track = (JSONObject) trackList.get(i);
                titleArtist = track.get("artist") + " - " + track.get("title");
                int duration = Integer.parseInt(track.get("duration").toString());
                URL trackUrl = new URL(track.get("url").toString());
                VKTrack vktrack = new VKTrack(titleArtist, trackUrl);
                System.out.println(vktrack.getTrackIndex());
                tracks.add(vktrack);
            }
        } catch (ParseException ex) {
            Logger.getLogger(VKApi.class.getName()).log(Level.SEVERE, null, ex);
        } catch (MalformedURLException ex) {
            Logger.getLogger(VKApi.class.getName()).log(Level.SEVERE, null, ex);
        }
        return tracks;
    }
    
    public ArrayList<VKPhotoAlbum> getAllUserAlbums(int us_id) throws IOException{
        BufferedReader reader = null;
        String albumsJSON = "";
        String url = createURL("photos.getAlbums", "album_ids&owner_id="+us_id);
        //System.out.println(url);
        /*Receive albums list in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream(), "utf-8"));
            albumsJSON = reader.readLine();
            System.out.println(albumsJSON);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                reader.close();
            }catch(IOException ex){
                Logger.getLogger(VKApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        /*Parse JSON*/
        ArrayList<VKPhotoAlbum> albums = new ArrayList<>();
        JSONParser parser = new JSONParser();
        StringBuilder thumbs = new StringBuilder();
        try{
            JSONObject jsonResp = (JSONObject) parser.parse(albumsJSON.toString());
            JSONArray postsList = (JSONArray) jsonResp.get("response");
            JSONObject album = null;
            for (int i=0; i < postsList.size(); i++){
                album = (JSONObject) postsList.get(i);
                if(Integer.parseInt(album.get("thumb_id").toString()) == 0) continue;
                thumbs.append(us_id + "_" + album.get("thumb_id").toString());
                if(!(i+1 == postsList.size()))  thumbs.append(",");
            }
            //System.out.println(thumbs);
            //System.out.println(thumbs);
            HashMap<Integer, String> thumbsLinks = getAlbumsThumbs(thumbs.toString());
            for(int i=0; i < postsList.size(); i++){
                album = (JSONObject) postsList.get(i);
                //String thumb = getAlbumThumb(Integer.parseInt(album.get("thumb_id").toString()));
                albums.add(new VKPhotoAlbum(Integer.parseInt(album.get("aid").toString()), 
                                            album.get("title").toString(), 
                                            album.get("description").toString(),
                                            thumbsLinks.get(Integer.parseInt(album.get("aid").toString()))
                ));
            }
        }catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        //System.out.println("Альбомов извелечено: " + albums.size());
        return albums;
    }
    
    
    public HashMap<Integer, String> getAlbumsThumbs(String thumbIds) throws IOException{
        BufferedReader reader = null;
        String url = createURL("photos.getById", "&photos=" + thumbIds);
        //System.out.println(url);
        String thumbJSON = "";
        /*Receive albums thumb in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream(), "utf-8"));
            thumbJSON = reader.readLine();
            System.out.println(thumbJSON);
            System.out.println(url);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            reader.close();
        }
        
        HashMap<Integer, String> thumbs = new HashMap<>();
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonResp = (JSONObject) parser.parse(thumbJSON.toString());
            JSONArray postsList = (JSONArray) jsonResp.get("response");
            JSONObject thumb_res = null;
            for (int i=0; i < postsList.size(); i++){
                thumb_res = (JSONObject) postsList.get(i);
                System.out.println(thumb_res.get("aid").toString() + " " + thumb_res.get("src_big").toString());
                thumbs.put(Integer.parseInt(thumb_res.get("aid").toString()), thumb_res.get("src_big").toString());
            }
        }catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return thumbs;
    }
    
    public ArrayList<VKPhoto> getPhotosFromAlbum(int albumId, int userId){
        BufferedReader reader = null;
        String photosJSON = "";
        String url = createURL("photos.get", "&album_id=" + albumId + "&owner_id="+userId);
        System.out.println(url);
        /*Receive photos list in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream(), "utf-8"));
            photosJSON = reader.readLine();
            //System.out.println(photosJSON);
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally{
            try{
                reader.close();
            }catch(IOException ex){
                Logger.getLogger(VKApi.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        ArrayList<VKPhoto> photos = new ArrayList<VKPhoto>();
        JSONParser parser = new JSONParser();
        try{
            JSONObject jsonResp = (JSONObject) parser.parse(photosJSON.toString());
            JSONArray postsList = (JSONArray) jsonResp.get("response");
            JSONObject photos_res = null;
            for (int i=0; i < postsList.size(); i++){
                photos_res = (JSONObject) postsList.get(i);
                String src_big;
                if(photos_res.get("src_xxxbig") != null){
                    src_big = photos_res.get("src_xxxbig").toString();
                }else if(photos_res.get("src_xxbig") != null){
                    src_big = photos_res.get("src_xxbig").toString();
                }else if(photos_res.get("src_xbig") != null){
                    src_big = photos_res.get("src_xbig").toString();
                }else if(photos_res.get("src_big") != null){
                    src_big = photos_res.get("src_big").toString();
                }else{
                    src_big = photos_res.get("src").toString();
                }
                photos.add(new VKPhoto(Integer.parseInt(photos_res.get("pid").toString()), 
                                       photos_res.get("text").toString(), 
                                       photos_res.get("src_big").toString(), 
                                       src_big
                           ));
            }
        }catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return photos;
    }
}