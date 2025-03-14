package com.example.masterplanbbe.infrastructure.repository;

import com.example.masterplanbbe.domain.repository.CommentRepository;
import com.example.masterplanbbe.domain.repository.CommentRepositoryPort;
import com.example.masterplanbbe.infrastructure.exception.GlobalException;
import com.example.masterplanbbe.infrastructure.exception.ErrorCode;
import com.example.masterplanbbe.domain.entity.Comment;
import com.example.masterplanbbe.domain.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class CommentRepositoryAdapter implements CommentRepositoryPort {

    private final CommentRepository commentRepository;

    @Override
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new GlobalException(ErrorCode.NOT_FOUND_COMMENT) {});
    }

    @Override
    public List<Comment> findByPost(Post post) {
        return commentRepository.findByPost(post);
    }

    @Override
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}
