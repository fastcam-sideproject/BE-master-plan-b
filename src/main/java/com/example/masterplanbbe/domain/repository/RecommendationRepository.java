//package com.example.masterplanbbe.domain.repository;
//
//import com.example.masterplanbbe.domain.entity.Recommendation;
//import com.example.masterplanbbe.domain.entity.Spec;
//import com.example.masterplanbbe.domain.enums.AgeGroup;
//import io.lettuce.core.dynamic.annotation.Param;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface RecommendationRepository extends JpaRepository<Recommendation, Long> {
//
//    @Query("""
//        SELECT r FROM Recommendation r
//        LEFT JOIN FETCH r.spec
//        WHERE r.ageGroup = :ageGroup
//        AND r.jobRoleName IN :jobRoleNames
//        ORDER BY r.score DESC
//    """)
//    List<Recommendation> findTopByJobRolesAndAge(
//            List<String> jobRoleNames,
//            AgeGroup ageGroup,
//            Pageable pageable);
//
//    @Query("""
//        SELECT r FROM Recommendation r
//        LEFT JOIN FETCH r.spec
//        WHERE r.ageGroup = :ageGroup
//        AND r.category.id IN :categoryIds
//        ORDER BY r.score DESC
//    """)
//    List<Recommendation> findTopByCategoryAndAge(
//            List<Long> categoryIds,
//            AgeGroup ageGroup,
//            Pageable pageable);
//
//    @Query("""
//        SELECT r FROM Recommendation r
//        LEFT JOIN FETCH r.spec
//        WHERE r.ageGroup = :ageGroup
//        ORDER BY r.score DESC
//    """)
//    List<Recommendation> findTopByAge(
//            AgeGroup ageGroup,
//            Pageable pageable);
//}
//
