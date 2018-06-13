package com.axonactive.training.sniffer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doAnswer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Arrays;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;
import org.hamcrest.core.Is;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import com.axonactive.training.address.*;
import com.axonactive.training.gpslocation.*;




@RunWith(MockitoJUnitRunner.class)
public class SnifferServiceTest {
	
	@Mock
	private EntityManager em;
	
	@InjectMocks
	SnifferService service;
	
    @Mock
    TypedQuery<Sniffer> mockQuery;


    @Test
    public void whenAddValidSnifferThenTheSnifferStoredIntoSystem() {
    	Sniffer snifferABC = new Sniffer("0A-0B-0C-1A-2A-3C",new Address("VietNam","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation()));
    	
    	doAnswer(new Answer<Sniffer>() {
    	@Override
    		public Sniffer answer(InvocationOnMock sn) throws Throwable {
    			Sniffer sniffer = (Sniffer) sn.getArguments()[0];
    			sniffer.setCode("123456");
    			return sniffer;
    		}    		
    	}).when(this.em).persist(snifferABC);    	
		this.service.add(snifferABC);

		assertEquals("123456", snifferABC.getCode());
    }
	@Test
	public void givenMacAdresIsNullWheChecknMacAddressThenReturnFalse(){
		String macAddress = null;
		service.checkMacAddress(macAddress);
	}
	
	@Test
	public void givenValidMacAddressWhenCheckMacAddressThenGetTrue(){
		String macAddress = "8C-EC-4B-49-50-6A";
		boolean result =service.checkMacAddress(macAddress);
		assertEquals(true, result);
	}
	
	@Test
	public void givenFalseWhenCheckMacAddressThenGetFalse() {
		String macAddress = "8C-EC-4B-49-50-6AA";
		boolean result =service.checkMacAddress(macAddress);
		Assert.assertFalse(result);
	}
	@Test
	public void givenValidAddressWhenCheckAddressThenCheckAddressReturnTrue() {
		Address address = new Address("VietNam","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation());
		boolean result = service.checkAddress(address);
		assertThat(result, Is.is(true));
//		assertTrue(result);
	}
	@Test
	public void givenMissingCountryWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address("","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation());
		boolean result = service.checkAddress(address);
		assertThat(result, Is.is(false));
	}
	@Test(expected = IllegalArgumentException.class)
	public void givenNullCountryWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address(null,"Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation());
		service.checkAddress(address);
		}
	@Test
	public void givenMissingProvinveWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address("VN","","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation());
		boolean result = service.checkAddress(address);
		assertThat(result, Is.is(false));
	}
	@Test(expected = IllegalArgumentException.class)
	public void givenNullProvinveWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address("VN",null,"Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation());
		service.checkAddress(address);
	}
	@Test
	public void givenMissingDistrictWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address("VN","A","","B","C","D",new Date(),new Date(),new GpsLocation());
		boolean result = service.checkAddress(address);
		assertThat(result, Is.is(false));
	}
	@Test(expected = IllegalArgumentException.class)
	public void givenNullDistrictWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address("VN","",null,"","","",new Date(),new Date(),new GpsLocation());
		service.checkAddress(address);	
	}
	
	@Test
	public void givenMissingStressWWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address("VN","","Ninh Kieu","A","","",new Date(),new Date(),new GpsLocation());
		boolean result = service.checkAddress(address);
		assertThat(result, Is.is(false));
	}
	@Test(expected = IllegalArgumentException.class)
	public void givenNullStreetWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address("VN","","Ninh Kieu","A",null,"",new Date(),new Date(),new GpsLocation());
		service.checkAddress(address);		
	}
	@Test(expected = IllegalArgumentException.class)
	public void givenNullStreetAndNullValidFromWhenCheckAddressThenCheckAddressReturnFalse() {
		Address address = new Address("VN","","Ninh Kieu","A",null,"",null,new Date(),new GpsLocation());
		service.checkAddress(address);		
	}
	@Test(expected = IllegalArgumentException.class)
	public void givenNullAddressWhenCheckAddressThenCheckAddressGetIllegalArgumentException() {
		Address address = null;
		service.checkAddress(address);		
	}
	@Test
	public void givenValidCheckDigitWhenCheckDigitThenGetTrue() {
		String str= "00";
		boolean result=service.checkDigit(str);
		assertThat(result, Is.is(true));
	}
	@Test
	public void givenInValidCheckDigitWhenCheckDigitThenCheckDigitReturnFalse() {
		String str= "0G";
		boolean result=service.checkDigit(str);
		assertThat(result, Is.is(false));
	}
	@Test
	public void givenOverLenghtCheckDigitWhenCheckDigitThenCheckDigitReturnFalse() {
		String str= "000";
		boolean result=service.checkDigit(str);
		assertThat(result, Is.is(false));
	}
	@Test
	public void givenLessLenghtCheckDigitWhenCheckDigitThenCheckDigitReturnFalse() {
		String str= "0";
		boolean result=service.checkDigit(str);
		assertThat(result, Is.is(false));
	}
	@Test
	public void givenWrongCheckDigitWhenCheckDigitThenCheckDigitReturnFalse() {
		String str= "0a";
		boolean result=service.checkDigit(str);
		assertThat(result, Is.is(false));
	}
	@Test(expected = IllegalArgumentException.class)
	public void givenSnifferIsNullWhenAddThenIllegalArgumentException() {		
		Sniffer sniffer= null;
		service.add(sniffer);

		}
	@Test 
	public void givenInValidMacAddressWhenAddThenSnifferNotStored() {	
		Sniffer sniffer= new Sniffer("",new Address("VietNam","Can Tho","Ninh Kieu","","","",new Date(),new Date(),new GpsLocation()));
		service.add(sniffer);		
		assertNull(service.add(sniffer));
	}
	@Test 
	public void givenInValidAddressWhenAddThenSnifferNotStored() {	
		Sniffer sniffer = new Sniffer("0A-0B-0C-1A-2A-3C",new Address("","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation()));
		assertNull(service.add(sniffer));

	}
//	@Test
//	public void givenOneSnifferWhenFindSnifferByCodeThenShowSniffer() {
//		Sniffer sniffer = new Sniffer("0A-0B-0C-1A-2A-3C",new Address("","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation()));
//		sniffer.setCode("123456");
//		Mockito.when(this.em.find(Sniffer.class, "123456")).thenReturn(sniffer);
//		Sniffer newSniffer = service.findByCode("123456");
//		assertEquals("123456",newSniffer.getCode());
//	}
	@Test
	public void givenAllSniffersWhenGetAllSnifferInSystem() {
		Sniffer sniffer1 = new Sniffer("0A-0B-0C-1A-2A-3C",new Address("","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation()));
		Sniffer sniffer2 = new Sniffer("0A-0B-0C-1A-2A-3C",new Address("","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation()));
		Mockito.when(this.em.createNamedQuery("Sniffer.getAllSniffer", Sniffer.class)).thenReturn(mockQuery);
		Mockito.when(mockQuery.getResultList()).thenReturn(Arrays.asList(sniffer1, sniffer2));
		List<Sniffer> listSniffer = service.getAllSniffer();
		Assert.assertEquals(2,listSniffer.size());
	}


    @Test
    public void whenDeleteSnifferThenTheSnifferDeleted() {
    	Sniffer snifferABC = new Sniffer("0A-0B-0C-1A-2A-3C",new Address("VietNam","Can Tho","Ninh Kieu","","Tran Van Hoai","",new Date(),new Date(),new GpsLocation()));
    	snifferABC.setCode("exampleCode");
//    	Integer id = service.add(snifferABC);
    	Mockito.when(em.createNamedQuery("Sniffer.findByCode", Sniffer.class)).thenReturn(mockQuery);
    	Mockito.when(mockQuery.getSingleResult()).thenReturn(snifferABC);
    	
    	doAnswer(new Answer<Sniffer>() {
        	@Override
        		public Sniffer answer(InvocationOnMock sn) throws Throwable {
        			Sniffer sniffer = (Sniffer) sn.getArguments()[0];
        			sniffer.setCode("123456");
        			return sniffer;
        		}    		
        	}).when(this.em).remove(snifferABC);
    	this.service.delete(snifferABC.getCode());
    	Assert.assertEquals("123456", snifferABC.getCode());
//    	assertThat(service.delete(id), Is.is(1));
    }
    
    

	
	

}
