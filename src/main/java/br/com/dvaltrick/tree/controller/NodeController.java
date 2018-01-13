package br.com.dvaltrick.tree.controller;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.com.dvaltrick.tree.model.Node;
import br.com.dvaltrick.tree.service.NodeService;

@RestController
public class NodeController {
	@Autowired
	NodeService service;
	
	@RequestMapping(method=RequestMethod.POST, 
			value="/node", 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public String postNode(@RequestBody Node toSaveNode){
		Node saved = new Node();
		try{
			saved = service.save(toSaveNode);
		}catch(Exception e){
			System.out.println("Erro de requisição");
			e.printStackTrace(); 
		}
		
		return new JSONObject("{\"id\":"+saved.getId().toString()+"}").toString();
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/node")
	public List<Node> getTreeByRoot(){
		return service.getTreeByRoot();
	}
}
