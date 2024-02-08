package com.reto.elorchatS.Sockets.socketsIO;



import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.reto.elorchatS.Messages.Model.Message;
import com.reto.elorchatS.Messages.Model.MessageDAO;
import com.reto.elorchatS.Messages.Service.MessageService;
import com.reto.elorchatS.Security.configuration.JwtTokenFilter;
import com.reto.elorchatS.Security.configuration.JwtTokenUtil;
import com.reto.elorchatS.Sockets.model.JoinRoom;
import com.reto.elorchatS.Sockets.model.MessageFromClient;
import com.reto.elorchatS.Sockets.model.MessageFromServer;
import com.reto.elorchatS.Sockets.model.MessageType;
import com.reto.elorchatS.chats.model.Chat;
import com.reto.elorchatS.chats.service.ChatService;
import com.reto.elorchatS.users.Service.UserService;
import com.reto.elorchatS.users.model.User;

import io.netty.handler.codec.http.HttpHeaders;
import jakarta.annotation.PreDestroy;

@Configuration
public class SocketIOConfig {
	
	@Autowired
	MessageService messageService;
	
	@Autowired
	ChatService chatService;
	
	@Autowired 
  	AuthenticationManager authenticationManager;
  	
  	@Autowired 
  	JwtTokenUtil jwtUtil;
  	
  	@Autowired
	UserService userService;
	
    @Value("${socket-server.host}")
    private String host;
    
    @Value("${socket-server.port}")
    private Integer port;
    
    private SocketIOServer server;
    
    public final static String CLIENT_USER_NAME_PARAM = "authorname";
    public final static String CLIENT_USER_ID_PARAM = "authorid";
    public final static String AUTHORIZATION_HEADER = "Authorization";
    
    
    @Bean
    public SocketIOServer socketIOServer() {
    	com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);
        
        config.setMaxFramePayloadLength(2621440);
        config.setMaxHttpContentLength(2621440);

        
        // vamos a permitir a una web que no este en el mismo host y port conectarse. Si no da error de Cross Domain
        config.setAllowHeaders("Authorization");
        config.setOrigin("http://localhost:8080");

        server = new SocketIOServer(config);

        server.addConnectListener(new MyConnectListener(server, jwtUtil, userService));
        server.addDisconnectListener(new MyDisconnectListener());
        server.addEventListener(SocketEvents.ON_MESSAGE_RECEIVED.value, MessageFromClient.class, onSendMessage());
        server.addEventListener(SocketEvents.ON_JOINED_ROOM.value, String.class, onJoinRoom());
        server.addEventListener(SocketEvents.ON_LEFT_ROOM.value, String.class, onLeftRoom());
        server.start();

