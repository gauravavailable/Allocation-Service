package org.jsp.allocationservice.controller;

import org.jsp.allocationservice.dto.AppResponseDTO;
import org.jsp.allocationservice.service.AllocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/allocation")
public class AllocationController {

    @Autowired
    private AllocationService allocationService;

    @RequestMapping(value = "/uploadGrants")
    public AppResponseDTO processAllocation(@RequestBody List<Map<String, Object>> dtoList) {
        return allocationService.processAllocation(dtoList);
    }

    @RequestMapping(value = "/getSumByGrantId/{planId}")
    public @ResponseBody AppResponseDTO processAllocatedGrantByPlanId(@PathVariable BigInteger planId){
        return allocationService.getAllocatedGrantsByPlanId(planId);
    }
}
