package org.cart.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cart.repository.BasketRepository;
import org.cart.repository.RowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Shop 
	{
	@Autowired
	BasketRepository basketRepository;
	@Autowired
	RowRepository rowRepository;
		
	private String name;
	
	public Basket newBasket()
		{
		Basket basket= new Basket();
		this.basketRepository.save(basket);
		return basket;
		}
	public List<Basket> getBaskets(String shop, String user)
		{
		if(shop==null && user==null)
			return basketRepository.findAll();
		if(shop!=null && user==null)
			return basketRepository.findByShop(shop);
		if(shop!=null && user!=null)
			return basketRepository.findByShopAndUser(shop, user);
		if(shop==null && user!=null)
			return basketRepository.findByUser(user);
		return new ArrayList<Basket>();
		}
	public Basket getBasket(String id)
		{
		return basketRepository.findOne(id);
		}
	public void saveBasket(Basket basket)
		{
		this.basketRepository.save(basket);
		this.changed(basket.getId());
		this.changed(basket.getShop());
		}
	public List<Basket> getConfirm(String mail)
		{
		return basketRepository.findByStatusAndUser("confirm",mail);
		}
	
	Map<String,String> map = new HashMap();
	public void changed(String id)
		{
		id= map.get(id);
		if(id==null) return;
		synchronized (id) 
			{
			id.notifyAll();
			}
		}
	public List<Basket> notifyChangeShop(String id)
		{	
		System.out.println("notify change: "+id);
		if(!map.containsKey(id)) map.put(id, id);
		id = map.get(id);
		synchronized (id) 
			{			
			try{id.wait();}catch (Exception e) {}
			}
		return basketRepository.findByShop(id);
		}
	public Basket notifyChange(String id)
		{	
		System.out.println("notify change: "+id);
		if(!map.containsKey(id)) map.put(id, id);
		id = map.get(id);
		synchronized (id) 
			{			
			try{id.wait();}catch (Exception e) {}
			}
		return basketRepository.findOne(id);
		}
	}
