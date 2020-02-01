package com.test.Routes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import com.test.utils.HealthConstants;

/**
 * @author VINAY
 *
 */
public class ArchiveRouteBuilder extends RouteBuilder{
	private static final Logger LOGGER = Logger.getLogger(ArchiveRouteBuilder.class);

	public static List<String> fileNames = new ArrayList<>();
	public static List<String> deletedFileNames = new ArrayList<>();
	public static List<String> exec = Arrays.asList(HealthConstants.EXEC_FORMATS.split("\\,"));
	
	@Override
	public void configure() throws Exception {
		LOGGER.debug("ArchiveRouteBuilder Starting to Transfer Files.....");
		deleteExecFiles(HealthConstants.SECURED, 0);
		from("file:"+HealthConstants.SECURED+"?delete=true&recursive=true")
		.process(new Processor() {
			@Override
			public void process(Exchange exchange) throws Exception {
				File file = (File) exchange.getIn().getBody(File.class);
				fileNames.add(file.getAbsolutePath());
			}
		})
		.to("file:"+HealthConstants.ARCHIVE);
		//FileUtils.deleteDirectory(new File(TestConstants.SECURED));
	}
	
	public static void deleteExecFiles(String dirPath, int level) {
		
        File dir = new File(dirPath);
        File[] firstLevelFiles = dir.listFiles();
        if (firstLevelFiles != null && firstLevelFiles.length > 0) {
            for (File aFile : firstLevelFiles) {
                if (aFile.isDirectory()) {
                    deleteExecFiles(aFile.getAbsolutePath(), level + 1);
                } else {
                    if(exec.contains(FilenameUtils.getExtension(aFile.getName()))) {
                    	LOGGER.debug("Deleted FileName  : "+aFile.getName());
                    	deletedFileNames.add(aFile.getName());
                    	FileUtils.deleteQuietly(new File(aFile.getAbsoluteFile().toString()));
                    }
                }
            }
        }
	}

}
