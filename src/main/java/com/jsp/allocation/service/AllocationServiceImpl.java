package com.jsp.allocation.service;

import com.jsp.allocation.dto.AppResponseDTO;
import com.jsp.allocation.entity.AllocationEntity;
import com.jsp.allocation.repository.AllocationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AllocationServiceImpl implements AllocationService {

    @Autowired
    AllocationRepository allocationRepositroy;

    @Override
    public AppResponseDTO processAllocation(List<Map<String, Object>> allocationList) {
        try {

            List<AllocationEntity> entityList = new ArrayList<>();

            entityList = allocationList.stream()
                    .flatMap(map -> {

                        Integer frequency =
                                map.get("frequency") != null ? (Integer) map.get("frequency") : 5;

                        LocalDate date = (LocalDate) map.get("grantDate");

                        return IntStream.range(0, frequency)
                                .mapToObj(i -> {

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

            allocationRepositroy.saveAll(entityList);

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

        allocationRepositroy.saveAll(entityList);

        return new AppResponseDTO("200", null, "SUCCESS", entityList);
    }
}