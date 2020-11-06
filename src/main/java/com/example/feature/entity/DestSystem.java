package com.example.feature.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "DEST_SYSTEM", schema = "origination_proxy")
public class DestSystem {

    @Id
    @Column(name = "TYPE")
    private String name;

    @Column(name = "PROTOCOL")
    private String protocol;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "TIMEOUT_SEC")
    private Integer timeoutSec = null;
}
