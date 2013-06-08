package com.singpro.myapp.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.neo4j.cypher.ExecutionResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.singpro.myapp.repo.SystemRepository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**

 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"test-servlet-context.xml"})
@Transactional
public class DomainTests {

    @Autowired 
    Neo4jOperations template;
    
    @Autowired
    protected SystemRepository systemRepository;
    
    
    @Before
    public void setUp() throws Exception {
    }


    //@Test
    public void systemIsAProducer() {
    	System advance = new System();
    	advance.setId("1");
    	advance.setTitle("Advance");
    	advance.setDescription("Faculty Information System");
        advance = template.save(advance);
        
    	System mpm = new System();
    	mpm.setId("2");
    	mpm.setTitle("mpm");
    	mpm.setDescription("PAF");
    	mpm = template.save(mpm);
    	
    	Produce produce= advance.produce( template, mpm, "FISData");
    	//System.out.println(advance.getDescription());
    	//#//advance.addProducelist(produce);
    	template.save(produce);

        System adv1 = systemRepository.findById("1");
        Produce produce1 = adv1.getProducelist().iterator().next();
        assertEquals("FISData",produce1.getDatasetname());
        

    }


    //@Test
    public void systemIsAConsumer() {
    	System advance = new System();
    	advance.setId("1");
    	advance.setTitle("Advance");
    	advance.setDescription("Faculty Information System");
        advance = template.save(advance);
        
    	System mpm = new System();
    	mpm.setId("2");
    	mpm.setTitle("mpm");
    	mpm.setDescription("PAF");
    	mpm = template.save(mpm);
    	
    	Consume consume= advance.consume( template, mpm, "FFP Report");
    	//System.out.println(advance.getDescription());
    	//#//advance.addProducelist(produce);
    	template.save(consume);

        System adv1 = systemRepository.findById("1");
        Consume consume1 = adv1.getConsumelist().iterator().next();
        assertEquals("FFP Report",consume1.getDatasetname());
        

    }    
    

    //@Test
    public void isConsumerFromMulltipleProducers() {
    	java.lang.System.out.println("isConsumerFromMulltipleProducers...");

    	System advance = new System();
    	advance.setId("1");
    	advance.setTitle("Advance");
    	advance.setDescription("Faculty Information System");
        advance = template.save(advance);
        
    	System mpm = new System();
    	mpm.setId("2");
    	mpm.setTitle("mpm");
    	mpm.setDescription("PAF");
    	mpm = template.save(mpm);
    	
    	System senatservice = new System();
    	senatservice.setId("3");
    	senatservice.setTitle("SenateService");
    	senatservice.setDescription("SenateService Descr");
    	senatservice = template.save(mpm);
    	
    	Consume consume= advance.consume( template, mpm, "FFP Report");
    	template.save(consume);
    	consume= advance.consume( template, senatservice, "FacultyGeneral");
    	template.save(consume);
    	
    	
    	System adv1 = systemRepository.findById("1");
    	Iterable<Consume> consumeList = adv1.getConsumelist();
    	for ( Consume consumeTemp : consumeList) {
    		java.lang.System.out.println(consumeTemp.getDatasetname());
    	}
        assertEquals("2","2");
        

    }     
    
        
    
