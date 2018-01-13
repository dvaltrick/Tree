package br.com.dvaltrick.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.dvaltrick.tree.model.Node;
import br.com.dvaltrick.tree.repository.NodeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class NodeRepositoryTest {
	@Autowired
	TestEntityManager entityManager;
	
	@Autowired
	NodeRepository nodeRepository;
	
	@Test
	public void addNodeTest(){
		Node toAddNode = new Node();
		toAddNode.setCode("Root");
		toAddNode.setDescription("Root Descriprion");
		toAddNode.setDetails("Root details");

		
	}
	
}
