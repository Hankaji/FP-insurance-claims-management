//package com.hankaji.icm.models.customer;
//
//import jakarta.persistence.*;
//
//import java.util.UUID;
//
//@Entity
//@Table(name = "customers")
//public class MappedCustomer{
//    public MappedCustomer(String fullname, String email, String password, String id) {
//        super(fullname, email, password);
//        this.id = id;
//    }
//
//    public MappedCustomer(UUID id, String fullname, String email, String password, String id1) {
//        super(fullname, email, password);
//        this.id = id1;
//        this.userId = id;
//    }
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private String id;
//    @Column(name ="insurance_card_number")
//    private Long insuranceCardNumber;
//    @Column(name ="user_id")
//    private UUID userId;
//    @Column(name = "is_holder")
//    private Boolean isHolder;
//
//    public MappedCustomer(String fullname, String email, String password, UUID userId) {
//        super(fullname, email, password);
//        this.userId = userId;
//    }
//
//    public MappedCustomer(String fullname, String email, String password, Boolean isHolder) {
//        super(fullname, email, password);
//        this.isHolder = isHolder;
//    }
//
//    public MappedCustomer(UUID userId) {
//        this.userId = userId;
//    }
//
//    public MappedCustomer(String id, Long insuranceCardNumber, UUID userId) {
//        this.id = id;
//        this.insuranceCardNumber = insuranceCardNumber;
//        this.userId = userId;
//    }
//
//    public MappedCustomer() {
//    }
//}
