package com.finalproject.secondhand.entity;

import com.finalproject.secondhand.enums.EStatusProcess;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Data
@Entity(name = "offers")
@EqualsAndHashCode(callSuper = true)
public class Offers extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "offer_id")
    private Integer offerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private Products product;

    @Column
    private String priceNegotiated;

    @Column
    @Enumerated(EnumType.STRING)
    private EStatusProcess statusProcess = EStatusProcess.WAITING;

}
