package com.reportes.services;



import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.reportes.model.Usuario;



@FeignClient(name="get",url ="http://localhost:8080/netv/")
public interface IServiceClientFeign {
	
		
	@RequestMapping(method = RequestMethod.GET, value = "/usuario")
	List<Usuario> getAll();

	
	
	
	
}