        return server;
    }
	@Component
    private static class MyConnectListener implements ConnectListener {

        private SocketIOServer server;
       
        
        @Autowired 
    	AuthenticationManager authenticationManager;
    	
    	JwtTokenUtil jwtUtil;
    	
    	UserService userService;
//    	
//    	@Autowired
//    	JwtTokenFilter jwtFilter;
    
        
    	MyConnectListener(SocketIOServer server, JwtTokenUtil jwtUtil, UserService userService) {
    		this.server = server;
    		this.jwtUtil = jwtUtil;
    		this.userService = userService;
    	}
    	
    	 @Override
         public void onConnect(SocketIOClient client) {
    		 
    		 
         	// ojo por que este codigo no esta bien en si
         	
         	// TODO el que no tenga autorization no deberia ni poder conectarse. gestionar
         	HttpHeaders headers = client.getHandshakeData().getHttpHeaders();
         	if (headers.get(AUTHORIZATION_HEADER) == null) {
         		// FUERA
         		System.out.println("Nuevo cliente no permitida la conexion: " + client.getSessionId());
         		client.disconnect();
         	} else {
         		loadClientData(headers, client);
         		System.out.printf("Nuevo cliente conectado: %s . Clientes conectados ahora mismo: %d \n", client.getSessionId(), this.server.getAllClients().size());
         		
         		// aqui incluso se podria notificar a todos o a salas de que se ha conectado...
             	// server.getBroadcastOperations().sendEvent("chat message", messageFromServer);
         	}
         }


    	 private void loadClientData(HttpHeaders headers, SocketIOClient client) {
    		    try {
    		        String jwt = headers.get(AUTHORIZATION_HEADER);
    		        
    		        System.out.println("llego aqui " + jwt);
    		        /*
    		        UserDetails userInfo = jwtFilter.getUserDetails("Bearer " + authorization);
    		        
    		        UserDetails userInfo2 = jwtFilter.getUserDetails(authorization);
    		        
    		        System.out.println("User Bearer " + userInfo.toString());
    		        System.out.println("User " + userInfo2.toString());
    		        */
    		        boolean isOk = jwtUtil.validateAccessToken(jwt);
    		        
    		        System.out.println("El token " + isOk);
    		        
    		        // Check if jwtUtil is initialized
    		        if (jwtUtil != null) {

    		            System.out.println("jwtuTIL: no es nulo");
    		            // Now you can use jwtUtil to get the user ID
    		            int userId = jwtUtil.getUserId(jwt);
    		            
    		            System.out.println("USER ID: " + userId);
    		            String authorId = String.valueOf(userId);
    		            
    		            User userLogged = userService.findUserById(userId).get();
    		            
    		            System.out.println("User Logged To String " + userLogged.toString());
    		            
    		            String authorName = userLogged.getName();

    		            client.set(CLIENT_USER_ID_PARAM, authorId);
    		            client.set(CLIENT_USER_NAME_PARAM, authorName);
    		            
    		            
    		            try {
    		                List<Chat> userLoggedChats = userLogged.getChats();  // Assuming you have a method to retrieve user chats

    		                if (userLoggedChats != null && !userLoggedChats.isEmpty()) {
    		                    for (Chat chat : userLoggedChats) {
    		                        // Join the user to each chat room
    		                        client.joinRoom(chat.getName()); // Assuming each chat has a roomName property
    		                        System.out.println("User Joined " + chat.getName());
    		                    }
    		                } else {
    		                    // If the user has no chats, join them to a default room
    		                    client.joinRoom("default-room");
    		                }
    		            } catch (Exception e) {
    		                e.printStackTrace();
    		            }

    		            //client.set(CLIENT_USER_ID_PARAM, authorId);
    		            //client.set(CLIENT_USER_NAME_PARAM, authorName);
    		            
    		            // Extract other information from the JWT
    		            //String jwt = authorization.split(" ")[1];
    		            //String[] datos = jwt.split(":");
    		            //String authorId = datos[1];
    		            //String authorName = datos[2];
    		            
    		            // Set client properties
    		            //client.set(CLIENT_USER_ID_PARAM, authorId);
    		            //client.set(CLIENT_USER_NAME_PARAM, authorName);
    		            
    		            // Example of joining rooms
    		        } else {
    		            // Handle the case where jwtUtil is not initialized
    		            System.err.println("jwtUtil is not initialized!");
    		        }
    		    } catch (Exception e) {
    		        e.printStackTrace();
    		    }
    		}
    }
    
    private static class MyDisconnectListener implements DisconnectListener {
    	
    	
    	@Override
		public void onDisconnect(SocketIOClient client) {
			client.getNamespace().getAllClients().stream().forEach(data-> {
				System.out.println("user disconnected "+ data.getSessionId().toString());
				// notificateDisconnectToUsers(client);
			});
		}

		// podemos notificar a los demas usuarios que ha salido. Ojo por que el broadcast envia a todos
    	private void notificateDisconnectToUsers(SocketIOClient client) {
			String room = null;
			String message = "el usuario se ha desconectado salido";
        	String authorIdS = client.get(CLIENT_USER_ID_PARAM);
        	Integer authorId = Integer.valueOf(authorIdS);
        	String authorName = client.get(CLIENT_USER_NAME_PARAM);
        	
        	MessageFromServer messageFromServer = new MessageFromServer(
        		MessageType.SERVER, 
        		room, 
        		message, 
        		authorName, 
        		authorId
        	);
			client.getNamespace().getBroadcastOperations().sendEvent(SocketEvents.ON_SEND_MESSAGE.value, messageFromServer);
    	}
    }
    
    private DataListener<MessageFromClient> onSendMessage() {
        return (senderClient, data, acknowledge) -> {
        	System.out.printf("Mensaje recibido");
        	String authorIdS = senderClient.get(CLIENT_USER_ID_PARAM);
        	Integer authorId = Integer.valueOf(authorIdS);
        	String authorName = senderClient.get(CLIENT_USER_NAME_PARAM);
        	
        	System.out.printf("Mensaje recibido de (%d) %s. El mensaje es el siguiente: %s \n", authorId, authorName, data.toString());
        	
        	// TODO comprobar si el usuario esta en la room a la que quiere enviar...
        	boolean isAllowedToSendToRoom = checkIfSendCanSendToRoom(senderClient, data.getRoom());
        	if (isAllowedToSendToRoom) {
        		
        		Chat chatDB = chatService.findChatByName(data.getRoom()).get();
        		
        		System.out.println("Grupo Del Mensaje " + chatDB.toString());

            	MessageDAO message = new MessageDAO(
            		data.getMessage(), 
            		authorName,
            		authorId,
            		chatDB.getId(),
            		new Timestamp(System.currentTimeMillis())
            	);
            	
            	 String messageContent = message.getMessage();
            	 System.out.println("messageContent " + messageContent);
            	 System.out.println("message " + message.toString());
            	    // Check if the message content length exceeds 2000 characters
            	    if (messageContent.length() > 2000) {
            	        // If the message content is greater than 2000 characters, process the image
            	    	server.getRoomOperations(data.getRoom()).sendEvent(SocketEvents.ON_SEND_MESSAGE.value, message);
            	    	
            	        MessageDAO newMessage = testImage(message);
            	        
            	        //message.setMessage(newMessage.getMessage());
            	        
            	        System.out.println("newMessage " + newMessage.toString());
            	        
            	        Message created = messageService.createMessage(message.getMessage(), authorId, chatDB.getId());
            	        
            	        System.out.println("Message created on the DB" + created.toString());
            	        
            	    } else {
            	    	
            	    	Message created = messageService.createMessage(message.getMessage(), authorId, chatDB.getId());
            	    	
            	    	System.out.println("Message created on the DB" + created.toString());
            	    	
            	    	server.getRoomOperations(data.getRoom()).sendEvent(SocketEvents.ON_SEND_MESSAGE.value, message);
            	    	
            	    }
            	
  
            	
            	
            	
            
            	// TODO esto es para mandar a todos los clientes. No para mandar a los de una Room
            	// senderClient.getNamespace().getBroadcastOperations().sendEvent("chat message", message);
            	
            	// esto puede que veamos mas adelante
                // acknowledge.sendAckData("El mensaje se envio al destinatario satisfactoriamente");
        	} else {
        		
        		MessageFromServer message = new MessageFromServer(
                		MessageType.SERVER, 
                		data.getRoom(), 
                		"ERROR AL ENVIAR EL MENSAJE", 
                		authorName, 
                		authorId
                	);
        		// TODO
        		// como minimo no dejar. se podria devolver un mensaje como MessageType.SERVER de que no puede enviar...
        		// incluso ampliar la clase messageServer con otro enum de errores
        		// o crear un evento nuevo, no "chat message" con otros datos
        	}
        };
    }
    
    private DataListener<String> onJoinRoom() {
        return (client, room, ackSender) -> {
            // Extract user details from the client
            String userId = client.get(CLIENT_USER_ID_PARAM);
            String userName = client.get(CLIENT_USER_NAME_PARAM);

            client.joinRoom(room);
            System.out.printf("User %s (%s) joined room %s\n", userId, userName, room);

            // TODO: Implement your custom logic when a user joins a room

            // For example, send a welcome message to the user
            String welcomeMessage = "Welcome to the room, " + userName + "!";
            client.sendEvent(SocketEvents.ON_JOINED_ROOM.value, welcomeMessage);
        };
    }
    private DataListener<String> onLeftRoom() {
        return (client, room, ackSender) -> {
            // Extract user details from the client
            String userId = client.get(CLIENT_USER_ID_PARAM);
            String userName = client.get(CLIENT_USER_NAME_PARAM);

            client.leaveRoom(room);
            System.out.printf("User %s (%s) left room %s\n", userId, userName, room);

            // TODO: Implement your custom logic when a user joins a room

            // For example, send a welcome message to the user
            String welcomeMessage = "Left the room, " + userName + "!";
            client.sendEvent(SocketEvents.ON_LEFT_ROOM.value, welcomeMessage);
        };
    }
    
    
    

    private boolean checkIfSendCanSendToRoom(SocketIOClient senderClient, String room) {
    	if (senderClient.getAllRooms().contains(room)) {
    		System.out.println("SI tiene permiso para enviar mensaje en la room");
    		return true;
    	} else {
    		System.out.println("NO tiene permiso para enviar mensaje en la room");
    		return false;
    	}
	}

	@PreDestroy
	public void stopSocketIOServer() {
		this.server.stop();
	}
	
	public MessageDAO testImage (MessageDAO message)  throws IOException{
		
		
		Integer num = 0;
		
		String imageString = message.getMessage();
		
		String extensionArchivo = detectMimeType(imageString);
		
		String fileName = "Image_" + System.currentTimeMillis() + extensionArchivo;
		
		String outputFile = "src/main/resources/images/" + fileName;
		
		message.setMessage(outputFile);
		
		byte[] decodeImg = Base64.getDecoder().decode(imageString.getBytes(StandardCharsets.UTF_8));
		
		Path destinationFile = Paths.get(outputFile);
		
		Files.write(destinationFile, decodeImg);
		
		return message;
	}
	
	private String detectMimeType(String base64Content) {
		HashMap<String, String> signatures = new HashMap<String, String>();
		
		signatures.put("JVBERi0", ".pdf");
		signatures.put("R0lGODdh", ".gif");
		signatures.put("R0lGODdh", ".gif");
		signatures.put("iVBORw0KGgo", ".png");
		signatures.put("/9j/", ".jpg");
		String response = "";
		
		for(Map.Entry<String, String> entry : signatures.entrySet()) {
			String key = entry.getKey();
			if(base64Content.indexOf(key) == 0) {
				response = entry.getValue();
			}
		}
		return response;
	}
}