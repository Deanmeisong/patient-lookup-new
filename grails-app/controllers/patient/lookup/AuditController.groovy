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

            String previousController = params.previousController.toString()
            String objectId= params.objectId.toString()
            String className = params.className.toString().trim()
            logger.info("params.object.toString()->"+params.objectId.toString() +"className ->"+className)

            auditLoggingResults.addAll( AuditTrail.findAllByClassNameAndPersistedObjectId(className, objectId, [sort: "lastUpdated", order: "desc"]))
            def totalResults = auditLoggingResults.size()
//            logger.info("className ->"+className)
            logger.info("totalResults ->"+totalResults)
            logger.info("controller ->"+previousController)



            render(view : "list", model: [auditHistory: auditLoggingResults, showId: objectId, previousPageId:previousPageId, perviousController: previousController])
        }
        return
    }
}
