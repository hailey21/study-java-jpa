package com.soojung.jpastudy;

import com.soojung.jpastudy.entity.Member;
import com.soojung.jpastudy.entity.Team;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class MappingMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpabook");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin(); // 트랜젝션 시작
//            login(em);  // 비즈니스 로직 실행
//            testSave(em);
//            updateRelation(em);
//            deleteRelation(em);
//            biDirection(em);
//            testSaveNonOwner(em);
            testORMBiDirection(em);
            tx.commit();    // 트랜젝션 커밋
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();  // 트랜젝션 롤백
        } finally {
            em.close(); // 엔티티 매니저 종료
        }
        emf.close(); // 엔티티 매니저 팩토리 종료
    }

    private static void testSave(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team1", "팀1", new ArrayList<>());
        em.persist(team1);

        // 회원1 저장
        Member member1 = new Member("member1", "회원1");
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
        em.persist(member1);

        // 회원2 저장
        Member member2 = new Member("member2", "회원2");
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
        em.persist(member2);
    }

    private static void updateRelation(EntityManager em) {
        // 새로운 팀2
        Team team2 = new Team("team2", "팀2", new ArrayList<>());
        em.persist(team2);

        // 회원1에 새로운 팀2 설정
        Member member = em.find(Member.class, "member1");
        member.setTeam(team2);
    }

    private static void deleteRelation(EntityManager em) {
        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null); // 연관관계 제거
    }

    // 일대 다 방향으로 객체 그래프 탐색
    private static void biDirection(EntityManager em) {
        Team team = em.find(Team.class, "team1");
        List<Member> members = team.getMembers();   // 팀 -> 회원 객체 그래프 탐색

        for (Member member : members) {
            System.out.println("member.username = " + member.getUsername());
        }
    }

    private static void testSaveNonOwner(EntityManager em) {
        // 회원3 저장
        Member member3 = new Member("member3", "회원3");
        em.persist(member3);

        // 회원4 저장
        Member member4 = new Member("member4", "회원4");
        em.persist(member4);

        Team team3 = new Team("team3", "팀3", new ArrayList<>());
        team3.getMembers().add(member3);    // 연관관계 설정 member3 -> team3
        team3.getMembers().add(member4);    // 연관관계 설정 member4 -> team3
        em.persist(team3);
    }

    private static void testORMBiDirection(EntityManager em) {
        // 팀1 저장
        Team team1 = new Team("team4", "팀4", new ArrayList<>());
        em.persist(team1);

        Member member1 = new Member("member5", "회원5");

        // 양방향 연관관계 설정
        member1.setTeam(team1); // 연관관계 설정 member1 -> team1
//        team1.getMembers().add(member1);   // 연관관계 설정 team1 -> member1
        em.persist(member1);

        Member member2 = new Member("member6", "회원6");

        // 양방향 연관관계 설정
        member2.setTeam(team1); // 연관관계 설정 member2 -> team1
//        team1.getMembers().add(member2);   // 연관관계 설정 team1 -> member2
        em.persist(member2);

        System.out.println("member2 = " + member2);

    }
}
