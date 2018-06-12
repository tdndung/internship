package com.axonactive.training.sniffer;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.axonactive.training.address.Address;
import com.axonactive.training.gpslocation.GpsLocation;

@Startup
@Singleton

public class SnifferStartup {
	@PersistenceContext  
	EntityManager em;
	
	@PostConstruct
	public void init() {
		Sniffer sniffer = new Sniffer("0A-0B-0C-1A-2A-3C",new Address("VietNam","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation()));
		Sniffer abc = new Sniffer("0A-0B-0C-1A-2A-3C", "name", "8C-EC-4B-49-50-6A",null, new Address("VietNam","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation()));
		sniffer.setCode("123456");
		em.persist(sniffer);
		abc.setCode("123457");
		em.persist(abc);
	}

}
