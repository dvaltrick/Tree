package br.com.dvaltrick.tree.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dvaltrick.tree.controller.NodeFromParent;
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
	
	public List<NodeFromParent> getTreeByParent(Integer parentId){
		Set<Node> childrenList = repository.findOne(parentId).getChildren();
		List<NodeFromParent> allNodesFromParent = new ArrayList<NodeFromParent>();
		
		if(!childrenList.isEmpty()){
			for(Node childrenNode:childrenList){
				NodeFromParent nodeFromParent = new NodeFromParent();
				nodeFromParent.setId(childrenNode.getId());
				nodeFromParent.setCode(childrenNode.getCode());
				nodeFromParent.setDescription(childrenNode.getDescription());
				nodeFromParent.setParentId(childrenNode.getParentId());
				nodeFromParent.setDetails(childrenNode.getDetails());
				nodeFromParent.setHasChildren(!childrenNode.getChildren().isEmpty());
				allNodesFromParent.add(nodeFromParent);
			}
		}
		
		return allNodesFromParent;
	}
}
