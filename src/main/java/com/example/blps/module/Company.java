package com.example.blps.module;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "company", schema = "public")
public class Company {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@JsonBackReference
	private User user;

	@NotBlank
	private String org_name;

	@NotBlank
	@Size(min = 10, max = 10)
	@Column
	private String inn;

	@NotBlank
	@Size(min = 13, max = 13)
	@Column
	private String ogrn;

	@Pattern(regexp = "(^$|[0-9]{11})")
	@Column
	private String phone;

	@Column
	private String website;

	@Column
	private String description;

	@Column
	private Boolean acceptable;

	@Column
	private Integer rating;


	@Column
	private String belief;
}
