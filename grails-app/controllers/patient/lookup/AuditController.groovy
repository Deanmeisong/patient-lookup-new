package patient.lookup

import ch.qos.logback.classic.Logger;
import grails.converters.JSON
import org.jboss.logging.Logger
import grails.plugin.springsecurity.SpringSecurityService

import grails.plugin.springsecurity.annotation.Secured
import grails.transaction.Transactional



@Secured(value=["ROLE_ADMIN", "IS_AUTHENTICATED_FULLY"])
@Transactional
class AuditController {
//    Logger logger = Logger.getLogger(AuditController.class)
    Logger logger = Logger.getLogger(AuditController.class)

    def index() { }
    def list() {
        def auditList = AuditTrail.findAll()
        render(view: "list", model: [auditList: auditList])

    }
    def listRead(){
        def auditReadList = AuditTrailForRead.findAll()
        render(view: "listRead", model: [auditReadList: auditReadList])
    }


    def getAuditLogging(){
        logger.info("params ->"+params)
        String previousPageId = null
        if(params.analysisRequestId != null){
            previousPageId = params.analysisRequestId.toString()
        }
        if(params.className && params.objectId && params.previousController){

            def auditLoggingResults = []

            //filter format{filter:[{className:value}, {className:value}, {className:value}]}
            if(params.filter){
                String filterValue = params.filter.toString()
                logger.info("filterValue ->" +filterValue + "filterValueSize" +filterValue.size())
                def fliterValueList = JSON.parse(filterValue)
//				logger.info("fliterValueList ->" +fliterValueList + "fliterValueList" +fliterValueList?.size()+"filter size -> "+fliterValueList.filter?.size())

                for(def entry : fliterValueList.filter){
                    logger.info("value ->" + entry)

                    Set keys = entry.keySet();
                    Iterator a = keys.iterator();
                    while(a.hasNext()) {
                        String key = (String)a.next();
                        // loop to get the dynamic key
                        String value = (String)entry.get(key);
                        logger.info("key ->" + key + ' ->value ->' +value)
                        auditLoggingResults.addAll( AuditTrail.findAllByClassNameAndPersistedObjectId(key, value, [sort: "lastUpdated", order: "desc"]))

                    }

                }
            }


            String previousController = params.previousController.toString()
            String id= params.objectId.toString()
            String className = params.className.toString().trim()
            logger.info("params.object.toString()->"+params.objectId.toString() +"className ->"+className)

            auditLoggingResults.addAll( AuditTrail.findAllByClassName(className, id, [sort: "lastUpdated", order: "desc"]))
            def totalResults = auditLoggingResults.size()
//            logger.info("className ->"+className)
            logger.info("totalResults ->"+totalResults)
            logger.info("controller ->"+previousController)



            render(view : "list", model: [auditHistory: auditLoggingResults, showId: id, previousPageId:previousPageId, perviousController: previousController])
        }
        return
    }
}
