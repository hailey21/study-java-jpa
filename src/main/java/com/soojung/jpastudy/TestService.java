package com.soojung.jpastudy;

import com.soojung.jpastudy.entity.Member;
import com.soojung.jpastudy.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    public Member test() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();
        Member member1 = new Member();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜젝션 시작
            // 팀1 저장
            Team team = em.find(Team.class, "team4");

            int memberNumber = (int)(Math.random()*100);
            member1 = new Member("member" + memberNumber, "회원" + memberNumber);

            // 양방향 연관관계 설정
            member1.setTeam(team); // 연관관계 설정 member1 -> team1
            em.persist(member1);

            tx.commit();    // 트랜젝션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();  // 트랜젝션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }
        emf.close(); // 엔티티 매니저 팩토리 종료
        return member1;
    }

}
