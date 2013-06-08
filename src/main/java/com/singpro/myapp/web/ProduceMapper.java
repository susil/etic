package com.singpro.myapp.web;


import java.util.ArrayList;
import java.util.List;

import com.singpro.myapp.domain.Produce;
import com.singpro.myapp.web.ProduceDto;

public class ProduceMapper {

	public static ProduceDto map(Produce produce) {
			ProduceDto dto = new ProduceDto();
			dto.setNodeId(produce.nodeId);
			dto.setDatasetname(produce.getDatasetname());
			dto.setProducer( SystemMapper.map( produce.getProducer() ));
			dto.setConsumer(SystemMapper.map(produce.getConsumer()));
			
			return dto;
	}
	
	public static List<ProduceDto> map(List<Produce> produces) {
		List<ProduceDto> dtos = new ArrayList<ProduceDto>();
		for (Produce produce: produces) {
			dtos.add(map(produce));
		}
		return dtos;
	}
}
