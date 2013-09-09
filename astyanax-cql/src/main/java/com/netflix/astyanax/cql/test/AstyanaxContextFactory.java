package com.netflix.astyanax.cql.test;

import static com.netflix.astyanax.cql.test.ClusterConfiguration.TEST_CLUSTER_NAME;
import static com.netflix.astyanax.cql.test.ClusterConfiguration.TEST_KEYSPACE_NAME;
import static com.netflix.astyanax.cql.test.ClusterConfiguration.TheDriver;

import java.util.Collections;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.google.common.base.Supplier;
import com.netflix.astyanax.AstyanaxContext;
import com.netflix.astyanax.Cluster;
import com.netflix.astyanax.Keyspace;
import com.netflix.astyanax.connectionpool.Host;
import com.netflix.astyanax.connectionpool.NodeDiscoveryType;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolConfigurationImpl;
import com.netflix.astyanax.connectionpool.impl.ConnectionPoolType;
import com.netflix.astyanax.connectionpool.impl.CountingConnectionPoolMonitor;
import com.netflix.astyanax.cql.CqlFamilyFactory;
import com.netflix.astyanax.cql.test.ClusterConfiguration.Driver;
import com.netflix.astyanax.impl.AstyanaxConfigurationImpl;

public class AstyanaxContextFactory {

    
    public static AstyanaxContext<Cluster> getCluster() {
    	return getCluster(TheDriver); 
    }

    public static AstyanaxContext<Cluster> getCluster(Driver driver) {
    	
    	if (driver != Driver.JAVA_DRIVER) {
    		throw new NotImplementedException();
    	}
    	return clusterWithJavaDriver(); 
    }
    
    
    private static AstyanaxContext<Cluster> clusterWithJavaDriver() {

    	final String SEEDS = "localhost:9160";

		Supplier<List<Host>> HostSupplier = new Supplier<List<Host>>() {

			@Override
			public List<Host> get() {
				Host host = new Host(SEEDS, -1);
				return Collections.singletonList(host);
			}
    	};
    	
    	AstyanaxContext<Cluster> context = new AstyanaxContext.Builder()
                .forCluster(TEST_CLUSTER_NAME)
                .forKeyspace(TEST_KEYSPACE_NAME)
                .withAstyanaxConfiguration(
                        new AstyanaxConfigurationImpl()
                                .setDiscoveryType(NodeDiscoveryType.RING_DESCRIBE)
                                .setConnectionPoolType(ConnectionPoolType.JAVA_DRIVER)
                                .setDiscoveryDelayInSeconds(60000))
                .withConnectionPoolConfiguration(
                        new ConnectionPoolConfigurationImpl(TEST_CLUSTER_NAME
                                + "_" + TEST_KEYSPACE_NAME)
                                .setSocketTimeout(30000)
                                .setMaxTimeoutWhenExhausted(2000)
                                .setMaxConnsPerHost(20)
                                .setInitConnsPerHost(10)
                                .setSeeds(SEEDS)
                                )
                .withHostSupplier(HostSupplier)
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildCluster(CqlFamilyFactory.getInstance());

    	return context;
    }
    
    public static AstyanaxContext<Keyspace> getKeyspace() {
    	return getKeyspace(TheDriver); 
    }

    public static AstyanaxContext<Keyspace> getKeyspace(Driver driver) {
    	if (driver != Driver.JAVA_DRIVER) {
    		throw new NotImplementedException();
    	}
    	return keyspaceWithJavaDriver(); 
    }
    
    private static AstyanaxContext<Keyspace> keyspaceWithJavaDriver() {

    	final String SEEDS = "localhost:9160";

		Supplier<List<Host>> HostSupplier = new Supplier<List<Host>>() {

			@Override
			public List<Host> get() {
				Host host = new Host(SEEDS, -1);
				return Collections.singletonList(host);
			}
    	};
    	
    	AstyanaxContext<Keyspace> context = new AstyanaxContext.Builder()
                .forCluster(TEST_CLUSTER_NAME)
                .forKeyspace(TEST_KEYSPACE_NAME)
                .withAstyanaxConfiguration(
                        new AstyanaxConfigurationImpl()
                                .setDiscoveryType(NodeDiscoveryType.DISCOVERY_SERVICE)
                                .setConnectionPoolType(ConnectionPoolType.JAVA_DRIVER)
                                .setDiscoveryDelayInSeconds(60000))
                .withConnectionPoolConfiguration(
                        new ConnectionPoolConfigurationImpl(TEST_CLUSTER_NAME
                                + "_" + TEST_KEYSPACE_NAME)
                                .setSocketTimeout(30000)
                                .setMaxTimeoutWhenExhausted(2000)
                                .setMaxConnsPerHost(20)
                                .setInitConnsPerHost(10)
                                .setSeeds(SEEDS)
                                )
                .withHostSupplier(HostSupplier)
                .withConnectionPoolMonitor(new CountingConnectionPoolMonitor())
                .buildKeyspace(CqlFamilyFactory.getInstance());

    	return context;
    }
}
