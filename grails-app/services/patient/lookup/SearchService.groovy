package patient.lookup

import grails.gorm.transactions.Transactional
import org.apache.log4j.Logger
import patient.lookup.PatientSelectionCriteria


@Transactional
class SearchService {
    Logger logger = Logger.getLogger(getClass())
    SecurityService securityService


    public List<LookupPerson> getLookupPersons(PatientSelectionCriteria ps) {
        List<LookupPerson> results = new ArrayList<LookupPerson>();
        results = LookupPerson.findAll()
        logger.info("PatientSelectionCriteria is: "+ps)
        if(ps.lastName && ps.firstName && ps.patientDbId) {
            for (LookupPerson lp : results) {
                lp.decrypt(securityService)
                if (lp.firstName.contains(ps.firstName) && lp.lastName.contains(ps.lastName) && lp.patientDbId.contains(ps.patientDbId)) {
                    logger.info("********************works!!!!")
                    resultsByLastNameAndFirstName.add(lp)
                }
            }
        } else {
            if(ps.lastName && ps.patientDbId) {
                List<LookupPerson> resultsByLastNameAndpatientDbId = new ArrayList<LookupPerson>()
                for (LookupPerson lp : results) {
                    lp.decrypt(securityService)
                    if (lp.lastName.contains(ps.lastName) && lp.patientDbId.contains(ps.patientDbId)) {
                        resultsByLastNameAndpatientDbId.add(lp)
                    }
                }
//            for (LookupPerson lp : resultsByLastNameAndFirstName) {
//                lp.decrypt(securityService)
//            }
                return resultsByLastNameAndpatientDbId
            }
            if(ps.firstName && ps.patientDbId) {
                List<LookupPerson> resultsByFirstNameAndpatientDbId = new ArrayList<LookupPerson>()
                for (LookupPerson lp : results) {
                    lp.decrypt(securityService)
                    if (lp.firstName.contains(ps.firstName) && lp.patientDbId.contains(ps.patientDbId)) {
                        resultsByFirstNameAndpatientDbId.add(lp)
                    }
                }
//            for (LookupPerson lp : resultsByLastNameAndFirstName) {
//                lp.decrypt(securityService)
//            }
                return resultsByFirstNameAndpatientDbId
            }
            if (ps.lastName && ps.firstName) {
                List<LookupPerson> resultsByLastNameAndFirstName = new ArrayList<LookupPerson>()
                for (LookupPerson lp : results) {
                    lp.decrypt(securityService)
                    if (lp.firstName.contains(ps.firstName) && lp.lastName.contains(ps.lastName)) {
                        resultsByLastNameAndFirstName.add(lp)
                    }
                }
//            for (LookupPerson lp : resultsByLastNameAndFirstName) {
//                lp.decrypt(securityService)
//            }
                return resultsByLastNameAndFirstName
            }
            if (ps.lastName&&!ps.firstName&&!ps.patientDbId) {
                List<LookupPerson> resultsByLastName = new ArrayList<LookupPerson>()
                for (LookupPerson lp : results) {
                    lp.decrypt(securityService)
                    if (lp.lastName.contains(ps.lastName)) {
                        resultsByLastName.add(lp)
                    }
                }
                for (LookupPerson lp : resultsByLastName) {
                    lp.decrypt(securityService)
                }
                return resultsByLastName
            }
            if (ps.firstName&&!ps.lastName&&!ps.patientDbId) {
                List<LookupPerson> resultsByFirstName = new ArrayList<LookupPerson>()
                for (LookupPerson lp : results) {
                    lp.decrypt(securityService)
                    if (lp.firstName.contains(ps.firstName)) {
                        resultsByFirstName.add(lp)
                    }
                }
                for (LookupPerson lp : resultsByFirstName) {
                    lp.decrypt(securityService)
                }
                return resultsByFirstName
            }
            if (ps.patientDbId&&!ps.firstName&&!ps.lastName) {
                List<LookupPerson> resultsByPatientDbId = new ArrayList<LookupPerson>()
                for (LookupPerson lp : results) {
                    lp.decrypt(securityService)
                    logger.info("***********only patientDbId*********works!!!!")
                    logger.info("patientDbIdlp is: "+lp.patientDbId)
                    logger.info("patientDbIdps is: "+ps.patientDbId)
                    if (lp.patientDbId.contains(ps.patientDbId)) {
                        logger.info("patientDbIdlp is: "+lp.patientDbId)
                        logger.info("patientDbIdps is: "+ps.patientDbId)

                        resultsByPatientDbId.add(lp)
                    }
                }
                for (LookupPerson lp : resultsByPatientDbId) {
                    lp.decrypt(securityService)
                }
                return resultsByPatientDbId
            }

        }

    }

}