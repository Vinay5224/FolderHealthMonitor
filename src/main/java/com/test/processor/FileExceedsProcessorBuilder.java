package com.test.processor;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.test.Routes.ArchiveRouteBuilder;
import com.test.utils.HealthConstants;

/**
 * @author VINAY
 *
 */
public class FileExceedsProcessorBuilder implements Processor {

    private static final Logger LOGGER = Logger.getLogger(FileExceedsProcessorBuilder.class);
	
	@Override
	public void process(Exchange exchange) throws Exception {
		if(new File(HealthConstants.SECURED).exists()) {
			long size = (FileUtils.sizeOfDirectory(new File(HealthConstants.SECURED))/HealthConstants.MEGABYTE);
			LOGGER.debug("Secured Folder Size :  "+size+" Mb");
			if(size >= 50) {
				CamelContext archiveCtx = new DefaultCamelContext();
				archiveCtx.addRoutes(new ArchiveRouteBuilder());
				archiveCtx.start();
			}
		}
	}

}
