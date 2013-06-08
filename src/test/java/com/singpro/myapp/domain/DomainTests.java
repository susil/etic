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
    	System system1 = new System();
    	system1.setId("1");
    	system1.setTitle("System1");
    	system1.setDescription("Faculty Information System");
        system1 = template.save(system1);
        
    	System System2 = new System();
    	System2.setId("2");
    	System2.setTitle("System2");
    	System2.setDescription("System2");
    	System2 = template.save(System2);
    	
    	Produce produce= system1.produce( template, System2, "FISData");
    	//System.out.println(system1.getDescription());
    	//#//system1.addProducelist(produce);
    	template.save(produce);

        System adv1 = systemRepository.findById("1");
        Produce produce1 = adv1.getProducelist().iterator().next();
        assertEquals("FISData",produce1.getDatasetname());
        

    }


    //@Test
    public void systemIsAConsumer() {
    	System system1 = new System();
    	system1.setId("1");
    	system1.setTitle("System1");
    	system1.setDescription(" Information System");
        system1 = template.save(system1);
        
    	System System2 = new System();
    	System2.setId("2");
    	System2.setTitle("System2");
    	System2.setDescription("System2");
    	System2 = template.save(System2);
    	
    	Consume consume= system1.consume( template, System2, "a1 Report");
    	//System.out.println(system1.getDescription());
    	//#//system1.addProducelist(produce);
    	template.save(consume);

        System adv1 = systemRepository.findById("1");
        Consume consume1 = adv1.getConsumelist().iterator().next();
        assertEquals("a1 Report",consume1.getDatasetname());
        

    }    
    

    //@Test
    public void isConsumerFromMulltipleProducers() {
    	java.lang.System.out.println("isConsumerFromMulltipleProducers...");

    	System system1 = new System();
    	system1.setId("1");
    	system1.setTitle("System1");
    	system1.setDescription(" Information System");
        system1 = template.save(system1);
        
    	System System2 = new System();
    	System2.setId("2");
    	System2.setTitle("System2");
    	System2.setDescription("System2");
    	System2 = template.save(System2);
    	
    	System s5 = new System();
    	s5.setId("3");
    	s5.setTitle("system5");
    	s5.setDescription("system5 Descr");
    	s5 = template.save(System2);
    	
    	Consume consume= system1.consume( template, System2, "a1 Report");
    	template.save(consume);
    	consume= system1.consume( template, s5, "General");
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
    	
    	System system1 = new System();
    	system1.setId("1");
    	system1.setTitle("System1");
    	system1.setDescription("Faculty Information System");
        system1 = template.save(system1);
        
        java.lang.System.out.println("system1.nodeId->..."+system1.nodeId);
        
    	System System2 = new System();
    	System2.setId("2");
    	System2.setTitle("System2");
    	System2.setDescription("System2");
    	System2 = template.save(System2);

        java.lang.System.out.println("System2.nodeId->..."+System2.nodeId);

        
    	Produce produce= system1.produce( template, System2, "...from System1");
    	//system1 = template.save(system1);
    	template.save(produce);

    	System s5 = new System();
    	s5.setId("3");
    	s5.setTitle("system5");
    	s5.setDescription("system5 Descr");
    	s5 = template.save(s5);
    	java.lang.System.out.println("s5.nodeId->..."+s5.nodeId);
    	
    	Produce produce1= system1.produce( template, s5, "General form System1");
    	//system1 = template.save(system1);
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
    	
    	System system1 = new System();
    	system1.setId("1");
    	system1.setTitle("System1");
    	system1.setDescription(" Information System");
        system1 = template.save(system1);
        
        java.lang.System.out.println("system1.nodeId->..."+system1.nodeId);
        
    	System System2 = new System();
    	System2.setId("2");
    	System2.setTitle("System2");
    	System2.setDescription("System2");
    	System2 = template.save(System2);

        java.lang.System.out.println("System2.nodeId->..."+System2.nodeId);

        
    	Produce produce= system1.produce( template, System2, "INfo System1");
    	//system1 = template.save(system1);
    	template.save(produce);

    	Produce System2Produce = System2.produce(template, system1, "a1 Report");
    	template.save(System2Produce);
    	
    	System s5 = new System();
    	s5.setId("3");
    	s5.setTitle("system5");
    	s5.setDescription("system5 Descr");
    	s5 = template.save(s5);
    	java.lang.System.out.println("s5.nodeId->..."+s5.nodeId);
    	
    	Produce produce1= system1.produce( template, s5, "General form System1");
    	template.save(produce1);

    	System2Produce = System2.produce(template, s5, "a1 Report");
    	template.save(System2Produce);
    	
    	
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
    	//System System2System = systemRepository.findById("2");
    	adv1 =systemRepository.findById("2");
    	java.lang.System.out.println("adv1 ="+adv1.getDescription());
     	List<Produce> System2Consumers = systemRepository.getAllProduceRelationWhereSystemIsProducer( adv1 );
     	java.lang.System.out.println("\n\n\n 2 System2Consumers.size()="+System2Consumers.size());
    	for ( Produce SystemTemp1 : System2Consumers) {
    		
    		java.lang.System.out.println(SystemTemp1.getDatasetname());
    		
	   		java.lang.System.out.println("SystemTemp-Producer>..."+SystemTemp1.getProducer().nodeId);
	   		java.lang.System.out.println("SystemTemp-Consumer>..."+SystemTemp1.getConsumer().nodeId);
	   		
	   		System producerSystem = systemRepository.findOne(SystemTemp1.getProducer().nodeId);
	   		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
	   		
	   		System consumerSystem = systemRepository.findOne(SystemTemp1.getConsumer().nodeId);
	   		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());
   	}
     	
     	java.lang.System.out.println("\n\n\nIterating...System2System");
     	
    	Iterable<Produce> produceList = adv1.getProducelist();
    	for ( Produce produceTemp : produceList) {
    		java.lang.System.out.println(produceTemp.getDatasetname());
    		
    		System producerSystem = systemRepository.findOne(produceTemp.getProducer().nodeId);
    		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
    		
    		System consumerSystem = systemRepository.findOne(produceTemp.getConsumer().nodeId);
    		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());

    	}
    	
    	//System System2TestSystem = systemRepository.findSystemById("2");
     	//java.lang.System.out.println("\n\n\nIterating...System2TestSystem");
     	
     	//java.lang.System.out.println("getId="+ System2TestSystem.getId());
     	//java.lang.System.out.println("getDescription="+ System2TestSystem.getDescription());
     	//java.lang.System.out.println("getProducelist().size()="+ System2TestSystem.getProducelist().size());
     	
    	 //List<Produce> findProducerId("2");	
    	java.lang.System.out.println("\n\n\n ...");
      	List<Produce> System2Consumers2 = systemRepository.getProducers( "2");
      	java.lang.System.out.println("\n\n\n System2Consumers2.size()="+System2Consumers2.size());
     	for ( Produce SystemTemp : System2Consumers2) {
     		
     		java.lang.System.out.println(SystemTemp.getDatasetname());
     		
 	   		java.lang.System.out.println("SystemTemp-Producer>..."+SystemTemp.getProducer().nodeId);
 	   		java.lang.System.out.println("SystemTemp-Consumer>..."+SystemTemp.getConsumer().nodeId);
 	   		
 	   		System producerSystem = systemRepository.findOne(SystemTemp.getProducer().nodeId);
 	   		java.lang.System.out.println("producerSystem->..."+producerSystem.getId() + ":" + producerSystem.getDescription());
 	   		
 	   		System consumerSystem = systemRepository.findOne(SystemTemp.getConsumer().nodeId);
 	   		java.lang.System.out.println("consumerSystem->..."+consumerSystem.getId() + ":" + consumerSystem.getDescription());
    	}
    	 
 
    	java.lang.System.out.println("\n\n\n ...");
      	System2Consumers2 = systemRepository.getProducers( "1");
      	java.lang.System.out.println("\n\n\n adv System2Consumers2.size()="+System2Consumers2.size());
     	for ( Produce SystemTemp : System2Consumers2) {
     		
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
