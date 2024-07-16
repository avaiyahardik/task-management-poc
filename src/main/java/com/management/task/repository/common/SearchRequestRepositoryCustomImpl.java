package com.management.task.repository.common;

import com.management.task.model.common.search.CriteriaSearchRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class SearchRequestRepositoryCustomImpl<T> implements SearchRequestRepositoryCustom<T> {
    @Autowired
    protected EntityManager entityManager;

    public Page<T> queryPage(CriteriaSearchRequest<T> request, Pageable pageable) {
        return queryPage(request, pageable, null);
    }

    public Page<T> queryPage(CriteriaSearchRequest<T> request, Pageable pageable, Authentication auth) {
        return PageableExecutionUtils.getPage(query(request, pageable, auth), pageable, () -> count(request, auth));
    }

    public List<T> query(CriteriaSearchRequest<T> request, Pageable pageable, Authentication auth) {
        CriteriaQuery<T> query = request.getCriteria(entityManager, pageable, auth);
        TypedQuery<T> tquery = entityManager.createQuery(query);
        if (pageable != null) {
            tquery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
            tquery.setMaxResults(pageable.getPageSize());
        }
        return tquery.getResultList();
    }

    public long count(CriteriaSearchRequest<T> request, Authentication auth) {
        CriteriaQuery<Long> query = request.getCountCriteria(entityManager, auth);
        TypedQuery<Long> tquery = entityManager.createQuery(query);
        try {
            return tquery.getSingleResult();
        } catch (NoResultException e) {
            return 0L;
        }
    }
}