    //@Test
    // tested
    public void isProducerForMulltipleConsumers() {
    	java.lang.System.out.println("isProducerForMulltipleConsumers...");
    	
    	System advance = new System();
    	advance.setId("1");
    	advance.setTitle("Advance");
    	advance.setDescription("Faculty Information System");
        advance = template.save(advance);
        
        java.lang.System.out.println("advance.nodeId->..."+advance.nodeId);
        
    	System mpm = new System();
    	mpm.setId("2");
    	mpm.setTitle("mpm");
    	mpm.setDescription("PAF");
    	mpm = template.save(mpm);

        java.lang.System.out.println("mpm.nodeId->..."+mpm.nodeId);

        
    	Produce produce= advance.produce( template, mpm, "Faculty CV from Advance");
    	//advance = template.save(advance);
    	template.save(produce);

    	System senatservice = new System();
    	senatservice.setId("3");
    	senatservice.setTitle("SenateService");
    	senatservice.setDescription("SenateService Descr");
    	senatservice = template.save(senatservice);
    	java.lang.System.out.println("senatservice.nodeId->..."+senatservice.nodeId);
    	
    	Produce produce1= advance.produce( template, senatservice, "FacultyGeneral form Advance");
    	//advance = template.save(advance);
    	template.save(produce1);
    	
    	System adv1 = systemRepository.findById("1");
    	//Produce foundProducer = produceRepository.findByPropertyValue("produce", adv1); 
    			//.findByPropertyValue("login", "ich");
    	
    	 List<Produce> advConsumers = systemRepository.getConsumers1( adv1 );
    	 
    	 //Map<String, Object> params = new HashMap<String, Object>();
    	 //params.put( "adv1", adv1 );
    	 //ExecutionResult advConsumers = (ExecutionResult) template.execute( "start n=node({node}) return n.name", params );
    	 
    	 
    	
    	 java.lang.System.out.println("\n\n\nadvConsumers.size()="+advConsumers.size());
     	for ( Produce SystemTemp : advConsumers) {
     		
     		java.lang.System.out.println(SystemTemp.getDatasetname());
     		
    		java.lang.System.out.println("SystemTemp-Producer>..."+SystemTemp.getProducer().nodeId);
    		java.lang.System.out.println("SystemTemp-Consumer>..."+SystemTemp.getConsumer().nodeId);
    		
    		System producerSystem = systemRepository.findOne(SystemTemp.getProducer().nodeId);
    		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
    		
    		System consumerSystem = systemRepository.findOne(SystemTemp.getConsumer().nodeId);
    		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());

    		
    	}

     	java.lang.System.out.println("\n\n\nIterating...adv1");
     	
