package com.singpro.myapp.domain;

import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.neo4j.annotation.*;
import org.springframework.data.neo4j.support.index.IndexType;

import java.util.*;

import static org.neo4j.graphdb.Direction.INCOMING;


/**
 * @author 
 * @since 
 */
@RelationshipEntity(type = "CONNECT_FROM")
public class Consume {
    @GraphId Long nodeId;


    @Indexed (indexName= "consumer_index")
    @StartNode System consumer;
    @EndNode System producer;
    
    String datasetname;
    
    public Consume(){}
    
    public Consume(System consumer, System producer, String datasetname) {
        this.consumer = consumer;
        this.producer = producer;
        this.datasetname = datasetname;
    }    

	public System getProducer() {
		return producer;
	}
	public System getConsumer() {
		return consumer;
	}
	
	public String getDatasetname() {
		return datasetname;
	}
	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
	}
    

    
    
}

