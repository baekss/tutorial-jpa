package com.bss.jpa.tutorial;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.tutorial.entity.Customer;

public class Create2 
{
    public static void main(String[] args) {
    	EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		Customer customer = new Customer();
    		customer.setCustomerName("S.S. BAEK");
    		customer.setAge(33);
    		
    		Customer customer2 = new Customer();
    		customer2.setCustomerName("홍길동");
    		customer2.setAge(22);
    		System.out.println("-----------------------");
    		//commit 때 insert문을 날리지 않는다. 왜냐하면 IDENTITY 전략은 insert를 하면서 자동증가 값을 얻어오기 때문에 insert 쿼리를 먼저 날림. commit까지 된 것은 아니다.
    		em.persist(customer);
    		em.persist(customer2);
    		System.out.println("-----------------------");
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
