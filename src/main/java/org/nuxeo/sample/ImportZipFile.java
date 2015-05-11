/**
 * 
 */

package org.nuxeo.sample;

import java.io.File;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;
import org.nuxeo.ecm.automation.core.Constants;
import org.nuxeo.ecm.automation.core.annotations.Context;
import org.nuxeo.ecm.automation.core.annotations.Operation;
import org.nuxeo.ecm.automation.core.annotations.OperationMethod;
import org.nuxeo.ecm.automation.core.annotations.Param;
import org.nuxeo.ecm.automation.core.collectors.DocumentModelCollector;
import org.nuxeo.ecm.automation.core.collectors.BlobCollector;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.DocumentRef;
import org.nuxeo.ecm.core.io.DocumentPipe;
import org.nuxeo.ecm.core.io.DocumentReader;
import org.nuxeo.ecm.core.io.DocumentWriter;
import org.nuxeo.ecm.core.io.impl.DocumentPipeImpl;
import org.nuxeo.ecm.core.io.impl.plugins.DocumentModelWriter;
import org.nuxeo.ecm.core.io.impl.plugins.DocumentTreeReader;
import org.nuxeo.ecm.core.io.impl.plugins.NuxeoArchiveWriter;
import org.nuxeo.ecm.core.io.impl.plugins.ZipReader;

/**
 * @author harlan
 */
@Operation(id=ImportZipFile.ID, category=Constants.CAT_SERVICES, label="ImportZipFile", description="")
public class ImportZipFile {

    public static final String ID = "ImportZipFile";

    @Param(name = "source", required = true)
    protected String source;
    
    @Param(name = "target", required = true)
    protected String target;
    
    @Context
    protected CoreSession session;

    private static final Log log = LogFactory.getLog(ImportZipFile.class);
    
    @OperationMethod
    public void run() {
       
    	DocumentReader reader = null;
    	DocumentWriter writer = null;
    	try {
    	  reader = new ZipReader(new File(source));
    	  log.debug("reader is "+reader.toString());
    	  writer = new DocumentModelWriter(session, target);
    	  log.debug("writer is "+writer.toString());
    	         
    	  // creating a pipe
    	  DocumentPipe pipe = new DocumentPipeImpl(10);
    	  pipe.setReader(reader);
    	  pipe.setWriter(writer);
    	  log.debug("running pipe");
    	  pipe.run();
    	  log.debug("done");
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e,e);
		} finally {
    	  if (reader != null) {
    	    reader.close();
    	  }
    	  if (writer != null) {
    	    writer.close();
    	  }
    	}
    	
    }    

}
