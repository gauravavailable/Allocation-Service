package org.jsp.allocationservice.service;

import org.jsp.allocationservice.dto.AppResponseDTO;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public interface AllocationService {

    public AppResponseDTO processAllocation(List<Map<String, Object>> allocationList);

    public AppResponseDTO preparedAllocation(List<Map<String, Object>> grantMapList);

    public AppResponseDTO getAllocatedGrantsByPlanId(BigInteger planId);

    public void updateAllocatedGrantsStatus(List<BigInteger> grantIdList);


}
