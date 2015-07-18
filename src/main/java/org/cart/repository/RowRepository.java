package org.cart.repository;

import java.util.List;

import org.cart.obj.Row;
import org.springframework.data.repository.Repository;



public interface RowRepository extends Repository<Row, String> 
{
	public List<Row> findAll();
	public Row save(Row wsdl);
	public Row findOne(String mail);
	public void delete(String entity);
	public boolean exists(String entity);
}
