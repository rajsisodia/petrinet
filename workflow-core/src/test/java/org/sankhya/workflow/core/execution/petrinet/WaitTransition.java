package org.sankhya.workflow.core.execution.petrinet;

import java.util.concurrent.TimeUnit;

import org.sankhya.workflow.core.petrinet.engine.AbstractTransition;
import org.sankhya.workflow.core.petrinet.execution.ExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WaitTransition extends AbstractTransition {

	private final static Logger logger = LoggerFactory.getLogger(LoggingTransition.class);
	private final int waitTime;

	public WaitTransition(String name, int waitTime) {
		super(name);
		this.waitTime = waitTime;
	}

	@Override
	public void trigger(ExecutionContext context) {
		try {
			logger.debug("Started waiting at {}", getName());
			TimeUnit.MILLISECONDS.sleep(waitTime);
			logger.debug("Stopped waiting at {}", getName());
			this.postTrigger(context);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
