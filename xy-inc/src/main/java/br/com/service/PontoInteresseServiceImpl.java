package br.com.service;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.entity.PontoInteresse;
import br.com.repository.PontoInteresseRepository;

/**
 * Classe de servico responsavel por encapsular as regras de negocio referentes
 * a pontos de interesse
 * 
 * @author Vitor B. Junior
 *
 */
@Service
public class PontoInteresseServiceImpl implements PontoInteresseService {

	private final PontoInteresseRepository pontoInteresseRepository;

	@Autowired
	public PontoInteresseServiceImpl(PontoInteresseRepository pontoInteresseRepository) {
		this.pontoInteresseRepository = pontoInteresseRepository;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.service.PontoInteresseService#findOne(java.lang.Long)
	 */
	public PontoInteresse findOne(Long id) {
		return pontoInteresseRepository.findOne(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.service.PontoInteresseService#findAll()
	 */
	public Collection<PontoInteresse> findAll() {
		return pontoInteresseRepository.findAll();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.service.PontoInteresseService#save(br.com.entity.PontoInteresse)
	 */
	public PontoInteresse save(PontoInteresse pontoInteresse) {
		return pontoInteresseRepository.save(pontoInteresse);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.service.PontoInteresseService#listarPontosInteresseProximos(java.
	 * lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	public Collection<PontoInteresse> listarPontosInteresseProximos(Integer x, Integer y, Integer dMax) {
		Collection<PontoInteresse> todosPontos = this.findAll();
		Collection<PontoInteresse> pontosProximos = new ArrayList<>();
		for (PontoInteresse pontoInteresse : todosPontos) {
			if (new Double(dMax)
					.compareTo(calcularDistanciaEntrePontos(x, y, pontoInteresse.getX(), pontoInteresse.getY())) >= 0) {
				pontosProximos.add(pontoInteresse);
			}
		}
		return pontosProximos;
	}

	/**
	 * Calcula a distancia entre os pontos utilizando a formula: Distancia =
	 * sqrt((x-x2)^2 + (y-y2)^2))
	 * 
	 * @param y2
	 * @param x2
	 * @param y
	 * @param x
	 * 
	 */
	private Double calcularDistanciaEntrePontos(Integer x, Integer y, Integer x2, Integer y2) {
		Double dX = new Double(x);
		Double dY = new Double(y);
		Double dX2 = new Double(x2);
		Double dY2 = new Double(y2);
		Double interiorRaiz = Math.pow((dX - dX2), 2) + Math.pow((dY - dY2), 2);

		return Math.sqrt(interiorRaiz);
	}

}
