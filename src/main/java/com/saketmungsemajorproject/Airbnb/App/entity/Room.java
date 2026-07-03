package com.saketmungsemajorproject.Airbnb.App.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore//we did it for frontend for search hotel page
    //Reason:-
//    What was actually happening on the backend
//    Your Room entity has a field pointing back to Hotel:
//    javaprivate Hotel hotel;
//    And your Hotel entity has a list pointing to all its Rooms:
//    javaprivate List<Room> rooms;
//    When Spring Boot converts a Hotel object into JSON text to send to the browser, it has to write out every field. So it writes:
//    Hotel → { rooms: [ Room → { hotel: { rooms: [ Room → { hotel: { rooms: [...
//    This never stops. The Hotel contains the Room, which contains the Hotel again, which contains the Room again — forever. This is called a circular reference.


    @ManyToOne(fetch = FetchType.LAZY)//we do not need hotel info when we are fetching Room
    //👉 "Many rooms can belong to one hotel, and a foreign key hotel_id is created in the Room table to maintain this relationship."
    //hotel_id refers to the primary key of the Hotel table
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal basePrice;//Base Price before dynamic pricing

    @Column(columnDefinition = "TEXT[]")
    private String[] photos;

    @Column(columnDefinition = "TEXT[]")
    private String[] amenities;

    @Column(nullable = false)
    private Integer totalCount;//How many rooms of the same type exists

    @Column(nullable = false)
    private Integer capacity;//Max people per room

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
