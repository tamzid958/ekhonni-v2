/**
 * Author: Rifat Shariar Sakil
 * Time: 4:26 PM
 * Date: 2/9/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.service;

import com.ekhonni.backend.model.Chat;
import com.ekhonni.backend.model.User;
import com.ekhonni.backend.repository.ChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatRepository chatRepository;

    public List<Chat> getChatHistory(User authenticatedUser, User otherUser) {
        return chatRepository.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(
                authenticatedUser, otherUser, otherUser, authenticatedUser);
    }
}
