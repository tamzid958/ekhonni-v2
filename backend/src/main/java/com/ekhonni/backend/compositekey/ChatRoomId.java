package com.ekhonni.backend.compositekey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

/**
 * Author: Safayet Rafi
 * Date: 02/10/25
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomId implements Serializable {

    private UUID user1;
    private UUID user2;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatRoomId that = (ChatRoomId) o;
        return user1.equals(that.user1) && user2.equals(that.user2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2);
    }

}
