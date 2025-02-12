package com.ekhonni.backend.model.log;

import com.ekhonni.backend.baseentity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Author: Asif Iqbal
 * Date: 2/6/25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class BidLog extends BaseEntity<Long> {

    @Column(nullable = false)
    private Long bidId;

    @Column(nullable = false)
    private Long productId;

    @Column(columnDefinition = "text", nullable = false)
    private String bidData;

}
