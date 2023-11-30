package com.example.blps.module.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "user", schema = "public")
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotBlank
	private String email;

	@Column
	@NotBlank
	@Size(max = 128)
	private String password;

	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY)
	@ToString.Exclude
	@JsonManagedReference
	private Set<Company> companies = new HashSet<>();

	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
}
