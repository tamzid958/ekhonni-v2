/**
 * Author: Rifat Shariar Sakil
 * Time: 2:31 PM
 * Date: 2/12/25
 * Project Name: ekhonni-v2
 */

package com.ekhonni.backend.repository;

import com.ekhonni.backend.enums.BoostType;
import com.ekhonni.backend.model.ProductBoost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductBoostRepository extends JpaRepository<ProductBoost, Long> {


    Optional<ProductBoost> findByProductId(Long productId);


    List<ProductBoost> findAllByExpiresAtBefore(LocalDateTime now);

    // Find all active boosts (boosts that have not expired)
    List<ProductBoost> findByExpiresAtAfter(LocalDateTime now);

    // Find all boosts that have expired
    List<ProductBoost> findByExpiresAtBefore(LocalDateTime now);

    // Find all boosts for a specific product that are active
    @Query("SELECT pb FROM ProductBoost pb WHERE pb.product.id = :productId AND pb.expiresAt > :now")
    List<ProductBoost> findActiveBoostsByProductId(@Param("productId") Long productId, @Param("now") LocalDateTime now);

    // Find all boosts for a specific product that have expired
    @Query("SELECT pb FROM ProductBoost pb WHERE pb.product.id = :productId AND pb.expiresAt <= :now")
    List<ProductBoost> findExpiredBoostsByProductId(@Param("productId") Long productId, @Param("now") LocalDateTime now);

    // Find all boosts of a specific type (e.g., ONE_WEEK, ONE_MONTH)
    List<ProductBoost> findByBoostType(BoostType boostType);

    // Find all boosts of a specific type for a specific product
    List<ProductBoost> findByBoostTypeAndProductId(BoostType boostType, Long productId);


    // Find the active boost for a product (if any)
    @Query("SELECT pb FROM ProductBoost pb WHERE pb.product.id = :productId AND pb.expiresAt > :now")
    Optional<ProductBoost> findActiveBoostByProductId(@Param("productId") Long productId, @Param("now") LocalDateTime now);


}