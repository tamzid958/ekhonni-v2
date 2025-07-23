package com.ekhonni.backend.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Md Jahid Hasan
 * Date: 12/29/24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Privilege {
    private Long id;
    private String name;
    private String description;
    private String type;
    private String httpMethod;
    private String endpoint;
}
