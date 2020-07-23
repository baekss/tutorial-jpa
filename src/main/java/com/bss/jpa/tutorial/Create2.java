package com.bss.jpa.tutorial;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.tutorial.entity.Customer;
import com.bss.jpa.tutorial.entity.History;

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
    		
    		//Customer와 1:N 구조의 테이블로 저장된다.
    		customer.getFavoriteFoods().add("순대");
    		customer.getFavoriteFoods().add("떡볶이");
    		customer.getHistories().add(new History("초등학생","어리다"));
    		customer.getHistories().add(new History("중학생","조금 어리다"));
    		
    		Customer customer2 = new Customer();
    		customer2.setCustomerName("홍길동");
    		customer2.setAge(22);
    		System.out.println("-----------------------");
    		//commit 때 insert문을 날리지 않는다. 왜냐하면 IDENTITY 전략은 insert를 하면서 자동증가 값을 얻어오기 때문에 insert 쿼리를 먼저 날림. commit까지 된 것은 아니다.
    		em.persist(customer);
    		em.persist(customer2);
    		System.out.println("-----------------------");
    		
    		em.flush();
    		em.clear();
    		
    		Customer cust = em.find(Customer.class, customer.getId()); //Customer만 조회
    		cust.getFavoriteFoods().forEach(System.out::println); //실제 값 사용시 조회. 지연로딩 전략
    		cust.getHistories().forEach(System.out::println); //실제 값 사용시 조회. 지연로딩 전략
        	
    		cust.getFavoriteFoods().remove("순대"); //삭제할 것을 삭제 후
    		cust.getFavoriteFoods().add("김밥"); //새롭게 추가하여 수정기능을 구현한다.
    		
    		cust.getHistories().remove(new History("초등학생","어리다")); //동등성 비교로 삭제. 컬렉션 안의 요소 모두 삭제 후 남아있는 요소를 다시 모두 insert 하는 방식임. 비효율적이니 OneToMany로 활용하는 방법을 사용
    		cust.getHistories().add(new History("고등학생","이제 어리지 않다"));
    		cust.getHistories().set(0,new History("대학생","어른이다"));
    		
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
