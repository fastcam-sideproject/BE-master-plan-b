package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.repository.PostRepository;
import com.example.masterplanbbe.domain.repository.PostRepositoryPort;
import com.example.masterplanbbe.infrastructure.exception.GlobalException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;
import com.example.masterplanbbe.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PostRepositoryAdapter implements PostRepositoryPort {

    private final PostRepository postRepository;

    @Override
    public Post findById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FIND_POST) {});
    }

    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Override
    public void delete(Long id) {
        Post post = findById(id);
        postRepository.delete(post);
    }

    @Override
    public Page<Post> findByTitleContaining(String keyword, Pageable pageable) {
        return postRepository.findByTitleContaining(keyword,pageable);
    }

    @Override
    public Page<Post> findAllByIdIn(List<Long> postIdList, Pageable pageable) {
        return postRepository.findByIdIn(postIdList, pageable);
    }

    @Override
    public Page<Post> findByMemberId(Long memberId,Pageable pageable) {
        return postRepository.findByMemberId(memberId, pageable);
    }

}
