package com.singpro.myapp.service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemConnection implements Serializable {
	
	private String id;
	private String name;
	
	private Map adjacencies;
	private Map data;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	
	

	public Map getAdjacencies() {
		return adjacencies;
	}
	public void setAdjacencies(Map adjacencies) {
		this.adjacencies = adjacencies;
	}
	
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}

	
	

}

////////////////////////////////////////////
class Adjacencie implements Serializable {

	private String nodeTo;
	private String nodeFrom;
	private Map data;
	
	public String getNodeTo() {
		return nodeTo;
	}
	public void setNodeTo(String nodeTo) {
		this.nodeTo = nodeTo;
	}
	public String getNodeFrom() {
		return nodeFrom;
	}
	public void setNodeFrom(String nodeFrom) {
		this.nodeFrom = nodeFrom;
	}
	public Map getData() {
		return data;
	}
	public void setData(Map data) {
		this.data = data;
	}
	
	
	
	
}

////////////////////////////////////////////
class Data implements Serializable {

	private String nodeTo;
	private String nodeFrom;
	private List data;
	
	public String getNodeTo() {
		return nodeTo;
	}
	public void setNodeTo(String nodeTo) {
		this.nodeTo = nodeTo;
	}
	public String getNodeFrom() {
		return nodeFrom;
	}
	public void setNodeFrom(String nodeFrom) {
		this.nodeFrom = nodeFrom;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	
	
	
	
}

