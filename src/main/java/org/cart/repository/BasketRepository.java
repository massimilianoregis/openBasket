package org.cart.repository;

import java.util.List;

import org.cart.obj.Basket;
import org.springframework.data.repository.Repository;



public interface BasketRepository extends Repository<Basket, String> 
{
	public List<Basket> findAll();
	public Basket save(Basket wsdl);
	public Basket findOne(String id);
	public void delete(String entity);
	public boolean exists(String entity);
	
	public List<Basket> findByStatusAndUser(String status,String user);
	public List<Basket> findByShop(String shop);
	public List<Basket> findByShopAndUser(String shop,String user);
	public List<Basket> findByUser(String user);
}
