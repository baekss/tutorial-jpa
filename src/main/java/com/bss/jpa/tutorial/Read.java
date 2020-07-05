package com.bss.jpa.tutorial;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import com.bss.jpa.tutorial.entity.User;

public class Read 
{
    public static void main(String[] args) throws JdbcSQLIntegrityConstraintViolationException {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	//트랜잭션 시작
    	tx.begin();
    	
    	try{
    		List<User> list = em.createQuery("SELECT m FROM User m").getResultList();
    		list.stream().map((user)->{return user.getUserName();}).forEach(System.out::println);
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
