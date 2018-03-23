package patient.lookup

import grails.gorm.transactions.Transactional
import org.apache.log4j.Logger
import grails.plugin.springsecurity.annotation.Secured

@Secured(value=["ROLE_ADMIN", "IS_AUTHENTICATED_FULLY", "IS_AUTHENTICATED_ANONYMOUSLY"])
@Transactional
class LookupPersonTestController {

    Logger logger = Logger.getLogger(LookupPersonTestController.class)

    SecurityService securityService

    def index() {
        logger.info("creating person")


        LookupPerson p = new LookupPerson()
        p.firstName="Mia"
        p.lastName="Wu"
        p.dateOfBirth= new Date()
        p.patientDbId = 789L
        p.encrypt(securityService)

        LookupPerson.withTransaction {
            boolean saved = p.save()
            logger.info("saved: " + saved)

            logger.info("person: " + p.properties)

            if (!saved) {
                logger.info("errors: " + p.errors)
            }
        }

        logger.info("id: "+p.id)

    }
}
