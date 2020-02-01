package com.test.main;


import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.log4j.Logger;
import com.test.processor.FileMoverProcessor;
import com.test.processor.MonitorProcessor;
import com.test.utils.HealthConstants;

public class FolderHealthMonitor {

	private static final Logger LOGGER = Logger.getLogger(FolderHealthMonitor.class);

	public static void main(String[] args) {
		CamelContext context = new DefaultCamelContext();

		try {
			LOGGER.debug("File Transfer Route Builder Started");
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("timer://fileMover?fixedRate=true&period="+HealthConstants.SECURED_COPY_TIME_MS)
					.process(new FileMoverProcessor());
				}
			});
			
			LOGGER.debug("Monitor Scheduler Started");
			context.addRoutes(new RouteBuilder() {

				@Override
				public void configure() throws Exception {
					from("timer://monitor?fixedRate=true&period="+HealthConstants.SCHEDULER_TIME_MS)
					.process(new MonitorProcessor());
				}
			});
			
			
			while(true)
				context.start();

			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				context.stop();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
