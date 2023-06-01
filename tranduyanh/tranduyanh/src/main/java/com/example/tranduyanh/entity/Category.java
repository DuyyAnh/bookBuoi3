package com.example.tranduyanh.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="maphongban")
    private String maphongban;

    @Column(name="phongban")
    private String phongban;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Staff> staffs;
}
