package com.singpro.myapp.web;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singpro.myapp.repo.SystemRepository;
import com.singpro.myapp.service.SystemConnection;
import com.singpro.myapp.service.SystemService;
import com.singpro.myapp.domain.System;
import com.singpro.myapp.domain.Produce;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/systems")
public class SystemController {
	
	private static final Logger logger = LoggerFactory.getLogger(SystemController.class);
	

//    @Autowired 
//    Neo4jOperations template;
//    
//    @Autowired
//    protected SystemRepository systemRepository;
    
	@Autowired
	private SystemService service;
	
	@RequestMapping
	public String getSystemsPage() {
		logger.info("returning systems.jsp page...");
		return "systems";
	}
	
	@RequestMapping(value="/get")
	public @ResponseBody com.singpro.myapp.domain.System get(@RequestBody com.singpro.myapp.domain.System system) {
		return service.read(system);
	}

	@RequestMapping(value = "/recordno/{id}", method = RequestMethod.GET )
    public System singleSystemView(final Model model, @PathVariable String id) {

    	
		System system = new System();
		system.setId(id);
		system = service.read(system);
    	//Movie movie = movieRepository.findById(movieId);
        model.addAttribute("id", id);
        if (system != null) {
            model.addAttribute("system", system);
            //final int stars = movie.getStars();
            //model.addAttribute("stars", stars);
            //Rating rating = null;
            //if (user!=null) rating = template.getRelationshipBetween(movie, user, Rating.class, "RATED");
            //if (rating == null) rating = new Rating().rate(stars,null);
            //model.addAttribute("userRating",rating);
        }
        return system;
    }    

	//@RequestMapping(value="/systems")
	@RequestMapping(value = "/records", method = RequestMethod.GET  )
	public @ResponseBody SystemListDto getSystems() {
		
//		UserListDto userListDto = new UserListDto();
//		userListDto.setUsers(UserMapper.map(service.readAll()));
//		return userListDto;
		List<System> systemList = service.readAll();
		SystemModel systemModel = new SystemModel();
		systemModel.setSystems(systemList); 
		
		logger.info("node size="+systemList.size());
		
		SystemListDto systemListDto = new SystemListDto();
		systemListDto.setSystems(SystemMapper.map(systemList));
		return systemListDto;
		
		//return systemModel;
	}    

