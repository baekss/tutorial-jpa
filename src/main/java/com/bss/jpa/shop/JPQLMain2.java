package com.bss.jpa.shop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.bss.jpa.shop.domain.Client;
import com.bss.jpa.shop.domain.Order;
import com.bss.jpa.shop.domain.OrderDto;

public class JPQLMain2 {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		/**
		    	select
			        order0_.ORDER_ID as ORDER_ID1_14_,
			        order0_.FK_CLIENT_ID as FK_CLIEN4_14_,
			        order0_.DELIVERY_ID as DELIVERY5_14_,
			        order0_.orderDate as orderDat2_14_,
			        order0_.status as status3_14_ 
			    from
            	ORDERS order0_
    		*/
    		List<Order> orders = em.<Order>createQuery("SELECT o FROM Order o", Order.class).getResultList();
    	
    		/**
		    	select
			        client1_.CLIENT_ID as CLIENT_I1_4_,
			        client1_.city as city2_4_,
			        client1_.street as street3_4_,
			        client1_.zipcode as zipcode4_4_,
			        client1_.name as name5_4_ 
			    from
            	ORDERS order0_ 
        		inner join
            	Client client1_ 
                on order0_.FK_CLIENT_ID=client1_.CLIENT_ID
    		*/
    		try {
    			//비권장
    			Client clientNotGood = em.createQuery("SELECT o.client FROM Order o", Client.class).getSingleResult();
    			//권장
    			Client clientGood = em.createQuery("SELECT c FROM Order o join o.client c", Client.class).getSingleResult();
    		}catch(NonUniqueResultException e){
    			e.printStackTrace();
    		}catch(NoResultException e){
    			e.printStackTrace();
    		}
    		
    		
    		/**
    		select
        		order0_.ORDER_ID as col_0_0_, 숫자형
        		order0_.status as col_1_0_ from 문자형
            ORDERS order0_
    		 */
    		//결과값이 서로 다른 타입으로 나오므로
    		List<Object[]> resultSet = em.createQuery("SELECT o.id, o.status FROM Order o").getResultList();
    		
    		//Object o =resultSet.get(0);
    		//Object[] resultColumn = (Object[]) o;
    		Object[] resultColumn =resultSet.get(0);
    		System.out.println(resultColumn[0]); //ORDER_ID
    		System.out.println(resultColumn[1]); //status
    		
    		//Dto객체를 만들어 해결하는 방법
    		TypedQuery<OrderDto> query = em.createQuery("SELECT new com.bss.jpa.shop.domain.OrderDto(o.id, o.status) FROM Order o", OrderDto.class);
    		List<OrderDto> orderDtos = query.getResultList();
    		orderDtos.forEach((dto)->{System.out.println(dto.getId()+" "+dto.getStatus());});
    		
    		//페이징 쿼리
    		/**
    		select 
	            order0_.ORDER_ID as ORDER_ID1_14_, 
	            order0_.FK_CLIENT_ID as FK_CLIEN4_14_, 
	            order0_.DELIVERY_ID as DELIVERY5_14_, 
	            order0_.orderDate as orderDat2_14_, 
	            order0_.status as status3_14_ 
        	from 
            	ORDERS order0_ 
        	order by 
            	order0_.ORDER_ID desc 
            	limit 5 offset 1
    		*/
    		String sql = "SELECT o FROM Order o order by o.id desc";
    		List<Order> orders2 = em.<Order>createQuery(sql, Order.class)
    				.setFirstResult(1) //0으로 시작하면 결과에서 첫번째 row부터 가져오는 것 이므로 limit만 설정하여 쿼리가 실행된다.
    				.setMaxResults(5)
    				.getResultList();
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
