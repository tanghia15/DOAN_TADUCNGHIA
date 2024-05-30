package com.example.nghiahaui.entity;

import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String firstname;

    private String lastname;

    private String country;

    private String address;

    private String town;

    private String state;

    private long postCode;

    private String email;
    
    private String phone;

    private String note;

    private long totalPrice;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy="order")
    @JsonBackReference
    private List<OrderDetail> orderDetails;

}
