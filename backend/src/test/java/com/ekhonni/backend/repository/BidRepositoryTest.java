package com.ekhonni.backend.repository;

import com.ekhonni.backend.dto.UserDTO;
import com.ekhonni.backend.dto.bid.BidCreateDTO;
import com.ekhonni.backend.enums.BidStatus;
import com.ekhonni.backend.model.Bid;
import com.ekhonni.backend.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Author: Asif Iqbal
 * Date: 1/14/25
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BidRepositoryTest {


    @Autowired
    private BidRepository bidRepository;

    @Test
    void shouldCountBidsForProduct() {
        // Given
        Long productId = 1L;

        // When
        long count = bidRepository.countByProductIdAndDeletedAtIsNull(productId);

        // Then
        assertThat(count).isGreaterThanOrEqualTo(0);
    }

    @Test
    void shouldCheckExistsByProductIdAndStatus() {
        // Given
        Long productId = 1L;
        BidStatus status = BidStatus.PENDING;

        // When
        boolean exists = bidRepository.existsByProductIdAndStatus(productId, status);

        // Then
        assertThat(exists).isFalse();
    }

    @Test
    void shouldFindBidderIdById() {
        // Given
        UserDTO userDTO = new UserDTO(
                "Test User",
                "test@example.com",
                "password123",
                "01712345678",
                "Test Address"
        );
        User user = new User(); // Assuming you have a constructor or builder
        user.setName(userDTO.name());
        user.setEmail(userDTO.email());
        user.setPassword(userDTO.password());
        user.setPhone(userDTO.phone());
        user.setAddress(userDTO.address());

        BidCreateDTO bidDTO = new BidCreateDTO(
                1L,      // productId
                100.00,  // amount
                "BDT"    // currency
        );

        Bid bid = new Bid();
        bid.setBidder(user);
        bid.setAmount(bidDTO.amount());
        bid.setCurrency(bidDTO.currency());
        bid.setStatus(BidStatus.PENDING);

        // When
        Bid savedBid = bidRepository.save(bid);

        // Then
        Optional<UUID> bidderId = bidRepository.findBidderIdById(savedBid.getId());
        assertThat(bidderId).isPresent();
        assertThat(bidderId.get()).isEqualTo(user.getId());

    }
}
