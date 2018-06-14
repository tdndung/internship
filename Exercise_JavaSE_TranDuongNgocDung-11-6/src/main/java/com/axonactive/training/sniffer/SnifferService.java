package com.axonactive.training.sniffer;

import static com.axonactive.training.util.Assertion.assertNotNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.axonactive.training.address.Address;

import lombok.Getter;


/**
 * @author tdndung
 *
 */
@Stateless
public class SnifferService {

	
	
	@PersistenceContext
	private EntityManager em;


	/**
	 * Add new sniffer to the system. If Null or invalid sniffer is given, the
	 * method throws <tt>IllegalArgumentException</tt> Mac address is required and
	 * valid (follow the mac address format) Address is required and valid (a valid
	 * one include country, province, district and stress Default status is INACTIVE
	 * Code is automatically generated
	 * 
	 * @param sniffer
	 * @return code
	 */
	public String add(Sniffer sniffer) {
		if (Objects.isNull(sniffer)) {
			throw new IllegalArgumentException("sniffer is missing");
		}
		if (Objects.isNull(sniffer.getMacAddress())) {
			throw new IllegalArgumentException("mac address is missing");
		}
		String code = UUID.randomUUID().toString();
		
		if (Objects.isNull(sniffer.getStatus())) {
			sniffer.setStatus(Status.INACTIVE);
		}
		if (checkAddress(sniffer.getAddress()) && checkMacAddress(sniffer.getMacAddress())) {
			sniffer.setCode(code);
			//this.storage.put(code, sniffer);			
			this.em.persist(sniffer);			
		}
		else {
			return null;
		}
		return code;
			//return sniffer.getId();
	}

