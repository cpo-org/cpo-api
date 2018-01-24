/*
 * Copyright (C) 2003-2012 David E. Berry
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 * A copy of the GNU Lesser General Public License may also be found at
 * http://www.gnu.org/licenses/lgpl.txt
 */
package org.synchronoss.cpo.cassandra;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.synchronoss.cpo.AbstractDataSourceInfo;
import org.synchronoss.cpo.CpoException;

import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * User: dberry
 * Date: 9/10/13
 * Time: 12:44 PM
 * To change this template use File | Settings | File Templates.
 */
public class ClusterDataSourceInfo extends AbstractDataSourceInfo<ClusterDataSource>{
  private static final Logger logger = LoggerFactory.getLogger(ClusterDataSourceInfo.class);
  private String[] contactPoints;
  private String keySpace;
  private String clusterName;
  private Integer maxSchemaAgreementWaitSeconds;
  private NettyOptions nettyOptions;
  private Integer port;
  private ProtocolVersion protocolVersion;
  private AddressTranslator addressTranslator;
  private LoadBalancingPolicy loadBalancingPolicy;
  private ReconnectionPolicy reconnectionPolicy;
  private RetryPolicy retryPolicy;
  private boolean credentials;
  private String userName;
  private String password;
  private AuthProvider authProvider;
  private ProtocolOptions.Compression compressionType;
  private Boolean useMetrics;
  private SSLOptions sslOptions;
  private Collection<Host.StateListener> listeners;
  private Boolean useJmxReporting;
  private PoolingOptions poolingOptions;
  private SocketOptions socketOptions;
  private QueryOptions queryOptions;
  private SpeculativeExecutionPolicy speculativeExecutionPolicy;
  private TimestampGenerator timestampGenerator;

  public ClusterDataSourceInfo(String clusterName, String keySpace, String[] contactPoints) {
    super(buildDataSourceName(clusterName, keySpace, contactPoints));
    this.keySpace=keySpace;
    this.clusterName=clusterName;
    this.contactPoints=contactPoints;
  }

  public Integer getMaxSchemaAgreementWaitSeconds() {
    return maxSchemaAgreementWaitSeconds;
  }

  public void setMaxSchemaAgreementWaitSeconds(Integer maxSchemaAgreementWaitSeconds) {
    this.maxSchemaAgreementWaitSeconds = maxSchemaAgreementWaitSeconds;
  }

  public NettyOptions getNettyOptions() {
    return nettyOptions;
  }

  public void setNettyOptions(NettyOptions nettyOptions) {
    this.nettyOptions = nettyOptions;
  }

  public AddressTranslator getAddressTranslator() {
    return addressTranslator;
  }

  public void setAddressTranslater(AddressTranslator addressTranslator) {
    this.addressTranslator = addressTranslator;
  }

  public String getClusterName() {
    return clusterName;
  }

  public void setClusterName(String clusterName) {
    this.clusterName = clusterName;
  }

