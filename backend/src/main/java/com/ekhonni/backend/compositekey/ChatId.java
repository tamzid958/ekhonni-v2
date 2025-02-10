package com.ekhonni.backend.compositekey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 02/10/25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatId implements Serializable {

    private UUID user1;
    private UUID user2;
}
