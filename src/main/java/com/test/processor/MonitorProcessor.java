package com.test.processor;

import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import com.test.Routes.ArchiveRouteBuilder;
import com.test.utils.HealthConstants;

/**
 * @author VINAY
 *
 */
public class MonitorProcessor implements Processor {
	
	private static final Logger LOGGER = Logger.getLogger(MonitorProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		LOGGER.debug("MonitorScheduler Processor....");
		double size = (FileUtils.sizeOfDirectory(new File(HealthConstants.SECURED))/HealthConstants.MEGABYTE);
		LOGGER.info("Current Size of 'SECURED' FOLDER : "+size+" Mb");
		LOGGER.info("Number of Files Archive : "+ArchiveRouteBuilder.fileNames.size());
		ArchiveRouteBuilder.deletedFileNames.forEach(s -> LOGGER.info("Deleted File Name : "+s));
		LOGGER.debug("Resetting All Values...");
		//Writing to output file
	/*	FileWriter file = new FileWriter(new File(TestConstants.outputPath));
		file.write(new Date().toString());
		file.write("\n Current Size of 'SECURED' FOLDER : "+size+" Mb");
		file.write("Number of Files Archive : "+ArchiveRouteBuilder.fileNames.size());
		file.write("\n Deleted File names : \n"+ArchiveRouteBuilder.deletedFileNames.toString()); */
		ArchiveRouteBuilder.fileNames.clear();
		ArchiveRouteBuilder.deletedFileNames.clear();

		
	}

}
