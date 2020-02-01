package com.test.main;

import static org.junit.Assert.assertEquals;

import java.io.File;

import org.apache.camel.CamelContext;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;

import com.test.Routes.ArchiveRouteBuilder;

/**
 * @author VINAY
 *
 */
public class RouteBuilderTest {
	
	CamelContext cmlCtx = new DefaultCamelContext();
	
	@Test
	public void FileCopyTest() throws Exception {
		
		cmlCtx.addRoutes(new RouteBuilder() {

		@Override
		public void configure() throws Exception {
				
				from("file:"+TestConstants.TMP+"?noop=true")
				.to("file:"+TestConstants.SECURED);
			}
			
		});
		
		cmlCtx.start();
		Thread.sleep(20000);
		int fromSize = new File(TestConstants.TMP).list().length;
		int toSize = new File(TestConstants.SECURED).list().length;
		assertEquals("Checking Files Transferred Count ", fromSize, toSize);
		cmlCtx.stop();
	}
	
	@Test
	public void ExecTest() throws Exception {
		
		ArchiveRouteBuilder arc = new ArchiveRouteBuilder();
		
		arc.deleteExecFiles(TestConstants.SECURED, 0);
		assertEquals("Deleted File Count ", 4, arc.deletedFileNames.size());
		
		cmlCtx.addRoutes(new RouteBuilder() {
			
		@Override
		public void configure() throws Exception {
				from("file:"+TestConstants.SECURED+"?delete=true")
				.to("file:"+TestConstants.ARCHIVE);
			}
		});
		
		cmlCtx.start();
		Thread.sleep(20000);
		int archiveSize = new File(TestConstants.ARCHIVE).list().length;
		int securedSize = new File(TestConstants.SECURED).list().length;
		assertEquals("Secured File Should be zero ", 0, securedSize);
		assertEquals("Without Exec File Transferred count ", 3, archiveSize);
		cmlCtx.stop();
		
	}
	
}
