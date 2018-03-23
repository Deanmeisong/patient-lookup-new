package patient.lookup

import grails.plugin.springsecurity.SpringSecurityService
import grails.plugin.springsecurity.annotation.Secured
import grails.validation.ValidationException
import org.apache.log4j.Logger
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
    def populatePatientsInfo() {

    }
    def doPopulatePatientsInfo() {
        String importReport = populateService.importGenotypingResults(params.data)
        redirect(action: "search")
    }
    def search() {}

    def showSearch() {
        def user = springSecurityService.currentUser
        logger.info("doing the search now...")
        logger.info("the user is: " + user)
        if(params) {
            def auditTrailForRead = new AuditTrailForRead()
            auditTrailForRead.dateRead = new Date()
            auditTrailForRead.actor = user.username
            auditTrailForRead.query = params
            auditTrailForRead.save()
            logger.info("auditTrailForRead.actor" + auditTrailForRead.actor)
            PatientSelectionCriteria ps = createPatientSelectionCriteria(params)
            List<LookupPerson> lookupPersonList = new ArrayList<LookupPerson>()
            if(ps) {
                logger.info("ps from controller is: "+ps)
                lookupPersonList = searchService.getLookupPersons(ps)
                for (LookupPerson lp: lookupPersonList) {
                    lp.decrypt(securityService)
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
        if (lookupPerson == null) {
            notFound()
            return
        }

        try {
            lookupPersonService.save(lookupPerson)
        } catch (ValidationException e) {
            respond lookupPerson.errors, view:'create'
            return
        }
        redirect(action: "search")

//        request.withFormat {
//            form multipartForm {
//                flash.message = message(code: 'default.created.message', args: [message(code: 'lookupPerson.label', default: 'LookupPerson'), lookupPerson.id])
//                redirect lookupPerson
//            }
//            '*' { respond lookupPerson, [status: CREATED] }
//        }
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
