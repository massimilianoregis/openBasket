package org.cart.obj;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.cart.repository.RowRepository;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;
@Entity
public class Basket 
{	
	static private 	ObjectMapper mapper = new ObjectMapper();
	
	@Id
	private String id;						//id univoco
	private Date date;						//data creazione
	private String user;					//user id
	private String name;					//nome carrello
	private String data;
	private String shop;
	private String status;					//stato del carrello
	
	@OneToMany	
	@Cascade(value=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Row> rows;					//righe
		
	//shipping
	@OneToOne
	@Cascade(value=CascadeType.ALL)
	private Shipping shipping;
	//billing
	@OneToOne
	@Cascade(value=CascadeType.ALL)
	private Payment payment;
	
	public Basket()
		{
		this.id=UUID.randomUUID().toString();
		this.date=new Date();
		this.rows= new ArrayList<Row>();		
		}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<Row> getRows() {
		return rows;
	}

	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
	public String getShop() {
		return shop;
	}
	public void setShop(String shop) {
		this.shop = shop;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String state) {
		this.status = state;
	}
	public String getUser() {
		return user;
	}
	
	public void setUser(String user) {
		this.user = user;
	}
	public Map getData()
		{		
		try{
			return mapper.readValue(data, Map.class);			
			}
		catch(Exception e){return new HashMap();}		
		}
	public void setData(String json)
		{
		this.data=json;
		}
	@JsonSetter
	public void setData(Map map)
		{	
		try	{
			this.data =mapper.writeValueAsString(map);
			}
		catch(Exception e){}		
		}
	
	public Shipping getShipping() {
		return shipping;
	}
	public void setShipping(Shipping shipping) {
		this.shipping = shipping;
	}
	public Payment getPayment() {
		return payment;
	}
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
}
