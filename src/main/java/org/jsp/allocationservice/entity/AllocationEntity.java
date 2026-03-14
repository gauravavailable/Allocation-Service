package org.jsp.allocationservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
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
    private LocalDate plannedAllocationDate;

    @Column(name = "allocation_year")
    private Integer allocationYear;

    @Column(name = "Status")
    private String status;

    @Column(name = "creation_Date")
    private Date creationDate;

    @Column(name = "modification_Date")
    private Date modificationDate;
}
