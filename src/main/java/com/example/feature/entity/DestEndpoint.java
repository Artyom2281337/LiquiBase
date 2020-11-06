package com.example.feature.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "DEST_ENDPOINT", schema = "origination_proxy")
public class DestEndpoint {

    @Column(name = "NAME")
    @Id
    private String name;

    @Column(name = "ENDPOINT")
    private String endpoint;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="DEST_SYSTEM_TYPE")
    private DestSystem destSystem;

    @Column(name="METHOD")
    private String method;
}
