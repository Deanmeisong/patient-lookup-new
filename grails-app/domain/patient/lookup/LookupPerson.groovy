package patient.lookup

import grails.neo4j.Neo4jEntity
import org.apache.log4j.Logger
import org.springframework.beans.factory.annotation.Autowired

class LookupPerson implements Serializable {

    Logger logger = Logger.getLogger(LookupPerson.class)
    static auditable = true

    static constraints = {
        encryptedFirstName(unique: ['encryptedLastName', 'encryptedDateOfBirth'])
        encryptedDateOfBirth(nullable:true)

        firstName bindable: true
        lastName bindable: true
        dateOfBirth bindable: true
        patientDbId bindable: true
    }

//    static mapping = {
//        autowire true
//    }

//    static mapping = {
//        id generator: 'assigned', name: 'encryptedPatientDbId'
//    }



    static transients=['firstName', 'lastName', 'dateOfBirth', 'patientDbId', 'securityService', 'logger']

    // For display purposes
    String firstName
    String lastName
    Date dateOfBirth
    Long patientDbId

    // For database purposes
    Long id
    String encryptedFirstName
    String encryptedLastName
    String encryptedDateOfBirth
    String encryptedPatientDbId
    Date dateCreated

//    def beforeInsert() {
//        logger.info("beforeInsert")
//        encrypt()
//        return true
//    }
//
//    def beforeUpdate() {
//        logger.info("beforeUpdate")
//        encrypt()
//        return true
//    }

//    def beforeValidate() {
//        logger.info("beforeValidate: securityService: "+securityService)
//        encrypt()
//        return true
//    }

    public void encrypt(SecurityService securityService) {

        if (firstName) {
            encryptedFirstName = securityService.encrypt(firstName)
        }

        if (lastName) {
            encryptedLastName = securityService.encrypt(lastName)
        }

        if (dateOfBirth) {
            //logger.info("encrypting date of birth: "+dateOfBirth)
            //encryptedDateOfBirth = securityService.encrypt(BigInteger.valueOf(dateOfBirth.time.toLong()))
            encryptedDateOfBirth = securityService.encrypt(dateOfBirth.time.toLong().toString())
            //BigInteger decryptedDateOfBirth = securityService.decrypt(encryptedDateOfBirth)
            //logger.info("decryptedDateOfBirth: "+decryptedDateOfBirth)
        }

        if (patientDbId) {
            encryptedPatientDbId = securityService.encrypt(patientDbId.toString())
        }
    }

    public void decrypt(SecurityService securityService) {
        if (encryptedFirstName) {
            firstName = securityService.decrypt(encryptedFirstName)
        }

        if (encryptedLastName) {
            lastName = securityService.decrypt(encryptedLastName)
        }

        if (encryptedDateOfBirth) {
            dateOfBirth = new Date(securityService.decrypt(encryptedDateOfBirth).toLong())
        }

        if (encryptedPatientDbId) {
            patientDbId = securityService.decrypt(encryptedPatientDbId).toLong()
        }

    }

}
