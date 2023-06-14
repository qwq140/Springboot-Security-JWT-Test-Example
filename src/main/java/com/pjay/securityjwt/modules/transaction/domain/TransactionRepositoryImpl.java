package com.pjay.securityjwt.modules.transaction.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

interface Dao {
    List<Transaction> findTransactionList(@Param("accountId") Long accountId, @Param("gubun") String gubun, @Param("page") Integer page);
}


// Impl을 꼭 붙여줘야 하고, TransactionRepository가 앞에 붙어야 한다.
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements Dao{

    private final EntityManager em;

    // fetch join : JPQL에서 성능 최적하를 위해 제공하는 기능
    // 연관된 엔티티나 컬렉션을 sql 한번에 함께 조회하는 기능이다.
    // fetch를 안붙이면 프로젝션을 안함(select 절에 포함을 안시킨다)
    @Override
    public List<Transaction> findTransactionList(Long accountId, String gubun, Integer page) {
        // JPQL
        String sql = "";
        sql += "select t from Transaction t ";

        if(gubun.equals("WITHDRAW")){
            sql += "join fetch t.withdrawAccount wa ";
            sql += "where t.withdrawAccount.id = :withdrawAccountId";
        } else if(gubun.equals("DEPOSIT")){
            sql += "join fetch t.depositAccount da ";
            sql += "where t.depositAccount.id = :depositAccountId";
        } else {
            sql += "left join fetch t.withdrawAccount wa ";
            sql += "left join fetch t.depositAccount da ";
            sql += "where t.withdrawAccount.id = :withdrawAccountId ";
            sql += "or ";
            sql += "t.depositAccount.id = :depositAccountId";
        }

        // createQuery
        TypedQuery<Transaction> query = em.createQuery(sql, Transaction.class);

        if(gubun.equals("WITHDRAW")){
            query = query.setParameter("withdrawAccountId", accountId);
        } else if(gubun.equals("DEPOSIT")){
            query = query.setParameter("depositAccountId", accountId);
        } else {
            query = query.setParameter("withdrawAccountId", accountId);
            query = query.setParameter("depositAccountId", accountId);
        }

        query.setFirstResult(page * 5); // 5, 10, 15
        query.setMaxResults(5);

        return query.getResultList();
    }
}
