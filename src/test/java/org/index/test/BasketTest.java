package org.index.test;

import org.cart.obj.Basket;
import org.cart.obj.Payment;
import org.cart.obj.Shipping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

public class BasketTest 
	{

	private ObjectMapper mapper = new ObjectMapper();
	public String toJson(Object obj)
		{
		try{
			return mapper.writeValueAsString(obj);
			}
		catch (Exception e) {
			e.printStackTrace();
			return "{}";
			}
		}

	public void saveBasket()
		{
		Payment payment = new Payment();
			payment.setAddress("indirizzo");
			payment.setCity("city");
			payment.setCompany("company");
			payment.setContry("country");
			payment.setFirst_name("first");
			payment.setLast_name("last");
			payment.setLine1("line1");
			payment.setLine2("line2");
			payment.setName("name");
			payment.setPhone("phone");
			payment.setState("state");
		Shipping shipping = new Shipping();
			shipping.setAddress("indirizzo");
			shipping.setCity("city");
			shipping.setCompany("company");
			shipping.setContry("country");
			shipping.setFirst_name("first");
			shipping.setLast_name("last");
			shipping.setLine1("line1");
			shipping.setLine2("line2");
			shipping.setName("name");
			shipping.setPhone("phone");
			shipping.setState("state");
		Basket basket= new Basket();
			basket.setName("basket");
			basket.setShop("bunga");
			basket.setStatus("wish");
			basket.setUser("max");
			basket.setShipping(shipping);
			basket.setPayment(payment);
			
		new RestTemplate().postForLocation("http://localhost:8080/basket", basket);
		}
	
	public static void main(String[] args) {
		new BasketTest().saveBasket();
	}
}
