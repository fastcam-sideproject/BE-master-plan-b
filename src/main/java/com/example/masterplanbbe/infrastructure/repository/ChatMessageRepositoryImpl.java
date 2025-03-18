package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.entity.QChatMessage;
import com.example.masterplanbbe.domain.entity.QMember;
import com.example.masterplanbbe.domain.repository.ChatMessageRepositoryCustom;
import com.example.masterplanbbe.presentation.response.ChatResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.masterplanbbe.domain.entity.QChatMessage.chatMessage;
import static com.example.masterplanbbe.domain.entity.QMember.member;

@Repository
@RequiredArgsConstructor
public class ChatMessageRepositoryImpl implements ChatMessageRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<ChatResponse> findChatList(Long lastChatId, Long specId, Pageable pageable) {
        QChatMessage parentMessage = new QChatMessage("parentMessage"); // 🔹 부모 메시지 별칭 추가
        QMember parentMember = new QMember("parentMember");

        BooleanBuilder whereBuilder = new BooleanBuilder();
        whereBuilder.and(chatMessage.specId.eq(specId));
        if (lastChatId != null) {
            whereBuilder.and(chatMessage.id.lt(lastChatId));
        }

        List<ChatResponse> chatMessages = queryFactory
                .select(Projections.constructor(ChatResponse.class,
                        chatMessage.id,
                        chatMessage.specId,
                        chatMessage.parentId,
                        chatMessage.member.id,
                        chatMessage.member.nickname,
                        chatMessage.content,
                        chatMessage.sendAt,
                        parentMessage.content.as("parentContent"), // 🔹 부모 메시지 내용
                        parentMember.nickname.as("parentNickname") // 🔹 부모 메시지 작성자 닉네임
                ))
                .from(chatMessage)
                .leftJoin(chatMessage.member, member)
                .leftJoin(parentMessage).on(chatMessage.parentId.eq(parentMessage.id))
                .leftJoin(parentMessage.member, parentMember)
                .where(whereBuilder)
                .orderBy(chatMessage.id.desc()) // 최신 메시지부터 정렬
                .offset(pageable.getOffset()) // 페이징 시작 위치
                .limit(pageable.getPageSize()) // 페이지 크기
                .fetch();

        boolean hasNext = chatMessages.size() > pageable.getPageSize(); // 🔹 다음 페이지 여부 체크

        return new SliceImpl<>(chatMessages, pageable, hasNext);
    }
}
