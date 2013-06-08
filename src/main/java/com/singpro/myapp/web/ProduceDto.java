package com.singpro.myapp.web;

import java.io.Serializable;

public class ProduceDto implements Serializable {
	
	private Long nodeId;
	private String datasetname;
	private SystemDto producer;
	private SystemDto consumer;
	
	public String getDatasetname() {
		return datasetname;
	}
	public void setDatasetname(String datasetname) {
		this.datasetname = datasetname;
	}
	public SystemDto getProducer() {
		return producer;
	}
	public void setProducer(SystemDto producer) {
		this.producer = producer;
	}
	public SystemDto getConsumer() {
		return consumer;
	}
	public void setConsumer(SystemDto consumer) {
		this.consumer = consumer;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	
	


}
