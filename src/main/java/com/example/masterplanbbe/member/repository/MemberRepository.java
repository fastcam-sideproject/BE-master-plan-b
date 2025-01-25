package com.example.masterplanbbe.member.repository;

import com.example.masterplanbbe.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(
            value = " select id, user_id, email, name, nickname, password, phone_number, birthday,  " +
                    " profile_image_url, role, create_time, modified_time " +
                    " from users where = :id",
            nativeQuery = true
    )
    List<Member> find(
            @Param("id") Long id
    );


}
