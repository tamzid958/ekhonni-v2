/**
 * Author: Rifat Shariar Sakil
 * Time: 2:00 PM
 * Date: 2/10/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {
    private String receiverId;
    private String content;
}
