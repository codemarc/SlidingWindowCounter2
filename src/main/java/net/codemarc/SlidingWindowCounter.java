package net.codemarc;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

@Component 
@RestxResource
public class SlidingWindowCounter {

	public static final Logger logger = LoggerFactory.getLogger("swc");
	public static final String version="/v1";
	private static SlidingWindowCounter singleton = null;

	private SlidingWindowCounter() {
	}

	public static SlidingWindowCounter getInstance() {
		if (singleton != null) {
			return singleton;
		}

		return open();
	}

	private synchronized static SlidingWindowCounter open() {
		if (singleton != null)
			return singleton;

		singleton = new SlidingWindowCounter();

		return singleton;
	}

    public JSONArray cause(Exception e) {
		JSONObject jo = new JSONObject();
		jo.put("error", e.getCause());
		return new JSONArray().put(jo);
		
    }
    
    private Long last(){
    	return 0L;
    }
    

	@GET(SlidingWindowCounter.version+"/increment")
	@PermitAll 
	public void increment() {
		
	}

	@GET(SlidingWindowCounter.version+"/numLastSecond")
	@PermitAll 
	public Long numLastSecond() {
		return last();
	}

	@GET(SlidingWindowCounter.version+"/numLastMinute")
	@PermitAll 
	public Long numLastMinute() {
		return last();		
	}
	
	@GET(SlidingWindowCounter.version+"/numLastHour")
	@PermitAll 	
	public Long numLastHour() {
		return last();
	}

}
