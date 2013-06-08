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
@RelationshipEntity(type = "CONNECT_TO")
public class Produce {
    @GraphId public Long nodeId;


    @Indexed (indexName= "producer_index")
    @StartNode System producer;
    @EndNode System consumer;
    
    String datasetname;

    public Produce(){}
    
    public Produce(System producer, System consumer, String datasetname) {
        this.producer = producer;
        this.consumer = consumer;
        this.datasetname = datasetname;
    }
    

	public System getProducer() {
		return this.producer;
	}

	public System getConsumer() {
		return this.consumer;
	}


	public String getDatasetname() {
		return datasetname;
	}

	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
	}
    
    
}

