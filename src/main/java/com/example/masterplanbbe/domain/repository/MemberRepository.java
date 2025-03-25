package com.example.masterplanbbe.domain.repository;

import com.example.masterplanbbe.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

//    @Query(
//            value = " select id, user_id, email, name, nickname, password, phone_number, birthday,  " +
//                    " profile_image_url, create_time, modified_time " +
//                    " from users where = :id",
//            nativeQuery = true
//    )
//    List<Member> find(
//            @Param("id") Long id
//    );

    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m JOIN FETCH m.memberJobRoles")
    List<Member> findAllWithJobRoles();

    @Query("""
        SELECT DISTINCT m
        FROM Member m
        LEFT JOIN FETCH m.memberJobRoles
        WHERE m.email = :email
    """)
    Optional<Member> findByEmailWithJobRoles(@Param("email") String email);
}
