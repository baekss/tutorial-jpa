package com.bss.jpa.shop;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import com.bss.jpa.shop.domain.Client;
import com.bss.jpa.shop.domain.Order;

public class JPQLJoinFetchMain {
	
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("shop");
    	EntityManager em = emf.createEntityManager();
    	EntityTransaction tx = em.getTransaction();
    	
    	tx.begin();
    	
    	try{
    		//Entity 필드에 즉시로딩(EAGER) 걸려있을 때 : 모든 연관관계 객체 N을 가져오기 위한 각각의 쿼리 날림
    		//Entity 필드에 지연로딩(LAZY) 걸려있을 때 : 모든 연관관계 객체에 대해 프록시를 반환하여 N+1 문제가 발생한다. 
    		//이를 조인쿼리로 한번에 모두 가져올수 있도록 fetch join 으로 해결한다.
    		/**
	    	select
	            order0_.ORDER_ID as ORDER_ID1_14_0_,
	            client1_.CLIENT_ID as CLIENT_I1_4_1_,
	            order0_.FK_CLIENT_ID as FK_CLIEN4_14_0_,
	            order0_.DELIVERY_ID as DELIVERY5_14_0_,
	            order0_.orderDate as orderDat2_14_0_,
	            order0_.status as status3_14_0_,
	            client1_.city as city2_4_1_,
	            client1_.street as street3_4_1_,
	            client1_.zipcode as zipcode4_4_1_,
	            client1_.name as name5_4_1_ 
	        from
	            ORDERS order0_ 
	        inner join
	            Client client1_ 
	                on order0_.FK_CLIENT_ID=client1_.CLIENT_ID
    		*/
    		//fetch join엔 가능하다 하더라도 별칭을 주지도 사용하지도 말자.
    		List<Order> orders = em.createQuery("SELECT o FROM Order o inner join fetch o.client", Order.class).getResultList();
    		//select 쿼리 안 날림
    		orders.get(0).getClient().getCity();
    		
    		em.clear();
    		
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
    		List<Order> orders2 = em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
    		//select 쿼리 날림
    		/**
		    select
		        client0_.CLIENT_ID as CLIENT_I1_4_0_,
		        client0_.city as city2_4_0_,
		        client0_.street as street3_4_0_,
		        client0_.zipcode as zipcode4_4_0_,
		        client0_.name as name5_4_0_ 
		    from
		        Client client0_ 
		    where
		        client0_.CLIENT_ID=?
    		*/
    		orders2.get(0).getClient().getCity();
    		
    		//JPQL DISTINCT 펑션을 사용하지 않으면 1:N 관계에서 N만큼 1에 해당하는 동일한 객체가 생성됨. 쿼리 조인 결과가 N만큼의 튜플로 나올거기 때문이다(이러한 이유로 페이징 api도 사용불가, 페이징은 꼭 1:1, N:1 일때만 사용!).
    		List<Client> clients = em.createQuery("SELECT DISTINCT c FROM Client c inner join fetch c.orders", Client.class).getResultList();
    		for(Client client : clients){
    			System.out.println("주문내역 : "+client.getOrders().size()+", 고객아이디, 이름 : "+client.getId()+" "+client.getName());
    			//주문내역 : 2, 고객아이디, 이름 : 1 S.S. BAEK
    			//주문내역 : 2, 고객아이디, 이름 : 33 G.D HONG
    		}
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
