package com.ekhonni.backend.model;

import lombok.*;

import java.util.List;


/**
 * Author: Md Jahid Hasan
 * Date: 2/24/25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrivilegeDetailed {
    private Privilege privilege;
    private List<String> assignedTo;
}
