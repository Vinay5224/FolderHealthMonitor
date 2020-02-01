package com.test.processor;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;
import com.test.Routes.FileMover;

/**
 * @author VINAY
 *
 */
public class FileMoverProcessor implements Processor{

	private static final Logger LOGGER = Logger.getLogger(FileMoverProcessor.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("FileMoveProcessor Started...");
		CamelContext filemvCtx = new DefaultCamelContext();
		filemvCtx.addRoutes(new FileMover());
		filemvCtx.start();
	}

}
