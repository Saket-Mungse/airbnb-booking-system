package com.saketmungsemajorproject.Airbnb.App.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(
        //Same hotel + same room + same date → cannot repeat so use uniqueConstraints
        uniqueConstraints = @UniqueConstraint(
                name = "unique_hotel_room_date",
                columnNames = {"hotel_id", "room_id", "date"}
        ))
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
//One row  = one room on one specific date
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //FetchType.LAZY is used to load related data only when required,
    //improving performance and reducing unnecessary database queries
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "room_id", nullable = false)
    private Room room;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer bookedCount;//how many rooms are confirmed booked

    //When we click on book button then for some time(10 mins) this reserved count will be increased
    //To initialise the booking
    @Column(nullable = false, columnDefinition = "INTEGER DEFAULT 0")
    private Integer reservedCount;


    @Column(nullable = false)
    private Integer totalCount;//same as room.totalCount (copied here for performance)

    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal surgeFactor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price; // basePrice * surgeFactor(recalculated by scheduler)

    @Column(nullable = false)
    private String city;//copied for fast searching from Hotel table

    @Column(nullable = false)
    private Boolean closed;//manager can close a room for specific date

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
