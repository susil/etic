package com.singpro.myapp.web;

import java.util.List;

public class SystemModel {

	private List<com.singpro.myapp.domain.System> systems;

	public List<com.singpro.myapp.domain.System> geSystems() {
		return systems;
	}

	public void setSystems(List<com.singpro.myapp.domain.System> systemList) {
		this.systems = systemList;
	}
}
