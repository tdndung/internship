package com.axonactive.training.gpslocation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter @Setter
@Entity
@Table(name = "tbl_GpsLocation")
public class GpsLocation {
	/**
	 * an identifier string to recognize an GpsLocation. it will be automatically generated by the system.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column(name = "latitude", length = 50, nullable = true)
	private String latitude;
	
	@Column(name = "longitude", length = 50, nullable = true)
	private String longitude;
	/**
	 * @param latitude
	 * @param longitude
	 */

	/**
	 * @param id
	 * @param latitude
	 * @param longitude
	 */

	
	
}
