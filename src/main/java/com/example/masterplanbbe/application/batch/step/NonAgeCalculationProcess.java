package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.NonAgeCalculationWriteDTO;
import com.example.masterplanbbe.application.batch.dto.PreCalculationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Slf4j
@Component
public class NonAgeCalculationProcess implements ItemProcessor<PreCalculationDTO, NonAgeCalculationWriteDTO> {

    private static final double APPLY_COEFFICIENT = 0.5;
    private static final double EXAM_START_COEFFICIENT = 0.3;
    private static final double PARTICIPANT_COEFFICIENT = 0.2;
    private static final double LIKE_COEFFICIENT = 0.1;
    private static final double VIEW_COEFFICIENT = 0.1;

    @Override
    public NonAgeCalculationWriteDTO process(PreCalculationDTO item) throws Exception {
        LocalDate today = LocalDate.now();

        // 날짜가 가까울수록 높은 점수를 부여하는 역수 방식
        double applyEndPoint = 1.0 / (1 + ChronoUnit.DAYS.between(today, item.applyEndDate()));
        double examStartPoint = 1.0 / (1 + ChronoUnit.DAYS.between(today, item.examStartDate()));

        // 참여자 수를 반영하되, 로그 스케일로 증가폭 조절
        double participantPoint = Math.log(1 + item.participantCount());

        // 캐스팅 처리
        double totalLikesPoint = (double) item.totalLikeCount();
        double totalViewPoint = (double) item.totalViewCount();

        Double intermediateResult = applyEndPoint * APPLY_COEFFICIENT // 지원 마감일 가중치 증가
                + examStartPoint * EXAM_START_COEFFICIENT // 시험 시작일 가중치
                + participantPoint * PARTICIPANT_COEFFICIENT // 참여자 가중치
                + totalLikesPoint * LIKE_COEFFICIENT // 좋아요 수 가중치
                + totalViewPoint * VIEW_COEFFICIENT; // 조회수 가중치

        return new NonAgeCalculationWriteDTO(
                item.specId(), item.examId(), intermediateResult);
    }
}
