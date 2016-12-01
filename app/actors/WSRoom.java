package actors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import akka.actor.UntypedActor;
import play.Logger;
import play.libs.Json;
import play.mvc.WebSocket;


public class WSRoom  extends UntypedActor {
	
	private static final Map<String, WebSocket.Out<JsonNode>> onlineMap = new HashMap<>();
	
	public static void initializeSocket(String random, WebSocket.Out<JsonNode> out) {
		onlineMap.put(random, out);
	}
	
	@Override
	public void onReceive(Object obj) throws Exception {
		if(obj.equals("DUMMY_MESSAGE")){
			Logger.info("GOT : ");
		}
		
		
		String url = "http://api.coindesk.com/v1/bpi/currentprice.json";
		String json =   readJsonFromUrl(url);
		Logger.info(""+json);
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = mapper.readValue(json, JsonNode.class);
		ObjectNode returnEventRev = Json.newObject();
		returnEventRev.put("data", json);
		onlineMap.keySet().forEach(k -> {
			onlineMap.get(k).write(actualObj);
		});
		
		Logger.info("onReceive called : "+new Date());
	}
    
	
	
	
	
	
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	  }

	  public static String readJsonFromUrl(String url) throws IOException, Exception {
	    InputStream is = new URL(url).openStream();
	   
	    try {
	      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
	      String jsonText = readAll(rd);
	      return jsonText;
	    } finally {
	      is.close();
	    }
	  }
    

}