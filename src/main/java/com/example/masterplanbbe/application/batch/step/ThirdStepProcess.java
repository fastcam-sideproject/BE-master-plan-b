package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.RecommendationWriteDTO;
import com.example.masterplanbbe.application.batch.dto.ThirdStepReadDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ThirdStepProcess implements ItemProcessor<ThirdStepReadDTO, RecommendationWriteDTO> {

    @Override
    public RecommendationWriteDTO process(ThirdStepReadDTO item) throws Exception {
        return null;
    }
}
