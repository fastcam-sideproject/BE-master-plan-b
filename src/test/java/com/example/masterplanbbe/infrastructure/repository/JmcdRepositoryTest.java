package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.presentation.response.JmcdApiResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JmcdRepositoryTest {

    private JdbcTemplate jdbcTemplate;
    private SpecJmcdBatchRepository jmcdRepository;

    @BeforeEach
    void setUp() {
        jdbcTemplate = mock(JdbcTemplate.class);
        jmcdRepository = new SpecJmcdBatchRepository(jdbcTemplate);
    }

    @Test
    void batchUpsert_shouldNotCallUpdate_whenItemsEmpty() {
        jmcdRepository.batchUpsert(List.of());

        verify(jdbcTemplate, never()).update(anyString(), Optional.ofNullable(any()));
    }

    @Test
    void batchUpsert_shouldBuildCorrectSqlAndParams_whenItemsProvided() {
        // given
        JmcdApiResponse.Item item1 = new JmcdApiResponse.Item(1, "Spec A");
        JmcdApiResponse.Item item2 = new JmcdApiResponse.Item(2, "Spec B");

        List<JmcdApiResponse.Item> items = List.of(item1, item2);

        ArgumentCaptor<String> sqlCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Object[]> paramsCaptor = ArgumentCaptor.forClass(Object[].class);

        // when
        jmcdRepository.batchUpsert(items);

        // then
        verify(jdbcTemplate).update(sqlCaptor.capture(), paramsCaptor.capture());

        String expectedSql =
                "INSERT INTO spec_jmcds (spec_id, spec_name, jmcd) VALUES (?, ?, ?), (?, ?, ?) ON DUPLICATE KEY UPDATE jmcd = VALUES(jmcd)";
        assertEquals(expectedSql, sqlCaptor.getValue());

        Object[] expectedParams = {
                null, "Spec A", 1,
                null, "Spec B", 2
        };
        assertArrayEquals(expectedParams, paramsCaptor.getValue());
    }

}
