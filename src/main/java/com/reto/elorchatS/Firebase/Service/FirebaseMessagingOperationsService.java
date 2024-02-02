package com.reto.elorchatS.Firebase.Service;

import java.util.List;

public interface FirebaseMessagingOperationsService {
	void sendMulticastNotification(List deviceTokens);
}
