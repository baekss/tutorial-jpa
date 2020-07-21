package com.bss.jpa.tutorial;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.tutorial.entity.Address;
import com.bss.jpa.tutorial.entity.Member;
import com.bss.jpa.tutorial.entity.Period;

public class Create 
{
    public static void main(String[] args) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		//비영속상태
    		Member member = new Member();
        	member.setId(100L);
        	member.setName("S.S. BAEK");
        	member.setAddress(new Address("서울","수서로","000-1234")); //@Embeddable 객체를 공유하지 말고 new로 각각 생성해서 사용해야 공유 참조 이슈를 피할 수 있다.
        	member.setPeriod(new Period(LocalDateTime.now(),LocalDateTime.now()));
        	Member member2 = new Member();
        	member2.setId(101L);
        	member2.setName("홍길동");
        	member2.setAddress(new Address("서울","서울로","123-4567"));
        	
        	//영속상태. insert 쿼리를 날리는 것은 아님. 1차캐시에 저장.
        	em.persist(member);
        	em.persist(member2);
        	
        	//만일 Address 객체를 공유했다면 member2의 Address city 속성도 변경감지에 의한 update 쿼리가 같이 발생했을 것이다.
        	//값 타입 객체는 setter를 없애서 불변객체로 만들자
        	//수정하고 싶을 땐 새로운 객체를 만들어 다시 할당한다.
        	//member.getAddress().setCity("경기");
        	
        	//insert쿼리 날림
        	tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		tx.rollback();
    	}finally{
    		em.close();
    	}
    	
    	emf.close();
	}
}
