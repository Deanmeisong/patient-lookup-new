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
    boolean isDuplicate(LookupPerson lookupPerson){
        logger.info("LookupPersonService isDuplicate Method")
        def lookupPersonList = LookupPerson.findAll();
        for(LookupPerson lp : lookupPersonList) {
            lp.decrypt(securityService)
            logger.info("lp.firstName: "+lp.firstName+"------------"+"lookupPerson.firstName: "+lookupPerson.firstName)
            logger.info("lp.lastName: "+lp.lastName+"------------"+"lookupPerson.lastName: "+lookupPerson.lastName)
            logger.info("lp.dateOfBirth: "+lp.dateOfBirth)
            if(lp.dateOfBirth.compareTo(lookupPerson.dateOfBirth)==0) {logger.info("date of birth equal")}

            if((lp.firstName.equals(lookupPerson.firstName))&&(lp.lastName.equals(lookupPerson.lastName))) {
                logger.info("It is a duplicate one!!!")
                return true;
            }
        }
        return false;
    }

}
