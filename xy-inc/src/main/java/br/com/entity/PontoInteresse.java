package br.com.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

/**
 * Classe
 * 
 * @author Vitor B. Junior
 *
 */
@Entity
public class PontoInteresse {

	@Id
	@GeneratedValue
	@ApiModelProperty(hidden = true)
	private Long id;

	private String nome;

	private Integer x;

	private Integer y;

	public PontoInteresse() {
	}

	public PontoInteresse(Long id, String nome, Integer x, Integer y) {
		super();
		this.id = id;
		this.nome = nome;
		this.x = x;
		this.y = y;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getX() {
		return x;
	}

	public void setX(Integer x) {
		this.x = x;
	}

	public Integer getY() {
		return y;
	}

	public void setY(Integer y) {
		this.y = y;
	}

}
