package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.entity.ChatMessage;
import com.example.masterplanbbe.domain.entity.Member;
import com.example.masterplanbbe.domain.fixture.ChatMessageFixture;
import com.example.masterplanbbe.domain.repository.ChatMessageRepository;
import com.example.masterplanbbe.domain.repository.MemberRepository;
import com.example.masterplanbbe.infrastructure.util.SnowflakeIdGenerator;
import com.example.masterplanbbe.presentation.response.ChatResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ChatMessageRepositoryImplTest {

    @Autowired
    private ChatMessageRepository chatRepository;

    @Autowired
    private ChatMessageRepositoryImpl chatRepositoryImpl;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private SnowflakeIdGenerator idGenerator;

    @PersistenceContext
    private EntityManager em;

    @Test
    @DisplayName("채팅 메시지를 조회하고 부모 메시지 및 작성자의 닉네임을 가져온다.")
    void findChatList_withParentMessage() {
        Member member1 = createPersistedMember();
        Member member2 = createPersistedMember();

        Long parentId = idGenerator.nextId();
        Long childId = idGenerator.nextId();

        ChatMessage parentMessage = ChatMessageFixture.createMessage(parentId, null, "부모", member1);
        chatRepository.save(parentMessage);
        em.flush();
        em.clear();

        ChatMessage childMessage = ChatMessageFixture.createMessage(childId, parentId, "자식", member2);
        chatRepository.save(childMessage);
        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0, 10);
        Slice<ChatResponse> chatList = chatRepositoryImpl.findChatList(null, 1L, pageable);

        assertThat(chatList).isNotEmpty();
        assertThat(chatList.getContent().get(0).content()).isEqualTo("자식");
        assertThat(chatList.getContent().get(0).parentContent()).isEqualTo("부모");
        assertThat(chatList.getContent().get(0).nickname()).isEqualTo(member2.getNickname());
        assertThat(chatList.getContent().get(0).parentNickname()).isEqualTo(member1.getNickname());
    }

    @Test
    @DisplayName("lastChatId가 마지막 메시지일 때")
    void testLastChatIdAtEnd() {
        Member member1 = createPersistedMember();

        Long chat1Id = idGenerator.nextId();
        Long chat2Id = idGenerator.nextId();

        ChatMessage chat1 = ChatMessageFixture.createMessage(chat1Id, null, "chat1", member1);
        chatRepository.save(chat1);
        em.flush();
        em.clear();

        ChatMessage chat2 = ChatMessageFixture.createMessage(chat2Id, null, "chat2", member1);
        chatRepository.save(chat2);
        em.flush();
        em.clear();

        Pageable pageable = PageRequest.of(0, 10);
        Slice<ChatResponse> chatList = chatRepositoryImpl.findChatList(chat1Id, 1L, pageable);
        assertThat(chatList.getContent().size()).isEqualTo(0);
    }

    @Test
    @DisplayName("채팅 메시지 대량 조회 (size가 큰 경우)")
    void testLargeSizePagination() {
        Member member = createPersistedMember();
        for (int i = 0; i < 100; i++) {
            Long snowflakeId = idGenerator.nextId();
            ChatMessage parentMessage = ChatMessageFixture.createMessage(snowflakeId, null, "chat" + i, member);
            chatRepository.save(parentMessage);
            em.flush();
            em.clear();
        }

        int pageSize = 50;
        Pageable pageable = PageRequest.of(0, pageSize);
        Slice<ChatResponse> chatList = chatRepositoryImpl.findChatList(null, 1L, pageable);

        assertThat(chatList).isNotEmpty();
        assertThat(chatList.getContent()).hasSize(pageSize);
    }

    private Member createPersistedMember() {
        String uniqueEmail = "test-" + UUID.randomUUID() + "@test.com";

        Member member = new Member(
                new com.example.masterplanbbe.presentation.request.MemberCreateRequestDTO(
                        uniqueEmail,
                        "User-" + UUID.randomUUID().toString().substring(0, 6),
                        "password123",
                        false
                ),
                "password123",
                com.example.masterplanbbe.domain.enums.MemberRoleEnum.USER
        );

        memberRepository.save(member);
        em.flush();
        em.clear();
        return member;
    }
}
