package br.com.emersonmorgado.aluraflix.aluraflix.controller;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@Profile("test")
@AutoConfigureMockMvc
class CategoriaControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void deveriaCriarCategoriasNoBancoH2() throws Exception {
		URI uri1 = new URI("/categorias");
		String categoria1 = " {\n"
					+ "\"titulo\": \"LIVRE\",\n"
					+ "\"cor\": \"#FFFFFF\"\n"
					+ " }";
	
		mockMvc.perform(MockMvcRequestBuilders.post(uri1).content(categoria1).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201));
		
		URI uri2 = new URI("/categorias/1");
		mockMvc.perform(MockMvcRequestBuilders.get(uri2)).andExpect(MockMvcResultMatchers.status().is(200));
		
		URI uri3 = new URI("/categorias/2");
		mockMvc.perform(MockMvcRequestBuilders.get(uri3)).andExpect(MockMvcResultMatchers.status().is(404));
	}
	
	@Test
	public void deveriaListarTodasAsCategoriasDoBanco() throws Exception {
		URI uri = new URI("/categorias");
		
		mockMvc.perform(MockMvcRequestBuilders.get(uri)).andExpect(MockMvcResultMatchers.status().is(200));
	}
	
	@Test
	public void deveriaEncontrarVideosPorCategorias() throws Exception {
		populAhBaseComVideosEhCategorias();
		
		URI encontraVideo = new URI("/categorias/1/videos");
		mockMvc.perform(MockMvcRequestBuilders.get(encontraVideo)).andExpect(MockMvcResultMatchers.status().is(200));
		
		URI buscaPorCategoriaNaoExistente = new URI("/categorias/5/videos");
		mockMvc.perform(MockMvcRequestBuilders.get(buscaPorCategoriaNaoExistente)).andExpect(MockMvcResultMatchers.status().is(404));
		
	}
	
	private void populAhBaseComVideosEhCategorias() throws Exception {		
		URI uriCategorias = new URI("/categorias");
				
		String categoria1 = " {\n"
				+ "\"titulo\": \"LIVRE\",\n"
				+ "\"cor\": \"#FFFFFF\"\n"
				+ " }";
		String categoria2 = " {\n"
				+ "\"titulo\": \"TECNOLOGIA\",\n"
				+ "\"cor\": \"#0000FF\"\n"
				+ " }";
		String categoria3 = " {\n"
				+ "\"titulo\": \"HUMOR\",\n"
				+ "\"cor\": \"#00FF00\"\n"
				+ " }";
		
		mockMvc
		.perform(MockMvcRequestBuilders.post(uriCategorias).content(categoria1).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201));
		mockMvc
		.perform(MockMvcRequestBuilders.post(uriCategorias).content(categoria2).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201));
		mockMvc
		.perform(MockMvcRequestBuilders.post(uriCategorias).content(categoria3).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201));
		
		
		URI uriVideos = new URI("/videos");
		
		String video1 ="{ \"titulo\": \"O acidente com o Ariane 5 no vôo inaugural \","
				+ "\"descricao\": \"Como um erro simples destruiu um foguete de $500 milhoes no seu voo de estreia em 1996 \","
				+ "\"url\": \"https://www.youtube.com/watch?v=72KES9VcnV0\" }";
		String video2 ="{ \"titulo\": \"F-1: o motor que nos levou à Lua \","
				+ "\"descricao\": \"O Rocketdyne F-1 é até hoje o motor de câmara de combustão única mais poderoso do mundo. \","
				+ "\"url\": \"https://www.youtube.com/watch?v=1oGQxkwJyfM\","
				+ "\"categoria\": 2 }";
		String video3 ="{ \"titulo\": \"A gigantesca bola que mantém de pé os arranha céus\","
				+ "\"descricao\": \"A gigantesca bola usada para amortecer as oscilações dos ventos em edifícios muito altos. \","
				+ "\"url\": \"https://www.youtube.com/watch?v=UMLCrbGKM5M\" }";
		String video4 ="{ \"titulo\": \"Nikola Tesla e seus motores \","
				+ "\"descricao\": \"Conheça a história do grande cientista e inventor Nikola Tesla e seu caminho até a invenção dos motores de corrente alternada Síncronos e de Indução, uma história cheia de aventura e conhecimento.  \","
				+ "\"url\": \"https://www.youtube.com/watch?v=5XTqEHY9p4w\","
				+ "\"categoria\": 2 }";
		
		mockMvc
		.perform(MockMvcRequestBuilders.post(uriVideos).content(video1).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201));
		mockMvc
		.perform(MockMvcRequestBuilders.post(uriVideos).content(video2).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201));
		mockMvc
		.perform(MockMvcRequestBuilders.post(uriVideos).content(video3).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201));
		mockMvc
		.perform(MockMvcRequestBuilders.post(uriVideos).content(video4).contentType(MediaType.APPLICATION_JSON))
		.andExpect(MockMvcResultMatchers.status().is(201));		
	}

}
