/**
 * 
 */
package com.pscs.moneyx.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity(name="roles")
//@Table(name = "roles")
public class Role {
	
	@Id 
	@GeneratedValue(strategy =GenerationType.AUTO)
	private Long id;
	private String name;
	
	

}
