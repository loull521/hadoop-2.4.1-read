/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hadoop.yarn.server.resourcemanager;

import java.util.concurrent.ConcurrentMap;

import org.apache.hadoop.ha.HAServiceProtocol.HAServiceState;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.NodeId;
import org.apache.hadoop.yarn.conf.ConfigurationProvider;
import org.apache.hadoop.yarn.event.Dispatcher;
import org.apache.hadoop.yarn.server.resourcemanager.ahs.RMApplicationHistoryWriter;
import org.apache.hadoop.yarn.server.resourcemanager.recovery.RMStateStore;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.RMApp;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.attempt.AMLivelinessMonitor;
import org.apache.hadoop.yarn.server.resourcemanager.rmcontainer.ContainerAllocationExpirer;
import org.apache.hadoop.yarn.server.resourcemanager.rmnode.RMNode;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.ResourceScheduler;
import org.apache.hadoop.yarn.server.resourcemanager.security.AMRMTokenSecretManager;
import org.apache.hadoop.yarn.server.resourcemanager.security.ClientToAMTokenSecretManagerInRM;
import org.apache.hadoop.yarn.server.resourcemanager.security.DelegationTokenRenewer;
import org.apache.hadoop.yarn.server.resourcemanager.security.NMTokenSecretManagerInRM;
import org.apache.hadoop.yarn.server.resourcemanager.security.RMContainerTokenSecretManager;
import org.apache.hadoop.yarn.server.resourcemanager.security.RMDelegationTokenSecretManager;

/**
 * Context of the ResourceManager.
 */
public interface RMContext {

  /**
   * 拿到RM的中央异步调度器
   * @return
   */
  Dispatcher getDispatcher();

  boolean isHAEnabled();

  HAServiceState getHAServiceState();

  RMStateStore getStateStore();

  /**
   * 保存了一些列RMApp，RMApp维护了一个应用程序Application的运行周期状态<br>
   * 由于一个application的生命周期可能启动多个appAttempt，因此可以认为，RMApp维护的是同一个application启动的所有运行实例的生命周期
   * @return
   */
  ConcurrentMap<ApplicationId, RMApp> getRMApps();
  
  ConcurrentMap<String, RMNode> getInactiveRMNodes();

  /**
   * 保存了一些列RMNode，RMNode维护了一个NM的运行周期状态
   * @return
   */
  ConcurrentMap<NodeId, RMNode> getRMNodes();

  AMLivelinessMonitor getAMLivelinessMonitor();

  AMLivelinessMonitor getAMFinishingMonitor();

  ContainerAllocationExpirer getContainerAllocationExpirer();
  
  DelegationTokenRenewer getDelegationTokenRenewer();

  AMRMTokenSecretManager getAMRMTokenSecretManager();

  RMContainerTokenSecretManager getContainerTokenSecretManager();
  
  NMTokenSecretManagerInRM getNMTokenSecretManager();

  ResourceScheduler getScheduler();

  NodesListManager getNodesListManager();

  ClientToAMTokenSecretManagerInRM getClientToAMTokenSecretManager();

  AdminService getRMAdminService();

  ClientRMService getClientRMService();

  ApplicationMasterService getApplicationMasterService();

  /**
   * 跟RM通信协议的实现，所以里面肯定有一个用于io通信的server。
   * 它还继承了AbstractService
   * @return
   */
  ResourceTrackerService getResourceTrackerService();

  void setClientRMService(ClientRMService clientRMService);

  RMDelegationTokenSecretManager getRMDelegationTokenSecretManager();

  void setRMDelegationTokenSecretManager(
      RMDelegationTokenSecretManager delegationTokenSecretManager);

  RMApplicationHistoryWriter getRMApplicationHistoryWriter();

  void setRMApplicationHistoryWriter(
      RMApplicationHistoryWriter rmApplicationHistoryWriter);

  ConfigurationProvider getConfigurationProvider();
}