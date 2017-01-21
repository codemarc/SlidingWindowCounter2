package net.codemarc;
import java.util.ArrayDeque;

public class SWC {

	public static final String version = "/v1";

	private static final int ONESEC = 1000;
	private static final int ONEMIN = ONESEC * 60;
	private static final int ONEHOUR = ONEMIN * 60;

	private static SWC singleton = null;
	private static ArrayDeque<Long> sec = new ArrayDeque<Long>();
	private static ArrayDeque<Long> min = new ArrayDeque<Long>();
	private static ArrayDeque<Long> hour = new ArrayDeque<Long>();

	private SWC() {
		setTimeout(SWC::slidesec, ONESEC);
		setTimeout(SWC::slidemin, ONEMIN);
		setTimeout(SWC::slidehour, ONEHOUR);
	}

	public static SWC getInstance() {
		if (singleton == null) {
			synchronized (SWC.class) {
				if (singleton == null) {
					singleton = new SWC();
				}
			}
		}
		return singleton;
	}
	
	private static synchronized void slider(ArrayDeque<Long> aq,int tf) {
		if (aq.size() > 0) {
			long t = aq.pop().longValue() / tf;
			while (aq.size() > 0 && aq.peek().longValue() / tf == t)
				aq.pop();
		}
	}
	
	private static synchronized void slidesec() {
		slider(sec,ONESEC);
	}
	
	private static synchronized void slidemin() {
		slider(min,ONEMIN);
	}

	private static synchronized void slidehour() {
		slider(hour,ONEHOUR);		
	}

	private static void setTimeout(Runnable runnable, int delay) {
		new Thread(() -> {
			try {
				while (true) {
					Thread.sleep(delay);
					runnable.run();
				}
			} catch (Exception e) {
				System.err.println(e);
			}
		}).start();
	}

	public static synchronized void inc() {
		long t = System.currentTimeMillis();
		sec.add(t);
		min.add(t);
		hour.add(t);
	}

	public static Long getLastSec() {
		return (long) sec.size();
	}

	public static Long getLastMinute() {
		return (long) min.size();
	}

	public static Long getLastHour() {
		return (long) hour.size();
	}
}
