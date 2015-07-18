package org.cart.service;

import java.util.List;

import org.cart.obj.Basket;
import org.cart.obj.Row;
import org.cart.obj.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CartService 
{
@Autowired
Shop shop;



@RequestMapping(value="/basket",method=RequestMethod.GET)
public @ResponseBody List<Basket> getList(@RequestParam(required=false) String shop, @RequestParam(required=false) String user)
	{
	return this.shop.getBaskets(shop,user);
	}
@RequestMapping(value="/basket/{id}",method=RequestMethod.GET)
public @ResponseBody Basket get(@PathVariable String id)
	{
	return this.shop.getBasket(id);
	}
@RequestMapping(value="/basket/{id}",method=RequestMethod.POST)
public @ResponseBody Id save(@PathVariable String id,@RequestBody Basket basket)
	{
	shop.saveBasket(basket);
	return new Id(basket.getId());
	}
@RequestMapping(value="/basket",method=RequestMethod.POST)
public @ResponseBody Id post(@RequestBody Basket basket)
	{
	shop.saveBasket(basket);
	
	return new Id(basket.getId());
	}

@RequestMapping(value="/basket/confirm/{mail:.+}",method=RequestMethod.GET)
public @ResponseBody List<Basket> confirm(@PathVariable String mail)
	{
	return shop.getConfirm(mail);
	}

@RequestMapping(value="/basket/synch/shop/{shop}",method=RequestMethod.GET)
public @ResponseBody List<Basket> synchShop(@PathVariable String shop)
	{
	return this.shop.notifyChangeShop(shop);
	}

@RequestMapping(value="/basket/synch/{id}",method=RequestMethod.GET)
public @ResponseBody Basket synch(@PathVariable String id)
	{
	return shop.notifyChange(id);
	}

@RequestMapping(value="/basket/change/{id}",method=RequestMethod.GET)
public @ResponseBody void changed(@PathVariable String id)
	{
	shop.changed(id);
	}

@RequestMapping(value="/basket/add",method=RequestMethod.GET)
public @ResponseBody Basket add()
	{
	Basket bsk = new Basket();
		bsk.getRows().add(new Row(bsk, "A", "prodotto A", 100F, 80F, "no Image"));
		bsk.getRows().add(new Row(bsk, "B", "prodotto B", 100F, 80F, "no Image"));
		shop.saveBasket(bsk);
	return bsk;
	}

public static class Id
	{
	String id;
	public Id(String id){this.id=id;}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	}
}
