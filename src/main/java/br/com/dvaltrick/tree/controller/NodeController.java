package br.com.dvaltrick.tree.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.apache.solr.common.util.Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonFactory;

import br.com.dvaltrick.tree.model.Node;
import br.com.dvaltrick.tree.model.NodeFromParent;
import br.com.dvaltrick.tree.service.NodeService;
import net.minidev.json.JSONObject;

@RestController
public class NodeController {
	@Autowired
	NodeService service;
	
	@RequestMapping(method={RequestMethod.POST,RequestMethod.PUT},
			value="/node", 
			consumes=MediaType.APPLICATION_JSON_VALUE,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Map<String, Object> saveNode(@RequestBody Node toSaveNode){
		Node saved = new Node();
		Map<String, Object> result = new HashMap<String, Object>(); 
		
		try{
			saved = service.save(toSaveNode);
			result.put("id", saved.getId());
		}catch(Exception e){
			result.put("error", e.getMessage());	
		}
		
		return result; 
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
