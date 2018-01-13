package br.com.dvaltrick.tree.controller;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import br.com.dvaltrick.tree.model.Node;

@JsonIgnoreProperties(value="children")
public class NodeFromParent extends Node implements Serializable { 
	private Boolean hasChildren;	

	public Boolean getHasChildren() {
		return hasChildren;    
	}

	public void setHasChildren(Boolean hasChildren) {
		this.hasChildren = hasChildren;
	}

}
