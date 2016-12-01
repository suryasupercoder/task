@(random : String)
var messageObj;
var bpival='USD'
$("#myddl").change(function()
		{ 
			bpival=  $(this).val();
		});
if ("WebSocket" in window){
$(function(){
    // get websocket class, firefox has a different way to get it for firefox use MozWebSocket
    var WS = window['WebSocket'] ? window['WebSocket'] : WebSocket;
    var element = $('#MessagesDiv');
    var pricediv  = $('#priceDiv');
    // open pewpew with websocket
    var chatSocket = new WS('@routes.WebSocketController.wsInterface(random).webSocketURL(request)');
    var writeMessages = function(event){
    	var data = JSON.parse(event.data);
    	element.html(data.time.updated);
    	if(bpival=='USD'){
    		pricediv.html(data.bpi.USD.rate)
    	}
    	if(bpival=='GBP'){
    		pricediv.html(data.bpi.GBP.rate)
    	}
    	if(bpival=='EUR'){
    		pricediv.html(data.bpi.EUR.rate)
    		
    	}
    	console.log(data);
    }
    //message from server comes here
    chatSocket.onmessage = writeMessages;
    
    function sendMessage(message){
    	
    	//chatSocket.send(jsonText);
    	//$('#messageBox').val(''); 
    }
    
    
    
    
});

}else{
	alert("Websocket is not supported by ur browser");
}