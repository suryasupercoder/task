package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.fasterxml.jackson.databind.JsonNode;

import actors.WSRoom;
import play.Logger;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;

public class WebSocketController extends Controller {
	
	

    public Result loadChatWSJs() {
    	Logger.debug(">>>>>>>>>UUID.randomUUID().toString()"+UUID.randomUUID().toString());
        return ok(views.js.chatws.render(UUID.randomUUID().toString()));
    }
    
    public WebSocket<JsonNode> wsInterface(String random){
        return new WebSocket<JsonNode>(){
            public void onReady(WebSocket.In<JsonNode> in, WebSocket.Out<JsonNode> out){
            	WSRoom.initializeSocket(random, out);
            	Logger.warn("websocket handshake is done of : "+random);
            }
        };   
    } 
}
