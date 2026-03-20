package org.jsp.allocationservice.service;

import jakarta.annotation.PostConstruct;
import org.jsp.allocationservice.dto.AppResponseDTO;
import org.jsp.allocationservice.entity.AllocationEntity;
import org.jsp.allocationservice.repository.AllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class AllocationServiceImpl implements AllocationService {

    @Autowired
    AllocationRepository allocationRepository;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        BigInteger planId = getActivePlan();
        getGrantsForAllocation(planId);
    }

    public BigInteger getActivePlan() {
        String url = "http://localhost:8080/ESOP/getActivePlan";
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);
        Map<String, Object> planMap = (Map) response.getBody().get("Data");
        return new BigInteger(planMap.get("altkey").toString());
    }


    @PostConstruct
    public void getGrantsForAllocation(BigInteger planId) {
        String url = "http://localhost:8080/ESOP/getGrantsByPlanId/100/ACTIVE/PENDING";
        ResponseEntity<AppResponseDTO> response = restTemplate.exchange(url, HttpMethod.GET, null, AppResponseDTO.class);
        AppResponseDTO body = response.getBody();
        System.out.println(body.getData());
    }


    @Override
    public AppResponseDTO processAllocation(List<Map<String, Object>> allocationList) {
        try {

            List<AllocationEntity> entityList = new ArrayList<>();

            entityList = allocationList.stream().flatMap(map -> {

                int frequency = map.get("frequency") != null ? (Integer) map.get("frequency") : 5;

                LocalDate date = (LocalDate) map.get("grantDate");

                return IntStream.range(0, frequency).mapToObj(i -> {

                    AllocationEntity entity = new AllocationEntity();
                    entity.setStatus("PENDING");

                    LocalDate nextDate = date.plusDays(i + 1);

                    entity.setPlannedAllocationDate(nextDate);
                    entity.setAllocationYear(nextDate.getYear());
                    entity.setModificationDate(null);
                    entity.setCreationDate(new Date());


                    return entity;
                });
            }).collect(Collectors.toList());

            allocationRepository.saveAll(entityList);

            return new AppResponseDTO("200", null, "SUCCESS", entityList);

        } catch (Exception e) {
            return new AppResponseDTO("500", e.getMessage(), "FAILURE", null);
        }
    }

    @Override
    public AppResponseDTO preparedAllocation(List<Map<String, Object>> grantMapList) {

        List<AllocationEntity> entityList = new ArrayList<>();

        for (Map<String, Object> grant : grantMapList) {

            Long frequency = (Long) grant.get("frequency");
            Long grantNumber = (Long) grant.get("grantNumber");
            Long allocationNumber = grantNumber / frequency;
            BigInteger grantId = (BigInteger) grant.get("altkey");

            for (int i = 0; i < frequency; i++) {

                AllocationEntity entity = new AllocationEntity();

                entity.setGrantId(grantId);
                entity.setAllocationNumber(Double.valueOf(allocationNumber));

                LocalDate startDate = LocalDate.now();
                LocalDate nextDate = startDate.plusYears(i + 1);

                entity.setPlannedAllocationDate(nextDate);
                entity.setAllocationYear(nextDate.getYear());
                entity.setCreationDate(new Date());

                entityList.add(entity);
            }
        }

        allocationRepository.saveAll(entityList);

        return new AppResponseDTO("200", null, "SUCCESS", entityList);
    }

    @Override
    public AppResponseDTO getAllocatedGrantsByPlanId(BigInteger planId) {
        try {
            String sql = "SELECT am.grant_id, SUM(am.allocation_number) AS total_allocation " + "FROM allocation_master am " + "LEFT OUTER JOIN Grant_entity g ON g.alt_key = am.grant_id " + "WHERE g.plan_Id = ? " + "AND am.status = 'APPROVED' " + "GROUP BY am.grant_id";

            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, planId);
            if (!rows.isEmpty()) {
                return new AppResponseDTO("200", null, "SUCCESS", rows);
            }
        } catch (Exception e) {
            return new AppResponseDTO("500", e.getMessage(), "FAILURE", null);
        }
        return null;
    }

    @Override
    public void updateAllocatedGrantsStatus(List<BigInteger> grantIdList) {
        String url = "http://localhost:8080/ESOP/updateAllocatedGrantsStatus";
        HttpEntity<List<BigInteger>> listHttpEntity = new HttpEntity<>(grantIdList);
        ResponseEntity<List> response = restTemplate.exchange(url, HttpMethod.POST, listHttpEntity, List.class);

    }

}