	/**
	 * Validate a given mac address If mac address is null then return false If mac
	 * address lenght different 6 then return false If mac address format different
	 * 2 character or checkDigit function false
	 * 
	 * @param employee
	 * @return
	 * @throws IllegalArgumentException
	 *             - if the given employee or its name is null.
	 */
	public boolean checkMacAddress(String macAddress) {
		if (Objects.isNull(macAddress)) {
			throw new IllegalArgumentException("mac address is missing");
		}

		String[] digits = macAddress.toUpperCase().split("-");
		if (digits.length != 6) {
			return false;
		}

		for (int i = 0; i < 6; i++) {
			if (digits[i].length() != 2 || !checkDigit(digits[i])) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Check address If Null or invalid address is given, the method throws
	 * <tt>IllegalArgumentException</tt> If Null or invalid country is given, the
	 * method throws <tt>IllegalArgumentException</tt> If Null or invalid province
	 * is given, the method throws <tt>IllegalArgumentException</tt> If Null or
	 * invalid district is given, the method throws
	 * <tt>IllegalArgumentException</tt> If Null or invalid street is given, the
	 * method throws <tt>IllegalArgumentException</tt> If country or province or
	 * district or street is empty, this method will return false
	 * 
	 * @param address
	 * @return
	 */
	public boolean checkAddress(Address address) {
		if (Objects.isNull(address))
			throw new IllegalArgumentException("Address is missing");
		boolean result = true;
		if (Objects.isNull(address.getCountry()))
			throw new IllegalArgumentException("Country is missing");
		if (Objects.isNull(address.getProvince()))
			throw new IllegalArgumentException("Province is missing");
		if (Objects.isNull(address.getDistrict()))
			throw new IllegalArgumentException("District is missing");
		if (Objects.isNull(address.getStreet()))
			throw new IllegalArgumentException("Street is missing");
		if (address.getCountry().isEmpty() || address.getDistrict().isEmpty() || address.getProvince().isEmpty()
				|| address.getStreet().isEmpty()) {
			return !result;
		}
		return result;
	}

	/**
	 * Check Digit If string length different 2, the method will return false Else
	 * check str. If str is the number or 'A', 'B','C','D','E','F' then return true
	 * 
	 * @param str
	 * @return true
	 */
	public boolean checkDigit(String str) {
		if (str.length() != 2)
			return false;

		for (int i = 0; i < 2; i++)
			try {
				Integer.parseInt(new StringBuilder().append(str.charAt(i)).toString());
			} catch (NumberFormatException e) {
				if (str.charAt(i) != 'A' && str.charAt(i) != 'B' && str.charAt(i) != 'C' && str.charAt(i) != 'D'
						&& str.charAt(i) != 'E' && str.charAt(i) != 'F') {
					return false;
				}
			}
		return true;
	}

	/**
	 * Update an existing sniffer with new information. The method will find the
	 * sniffer in the storage with given code. If the sniffer found, the method will
	 * then update the sniffer with new information. If Null or invalid code is
	 * given, the method throws <tt>IllegalArgumentException</tt> If Sniffer not
	 * found,the method throws <tt>EntityNotFoundException</tt>
	 * 
	 * @param code
	 *            - contains new information of the updated sniffer
	 * @param macAddress
	 *            - contains new information of the updated sniffer
	 * @param address
	 *            - contains new information of the updated sniffer
	 * @throws IllegalArgumentException
	 *             - if code is missing.
	 * @throws EntityNotFoundException
	 *             - If code is wrong format
	 * @throws IllegalArgumentException
	 *             - If ValidFrom is missing
	 * @throws IllegalArgumentException
	 *             - If ValidFrom of new no greater than the previous on
	 */
	private static final String ACTION_1 = "Code is missing";

	public void update(Sniffer sniffer) {
		assertSnifferNotNull(sniffer);
		String code = sniffer.getCode();
		assertNotNull(code, "Sniffer is must not be null");
		this.em.merge(sniffer);
	}

	public void assertSnifferNotNull(Sniffer sniffer) {
		assertNotNull(sniffer, "Sniffer must be not null");
	}
	
	public Sniffer find(Integer id) {
		Sniffer sniffer = this.em.find(Sniffer.class,id);
		if(Objects.isNull(sniffer)) {
			throw new EntityNotFoundException("Sniffer not found");
		}
		return sniffer;
	}
	
	public Sniffer findByCode(String code) {
		Query mocQuery = this.em.createNamedQuery("Sniffer.findByCode", Sniffer.class);
		mocQuery.setParameter("snifferCode", code);
		return (Sniffer) mocQuery.getSingleResult();
	}
	
	public Sniffer findById(Integer id) {
		Query mocQuery = this.em.createNamedQuery("Sniffer.findByCode", Sniffer.class);
		mocQuery.setParameter("snifferCode", id);
		return (Sniffer) mocQuery.getSingleResult();
	}

	/**
	 * Delete an existing sniffer. The method will find the sniffer in the storage
	 * with given code. If the sniffer found, the method will delete the sniffer.
	 * Otherwise, <tt>EntityNotFoundException<tt> is throw.
	 * 
	 * @param code
	 *            - contains new information of the updated sniffer
	 * @param macAddress
	 *            - contains new information of the updated sniffer
	 * @param address
	 *            - contains new information of the updated sniffer
	 * @throws IllegalArgumentException
	 *             - if code is missing.
	 * @throws EntityNotFoundException
	 *             - If code is wrong format
	 */
	public void delete(String code) {
				
		if (Objects.isNull(code))
			throw new IllegalArgumentException(ACTION_1);
		Sniffer sniffer = this.findByCode(code);
		if (Objects.isNull(sniffer))
			throw new EntityNotFoundException("Sniffer is not found");
		this.em.remove(sniffer);
	}
	/**
	 * get a sniffer in system by code
	 * @param code
	 * @return sniffer which match this code
	 * given IllegalArgumentException when code is null
	 */
	public Sniffer getOneSniffer(String code) {
		assertNotNull(code,"code is missing");
		Query query= this.em.createNamedQuery("Sniffer.findByCode",Sniffer.class);
		query.setParameter("snifferCode", code);
		return (Sniffer) query.getSingleResult();
	}
	/**
	 * get all sniffer in system
	 * @return all sniffers exist
	 */
	public List<Sniffer> getAllSniffer(){
		Query query = this.em.createNamedQuery("Sniffer.getAllSniffer",Sniffer.class);
		return query.getResultList();
		
	}

	

	/**
	 * Get all Sniffer. The method will return list of sniffer in the storage. If
	 * the list size is empty, <tt>EntityNotFoundException<tt> is throw.
	 * 
	 * @return sniffer
	 */


}
