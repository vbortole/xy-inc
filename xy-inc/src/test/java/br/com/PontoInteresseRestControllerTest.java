/**
 * 
 */
package br.com;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import br.com.entity.PontoInteresse;
import br.com.repository.PontoInteresseRepository;

/**
 * @author Vitor B. Junior
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class PontoInteresseRestControllerTest {
	private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private MockMvc mockMvc;

	private HttpMessageConverter mappingJackson2HttpMessageConverter;

	private List<PontoInteresse> listaPontosInteresse;

	@Autowired
	private PontoInteresseRepository pontoInteresseRepository;

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	void setConverters(HttpMessageConverter<?>[] converters) {

		this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
				.filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().orElse(null);

		assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
	}

	@Before
	public void setup() throws Exception {
		this.mockMvc = webAppContextSetup(webApplicationContext).build();

		listaPontosInteresse = new ArrayList<>();
		pontoInteresseRepository.deleteAllInBatch();
		listaPontosInteresse.add(this.pontoInteresseRepository.save(new PontoInteresse(null, "Lanchonete", 27, 12)));
		listaPontosInteresse.add(this.pontoInteresseRepository.save(new PontoInteresse(null, "Posto", 31, 18)));
		listaPontosInteresse.add(this.pontoInteresseRepository.save(new PontoInteresse(null, "Joalheria", 15, 12)));
		listaPontosInteresse.add(this.pontoInteresseRepository.save(new PontoInteresse(null, "Floricultura", 19, 21)));
		listaPontosInteresse.add(this.pontoInteresseRepository.save(new PontoInteresse(null, "Pub", 12, 8)));
		listaPontosInteresse.add(this.pontoInteresseRepository.save(new PontoInteresse(null, "Supermercado", 23, 6)));
		listaPontosInteresse.add(this.pontoInteresseRepository.save(new PontoInteresse(null, "Churrascaria", 28, 2)));

	}

	@Test
	public void pontoInteresseNotFound() throws Exception {
		mockMvc.perform(get("/pontoInteresse/" + "0").contentType(contentType)).andExpect(status().isNotFound());
	}

	@Test
	public void pontoInteresseFound() throws Exception {
		PontoInteresse poi = this.listaPontosInteresse.get(0);
		mockMvc.perform(get("/pontosInteresse/" + poi.getId()).contentType(contentType)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(poi.getId().intValue())))
				.andExpect(jsonPath("$.nome", is(poi.getNome()))).andExpect(jsonPath("$.x", is(poi.getX())))
				.andExpect(jsonPath("$.y", is(poi.getY())));
	}

	@Test
	public void listarPontosInteresseProximosNotFound() throws Exception {
		mockMvc.perform(get("/pontosInteresse/20/10/0").contentType(contentType)).andExpect(status().isNotFound());
	}

	@Test
	public void listarPontosInteresseProximosFound() throws Exception {
		mockMvc.perform(get("/pontosInteresse/20/10/10").contentType(contentType)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].nome", is(this.listaPontosInteresse.get(0).getNome())))
				.andExpect(jsonPath("$[1].nome", is(this.listaPontosInteresse.get(2).getNome())))
				.andExpect(jsonPath("$[2].nome", is(this.listaPontosInteresse.get(4).getNome())))
				.andExpect(jsonPath("$[3].nome", is(this.listaPontosInteresse.get(5).getNome())));
	}

	@Test
	public void adicionarPontoInteresse() throws Exception {
		mockMvc.perform(post("/pontosInteresse").content(json(new PontoInteresse(null, "Test", 10, 10)))
				.contentType(contentType)).andExpect(status().isCreated());
	}

	@Test
	public void listarPontosInteresseFound() throws Exception {
		mockMvc.perform(get("/pontosInteresse").contentType(contentType)).andExpect(status().isOk());
	}

	@Test
	public void listarPontosInteresseNotFound() throws Exception {
		this.pontoInteresseRepository.deleteAllInBatch();
		mockMvc.perform(get("/pontosInteresse").contentType(contentType)).andExpect(status().isNotFound());
	}

	protected String json(Object o) throws IOException {
		MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
		this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
		return mockHttpOutputMessage.getBodyAsString();
	}
}
