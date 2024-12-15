/**
 * Author: Rifat Shariar Sakil
 * Time: 8:30 PM
 * Date: 12/12/2024
 * Project Name: backend
 */

package com.ekhonni.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.List;

@Setter
@Getter
public class PageDTO<T> {
    private List<T> content;
    private int number;
    public PageDTO(Page<T> page) {
        this.content = page.getContent();
        this.number = page.getNumber();
    }
}
