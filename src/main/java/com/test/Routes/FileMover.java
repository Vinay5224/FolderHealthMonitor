package com.test.Routes;

import org.apache.camel.builder.RouteBuilder;

import com.test.processor.FileExceedsProcessorBuilder;
import com.test.utils.HealthConstants;
import org.apache.log4j.Logger;

/**
 * @author VINAY
 *
 */
public class FileMover extends RouteBuilder {

	private static final Logger LOGGER = Logger.getLogger(FileMover.class);

	@Override
	public void configure() throws Exception {
		LOGGER.debug("FileMover....");
		from("file:" + HealthConstants.TMP + "?noop=true&recursive=true")
		.process(new FileExceedsProcessorBuilder())
		.to("file:" + HealthConstants.SECURED);
	}


}
