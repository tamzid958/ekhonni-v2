/**
 * Author: Rifat Shariar Sakil
 * Time: 3:39 PM
 * Date: 12/10/2024
 * Project Name: backend
 */

package com.ekhonni.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;
import java.util.Objects;

@Getter
@Setter
public class ProductPageDTO {

    private final Integer pageNo= 0;
    private final Integer pageSize= 10;

    public Pageable getPageable(Integer pageNo){

        Integer page =  pageNo>100? this.pageNo : pageNo;
        return PageRequest.of(page, this.pageSize);

    }


}
