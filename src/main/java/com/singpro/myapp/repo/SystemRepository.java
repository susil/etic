package com.singpro.myapp.repo;

import com.singpro.myapp.domain.System;
import com.singpro.myapp.domain.Produce;
import com.singpro.myapp.domain.Consume;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.MapResult;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.annotation.ResultColumn;
import org.springframework.data.neo4j.annotation.StartNode;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**

 */
public interface SystemRepository extends GraphRepository<System>,
        NamedIndexRepository<System>,
        RelationshipOperationsRepository<Produce>
	{
	System findById(String id);

	
	System findByNodeId(Long nodeId);
	
	
    Page<System> findByTitleLike(String title, Pageable page);

	  /*
	   * Works
	   */
  
	  @Query( " START produce=relationship( * )" +
		  " RETURN produce "
		 )
	  List<Produce> getConsumers1( System producer );

  
 
//	  @Query( 		
//			  " START produce=node({adv1}) " +
//			  " MATCH (produce)-[relation]->() " +
//			  " WHERE type(relation) =~ 'CONNECT_TO' " +
//			  " RETURN relation " 
//			)
//		  	List<Produce> getAllProduceRelationWhereSystemIsProducer(@Param("adv1") System adv1 );		  
//	  
	  
//	  @Query( 		
//		  //" START produce=node(*) " +
//		  " START r=relationship(*) " +
//		  //" MATCH (produce)-[relation]->() " +
//		  " WHERE type(r) =~ 'CONNECT_TO' " +
//		  " and r.producer = {producerSystem} " +
//		  " RETURN r " 
//		)
//	  	List<Produce> getAllProduceRelationWhereSystemIsProducer(@Param("producerSystem") System producerSystem );		  
  
  
	  @Query( 		
	  " START p=node({adv1}) " +
	  " MATCH (p)-[relation:CONNECT_TO]->(c) " +
	  //" WHERE type(relation) =~ 'CONNECT_TO' " +
	  //" WHERE p = {adv1} " +
	  " RETURN relation " 
	)
	  List<Produce> getAllProduceRelationWhereSystemIsProducer(@Param("adv1") System adv1 );		  

	  @Query( 		
	  " START p=node(*) " +
	  " MATCH (p)-[r:CONNECT_TO]->(c) " +
	  " WHERE p.id={id} " +
	  " RETURN r " 
	)
	  List<Produce> findProducerById1(@Param("id") String id );		
	  
	  @Query
	 (		 "Start r1=relationship(*) " +
			 " MATCH me-[r1:CONNECT_TO]->other " +
			 "  WHERE me.id={id}  "+
			 "  RETURN r1 "
	 )
	  List<Produce> getProducers(@Param("id") String id);		

}
