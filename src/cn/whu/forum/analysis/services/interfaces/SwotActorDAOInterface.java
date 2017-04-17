﻿package cn.whu.forum.analysis.services.interfaces;

import java.util.List;

import cn.whu.forum.analysis.entities.SwotActor;
import cn.whu.forum.analysis.entities.SwotActorProperty;
import cn.whu.forum.analysis.entities.SwotTask;

public interface SwotActorDAOInterface {
	public boolean addActor(SwotActor swotActor);
	public boolean addActor(SwotTask swotTask,SwotActor swotActor);
	public boolean updateActor(SwotActor swotActor);
	public boolean deleteActor(SwotActor swotActor);
	public boolean deleteActorByID(int id);
	public SwotActor getActorByID(int id);
	public List<SwotActor> getActorByName(String nameString);
	public List<SwotActor> getActorByTask(SwotTask swotTask);
	public List<SwotActor> getActorByTaskID(int taskID);
	public List<SwotActorProperty> getAllActorPropertys(SwotActor swotActor);
	List<SwotActor> getSwotActorByActorName(String nameString);
}
