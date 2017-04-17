package cn.whu.forum.analysis.views;

import java.util.Calendar;

import javax.swing.JFrame;

import cn.whu.forum.analysis.entities.SwotTask;
import cn.whu.forum.analysis.services.SwotTaskDAO;
import cn.whu.forum.analysis.services.interfaces.SwotTaskDAOInterface;

public class Test  extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//			UserDAO ud = new UserDAO();
//			System.out.println(ud.getAllUsers());
		
		SwotTaskDAOInterface swotTaskDAO = new SwotTaskDAO();
//		SwotActorDAOInterface swotActorDAO = new SwotActorDAO();
//		
//		SwotTask swotTask = swotTaskDAO.getSwotTaskByID(1);
//		List<SwotActor> swotActorList = swotTaskDAO.getAllTaskActors(swotTask);
//		
//		for(SwotActor swotActor:swotActorList) {
//			SwotActorDAOInterface swotActorDAO = new SwotActorDAO();
//			List<SwotActorProperty> actorPropertyList = swotActorDAO.getAllActorPropertys(swotActor);	
//			for(SwotActorProperty actorProperty:actorPropertyList) {		//删除属性
//				SwotPropertyDAOInterface swotPropertyDAO = new SwotPropertyDAO();
//				swotPropertyDAO.deletePropertyByID(actorProperty.getPropertyID());
//			}	
//			swotActorDAO.deleteActorByID(swotActor.getActorID());
//		}
//		swotTaskDAO.deleteTaskByID(1);
		
		Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 		//年份自动多余，不知缘由
        		1, 2, 0, 0, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
		SwotTask swotTask = new SwotTask();
		swotTask.setTaskName("测试用例3");
		swotTask.setArgueTime(calendar.getTime());	
		swotTaskDAO.addTask(swotTask);
//
//		SwotActor swotActor = new SwotActor();
//		swotActor.setActorName("参与者0");
//		swotActorDAO.addActor(swotTask, swotActor);
//		
//		SwotActor swotActor1 = new SwotActor();
//		swotActor1.setActorName("参与者1");
//		swotActorDAO.addActor(swotTask, swotActor1);
		
		
//		List<SwotTask> swotTaskssList = swotActorDAO.getSwotTaskByActorName("2");
//		for(SwotTask lists:swotTaskssList) {
//			System.out.println(lists.toString());
//		}
		
	//	List<SwotActor> swotTasksList = swotActorDAO.getSwotActorByActorName("2");
//		List<SwotTask> swotTaskList = swotTaskDAO.getAllSwotTasks();
//		for(SwotTask lists:swotTaskList) {
//			System.out.println(lists.toString());
//		}
//		
//		SwotTask swotTask = swotTaskDAO.getSwotTaskByID(1);
//		SwotActor swotActor = new SwotActor();
//		swotActor.setActorName("22222");
//		swotActorDAO.addActor(swotTask, swotActor);
		

	}

}