    	Iterable<Produce> produceList = adv1.getProducelist();
    	for ( Produce produceTemp : produceList) {
    		java.lang.System.out.println(produceTemp.getDatasetname());
    		//java.lang.System.out.println("Producer.nodeId->..."+produceTemp.getProducer().nodeId);
    		
    		System producerSystem = systemRepository.findOne(produceTemp.getProducer().nodeId);
    		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
    		
    		System consumerSystem = systemRepository.findOne(produceTemp.getConsumer().nodeId);
    		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());

    	}
    	
    	
        assertEquals("2","2");
        

    } 
    
    
    @Test
    public void isProducerForMulltipleConsumers2() {
    	java.lang.System.out.println("isProducerForMulltipleConsumers...2");
    	
    	System advance = new System();
    	advance.setId("1");
    	advance.setTitle("Advance");
    	advance.setDescription("Faculty Information System");
        advance = template.save(advance);
        
        java.lang.System.out.println("advance.nodeId->..."+advance.nodeId);
        
    	System mpm = new System();
    	mpm.setId("2");
    	mpm.setTitle("mpm");
    	mpm.setDescription("PAF");
    	mpm = template.save(mpm);

        java.lang.System.out.println("mpm.nodeId->..."+mpm.nodeId);

        
    	Produce produce= advance.produce( template, mpm, "Faculty CV from Advance");
    	//advance = template.save(advance);
    	template.save(produce);

    	Produce mpmProduce = mpm.produce(template, advance, "FFP Report");
    	template.save(mpmProduce);
    	
    	System senatservice = new System();
    	senatservice.setId("3");
    	senatservice.setTitle("SenateService");
    	senatservice.setDescription("SenateService Descr");
    	senatservice = template.save(senatservice);
    	java.lang.System.out.println("senatservice.nodeId->..."+senatservice.nodeId);
    	
    	Produce produce1= advance.produce( template, senatservice, "FacultyGeneral form Advance");
    	template.save(produce1);

    	mpmProduce = mpm.produce(template, senatservice, "FFP Report");
    	template.save(mpmProduce);
    	
    	
    	System adv1 = systemRepository.findById("1");
    	java.lang.System.out.println("adv1 ="+adv1.getDescription());
    	List<Produce> advConsumers = systemRepository.getAllProduceRelationWhereSystemIsProducer( adv1 );
    	java.lang.System.out.println("\n\n\nadvConsumers.size()="+advConsumers.size());
     	
    	for ( Produce SystemTemp : advConsumers) {
     		
     		java.lang.System.out.println(SystemTemp.getDatasetname());
     		
    		java.lang.System.out.println("SystemTemp-Producer>..."+SystemTemp.getProducer().nodeId);
    		java.lang.System.out.println("SystemTemp-Consumer>..."+SystemTemp.getConsumer().nodeId);
    		
    		System producerSystem = systemRepository.findOne(SystemTemp.getProducer().nodeId);
    		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
    		
    		System consumerSystem = systemRepository.findOne(SystemTemp.getConsumer().nodeId);
    		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());
    		
    	}

    	adv1 = null;
    	//System mpmSystem = systemRepository.findById("2");
    	adv1 =systemRepository.findById("2");
    	java.lang.System.out.println("adv1 ="+adv1.getDescription());
     	List<Produce> mpmConsumers = systemRepository.getAllProduceRelationWhereSystemIsProducer( adv1 );
     	java.lang.System.out.println("\n\n\n 2 mpmConsumers.size()="+mpmConsumers.size());
    	for ( Produce SystemTemp1 : mpmConsumers) {
    		
    		java.lang.System.out.println(SystemTemp1.getDatasetname());
    		
	   		java.lang.System.out.println("SystemTemp-Producer>..."+SystemTemp1.getProducer().nodeId);
	   		java.lang.System.out.println("SystemTemp-Consumer>..."+SystemTemp1.getConsumer().nodeId);
	   		
	   		System producerSystem = systemRepository.findOne(SystemTemp1.getProducer().nodeId);
	   		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
	   		
	   		System consumerSystem = systemRepository.findOne(SystemTemp1.getConsumer().nodeId);
	   		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());
   	}
     	
     	java.lang.System.out.println("\n\n\nIterating...mpmSystem");
     	
    	Iterable<Produce> produceList = adv1.getProducelist();
    	for ( Produce produceTemp : produceList) {
    		java.lang.System.out.println(produceTemp.getDatasetname());
    		
    		System producerSystem = systemRepository.findOne(produceTemp.getProducer().nodeId);
    		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
    		
    		System consumerSystem = systemRepository.findOne(produceTemp.getConsumer().nodeId);
    		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());

    	}
    	
    	//System mpmTestSystem = systemRepository.findSystemById("2");
     	//java.lang.System.out.println("\n\n\nIterating...mpmTestSystem");
     	
     	//java.lang.System.out.println("getId="+ mpmTestSystem.getId());
     	//java.lang.System.out.println("getDescription="+ mpmTestSystem.getDescription());
     	//java.lang.System.out.println("getProducelist().size()="+ mpmTestSystem.getProducelist().size());
     	
    	 //List<Produce> findProducerId("2");	
    	java.lang.System.out.println("\n\n\n ...");
      	List<Produce> mpmConsumers2 = systemRepository.getProducers( "2");
      	java.lang.System.out.println("\n\n\n mpmConsumers2.size()="+mpmConsumers2.size());
     	for ( Produce SystemTemp : mpmConsumers2) {
     		
     		java.lang.System.out.println(SystemTemp.getDatasetname());
     		
 	   		java.lang.System.out.println("SystemTemp-Producer>..."+SystemTemp.getProducer().nodeId);
 	   		java.lang.System.out.println("SystemTemp-Consumer>..."+SystemTemp.getConsumer().nodeId);
 	   		
 	   		System producerSystem = systemRepository.findOne(SystemTemp.getProducer().nodeId);
 	   		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
 	   		
 	   		System consumerSystem = systemRepository.findOne(SystemTemp.getConsumer().nodeId);
 	   		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());
    	}
    	 
 
    	java.lang.System.out.println("\n\n\n ...");
      	mpmConsumers2 = systemRepository.getProducers( "1");
      	java.lang.System.out.println("\n\n\n adv mpmConsumers2.size()="+mpmConsumers2.size());
     	for ( Produce SystemTemp : mpmConsumers2) {
     		
     		java.lang.System.out.println(SystemTemp.getDatasetname());
     		
 	   		java.lang.System.out.println("SystemTemp-Producer>..."+SystemTemp.getProducer().nodeId);
 	   		java.lang.System.out.println("SystemTemp-Consumer>..."+SystemTemp.getConsumer().nodeId);
 	   		
 	   		System producerSystem = systemRepository.findOne(SystemTemp.getProducer().nodeId);
 	   		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
 	   		
 	   		System consumerSystem = systemRepository.findOne(SystemTemp.getConsumer().nodeId);
 	   		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());
    	}
     	
        assertEquals("2","2");
        

    } 
    
    
    
}
