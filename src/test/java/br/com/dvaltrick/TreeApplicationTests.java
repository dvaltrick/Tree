package br.com.dvaltrick;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Matchers.contains;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dvaltrick.tree.TreeApplication;
import br.com.dvaltrick.tree.controller.NodeController;
import br.com.dvaltrick.tree.model.Node;
import br.com.dvaltrick.tree.repository.NodeRepository;
import br.com.dvaltrick.tree.service.NodeService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=TreeApplication.class)
@AutoConfigureMockMvc
public class TreeApplicationTests {
	private MockMvc mock;
	
	@InjectMocks
	NodeController nodeController;
	
	@Before
	public void setup() throws Exception{
		MockitoAnnotations.initMocks(this);
		mock = MockMvcBuilders.standaloneSetup(nodeController).build();
	}
	
	@Test
	public void contextLoads() throws Exception{
		Node addNodeRoot = new Node();
		addNodeRoot.setCode("Root");
		addNodeRoot.setDescription("Root Descriprion");
		addNodeRoot.setDetails("Root details");
		
		this.mock.perform(MockMvcRequestBuilders.post("/node")
				.content(asJsonString(addNodeRoot))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("id")));
		
		this.mock.perform(MockMvcRequestBuilders.post("/node")
				.content(asJsonString(addNodeRoot))
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.accept(MediaType.APPLICATION_JSON_VALUE))
					.andDo(print())
					.andExpect(status().isOk())
					.andExpect(content().string(containsString("error")));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        final ObjectMapper mapper = new ObjectMapper();
	        final String jsonContent = mapper.writeValueAsString(obj);
	        return jsonContent;
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}  

}
