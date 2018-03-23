package patient.lookup

import grails.gorm.services.Service
import org.springframework.beans.factory.annotation.Autowired


interface ILookupPersonService {

    LookupPerson get(Serializable id)

    List<LookupPerson> list(Map args)

    Long count()

    void delete(Serializable id)

    LookupPerson save(LookupPerson lookupPerson)

}