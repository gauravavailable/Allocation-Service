package org.jsp.allocationservice.repository;

import org.jsp.allocationservice.entity.AllocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.math.BigInteger;


@Repository
public  interface AllocationRepository extends JpaRepository<AllocationEntity, BigInteger> {


}
