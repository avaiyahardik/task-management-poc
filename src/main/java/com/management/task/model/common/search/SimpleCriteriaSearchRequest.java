package com.management.task.model.common.search;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;

import java.util.List;

public abstract class SimpleCriteriaSearchRequest<T> implements CriteriaSearchRequest<T> {
    private Class<T> clazz;
    private Sort defaultSort;

    protected SimpleCriteriaSearchRequest(Class<T> clazz, Sort defaultSort) {
        this.clazz = clazz;
        this.defaultSort = defaultSort;
    }

    protected abstract List<Predicate> getPredicates(CriteriaBuilder builder, Root<T> root, Authentication auth);

    public CriteriaQuery<Long> getCountCriteria(EntityManager entityManager, Authentication auth) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = builder.createQuery(Long.class);
        Root<T> eventRoot = query.from(clazz);
        Predicate[] predicates = getPredicates(builder, eventRoot, auth).toArray(new Predicate[0]);
        query.select(builder.count(eventRoot));
        if (predicates.length > 0) {
            query.where(predicates);
        }
        return query;
    }

    public CriteriaQuery<T> getCriteria(EntityManager entityManager, Pageable pageable, Authentication auth) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = builder.createQuery(clazz);
        Root<T> root = query.from(clazz);
        Predicate[] predicates = getPredicates(builder, root, auth).toArray(new Predicate[0]);
        if (predicates.length > 0) {
            query.where(predicates);
        }
        var sort = pageable != null ? pageable.getSortOr(defaultSort) : defaultSort;
        if (sort != null && !sort.isEmpty() && sort.isSorted()) {
            query.orderBy(getOrderByList(builder, query, sort, root));
        }
        return query;
    }

    protected List<Order> getOrderByList(CriteriaBuilder builder, CriteriaQuery<T> query, Sort sort, Root<T> root) {
        return sort.stream().map(s -> getOrder(s, builder, root.get(s.getProperty()))).toList();
    }
}
