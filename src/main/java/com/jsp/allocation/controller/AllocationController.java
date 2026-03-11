package com.jsp.allocation.controller;

import com.jsp.allocation.dto.AllocationDTO;
import com.jsp.allocation.dto.AppResponseDTO;
import com.jsp.allocation.service.AllocationService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class AllocationController {

    private AllocationService allocationService;

    @RequestMapping(value = "/uploadGrants")
    public AppResponseDTO processAllocation(@RequestBody List<Map<String, Object>> dtoList) {
        return allocationService.processAllocation(dtoList);
    }

}
