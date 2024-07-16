package com.management.task.repository.common;

import com.management.task.model.common.search.CriteriaSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface SearchRequestRepositoryCustom<T> {
    Page<T> queryPage(CriteriaSearchRequest<T> request, Pageable pageable);

    Page<T> queryPage(CriteriaSearchRequest<T> request, Pageable pageable, Authentication auth);

    List<T> query(CriteriaSearchRequest<T> request, Pageable pageable, Authentication auth);

    long count(CriteriaSearchRequest<T> request, Authentication auth);
}
