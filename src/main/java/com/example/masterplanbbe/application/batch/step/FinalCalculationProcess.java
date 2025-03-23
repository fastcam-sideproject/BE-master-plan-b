package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.FinalCalculationWriteDTO;
import com.example.masterplanbbe.application.batch.dto.FinalJoinReadDTO;
import com.example.masterplanbbe.domain.enums.AgeGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FinalCalculationProcess implements ItemProcessor<FinalJoinReadDTO, FinalCalculationWriteDTO> {

    @Override
    public FinalCalculationWriteDTO process(FinalJoinReadDTO item) {
        AgeGroup ageGroup = AgeGroup.EARLY_20S;

        if (item.ageGroup().equalsIgnoreCase("LATE_20S")) {
            ageGroup = AgeGroup.LATE_20S;
        } else if (item.ageGroup().equalsIgnoreCase("MID_20S")) {
            ageGroup = AgeGroup.MID_20S;
        } else if (item.ageGroup().equalsIgnoreCase("OVER_30S")) {
            ageGroup = AgeGroup.OVER_30S;
        }

        return new FinalCalculationWriteDTO(
                ageGroup, item.score(), item.specId(), item.jobRoleName(), item.categoryId());
    }
}
