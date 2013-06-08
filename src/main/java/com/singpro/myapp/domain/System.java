package com.singpro.myapp.domain;

import org.neo4j.graphdb.Direction;
import org.neo4j.helpers.collection.IteratorUtil;
import org.springframework.data.neo4j.annotation.*;
import org.springframework.data.neo4j.support.index.IndexType;
import org.springframework.data.neo4j.template.Neo4jOperations;

import java.util.*;

import static org.neo4j.graphdb.Direction.INCOMING;


/**
 * @author 
 * @since 
 */
@NodeEntity
public class System {
    @GraphId public Long nodeId;

    @Indexed (indexName= "indexId")
    String id;

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Indexed(indexType=IndexType.FULLTEXT, indexName = "search")
    String title;
    String description;


    @RelatedToVia(type = "CONNECT_FROM", direction = Direction.OUTGOING )
    Set<Consume> consumelist;

    public Set<Consume> getConsumelist() {
        return consumelist;
    }
    
    
    @RelatedToVia(type = "CONNECT_TO", direction = Direction.OUTGOING )
    Set<Produce> producelist;

    public Set<Produce> getProducelist() {
        return producelist;
    }    
    


    public Consume consume(Neo4jOperations template, System producer, String datasetname) {
    	Consume consume = new Consume(this, producer, datasetname);
    	this.consumelist.add(consume);
        return consume;
    }    
    
    

    public void addProducelist(Produce produce) {
        this.producelist.add(produce);
    }
    
    public Produce produce(Neo4jOperations template, System consumer, String datasetname) {
        Produce produce = new Produce(this, consumer, datasetname);
    	this.producelist.add(produce);
        return produce;
    }    



    public String getDescription() {
        return description;
    }


    public void setTitle(String title) {
        this.title=title;
    }

    public String getTitle() {
        return this.title;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

	public void setConsumelist(Set<Consume> consumelist) {
		this.consumelist = consumelist;
	}

	public void setProducelist(Set<Produce> producelist) {
		this.producelist = producelist;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        System system = (System) o;
        if (nodeId == null) return super.equals(o);
        return nodeId.equals(system.nodeId);

    }

    @Override
    public int hashCode() {
        return nodeId != null ? nodeId.hashCode() : super.hashCode();
    }

}

