package com.example.feature.entity;

import com.example.feature.enums.Type;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "NUMERIC(19,0)")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Type type;

    @Column(unique = true)
    private String fullPath;

    @OneToMany(cascade = CascadeType.ALL)
    @Column
    private List<Feature> childs;

    @Column
    private Boolean isActive;

    @Column(nullable = false)
    private String path;

}
