package loull.common;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.service.CompositeService;
import org.apache.hadoop.service.Service;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.event.AbstractEvent;
import org.apache.hadoop.yarn.event.AsyncDispatcher;
import org.apache.hadoop.yarn.event.Dispatcher;
import org.apache.hadoop.yarn.event.EventHandler;

public class ServiceAndStateTest{

	public enum TaskEventType {
		T_KILL,
		T_SHEDULE
	}
	
	public enum JobEventType {
		JOB_KILL,
		JOB_INIT,
		JOB_START
	}
	
	public class TaskEvent extends AbstractEvent<TaskEventType> {
		private String taskID;
		public TaskEvent(String taskID, TaskEventType type) {
			super(type);
			this.taskID = taskID;
		}
		public String getTaskID() {
			return taskID;
		}
	}
	
	public class JobEvent extends AbstractEvent<JobEventType> {
		private String jobID;
		public JobEvent(String jobID, JobEventType type) {
			super(type);
			this.jobID = jobID;
		}
		public String getJobID() {
			return jobID;
		}
	}
	
	public class SimpleMRAppMaster extends CompositeService {
		private Dispatcher dispatcher;
		private String jobID;
		private int taskNumber;
		private String[] taskIDs;
		
		public SimpleMRAppMaster(String name, String jobID, int taskNumber) {
			super(name);
			this.jobID = jobID;
			this.taskNumber = taskNumber;
			taskIDs = new String[taskNumber];
			for (int i=0; i<taskNumber; i++) {
				taskIDs[i] = new String(jobID + "_task_" + i);
			}
		}
		
		public void serviceInit(final Configuration conf) throws Exception {
			dispatcher = new AsyncDispatcher();
			dispatcher.register(JobEventType.class, new JobEventDispatcher());
			dispatcher.register(TaskEventType.class, new TaskEventDispatcher());
			addService((Service) dispatcher);
			super.serviceInit(conf);
		}
		
		public Dispatcher getDispatcher() {
			return dispatcher;
		}
		
		@Override
		protected void serviceStart() throws Exception {
			super.serviceStart();
		}
		
		private class JobEventDispatcher implements EventHandler<JobEvent> {
			@Override
			public void handle(JobEvent event) {
				if (event.getType() == JobEventType.JOB_KILL) {
					System.out.println("receive job_kill event, killing all the tasks");
					for (int i=0; i<taskNumber; i++) {
						dispatcher.getEventHandler().handle(new TaskEvent(taskIDs[i], TaskEventType.T_KILL));
					}
				}
				else if (event.getType() == JobEventType.JOB_INIT) {
					System.out.println("receive job_init event, sheduling tasks");
					for (String taskID : taskIDs) {
						dispatcher.getEventHandler().handle(new TaskEvent(taskID, TaskEventType.T_SHEDULE));
					}
				}
			}
		}
		
		private class TaskEventDispatcher implements EventHandler<TaskEvent> {
			@Override
			public void handle(TaskEvent event) {
				if (event.getType() == TaskEventType.T_KILL) {
					System.out.println("receive T_KILL event of task" + event.getTaskID());
				}
				else if (event.getType() == TaskEventType.T_SHEDULE) {
					System.out.println("receive T_schedule event of task" + event.getTaskID());
				}
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		String jobID = "job_333";
		ServiceAndStateTest test = new ServiceAndStateTest();
		SimpleMRAppMaster appMaster = test.new SimpleMRAppMaster("simple MRAppMaster", jobID, 5);
		YarnConfiguration conf = new YarnConfiguration(new Configuration());
		appMaster.serviceInit(conf);
		appMaster.serviceStart();
		appMaster.getDispatcher().getEventHandler().handle(test.new JobEvent(jobID, JobEventType.JOB_KILL));
		appMaster.getDispatcher().getEventHandler().handle(test.new JobEvent(jobID, JobEventType.JOB_INIT));
	}

}
