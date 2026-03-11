package com.jsp.allocation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigInteger;
import java.util.Date;

@Data
@Entity
@Table(name = "allocation_master")
public class AllocationEntity {

    @Id
    @Column(name = "alt_Key")
    private BigInteger altkey;

    @Column(name = "grant_id")
    private BigInteger grantId;

    @Column(name = "allocation_number")
    private Double allocationNumber;

    @Column(name = "Planned_Allocation_Date")
    private Date PlannedAllocationDate;

    @Column(name = "allocation_year")
    private String allocationYear;

    @Column(name = "Status")
    private String status;

    @Column(name = "creation_Date")
    private Date creationDate;

    @Column(name = "modification_Date")
    private Date modificationDate;
}
