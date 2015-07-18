package org.cart.obj;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Row  
{		
	static private 	ObjectMapper mapper = new ObjectMapper();
	
	@Id	
	private String id;			//id
	private Float qta;			//qta	
	
	private String code;		//codice prodotto
	private String name;		//nome
	private Float price;		//prezzo
	private Float discount;	
	@Column(columnDefinition="TEXT")
	private String img;			//immagine	
	
	private String link;		//link
	private String object;		//oggetto in json
	private String status;		
		
	@Transient	
	private Basket basket;
	
	public Row(){}
	public Row(Basket basket,String code, String name, Float price, Float discount, String img)
		{				
		this.id			= UUID.randomUUID().toString();
		this.qta		= 1F;
		this.code		= code;
		this.name		= name;
		this.price		= price;
		this.discount	= discount;
		this.basket		= basket;		
		this.img		= img;
		}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Float getQta() {
		return qta;
	}
	public void setQta(Float qta) {
		this.qta = qta;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	public Float getDiscount() {
		return discount;
	}
	public void setDiscount(Float discount) {
		this.discount = discount;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String imgUrl) {
		this.img = imgUrl;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public Map getObject()
	{		
	try{
		return mapper.readValue(object, Map.class);			
		}
	catch(Exception e){return new HashMap();}		
	}
	public void setObject(String json)
		{
		this.object=json;
		}
	@JsonSetter
	public void setObject(Map map)
		{	
		try	{
			this.object =mapper.writeValueAsString(map);
			}
		catch(Exception e){}		
		}
}
