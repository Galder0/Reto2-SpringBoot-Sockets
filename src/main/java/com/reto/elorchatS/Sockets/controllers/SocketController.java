package com.reto.elorchatS.Sockets.controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.reto.elorchatS.ChatsUsers.Service.ChatUserService;
import com.reto.elorchatS.ChatsUsers.model.ChatUser;
import com.reto.elorchatS.Sockets.model.MessageFromServer;
import com.reto.elorchatS.Sockets.model.MessageType;
import com.reto.elorchatS.Sockets.socketsIO.SocketEvents;
import com.reto.elorchatS.Sockets.socketsIO.SocketIOConfig;
import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.service.ChatService;
import com.reto.elorchatS.users.model.User;

@RestController
@RequestMapping("/api/sockets")
public class SocketController {

    private final SocketIOServer socketIoServer;
    

    @Autowired
    public SocketController(SocketIOServer socketIoServer) {
        this.socketIoServer = socketIoServer;
    }
    
    @Autowired
    ChatUserService chatUserService;
    
    @Autowired
    ChatService chatService;
    

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
    @GetMapping("/joinRoom/{room}/{isAdmin}")
    public String joinRoom(@PathVariable("room") String room, @PathVariable("isAdmin") Boolean isAdmin) {
        // Retrieve the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Check if the user is authenticated
        if (authentication != null && authentication.isAuthenticated()) {
            // Assuming your user details contain the user ID
        	User userDetails = (User) authentication.getPrincipal();
        	Integer idUser = userDetails.getId();

            // Proceed with joining the room using the retrieved user ID
            System.out.println("socket Join Get Request 1");
            SocketIOClient client = findClientByUserId(idUser);
            if (client != null) {
            	
            	ChatUser thisChatUser = new ChatUser();
            	
            	thisChatUser.setUserId(idUser);
            	
            	Chat chat = chatService.getChatFromName(room);
            	
            	thisChatUser.setChatId(chat.getId());
            	
            	thisChatUser.setAdmin(isAdmin);
            	
            	ChatUser createdChatUser = chatUserService.createChatUser(thisChatUser);
            	
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
        } else {
            return "Usuario no autenticado";
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