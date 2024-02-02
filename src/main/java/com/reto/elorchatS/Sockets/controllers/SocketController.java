package com.reto.elorchatS.Sockets.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.reto.elorchatS.Sockets.model.MessageFromServer;
import com.reto.elorchatS.Sockets.model.MessageType;
import com.reto.elorchatS.Sockets.socketsIO.SocketEvents;
import com.reto.elorchatS.Sockets.socketsIO.SocketIOConfig;

@RestController
@RequestMapping("/api/sockets")
public class SocketController {

    private final SocketIOServer socketIoServer;

    @Autowired
    public SocketController(SocketIOServer socketIoServer) {
        this.socketIoServer = socketIoServer;
    }
    
    

    @GetMapping("/sendessage")
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
    
    // deberia ser un POST y con body, pero para probar desde el navegador...
    @GetMapping("/joinRoom/{room}/{idUser}")
    public String joinRoom(@PathVariable("room") String room, @PathVariable("idUser") Integer idUser) {
    	
    	System.out.println("socket Join Get Request 1");
        SocketIOClient client = findClientByUserId(idUser);
        if (client != null) {
            client.joinRoom(room);

            System.out.println("socket Join Get Request 2");
            // Emit a custom event to notify others in the room
            socketIoServer.getRoomOperations(room).sendEvent(SocketEvents.ON_JOINED_ROOM.value, "User with ID " + idUser + " has joined the room " + room);
            
            // You can also send a welcome message to the user who joined
            client.sendEvent(SocketEvents.ON_JOINED_ROOM.value, "Welcome to the room " + room);
            System.out.println("socket Join Get Request 3");

            return "Usuario unido a la sala";
        } else {
            return "Ese usuario no está conectado";
        }
    }
    
    
    // deberia ser un POST y con body, pero para probar desde el navegador...
    @GetMapping("/leaveRoom/{room}/{idUser}")
    public String leaveRoom(@PathVariable("room") String room, @PathVariable("idUser") Integer idUser) {
    	
    	System.out.println("socket left Get Request 1");
        SocketIOClient client = findClientByUserId(idUser);
        if (client != null) {
            client.leaveRoom(room);

            System.out.println("socket left Get Request 2");
            // Emit a custom event to notify others in the room
            socketIoServer.getRoomOperations(room).sendEvent(SocketEvents.ON_LEFT_ROOM.value, "User with ID " + idUser + " has left the room " + room);
            
            // You can also send a welcome message to the user who joined
            client.sendEvent(SocketEvents.ON_LEFT_ROOM.value, "You left the room " + room);
            System.out.println("socket left Get Request 3");

            return "Usuario se ha ido de la sala";
        } else {
            return "Ese usuario no está conectado";
        }
    }
    
    
    private SocketIOClient findClientByUserId(Integer idUser) {
        Collection<SocketIOClient> clients = socketIoServer.getAllClients();

        for (SocketIOClient client : clients) {
            String userIdParam = client.get(SocketIOConfig.CLIENT_USER_ID_PARAM);

            try {
                if (userIdParam != null) {
                    Integer currentClientId = Integer.valueOf(userIdParam);

                    if (idUser.equals(currentClientId)) {
                        return client;
                    }
                }
            } catch (NumberFormatException e) {
                // Handle the exception (e.g., log it)
            }
        }

        return null; // Return null if no matching client is found
    }
}