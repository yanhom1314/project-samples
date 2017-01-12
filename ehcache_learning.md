#### Maven

		<dependency>
            <groupId>net.sf.ehcache</groupId>
            <artifactId>ehcache</artifactId>
        </dependency>


#### ehcache.xml

		
		<?xml version="1.0" encoding="UTF-8"?>
		<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
		         xsi:noNamespaceSchemaLocation="ehcache.xsd"
		         updateCheck="true" monitoring="autodetect"
		         dynamicConfig="true">
		
		    <diskStore path="java.io.tmpdir"/>
		    <transactionManagerLookup class="net.sf.ehcache.transaction.manager.DefaultTransactionManagerLookup"
		                              properties="jndiName=java:/TransactionManager" propertySeparator=";"/>
		    <cacheManagerEventListenerFactory class="" properties=""/>
		
		    <defaultCache
		            maxEntriesLocalHeap="0"
		            eternal="false"
		            timeToIdleSeconds="5"
		            timeToLiveSeconds="5">
		    </defaultCache>
		</ehcache>
        

#### Cache

		final String cacheName="test";
		final String keyStr = "hello";
		CacheManager manager = CacheManager.create(getClass().getResourceAsStream("/ehcache.xml"));
        if (!manager.cacheExists(cacheName)) {
            manager.addCache(cacheName);
        }
        Cache cache = manager.getCache(cacheName);        

        //add
        Element element = new Element(keyStr, user);
        //如果设置了external为true则ehcache.xml配置的time失效
		//element.setEternal(true);
		//element.setTimeToLive(5);
		//element.setTimeToIdle(2);
        cache.put(element);

       	cache.get(keyStr);
        cache.isKeyInCache(keyStr);