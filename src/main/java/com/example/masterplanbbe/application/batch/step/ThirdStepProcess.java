package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.RecommendationWriteDTO;
import com.example.masterplanbbe.application.batch.dto.ThirdStepReadDTO;
import com.example.masterplanbbe.domain.enums.AgeGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ThirdStepProcess implements ItemProcessor<ThirdStepReadDTO, RecommendationWriteDTO> {

    @Override
    public RecommendationWriteDTO process(ThirdStepReadDTO item) {
        AgeGroup ageGroup = AgeGroup.EARLY_20S;

        if (item.ageGroup().equalsIgnoreCase("LATE_20S")) {
            ageGroup = AgeGroup.LATE_20S;
        } else if (item.ageGroup().equalsIgnoreCase("MID_20S")) {
            ageGroup = AgeGroup.MID_20S;
        } else if (item.ageGroup().equalsIgnoreCase("OVER_30S")) {
            ageGroup = AgeGroup.OVER_30S;
        }

        return new RecommendationWriteDTO(ageGroup, item.countSum(), item.specId());
    }
}
