package br.com.dvaltrick.tree.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import br.com.dvaltrick.tree.model.Node;
import br.com.dvaltrick.tree.repository.NodeRepository;

@Service
public class NodeService {
	@Autowired
	NodeRepository repository;
	
	public Node save(Node toSaveNode) throws Exception{
		if(toSaveNode.getParentId() != null){
			if(toSaveNode.getParentId() > 0){
				toSaveNode.setParent(repository.findOne(toSaveNode.getParentId()));
			}
		}
		
		return repository.save(toSaveNode);
	}
	
	public List<Node> getTreeByRoot(){
		return repository.getTreeByRoot();
	}
}
