package com.ekhonni.backend.projection;

import java.util.UUID;


public interface UserProjection {

    UUID getId();

    String getName();

    String getEmail();

    String getAddress();

}
