package com.singpro.myapp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singpro.myapp.domain.Produce;
import com.singpro.myapp.domain.System;
import com.singpro.myapp.repo.SystemRepository;
import com.singpro.myapp.web.ProduceDto;
import com.singpro.myapp.web.ProduceListDto;
import com.singpro.myapp.web.ProduceMapper;
import com.singpro.myapp.web.SystemController;
import com.singpro.myapp.web.SystemMapper;

@Service
public class SystemService {

	private static final Logger logger = LoggerFactory.getLogger(SystemService.class);
	
	@Autowired
	private SystemRepository systemRepository;
	
    @Autowired 
    Neo4jOperations template;
    
	public com.singpro.myapp.domain.System create(com.singpro.myapp.domain.System system) {
		
		
		logger.info("create...");
		
		com.singpro.myapp.domain.System existingSystem = systemRepository.findById(system.getId());
		
		if (existingSystem != null) {
			throw new RuntimeException("Record already exists!");
		}
		logger.info("Record saved...");
		return systemRepository.save(system);
	}
	
	public com.singpro.myapp.domain.System read(com.singpro.myapp.domain.System system) {
		return system;
	}
	
	public List<com.singpro.myapp.domain.System> readAll() {
		List<com.singpro.myapp.domain.System> systems = new ArrayList<com.singpro.myapp.domain.System>();
		
		logger.info("readAll...");
		EndResult<com.singpro.myapp.domain.System> results = systemRepository.findAll();
		for (com.singpro.myapp.domain.System r: results) {
			systems.add(r);
			logger.info("found="+r.getTitle());
		}
		
		return systems;
	}
	
	public com.singpro.myapp.domain.System update(com.singpro.myapp.domain.System system) {
		
		logger.info("update...");
		
		com.singpro.myapp.domain.System existingSystem = systemRepository.findById(system.getId());
		
		if (existingSystem == null) {
			return null;
		}
		
		existingSystem.setId(system.getId());
		existingSystem.setTitle(system.getTitle());
		existingSystem.setDescription(system.getDescription());
		
		existingSystem.setTechcontact(system.getTechcontact());
		existingSystem.setFunccontact(system.getFunccontact());
		existingSystem.setLaunchdate(system.getLaunchdate());
		existingSystem.setCurrentrelease(system.getCurrentrelease());
		existingSystem.setCurrentreleaseddate(system.getCurrentreleaseddate());
		
		return systemRepository.save(existingSystem);
	}
	
	public Boolean delete(com.singpro.myapp.domain.System system) {
		
		logger.info("delete...");
		com.singpro.myapp.domain.System existingSystem = systemRepository.findById(system.getId());
		
		if (existingSystem == null) {
			return false;
		}
		
		systemRepository.delete(existingSystem);
		return true;
	}
	
