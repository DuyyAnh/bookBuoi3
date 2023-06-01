package com.example.tranduyanh.entity;

import com.example.tranduyanh.validator.annotation.ValidCategoryId;
import com.example.tranduyanh.validator.annotation.ValidUserId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @NotEmpty(message = "name must not be empty")
    @Size(max = 250, min = 1, message = "name must be less than 250 characters")
    private String name;

    @Column(name = "sex")
    @NotEmpty(message = "sex must not be empty")
    @Size(max = 3, min = 1, message = "sex must be less than 3 characters")
    private String sex;


    @Column(name = "image")
    private String image;

    @Column(name = "place")
    private String place;


    @Column(name = "luong")
    @NotNull(message = "luong is required")
    private Double luong;

    @ManyToOne
    @JoinColumn(name = "ma_phongban")
    @ValidCategoryId
    private Category category;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ValidUserId
    private User user;
}
