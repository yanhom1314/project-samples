## Redis安装运行

		wget -c http://download.redis.io/releases/redis-2.8.1.tar.gz
		tar zxf redis-2.8.1.tar.gz
		cd redis-2.8.1
		make && make install

#### Redis Java Client
+ [Jedis](https://github.com/xetorthio/jedis)
+ 使用Jedis

		Jedis client=new Jedis("127.0.0.1",6379);
		client.XXX;
+ 适用JedisPool

		JedisPool pool=new JedisPool("127.0.0.1",6379);
		Jedis client=pool.getResource();
		client.XXX;
		pool.returnResource(client);

#### Redis Scala Client

+ 封装工具
		case class RedisUtilPool(pool: JedisPool) {
		  def withClient(call: (Jedis) => Unit) {
		    val client = pool.getResource
		    try {
		      call(client)
		    } finally {
		      pool.returnResourceObject(client)
		    }
		  }
		}

		object RedisUtilPool {
		  private val POOL = mutable.HashMap[(String, Int), JedisPool]()

		  def apply(host: String, port: Int): RedisUtilPool = {
		    val config = new JedisPoolConfig
		    config.setMaxActive(20)
		    config.setMaxIdle(5)
		    config.setMaxWait(10001)
		    config.setTestOnBorrow(false)
		    apply(config, host, port)
		  }

		  def apply(config: JedisPoolConfig, host: String, port: Int): RedisUtilPool = {

		    val key = (host, port)
		    if (!POOL.contains(key)) {
		      POOL += (key -> new JedisPool(config, host, port))
		    }
		    RedisUtilPool(POOL(key))
		  }
		}			

+ 使用

		val pool = RedisUtilPool("127.0.0.1", 6379)
		pool.withClient {
	        client =>
    		      client.get("key")
      	}



