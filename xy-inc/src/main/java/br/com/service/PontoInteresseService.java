package br.com.service;

import java.util.Collection;

import br.com.entity.PontoInteresse;

/**
 * 
 * @author Vitor B. Junior
 *
 */
public interface PontoInteresseService {
	/**
	 * Recupera o Ponto de Interesse referente ao Id passado
	 * 
	 * @param id
	 * @return
	 */
	PontoInteresse findOne(Long id);

	/**
	 * Recupera todos os pontos de interesse
	 * 
	 * @return
	 */
	Collection<PontoInteresse> findAll();

	/**
	 * Insere um novo ponto de interesse
	 * 
	 * @param pontoInteresse
	 * @return
	 */
	PontoInteresse save(PontoInteresse pontoInteresse);

	/**
	 * Lista os pontos de interesse cuja distancia do ponto (x, y) e inferior a
	 * dMax
	 * 
	 * @param x
	 * @param y
	 * @param dMax
	 * @return
	 */
	Collection<PontoInteresse> listarPontosInteresseProximos(Integer x, Integer y, Integer dMax);
}
