package com.bss.jpa.tutorial;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import com.bss.jpa.tutorial.entity.Member;

public class Create 
{
    public static void main(String[] args) throws JdbcSQLIntegrityConstraintViolationException {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	//트랜잭션 시작
    	tx.begin();
    	
    	try{
    		//비영속상태
        	Member member = new Member();
        	member.setId(100L);
        	member.setName("백승석");
        	
        	Member member2 = new Member();
        	member2.setId(101L);
        	member2.setName("홍길동");
        	
        	//영속상태
        	em.persist(member);
        	em.persist(member2);
        	
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
