package com.jsp.allocation.service;

import com.jsp.allocation.dto.AppResponseDTO;

import java.util.List;
import java.util.Map;

public interface AllocationService {

    public AppResponseDTO processAllocation(List<Map<String,Object>> allocationList);

    public AppResponseDTO preparedAllocation(List<Map<String,Object>> grantMapList);

}
