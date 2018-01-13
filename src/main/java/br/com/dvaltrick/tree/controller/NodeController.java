package br.com.dvaltrick.tree.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@RequestMapping(method={RequestMethod.POST,RequestMethod.PUT},
			value="/node", 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public String saveNode(@RequestBody Node toSaveNode){
		Node saved = new Node();
		try{
			saved = service.save(toSaveNode);
		}catch(Exception e){
			System.out.println("Erro de requisição"); 
			e.printStackTrace(); 
		}
		
		return "{\"id\":"+saved.getId().toString()+"}";
	}
	
	@RequestMapping(method=RequestMethod.GET, 
			value="/node",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Node> getTreeByRoot(){
		return service.getTreeByRoot();
	}
	
	@RequestMapping(method=RequestMethod.GET, 
			value="/node/{parentId}",
			produces=MediaType.APPLICATION_JSON_VALUE)
	public List<NodeFromParent> getTreeByParent(@PathVariable("parentId") Integer parentId){
		return service.getTreeByParent(parentId);
	}
}