  public String getKeySpace() {
    return keySpace;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public LoadBalancingPolicy getLoadBalancingPolicy() {
    return loadBalancingPolicy;
  }

  public void setLoadBalancingPolicy(LoadBalancingPolicy loadBalancingPolicy) {
    this.loadBalancingPolicy = loadBalancingPolicy;
  }

  public ReconnectionPolicy getReconnectionPolicy() {
    return reconnectionPolicy;
  }

  public void setReconnectionPolicy(ReconnectionPolicy reconnectionPolicy) {
    this.reconnectionPolicy = reconnectionPolicy;
  }

  public RetryPolicy getRetryPolicy() {
    return retryPolicy;
  }

  public void setRetryPolicy(RetryPolicy retryPolicy) {
    this.retryPolicy = retryPolicy;
  }

  public boolean hasCredentials() {
    return credentials;
  }

  public void setHasCredentials(boolean credentials) {
    this.credentials = credentials;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public AuthProvider getAuthProvider() {
    return authProvider;
  }

  public void setAuthProvider(AuthProvider authProvider) {
    this.authProvider = authProvider;
  }

  public ProtocolOptions.Compression getCompressionType() {
    return compressionType;
  }

  public void setCompressionType(ProtocolOptions.Compression compressionType) {
    this.compressionType = compressionType;
  }

  public Boolean getUseMetrics() {
    return useMetrics;
  }

  public void setUseMetrics(Boolean useMetrics) {
    this.useMetrics = useMetrics;
  }

  public SSLOptions getSslOptions() {
    return sslOptions;
  }

  public void setSslOptions(SSLOptions sslOptions) {
    this.sslOptions = sslOptions;
  }

  public Collection<Host.StateListener> getListeners() {
    return listeners;
  }

  public void setListeners(Collection<Host.StateListener> listeners) {
    this.listeners = listeners;
  }

  public Boolean getUseJmxReporting() {
    return useJmxReporting;
  }

  public void setUseJmxReporting(Boolean useJmxReporting) {
    this.useJmxReporting = useJmxReporting;
  }

  public PoolingOptions getPoolingOptions() {
    return poolingOptions;
  }

  public void setPoolingOptions(PoolingOptions poolingOptions) {
    this.poolingOptions = poolingOptions;
  }

  public SocketOptions getSocketOptions() {
    return socketOptions;
  }

  public void setSocketOptions(SocketOptions socketOptions) {
    this.socketOptions = socketOptions;
  }

  public QueryOptions getQueryOptions() {
    return queryOptions;
  }

  public void setQueryOptions(QueryOptions queryOptions) {
    this.queryOptions = queryOptions;
  }

  public SpeculativeExecutionPolicy getSpeculativeExecutionPolicy() {
    return speculativeExecutionPolicy;
  }

  public void setSpeculativeExecutionPolicy(SpeculativeExecutionPolicy speculativeExecutionPolicy) {
    this.speculativeExecutionPolicy = speculativeExecutionPolicy;
  }

  public TimestampGenerator getTimestampGenerator() {
    return timestampGenerator;
  }

  public void setTimestampGenerator(TimestampGenerator timestampGenerator) {
    this.timestampGenerator = timestampGenerator;
  }

  public ProtocolVersion getProtocolVersion() {
    return protocolVersion;
  }

  public void setProtocolVersion(ProtocolVersion protocolVersion) {
    this.protocolVersion = protocolVersion;
  }

  @Override
  protected ClusterDataSource createDataSource() throws CpoException {
    Cluster.Builder clusterBuilder = Cluster.builder();

    // add the contact points
    for(String s : contactPoints)
      clusterBuilder.addContactPoint(s);

    // add addressTranslater
    if (addressTranslator != null)
      clusterBuilder.withAddressTranslator(addressTranslator);

    // add AuthProvider
    if (authProvider != null)
      clusterBuilder.withAuthProvider(authProvider);

    // add clusterName
    if (clusterName != null)
      clusterBuilder.withClusterName(clusterName);

    // add Compression
    if (compressionType != null)
      clusterBuilder.withCompression(compressionType);

    // add credentials
    if (hasCredentials())
      clusterBuilder.withCredentials(userName, password);

    // add Listeners
    if (listeners!=null && !listeners.isEmpty() && listeners.size()>0)
      clusterBuilder.withInitialListeners(listeners);

    // add loadBalancing
    if (loadBalancingPolicy != null)
      clusterBuilder.withLoadBalancingPolicy(loadBalancingPolicy);

    // add maxSchemaAgreementWaitSeconds
    if (maxSchemaAgreementWaitSeconds != null)
      clusterBuilder.withMaxSchemaAgreementWaitSeconds(maxSchemaAgreementWaitSeconds);

    // add NettyOptions
    if (nettyOptions != null)
      clusterBuilder.withNettyOptions(nettyOptions);

    // add JMX Reporting
    if (useJmxReporting != null && !useJmxReporting)
      clusterBuilder.withoutJMXReporting();

    // add Metrics
    if (useMetrics != null && !useMetrics)
      clusterBuilder.withoutMetrics();

    // add pooling options
    if (poolingOptions != null)
      clusterBuilder.withPoolingOptions(poolingOptions);

    // add port
    if (port != null)
      clusterBuilder.withPort(port);

    if (protocolVersion != null)
      clusterBuilder.withProtocolVersion(protocolVersion);

    // add query options
    if (queryOptions != null)
      clusterBuilder.withQueryOptions(queryOptions);

    // add reconnectionPolicy
    if (reconnectionPolicy != null)
      clusterBuilder.withReconnectionPolicy(reconnectionPolicy);

    // add retryPolicy
    if (retryPolicy != null)
      clusterBuilder.withRetryPolicy(retryPolicy);

    // add socket options
    if (socketOptions != null)
      clusterBuilder.withSocketOptions(socketOptions);

    // add SpeculativeExecutionPolicy
    if (speculativeExecutionPolicy == null)
      clusterBuilder.withSpeculativeExecutionPolicy(speculativeExecutionPolicy);

    // add SSL
    if (sslOptions != null)
      clusterBuilder.withSSL(sslOptions);

    // add TimestampGenerator
    if (timestampGenerator == null)
      clusterBuilder.withTimestampGenerator(timestampGenerator);

    return new ClusterDataSource(clusterBuilder.build(), keySpace);
  }

  private static String buildDataSourceName(String clusterName, String keySpace, String[] contactPoints) {
    StringBuilder sb = new StringBuilder();
    sb.append(clusterName);
    sb.append(keySpace);
    for (String s : contactPoints)
      sb.append(s);
    logger.debug("DatasourceName="+sb.toString());
    return sb.toString();
  }

}
