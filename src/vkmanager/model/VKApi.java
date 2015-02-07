package vkmanager.model;

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
    private static String client_id = "4763444";   //id приложения
    private static int user_id;   //id пользователя
    private static String redirect_uri = "http://api.vkontakte.ru/blank.html";
    private static String token;
    private static int musicOffset = 0;
    //String settings = "friends";    //запрашиваемые функции
    //"40b7159ef3fc727b6974a0e4093e60381486903e030753ee5c9e820b288ab91b9f940fad853598e39e2de";
    
    public VKApi(User user){
        this.user_id = user.getId();
        this.token = user.getToken();
    }
    
    //String login = "79524706515";   //логин
    //String password = "zexuJJun6";  //пароль
    
    public String createURL(String method, String parameters){
        String url = "https://api.vk.com/method/" + 
                method +
                "?fields=" + parameters + "&out=0" +
                "&access_token=" + token;
        return url;
    }
    
    public ArrayList<VKTrack> getAllUserMusic(){
        BufferedReader reader = null;
        String tracksJSON = "";
        String url = createURL("audio.get","&count=10&offset="+musicOffset);
        musicOffset+=10;
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream()));
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
    
    public ArrayList<VKPhotoAlbum> getAllUserAlbums() throws IOException{
        BufferedReader reader = null;
        String albumsJSON = "";
        String url = createURL("photos.getAlbums", "album_ids");
        /*Receive albums list in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream()));
            albumsJSON = reader.readLine();
            //System.out.println(albumsJSON);
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
            for (int i=1; i < postsList.size(); i++){
                album = (JSONObject) postsList.get(i);
                thumbs.append(user_id + "_" + album.get("thumb_id").toString());
                if(!(i+1 == postsList.size()))  thumbs.append(",");
            }
            //System.out.println(thumbs);
            HashMap<Integer, String> thumbsLinks = getAlbumsThumbs(thumbs.toString());
            for(Map.Entry<Integer, String> entrySet : thumbsLinks.entrySet()){
                Integer key = entrySet.getKey();
                String value = entrySet.getValue();
                //System.out.println(key + ": " + value);
            }
            
            for (int i=0; i < postsList.size()-1; i++){
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
        
        return albums;
    }
    
    
    public HashMap<Integer, String> getAlbumsThumbs(String thumbIds) throws IOException{
        BufferedReader reader = null;
        String url = createURL("photos.getById", "&photos=" + thumbIds);
        String thumbJSON = "";
        /*Receive albums thumb in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream()));
            thumbJSON = reader.readLine();
            //System.out.println(thumbJSON);
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
            for (int i=0; i < postsList.size()-1; i++){
                thumb_res = (JSONObject) postsList.get(i);
                thumbs.put(Integer.parseInt(thumb_res.get("aid").toString()), thumb_res.get("src").toString());
            }
        }catch (ParseException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        return thumbs;
    }
    
    public ArrayList<VKPhoto> getPhotosFromAlbum(int albumId){
        BufferedReader reader = null;
        String photosJSON = "";
        String url = createURL("photos.get", "&album_id=" + albumId);
        /*Receive photos list in JSON*/
        try{
            URL query = new URL(url);
            reader = new BufferedReader(new InputStreamReader(query.openStream()));
            photosJSON = reader.readLine();
            System.out.println(photosJSON);
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
            for (int i=0; i < postsList.size()-1; i++){
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
                                       photos_res.get("src").toString(), 
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