package zlbyzc.sub3.analysis.services.interfaces;

import java.util.Date;
import java.util.List;

import zlbyzc.sub3.analysis.entities.SwotActor;
import zlbyzc.sub3.analysis.entities.SwotTask;

public interface SwotTaskDAOInterface {
	public boolean updateTaskResult(SwotTask swotTask);
	public String getTaskResult(SwotTask swotTask);
	public boolean addTask(SwotTask swotTask);
	public boolean updateTask(SwotTask swotTask);
	public boolean deleteTask(SwotTask swotTask);
	public boolean deleteTaskByID(int id);
	public SwotTask getSwotTaskByID(int id);
	public List<SwotTask> getSwotTaskByName(String nameString);
	public List<SwotTask> getAllSwotTasks();
	List<SwotActor> getAllTaskActors(SwotTask swotTask);
	List<SwotTask> getSwotTaskByDate(Date startDate, Date endDate);
	List<String> getAllSwotTasksLocation();
	List<SwotTask> getSwotTaskByLocation(String locationString);
}
