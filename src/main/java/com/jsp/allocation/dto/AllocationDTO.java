package com.jsp.allocation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@Data
@AllArgsConstructor
public class AllocationDTO {
    private long grantNumber;
    private double grantDate;
    private Double frequency;
    private BigInteger empId;
    private BigInteger grantId;


}
