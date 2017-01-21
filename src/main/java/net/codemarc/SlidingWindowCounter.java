package net.codemarc;

import org.json.JSONObject;

import restx.annotations.GET;
import restx.annotations.RestxResource;
import restx.factory.Component;
import restx.security.PermitAll;

@Component 
@RestxResource
public class SlidingWindowCounter {

	public SlidingWindowCounter() {
		// Initialize and startup the sliders
		SWC.getInstance();
	}

    
	@GET(SWC.version+"/increment")
	@PermitAll 
	public void increment() {
		SWC.inc();
	}

	@GET(SWC.version+"/numLastSecond")
	@PermitAll 
	public Long numLastSecond() {
		return SWC.getLastSec();
	}

	@GET(SWC.version+"/numLastMinute")
	@PermitAll 
	public Long numLastMinute() {
		return SWC.getLastMinute();		
	}
	
	@GET(SWC.version+"/numLastHour")
	@PermitAll 	
	public Long numLastHour() {
		return SWC.getLastHour();
	}
	
	@GET(SWC.version+"/count")	
	@PermitAll 	
	public String count() {
		JSONObject jo=new JSONObject();
		jo.put("LastHour", numLastHour());
		jo.put("LastMinute", numLastMinute());
		jo.put("LastSecond", numLastSecond());
		return jo.toString(2);
	}
	
	
}
