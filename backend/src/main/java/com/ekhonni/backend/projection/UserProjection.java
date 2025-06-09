package com.ekhonni.backend.projection;

import java.util.UUID;

/**
 * Author: Md Jahid Hasan
 */

public interface UserProjection {

    UUID getId();

    String getName();

    String getEmail();

    String getAddress();

    String getProfileImage();

}
