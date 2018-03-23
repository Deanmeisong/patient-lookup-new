package patient.lookup

import grails.gorm.services.Service
import grails.gorm.transactions.Transactional
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired

/**
 * LookupPersonService implementation
 * @author Philip Wu
 */
@Service(LookupPerson)
@Transactional
abstract class LookupPersonService implements ILookupPersonService {

    Logger logger = Logger.getLogger(LookupPersonService.class)

    @Autowired
    SecurityService securityService

    LookupPerson save(LookupPerson lookupPerson) {
        LookupPerson.withTransaction {
            if (lookupPerson) {
                lookupPerson.encrypt(securityService)
                boolean saved = lookupPerson.save(flush: true)
                logger.info("saved: " + saved)
                if (!saved) {
                    logger.error(lookupPerson.errors)
                }
            }
        }
    }

}
