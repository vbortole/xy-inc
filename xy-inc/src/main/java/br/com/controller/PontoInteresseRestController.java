package br.com.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.entity.PontoInteresse;
import br.com.service.PontoInteresseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * Classe controller responsavel por mapear objetos referentes a entidade
 * PontoInteresse
 * 
 * @author Vitor B. Junior
 *
 */
@RestController
@RequestMapping("/pontosInteresse")
@Api(tags = { "Pontos de Interesse" }, description = "Operações relacionadas a Ponto de interesse")
public class PontoInteresseRestController {
	private final PontoInteresseService pontoInteresseService;

	@Autowired
	public PontoInteresseRestController(PontoInteresseService pontoInteresseService) {
		this.pontoInteresseService = pontoInteresseService;
	}

	/**
	 * Recupera um recurso cadastrado com o id passado
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	@ApiOperation(value = "Recupera o Ponto de Interesse referente ao argumento Id")
	public ResponseEntity<?> getById(
			@ApiParam(value = "Id do ponto de interesse a ser buscado") @PathVariable Long id) {
		PontoInteresse poi = pontoInteresseService.findOne(id);
		return poi != null ? ResponseEntity.ok(poi) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Lista todos os pontos de interesse cadastrados
	 * 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ApiOperation(value = "Lista todos os pontos de interesse cadastrados")
	public ResponseEntity<?> listarPontosInteresse() {
		Collection<PontoInteresse> pois = pontoInteresseService.findAll();
		if (pois != null && !pois.isEmpty()) {
			return ResponseEntity.ok(pois);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

	/**
	 * Cria um novo ponto de interesse
	 * 
	 * @param pontoInteresse
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ApiOperation(value = "Cria um novo ponto de interesse")
	public ResponseEntity<?> adicionarPontoInteresse(@RequestBody PontoInteresse pontoInteresse) {
		PontoInteresse novo = this.pontoInteresseService.save(pontoInteresse);
		return ResponseEntity.status(HttpStatus.CREATED).body(novo);
	}

	/**
	 * Lista os pontos de interesse cuja distancia da coordenada (x, y) é
	 * inferior a d-max
	 * 
	 * @param x
	 * @param y
	 * @param dMax
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{x}/{y}/{dMax}")
	@ApiOperation(value = "Lista os pontos de interesse cuja distancia da coordenada (x, y) é inferior a d-max")
	public ResponseEntity<?> listarPontosInteresseProximos(@ApiParam(value = "Cordenada X") @PathVariable Integer x,
			@ApiParam(value = "Cordenada Y") @PathVariable Integer y,
			@ApiParam(value = "D-Max") @PathVariable Integer dMax) {
		Collection<PontoInteresse> pois = pontoInteresseService.listarPontosInteresseProximos(x, y, dMax);
		if (pois != null && !pois.isEmpty()) {
			return ResponseEntity.ok(pois);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}

}