	@RequestMapping(value = "/systemtable", method = RequestMethod.GET  )
	public @ResponseBody HashMap<String, List<SystemDto>> getSystemsTable() {
		
//		UserListDto userListDto = new UserListDto();
//		userListDto.setUsers(UserMapper.map(service.readAll()));
//		return userListDto;
		List<System> systemList = service.readAll();
		SystemModel systemModel = new SystemModel();
		systemModel.setSystems(systemList); 
		
		logger.info("node size="+systemList.size());
		
		SystemListDto systemListDto = new SystemListDto();
		systemListDto.setSystems(SystemMapper.map(systemList));
		HashMap<String, List<SystemDto>> mapOfList = new HashMap<String, List<SystemDto>>();
		//for (  SystemDto systemDto : systemListDto.getSystems() ) {
			
		//}
		mapOfList.put("aaData", systemListDto.getSystems() );
		
		return mapOfList;
		
		//return systemModel;
	} 
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/create", method=RequestMethod.POST )
	public @ResponseBody System create(
							@RequestParam String id,
							@RequestParam String title,
							@RequestParam String description,
							@RequestParam String techcontact,
							@RequestParam String funccontact,
							@RequestParam String launchdate,
							@RequestParam String currentrelease,
							@RequestParam String currentreleaseddate
			) {
		logger.info("id"+id);
		logger.info("title"+title);
		logger.info("description"+description);
		
		System newSystem = new System();
		newSystem.setId(id);
		newSystem.setTitle(title);
		newSystem.setDescription(description);
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date launchDateTemp = null;
		Date currentReleaseDateTemp = null;
		try {
			if ( (launchdate != null ) &&  (launchdate.trim().length() > 6) )
				launchDateTemp = df.parse(launchdate);
			if ( (currentreleaseddate != null ) &&  (currentreleaseddate.trim().length() > 6) )
				currentReleaseDateTemp = df.parse(currentreleaseddate);
		}
		catch ( Exception exp ) {
			exp.printStackTrace();
		}
		
		
		newSystem.setTechcontact(techcontact);
		newSystem.setFunccontact(funccontact);
		newSystem.setLaunchdate(launchDateTemp);
		newSystem.setCurrentrelease(currentrelease);
		newSystem.setCurrentreleaseddate(currentReleaseDateTemp);
		
		
		newSystem = service.create(newSystem);
		
		return newSystem;
	}
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value="/update", method=RequestMethod.POST )
	public @ResponseBody SystemDto update(
							@RequestParam String id,
							@RequestParam String title,
							@RequestParam String description,
							@RequestParam String techcontact,
							@RequestParam String funccontact,
							@RequestParam String launchdate,
							@RequestParam String currentrelease,
							@RequestParam String currentreleaseddate
			) {
		logger.info("id"+id);
		logger.info("title"+title);
		logger.info("description"+description);
		logger.info("launchdate"+launchdate);
		
		System newSystem = new System();
		newSystem.setId(id);
		newSystem.setTitle(title);
		newSystem.setDescription(description);
		
		SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		Date launchDateTemp = null;
		Date currentReleaseDateTemp = null;
		try {
			if ( (launchdate != null ) &&  (launchdate.trim().length() > 6) )
				launchDateTemp = df.parse(launchdate);
			if ( (currentreleaseddate != null ) &&  (currentreleaseddate.trim().length() > 6) )
				currentReleaseDateTemp = df.parse(currentreleaseddate);
		}
		catch ( Exception exp ) {
			exp.printStackTrace();
		}	
		Calendar cal = Calendar.getInstance();
		cal.setTime(launchDateTemp);
		int month = cal.get(Calendar.MONTH) ;
		logger.info( "Month to be saved="+String.valueOf(month)  );
		
		newSystem.setTechcontact(techcontact);
		newSystem.setFunccontact(funccontact);
		newSystem.setLaunchdate(launchDateTemp);
		newSystem.setCurrentrelease(currentrelease);
		newSystem.setCurrentreleaseddate(currentReleaseDateTemp);
		
		
		newSystem = service.update(newSystem);
		
		//return newSystem;
		return SystemMapper.map(newSystem);
		
	}
	
	
	@RequestMapping(value="/delete", method=RequestMethod.POST )
	public @ResponseBody boolean delete(
							@RequestParam String id
			) {
		logger.info("id"+id);
		
		System newSystem = new System();
		newSystem.setId(id);
		
		boolean deleted = service.delete(newSystem);
		logger.info("deleted="+deleted);
		
		return deleted;
	}
	

	@RequestMapping(value="/connect", method=RequestMethod.POST )
	public @ResponseBody boolean connect(
					@RequestParam String sourceId,
					@RequestParam String destinationId,
					@RequestParam String datasetname
			) {
		logger.info("connect");
		logger.info("sourceId"+sourceId);
		logger.info("destinationId"+destinationId);
		logger.info("datasetname"+datasetname);
		
		
		boolean connected = service.connect( sourceId, destinationId, datasetname );
		logger.info("connected="+connected);
		
		return connected;
		
	}	
	
	
	
	@RequestMapping(value="/consumers", method=RequestMethod.GET )
	public @ResponseBody ProduceListDto getAllProduceConnection(
			@RequestParam String sourceId
		) {
		
		logger.info("getAllProduceConnection...");
		logger.info("sourceId"+sourceId);
//		List<Produce> neoProduceList= service.getAllConnectWhereSystemIsProducer(sourceId);
//		
//		ProduceListDto produceListDto = new ProduceListDto();
//		
//		produceListDto.setProduces(ProduceMapper.map(neoProduceList));
//		 
//		for (ProduceDto p : produceListDto.getProduces() ) {
//			logger.info("producer="+p.getProducer().getTitle());
//			logger.info("consumer="+p.getConsumer().getTitle());
//		}
//		
//		return produceListDto;
		ProduceListDto produceListDto= service.getAllConnectWhereSystemIsProducer(sourceId);

		for (ProduceDto p : produceListDto.getProduces() ) {
		logger.info("producer="+p.getProducer().getTitle());
		logger.info("consumer="+p.getConsumer().getTitle());
		}
		
		return produceListDto;
	}
	
	
	
	@RequestMapping(value="/nodes", method=RequestMethod.GET )
	public @ResponseBody List getAllSystemConnection(
		) {
		
		logger.info("getAllSystemConnection...");
		logger.info("/nodes url called...");
		//http://stackoverflow.com/questions/6818011/infovis-jit-library
		List sc =service.getAllSystemConnection();
			
		
		return sc;

	}
	
	@RequestMapping(value="/record/{id}", method=RequestMethod.GET )
	public @ResponseBody SystemDto getExiting(
							@PathVariable String id //@RequestParam String id
			) {
		logger.info("id"+id);
		
		System system = new System();
		system = service.get(id);
		
		return SystemMapper.map(system);
	}	

	
	@RequestMapping(value="/producers", method=RequestMethod.GET )
	public @ResponseBody ProduceListDto getAllConsumerConnection(
			@RequestParam String sourceId
		) {
		
		logger.info("getAllConsumerConnection...");
		logger.info("sourceId"+sourceId);
		ProduceListDto produceListDto= service.getAllConnectWhereSystemIsConsumer(sourceId);

		for (ProduceDto p : produceListDto.getProduces() ) {
		logger.info("consumer="+p.getProducer().getTitle());
		logger.info("producer="+p.getConsumer().getTitle());
		}
		
		return produceListDto;
	}
	
	
	
	
}
