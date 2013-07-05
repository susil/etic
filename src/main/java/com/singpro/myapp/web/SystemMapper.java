package com.singpro.myapp.web;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.singpro.myapp.domain.System;
import com.singpro.myapp.web.SystemDto;

public class SystemMapper {

	public static SystemDto map(System system) {
			SystemDto dto = new SystemDto();
			dto.setId(system.getId());
			//dto.setTitle(system.getTitle()+":"+system.getId());
			//dto.setTitle(system.getTitle()+"::"+system.getId());
			dto.setTitle(system.getTitle());
			dto.setDescription(system.getDescription());
			
		    dto.setTechcontact(system.getTechcontact());
		    dto.setFunccontact(system.getFunccontact());
		    dto.setLaunchdate(system.getLaunchdate());
		    dto.setCurrentrelease(system.getCurrentrelease());
		    dto.setCurrentreleaseddate(system.getCurrentreleaseddate());
			
			return dto;
	}
	
	public static List<SystemDto> map(List<System> systems) {
		List<SystemDto> dtos = new ArrayList<SystemDto>();
		for (System system: systems) {
			dtos.add(map(system));
		}
		return dtos;
	}
}
