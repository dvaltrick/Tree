package br.com.dvaltrick;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.dvaltrick.tree.TreeApplication;
import br.com.dvaltrick.tree.model.Node;

@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT,
		classes=TreeApplication.class
)
@AutoConfigureMockMvc
@TestPropertySource(locations="classpath:application-test.properties")
public class NodeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void testNodeController() throws Exception{		
		//Try to add the root in the tree
		testPost(new Node(null,"root","Root description","Root details",null),"$.id", Matchers.is(1));
		
		//Try to add one more root in the tree
		testPost(new Node(null,"root","Root description","Root details",null),"$.error", Matchers.any(String.class));
		
		//Try to add a child in a root
		testPost(new Node(null,"first child","first child description","first child details",1),"$.id", Matchers.is(2));
		
		//Try to add a child in a no existent parent
		testPost(new Node(null,"second child","second child description","second child details",10),"$.error", Matchers.any(String.class));
		
		//Try to add a child parent from itself
		testPut(new Node(2,"second child","second child description","second child details",2),"$.error", Matchers.any(String.class));
		
		//Try to add a new child in a root
		testPost(new Node(null,"third child","third child description","third child details",1),"$.id", Matchers.is(3));
		
		//Try to add a new child in a child
		testPost(new Node(null,"forth child","forth child description","forth child details",3),"$.id", Matchers.is(4));
		
		//Try to add a new child in a child of a child
		testPost(new Node(null,"fifth child","fifth child description","fifth child details",4),"$.id", Matchers.is(5));
		
		//Try to add a new child in a child of a child
		testPost(new Node(null,"sixth child","sixth child description","sixth child details",5),"$.id", Matchers.is(6));
		
		//Try to change a hierarchical dependent node
		testPut(new Node(4,"forth child","forth child description","forth child details",5),"$.error", Matchers.any(String.class));
		
		//Try to change a hierarchical dependent node level two
		testPut(new Node(4,"forth child","forth child description","forth child details",6),"$.error", Matchers.any(String.class));

		//Try to change to a non dependent node
		testPut(new Node(4,"forth child","forth child description","forth child details",2),"$.id", Matchers.is(4));

	}
		
	private void testPost(Node toTestNode,String jsonKey, Matcher matcher) throws Exception{
		mockMvc.perform(post("/node")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(toTestNode)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath(jsonKey, matcher));
		
	}
	
	private void testPut(Node toTestNode,String jsonKey, Matcher matcher) throws Exception{
		mockMvc.perform(put("/node")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(asJsonString(toTestNode)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath(jsonKey, matcher));
		
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
