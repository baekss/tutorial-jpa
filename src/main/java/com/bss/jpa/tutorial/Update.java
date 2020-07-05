package com.bss.jpa.tutorial;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import com.bss.jpa.tutorial.entity.Member;

public class Update 
{
    public static void main(String[] args) throws JdbcSQLIntegrityConstraintViolationException {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	//트랜잭션 시작
    	tx.begin();
    	
    	try{
    		Member member = em.find(Member.class, 100L);
    		System.out.println("---------------------");
    		
    		System.out.println("Name : "+member.getName());
    		//member.setName("S.S. BAEK");
    		member.setName(new String("백승석"));
    		
    		//member(primary key 100)가 setter를 통해 속성을 set했을 때 무조건 update 쿼리를 날리는 것은 아니다. 
    		//String의 경우 hashCode와 equals를 통해 변경이 맞다면 update 쿼리를 날린다.
    		
    		System.out.println("---------------------");
        	
    		em.flush(); //flush는 변경이 감지되면 SQL 쓰기지연 저장소에있는 쿼리를 DB에 날리기만 한다 배타적 lock 작동, commit이 된 것은 아니다. 1차 캐시도 날라간 것은 아니다.
        	
        	//tx.commit(); commit이 되었다고 1차 캐시가 날라간 것은 아니다.
        	
        	//primary key가 같은 것을 조회하여 SELECT 쿼리 안 날리고 1차 캐시에서 결과를 얻어옴
        	Member member2 = em.find(Member.class, 100L);
        	
        	System.out.println("---------------------");
        	
        	System.out.println("Name : "+member2.getName());
        	//1차 캐시에서 결과를 얻었으므로 객체의 주소값이 같음
        	System.out.println("member == member2 ? "+ (member == member2));
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
