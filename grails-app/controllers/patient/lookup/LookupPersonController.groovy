package patient.lookup

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.apache.log4j.Logger

import java.text.ParseException
import java.util.*
import static org.springframework.http.HttpStatus.*

@Secured(value=["ROLE_ADMIN", "IS_AUTHENTICATED_FULLY"])
class LookupPersonController {

    Logger logger = Logger.getLogger(LookupPersonController.class)
    ILookupPersonService lookupPersonService
    SpringSecurityService springSecurityService
    SecurityService securityService
    SearchService searchService
    PopulateService populateService
    //static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
    def populateInstruction() {

    }
    def populatePatientsInfo() {

    }
    def doPopulatePatientsInfo() {
        List<LookupPerson> persons = new ArrayList<LookupPerson>()
        try {
            persons = populateService.importLookupPersons(params.data)
        } catch(ParseException e1) {
            logger.info("message exceptio is: "+ e1.getMessage())
            flash.message = e1.getMessage()
        }
        if(persons.size()>0) {
            for(LookupPerson lp: persons) {
                logger.info("the person is: "+lp.firstName)
            }
            populateService.savePersons(persons)
        }
        render(view: "showSearch", model: [lookupPersonList: persons, lookupPersonCount: persons.size()])
    }
    def search() {}
    def testAuditRead() {
        def auditList = AuditTrail.findByEventName("Read")
        for(AuditTrail at : auditList) {


        }
    }
    def showSearch() {
        def user = springSecurityService.currentUser
        logger.info("doing the search now...")
        logger.info("the user is: " + user)
        if(params) {
            PatientSelectionCriteria ps = createPatientSelectionCriteria(params)
            List<LookupPerson> lookupPersonList = new ArrayList<LookupPerson>()
            if(ps) {
                logger.info("ps from controller is: "+ps)
                lookupPersonList = searchService.getLookupPersons(ps)
                for (LookupPerson lp: lookupPersonList) {
                    lp.decrypt(securityService)
                    def auditTrail = new AuditTrail()
                    auditTrail.lastUpdated = new Date()
                    auditTrail.actor = user.username
                    auditTrail.className = lp.getClass().getSimpleName()
                    auditTrail.eventName = "Read"
                    auditTrail.persistedObjectId = lp.id.toString()
                    boolean isSave = auditTrail.save()
                    logger.info("persistedObjectId is: "+auditTrail.persistedObjectId)
                    logger.info("eventName is: "+auditTrail.eventName)
                    logger.info("className is: "+auditTrail.className)
                    logger.info("isSave auditTrail: "+ isSave)
                    logger.info("firstName is: "+lp.firstName+" and lastName is: "+lp.lastName+"patientDbId is: "+lp.patientDbId)
                }
            }
            render(view: "showSearch", model: [lookupPersonList: lookupPersonList, lookupPersonCount: lookupPersonList.size()])
        } else {
                flash.message("pra")
        }

    }


    private PatientSelectionCriteria createPatientSelectionCriteria(Map params) {
        logger.info("params in PatientSelectionCriteria are: " + params)
        PatientSelectionCriteria ps = new PatientSelectionCriteria()
        if(params.firstName) {
            ps.firstName = params.firstName
        }
        if(params.lastName) {
            ps.lastName = params.lastName
        }
        if(params.patientDbId) {
            logger.info("params.patientDbId"+params.patientDbId)
            logger.info("instanceof" + params.patientDbId.getClass())
            ps.patientDbId = Long.valueOf(params.patientDbId)
            logger.info("ps.patientDbId"+ps.patientDbId)

        }
        return ps
    }

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)

        def list = lookupPersonService.list(params)
        for (LookupPerson lp: list) {
            lp.decrypt(securityService)
        }

        respond list, model:[lookupPersonCount: lookupPersonService.count()]
    }

    def show(Long id) {
        respond lookupPersonService.get(id)
    }

    def create() {
        respond new LookupPerson(params)
    }
    def saveEdit() {

    }
    def save(LookupPerson lookupPerson) {
        logger.info("this is the right controller!")
        logger.info("this is the right controller2222!")

        logger.info("lookupPersonService.isDuplicate(lookupPerson)1")
        if (lookupPerson == null) {
            notFound()
            return
        }
        logger.info("lookupPersonService.isDuplicate(lookupPerson)2")
        logger.info("lookupPersonService.isDuplicate(lookupPerson)"+lookupPersonService.isDuplicate(lookupPerson))
        if(!lookupPersonService.isDuplicate(lookupPerson)) {
            try {
                lookupPersonService.save(lookupPerson)
            } catch (ValidationException e) {
                respond lookupPerson.errors, view:'create'
                return
            }
            flash.message= "The lookupPerson ${lookupPerson.firstName} has been created."

        }else {
            flash.message = "The lookupPerson is already existent!!!!!!!"
        }
        redirect(action: "create")
    }

    def edit(Long id) {
        respond lookupPersonService.get(id)
    }
    def editLookupPerson() {
        logger.info("params.lookupPersonId"+params.lookupPersonId)
        int lookupPersonId = Integer.valueOf(params.lookupPersonId)
        def lookupPersonInstance = LookupPerson.get(lookupPersonId)
        lookupPersonInstance.decrypt(securityService)

        render(view: "editLookupPerson", model: [lookupPerson: lookupPersonInstance])

    }
    def update(LookupPerson lookupPerson) {
        if (lookupPerson == null) {
            notFound()
            return
        }

        try {
            lookupPersonService.save(lookupPerson)
        } catch (ValidationException e) {
            respond lookupPerson.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lookupPerson.label', default: 'LookupPerson'), lookupPerson.id])
                redirect lookupPerson
            }
            '*'{ respond lookupPerson, [status: OK] }
        }
    }

    def delete() {
        logger.info("params.lookupPersonId is: " + params.lookupPersonId)
        if (params.lookupPersonId == null) {
            notFound()
            return
        }

        int lookupPersonId = Integer.valueOf(params.lookupPersonId)
        def lookupPerson = LookupPerson.findById(lookupPersonId)
        lookupPerson.decrypt(securityService)
        logger.info(lookupPerson.firstName + "has been deleted.")
        flash.message = lookupPerson.firstName + "has been deleted."
        lookupPersonService.delete(params.lookupPersonId)
        redirect(action: "search")
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lookupPerson.label', default: 'LookupPerson'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
