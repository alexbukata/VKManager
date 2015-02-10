/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vkmanager.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import vkmanager.model.User;
import vkmanager.model.VKApi;
import vkmanager.model.VkManager;

/**
 *
 * @author i-mad_000
 */
public class AuthorizeController implements Initializable {
    
    @FXML
    private AnchorPane webAuth;
        
    @Override
    public void initialize(URL url, ResourceBundle rb){
        String autLink = "https://oauth.vk.com/authorize?client_id=4763444&scope=13&redirect_uri=http://api.vkontakte.ru/blank.html&display=page&v=5.21&response_type=token";
        
        WebView webView = new WebView();
        WebEngine engine = webView.getEngine();
        webAuth.getChildren().add(webView);
        engine.load(autLink);
        
        engine.getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>() {
                  @Override public void changed(ObservableValue ov, State oldState, State newState) {
                        String newLocation = engine.getLocation();
                        int accessTokenStart = newLocation.lastIndexOf("access_token=");
                        //if(accessTokenStart != -1){
                            newLocation = newLocation.substring(accessTokenStart + 13);
                            int accessTokenEnd = newLocation.indexOf("&");
                            //String token = newLocation.substring(0, accessTokenEnd);
                            String token = "9a138f2d9482eb8ce98aa6071755e145422c62358d36998a2120a5ca55a4789d8f18ed0e027e5022a386f";
                            User user = VKApi.getUserInfo(token);
                            VKApi.createVKApi(user);
                            
                            System.out.println(user.getId() + " Token: " + user.getToken());
                            
                            webAuth.getScene().getWindow().hide();
                                
                            Parent root;
                            try{
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/vkmanager/view/RootVK.fxml"));
                                root = loader.load();
                                Stage stage = new Stage();
                                stage.setTitle("VKManager beta");
                                stage.setScene(new Scene(root, 800, 600));
                                stage.show();   
                            }catch(IOException ex){
                                Logger.getLogger(AuthorizeController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    //}
                  }
                );
    }    
    
}
