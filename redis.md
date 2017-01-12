## Redis安装运行

		wget -c http://download.redis.io/releases/redis-x.y.z.tar.gz
		tar zxf redis-x.y.z.tar.gz
		cd redis-x.y.z
		make && make install

#### Redis Java Client Library
+ [Jedis](https://github.com/xetorthio/jedis)

#### Java Client Call
+ 使用

		JedisPool pool=new JedisPool("127.0.0.1",6379);
		Jedis client=pool.getResource();
		client.XXX;
		pool.returnResource(client);

#### Scala Client Call
+ 封装

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



