package com.reto.elorchatS.Sockets.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.reto.elorchatS.Firebase.Service.FirebaseMessagingOperationsService;
import com.reto.elorchatS.Sockets.model.MessageFromServer;
import com.reto.elorchatS.Sockets.model.MessageType;
import com.reto.elorchatS.Sockets.socketsIO.SocketEvents;
import com.reto.elorchatS.Sockets.socketsIO.SocketIOConfig;

@RestController
@RequestMapping("/api/sockets")
public class SocketController {

    private final SocketIOServer socketIoServer;
    // private final FirebaseMessaging fcm;

    @Autowired
    private FirebaseMessagingOperationsService firebaseMessagingOperationsService;

    @Autowired
    public SocketController(SocketIOServer socketIoServer) {
        this.socketIoServer = socketIoServer;
        // this.fcm = fcm;
    }

    @GetMapping("/send-message")
    public String sendMessage() {
        // Envia un mensaje a todos los clientes conectados
    	
    	MessageFromServer message = new MessageFromServer(
    		MessageType.SERVER, 
    		null, 
    		"Hola, clientes de Socket.IO!", 
    		"Server", 
    		0
    	);
    	
        socketIoServer.getBroadcastOperations().sendEvent(SocketEvents.ON_SEND_MESSAGE.value, message);
        return "Mensaje enviado";
    }

    @GetMapping("/send-firebase-message")
    public String sendFirebaseMessage() {
        // Envia un mensaje a todos los clientes conectados

    	String deviceToken = "fELjajD7Q-mg4nUrPMzOPa:APA91bEUjgJdPiD7dSYh5FW44rkq9b3FABvAVW9fhHFdGXhnHvcXFXqu4gJxBJNnwO6wv962hPGVVdGMrn0K_7Nm3-TwL5D2duhsjbjQN_GltVa8Ck2VbBKFu3vXoGYN8kzdHtnTeIo4";
    	List<String> deviceTokens = new ArrayList<String>();
    	deviceTokens.add(deviceToken);
    	// firebaseMessagingOperations.sendMulticastNotification(fcm, deviceTokens);
    	firebaseMessagingOperationsService.sendMulticastNotification(deviceTokens);

        return "Mensaje enviado";
    }

    // deberia ser un POST y con body, pero para probar desde el navegador...
    @GetMapping("/join-room/{room}/{idUser}")
    public String joinRoom(@PathVariable("room") String room, @PathVariable("idUser") Integer idUser) {
    	
    	
    	SocketIOClient client = findClientByUserId(idUser);
    	if (client != null) {
    		client.joinRoom(room);
    		
    		// se podria notificar a aquellos que estan en la room
    		// socketIoServer.getRoomOperations(room).sendEvent(SocketEvents.ON_SEND_MESSAGE.value, "el usuario XXXXXX se ha unido a la sala " + room);
    		
    		// aunque lo interesante y lo que habra que hacer es notificarle a dicho cliente que ha accedido a la room
        	return "Usuario unido a la sala";
    	} else {
    		return "Ese usuario no esta conectado";
    	}
    	
    }
    
    
    // deberia ser un POST y con body, pero para probar desde el navegador...
    @GetMapping("/leave-room/{room}/{idUser}")
    public String leaveRoom(@PathVariable("room") String room, @PathVariable("idUser") Integer idUser) {
    	
    	SocketIOClient client = findClientByUserId(idUser);
    	if (client != null) {
    		System.out.println(client.getAllRooms().size());
    		client.leaveRoom(room);
    		System.out.println(client.getAllRooms().size());
    		// se podria notificar a aquellos que estan en la room
    		// socketIoServer.getRoomOperations(room).sendEvent("chat message", "el usuario XXXXXX se ha ido de la sala " + room);
    		// podriamos registrar distintos eventos, no "chat message" para estos casos
    	
    		// lo interesante y lo que habra que hacer es notificarle a dicho cliente que ha sido eliminado de la room
			return "Usuario expulsado de la sala";
		} else {
			return "Ese usuario no estaba conectado";
		}
    }
    
    
    private SocketIOClient findClientByUserId(Integer idUser) {
    	SocketIOClient response = null;
    	
    	Collection<SocketIOClient> clients = socketIoServer.getAllClients();
    	for (SocketIOClient client: clients) {
    		Integer currentClientId = Integer.valueOf(client.get(SocketIOConfig.CLIENT_USER_ID_PARAM));
    		if (currentClientId == idUser) {
    			response = client;
    			break;
    		}
    	}
    	
    	return response;
    }
}