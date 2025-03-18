package com.example.masterplanbbe.application.batch.step;

import com.example.masterplanbbe.application.batch.dto.FirstStepReadDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FirstStepReader implements ItemReader<FirstStepReadDTO> {

    private final JdbcTemplate jdbcTemplate;

    private List<FirstStepReadDTO> data;
    private int index = 0;

    private static final String SQL = "SELECT \n" +
            "    e.id AS exam_id, \n" +
            "    e.apply_end_date, \n" +
            "    e.exam_start_date, \n" +
            "    e.participant_count\n" +
            "FROM specs s\n" +
            "JOIN exams e ON s.latest_exam = e.id";

    @Override
    public FirstStepReadDTO read() throws UnexpectedInputException, ParseException, NonTransientResourceException {
        if (data == null) {
            data = fetchData();
        }
        return (index < data.size()) ? data.get(index++) : null;
    }

    private List<FirstStepReadDTO> fetchData() {
        return jdbcTemplate.query(SQL, (rs, rowNum) -> new FirstStepReadDTO(
                        rs.getLong("exam_id"),
                        rs.getDate("apply_end_date").toLocalDate(),
                        rs.getDate("exam_start_date").toLocalDate(),
                        rs.getInt("participant_count")));
    }
}
