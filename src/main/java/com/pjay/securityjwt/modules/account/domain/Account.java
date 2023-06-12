package com.pjay.securityjwt.modules.account.domain;

import com.pjay.securityjwt.handler.ex.CustomApiException;
import com.pjay.securityjwt.modules.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor // 스프링이 User 객체생성할 때 빈 생성자로 new를 하기 때문에 필요
@Getter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "account_tb")
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false, length = 4)
    private Long number; // 계좌번호
    @Column(nullable = false, length = 4)
    private Long password; // 계좌비밀번호
    @Column(nullable = false)
    private Long balance; // 잔액 (기본값 1000원)

    // 항상 ORM에서 fk의 주인은 Many Entity 쪽이다.
    @ManyToOne(fetch = FetchType.LAZY) // account.getUser().아무필드호출() == Lazy 발동
    private User user; // 테이블에서 컬럼명 user_id

    @CreatedDate // Insert
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate // Insert, Update
    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public Account(Long id, Long number, Long password, Long balance, User user, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.balance = balance;
        this.user = user;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void checkOwner(Long userId){
//        String username = user.getUsername(); // Lazy 로딩이 되어야 한다.
//        System.out.println("테스트 : "+ username);
        if(user.getId().longValue() != userId.longValue()){ // Lazy 로딩이여도 id를 조회할 때는 select 쿼리가 날라가지 않는다.
            throw new CustomApiException("계좌 소유자가 아닙니다");
        }
    }

    public void checkSamePassword(Long password){
        if(this.password.longValue() != password.longValue()){
            throw new CustomApiException("계좌 비밀번호 검증에 실패했습니다");
        }
    }

    public void deposit(Long amount){
        balance = balance + amount;
    }


    public void checkBalance(Long amount) {
        if(this.balance < amount){
            throw new CustomApiException("계좌 잔액이 부족합니다");
        }

    }

    public void withdraw(Long amount) {
        checkBalance(amount);
        balance = balance - amount;
    }
}
