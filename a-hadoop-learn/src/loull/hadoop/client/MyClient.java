package loull.hadoop.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.yarn.api.ApplicationClientProtocol;
import org.apache.hadoop.yarn.api.protocolrecords.GetNewApplicationRequest;
import org.apache.hadoop.yarn.api.protocolrecords.GetNewApplicationResponse;
import org.apache.hadoop.yarn.api.protocolrecords.SubmitApplicationRequest;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.ApplicationSubmissionContext;
import org.apache.hadoop.yarn.api.records.ContainerLaunchContext;
import org.apache.hadoop.yarn.api.records.LocalResource;
import org.apache.hadoop.yarn.exceptions.YarnException;
import org.apache.hadoop.yarn.util.Records;

public class MyClient {
	
	public static final String ADDR = "";
	private static InetSocketAddress address = new InetSocketAddress(ADDR, 0);
	
	public static void submitApplication(Configuration conf) throws IOException, YarnException {
		// 获取 application id
		ApplicationClientProtocol rmClient = 
				RPC.getProxy(ApplicationClientProtocol.class, 0, address, conf);
		GetNewApplicationRequest request = Records.newRecord(GetNewApplicationRequest.class);
		GetNewApplicationResponse response = rmClient.getNewApplication(request);
		ApplicationId appId = response.getApplicationId();
		
		// 发送 submitApplication 请求
		ApplicationSubmissionContext appContext = Records.newRecord(ApplicationSubmissionContext.class);
		appContext.setApplicationName("app name");
		ContainerLaunchContext amContainer = Records.newRecord(ContainerLaunchContext.class);
		amContainer.setLocalResources(new HashMap<String, LocalResource>());
		amContainer.setEnvironment(new HashMap<String, String>());
		appContext.setAMContainerSpec(amContainer);
		appContext.setApplicationId(appId);
		SubmitApplicationRequest submitApplicationRequest = Records.newRecord(SubmitApplicationRequest.class);
		submitApplicationRequest.setApplicationSubmissionContext(appContext);
		rmClient.submitApplication(submitApplicationRequest);
	}

}