	@Transactional 
	// for org.neo4j.graphdb.notintransactionexception spring-data
	public Boolean connect(String sourceId, String destinationId, String datasetname) {
		
		logger.info("connect...");
		com.singpro.myapp.domain.System sourceSystem = systemRepository.findById(sourceId);
		com.singpro.myapp.domain.System destinationSystem = systemRepository.findById(destinationId);
		
		if ( (sourceSystem == null) || (destinationSystem==null) || 
			 (datasetname==null) || ( datasetname.trim().length()==0 )  ) {
			return false;
		}
		
		Produce connectionProducer= sourceSystem.produce( template, destinationSystem, datasetname);
		template.save(connectionProducer);
    	
    	logger.info(connectionProducer.toString() );
		
    	return (connectionProducer==null ? false : true);
		//return true;
		
	}	
	
	
	//public List<Produce> getAllConnectWhereSystemIsProducer(String sourceId) {
	public ProduceListDto getAllConnectWhereSystemIsProducer(String sourceId) {
		
		logger.info("getAllConnectWhereSystemIsProducer...");
		
		//List<Produce> produceList = new ArrayList<Produce>();
		ProduceListDto produceListDto = new ProduceListDto();
		List<ProduceDto> produces = new ArrayList<ProduceDto>();
				 
		
		com.singpro.myapp.domain.System sourceSystem =systemRepository.findById(sourceId);
		logger.info("sourceSystem ="+sourceSystem.getDescription());
 	
		List<Produce> produceListFromRepo = systemRepository.getAllProduceRelationWhereSystemIsProducer( sourceSystem );
		logger.info("produceListFromRepo.size()="+produceListFromRepo.size());
		
		for ( Produce produceConnectionTemp : produceListFromRepo) {
			logger.info(produceConnectionTemp.getDatasetname());
	   		logger.info("SystemTemp-Producer>..."+produceConnectionTemp.getProducer().nodeId);
	   		logger.info("SystemTemp-Consumer>..."+produceConnectionTemp.getConsumer().nodeId);
	   		
	   		System producerSystem = systemRepository.findOne(produceConnectionTemp.getProducer().nodeId);
	   		logger.info("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
	   		
	   		System consumerSystem = systemRepository.findOne(produceConnectionTemp.getConsumer().nodeId);
	   		logger.info("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());
	   		
	   		ProduceDto produceDto = new ProduceDto();
	   		produceDto.setDatasetname(produceConnectionTemp.getDatasetname());
	   		produceDto.setProducer(SystemMapper.map(producerSystem) );
	   		produceDto.setConsumer(SystemMapper.map(consumerSystem) );
	   		
			//com.singpro.myapp.domain.System sourceSystem =systemRepository.findById(sourceId);
	   		produces.add(produceDto);
		}
		
		produceListDto.setProduces(produces);
		return produceListDto;
		
		//return produceListFromRepo;
	}
	
	
	
	//public List<Produce> getAllConnectWhereSystemIsProducer(String sourceId) {
	public List<SystemConnection> getAllSystemConnection() {
		
		logger.info("getAllSystemConnection...");
		
		List json = new ArrayList();
		
		EndResult<com.singpro.myapp.domain.System> systems =systemRepository.findAll();
		Iterator<com.singpro.myapp.domain.System> itr = systems.iterator();
		
		while ( itr.hasNext() ) {
			//SystemConnection systemConnection = new SystemConnection();
	   		Map<String, Object> systemConnectionMap = new HashMap<String, Object>();
			
			
			com.singpro.myapp.domain.System systemLocal = (com.singpro.myapp.domain.System)itr.next();
			logger.info("sourceSystem ="+systemLocal.getDescription());
			
			//#//systemConnection.setId(systemLocal.getId());
			//#//systemConnection.setName(systemLocal.getTitle());
			systemConnectionMap.put("id", systemLocal.getId());
			systemConnectionMap.put("name", systemLocal.getTitle());
			
			
	   		Map<String, Object> data = new HashMap<String, Object>();
	   		Map<String, Object> dataAttr = new HashMap<String, Object>();
	   		dataAttr.put("$color", "#C74243");
	   		dataAttr.put("$type", "circle");
	   		//data.put("data",dataAttr );
	   		
	   		//#//systemConnection.setData(data);
	   		systemConnectionMap.put("data",dataAttr );
	   		
			// find all the connections for this system
			List<Produce> produceListFromRepo = systemRepository.getAllProduceRelationWhereSystemIsProducer( systemLocal );
			logger.info("produceListFromRepo.size()="+produceListFromRepo.size());
			
			List<Object> adjlist = new ArrayList<Object>();
			
			Map<String, Object> adjacenciesTemp = new HashMap<String, Object>();

			for ( Produce produceConnectionTemp : produceListFromRepo) {
				
				logger.info(produceConnectionTemp.getDatasetname());
				logger.info("SystemTemp-Producer>..."+produceConnectionTemp.getProducer().nodeId);
		   		logger.info("SystemTemp-Consumer>..."+produceConnectionTemp.getConsumer().nodeId);

		   		String fromToKey = produceConnectionTemp.getProducer().nodeId + "." +produceConnectionTemp.getConsumer().nodeId;
		   		logger.info("fromToKey="+fromToKey);
		   		
		   		//check if there exist same fromTo relation
//		   		if ( adjacenciesTemp.containsKey(fromToKey)) {
//		   			//get just the new data set
//		   			List<Object> adjlisttemp = (ArrayList<Object>)adjacenciesTemp.get(fromToKey);
//		   			HashMap<String, Object> adjattrTemp = (HashMap<String, Object>)adjlisttemp.get(0);
//		   			Map<String, Object> dataSetMapTemp= (Map<String, Object>)adjattrTemp.get("data");
//		   			List<Object> adjDatalistTemp = (List<Object>)dataSetMapTemp.get("dataset");
//		   			
//		   			// now add new dataset to adjDatalistTemp
//		   			logger.info("Added new dataset...to, adjDatalistTemp.size()="+ adjDatalistTemp.size());
//		   			adjDatalistTemp.add(produceConnectionTemp.getDatasetname());
//		   			
//		   			
//		   			Map<String, Object> dataSetMap = new HashMap<String, Object>();
//		   			dataSetMap.put("dataset", adjDatalistTemp);
//			   		// dataset within data
//		   			Map<String, Object> adjattr = new HashMap<String, Object>();
//		   			adjattr.put("data", dataSetMapTemp );
//			   		
//			   		
//			   		adjlist.add(adjattrTemp);
//		   			
//			   		adjacenciesTemp.put(fromToKey, adjlist);
//			   		
//		   		} else {

			   		Map<String, Object> adjData = new HashMap<String, Object>();
			   		List<Object> adjDatalist = new ArrayList<Object>();
			   		
			   		adjDatalist.add(produceConnectionTemp.getDatasetname());
			   		//adjData.put("data", adjDatalist);
			   		
			   		
			   		System producerSystem = systemRepository.findOne(produceConnectionTemp.getProducer().nodeId);
			   		logger.info("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getTitle());
			   		
			   		System consumerSystem = systemRepository.findOne(produceConnectionTemp.getConsumer().nodeId);
			   		logger.info("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getTitle());

			   		Map<String, Object> adjattr = new HashMap<String, Object>();
			   		adjattr.put("nodeTo", consumerSystem.getId() );
			   		adjattr.put("nodeFrom",producerSystem.getId() );
			   		
			   		Map<String, Object> dataSetMap = new HashMap<String, Object>();
			   		dataSetMap.put("dataset", adjDatalist);
			   		// dataset within data
			   		adjattr.put("data", dataSetMap );
			   		
			   		
			   		adjlist.add(adjattr);
			   	
			   		// hold data so as to find if there are any other same relation with diff data set
			   		adjacenciesTemp.put(fromToKey, adjlist);
		   		//}


		   		
			}	

					
			//systemConnectionMap.put("adjacencies",adjacenciesTemp.values() );					
			systemConnectionMap.put("adjacencies",adjlist );
			
			//systemConnection.setAdjacencies(adjacencies);
			
			json.add(systemConnectionMap);
			
		}
			
		return json;

	}	

	
	public com.singpro.myapp.domain.System get(String id) {
		
		logger.info("get by id...");
		
		com.singpro.myapp.domain.System existingSystem = systemRepository.findById(id);
		
		return existingSystem;
	}	

	
	
	public ProduceListDto getAllConnectWhereSystemIsConsumer(String sourceId) {
		
		logger.info("getAllConnectWhereSystemIsConsumer...");
		
		ProduceListDto produceListDto = new ProduceListDto();
		List<ProduceDto> produces = new ArrayList<ProduceDto>();
				 
		
		com.singpro.myapp.domain.System sourceSystem =systemRepository.findById(sourceId); //consumer here
		logger.info("sourceSystem ="+sourceSystem.getDescription());
 	
		List<Produce> produceListFromRepo 
			= systemRepository.getAllProduceRelationWhereSystemIsConsumer( sourceSystem );
		logger.info("produceListFromRepo.size()="+produceListFromRepo.size());
		
		for ( Produce produceConnectionTemp : produceListFromRepo) {
			logger.info(produceConnectionTemp.getDatasetname());
	   		logger.info("SystemTemp-Producer>..."+produceConnectionTemp.getProducer().nodeId);
	   		logger.info("SystemTemp-Consumer>..."+produceConnectionTemp.getConsumer().nodeId);
	   		
	   		System consumerSystem = systemRepository.findOne(produceConnectionTemp.getProducer().nodeId);
	   		logger.info("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());
	   		
	   		System producerSystem = systemRepository.findOne(produceConnectionTemp.getConsumer().nodeId);
	   		logger.info("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
	   		
	   		ProduceDto produceDto = new ProduceDto();
	   		produceDto.setDatasetname(produceConnectionTemp.getDatasetname());
	   		produceDto.setProducer(SystemMapper.map(producerSystem) );
	   		produceDto.setConsumer(SystemMapper.map(consumerSystem) );
	   		
			//com.singpro.myapp.domain.System sourceSystem =systemRepository.findById(sourceId);
	   		produces.add(produceDto);
		}
		
		produceListDto.setProduces(produces);
		return produceListDto;
		
		//return produceListFromRepo;
	}
	
	
	
}
