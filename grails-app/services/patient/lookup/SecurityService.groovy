package patient.lookup

import grails.gorm.transactions.Transactional
import org.apache.log4j.Logger
import org.jasypt.util.numeric.BasicIntegerNumberEncryptor
import org.jasypt.util.text.StrongTextEncryptor

@Transactional
class SecurityService {

    Logger logger = Logger.getLogger(SecurityService.class)

    private String jasyptPassword = 'rougueOne'

    def instantiateEncryptor(Object value) {
        if (value instanceof String) {
            StrongTextEncryptor encryptor = new StrongTextEncryptor()
            encryptor.password = jasyptPassword
            return encryptor
        } else if (value instanceof BigInteger) {
            BasicIntegerNumberEncryptor encryptor = new BasicIntegerNumberEncryptor()
            encryptor.password = jasyptPassword
            return encryptor
        } else {
            throw new Exception("Could not find a matching encryptor based on data type: "+value.class.name)
        }
    }

    def encrypt(Object value) {
        def encryptor = instantiateEncryptor(value)


        def encrypted = encryptor.encrypt(value)
        logger.info("encrypted class: "+encrypted.class.name)
        //def decrypted = instantiateEncryptor(value).decrypt(encrypted)
        //logger.info("decrypted: "+decrypted)

        return encrypted
    }

    def decrypt(Object value) {
        def decryptor = instantiateEncryptor(value)
        logger.info("decryptor: "+decryptor+" value class: "+value.class.name+ " value: "+value)

        return decryptor.decrypt(value)
    }


}
