package com.management.task.model.common.search;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

public interface CriteriaSearchRequest<T> {

    CriteriaQuery<T> getCriteria(EntityManager entityManager, Pageable pageable, Authentication auth);


    CriteriaQuery<Long> getCountCriteria(EntityManager entityManager, Authentication auth);

    default Order getOrder(Sort.Order s, CriteriaBuilder builder, Expression<?> e) {
        return s.getDirection().equals(Sort.Direction.DESC) ? builder.desc(e) : builder.asc(e);
    }

}
