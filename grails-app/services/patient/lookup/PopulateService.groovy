package patient.lookup

import grails.gorm.transactions.Transactional
import grails.validation.ValidationException
import org.apache.log4j.Logger

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat

@Transactional
class PopulateService {
    Logger logger = Logger.getLogger(getClass())
    ILookupPersonService lookupPersonService

    def serviceMethod() {

    }
    def savePersons(List<LookupPerson> persons) {

        for(LookupPerson lookupPerson : persons) {
            try {
                boolean isSave = lookupPersonService.save(lookupPerson)
                if(isSave) {
                    logger.info("-------------------------------------populate service the saved person is: " + lookupPerson.firstName)
                }
            } catch (ValidationException e) {
                logger.info("PopulateService savePersons: " + e.getMessage())
                flash.message = e.getMessage()
            }
        }

    }
    @Transactional
    public List<LookupPerson> importLookupPersons(String data) throws ParseException {

        List<LookupPerson> lpList = new ArrayList<LookupPerson>()
        if (data) {
            final Date TODAYS_DATE = new Date()

            // Keep track of what MouseObservations have already been saved.
            // This is to handle the case where genotyping results are repeated on the same plate for the same mouse for the same assay
            // Keep track of the last sequence number
            Map<String, Integer> alreadySavedMouseObservations = new HashMap<String, Integer>()

            // Parse the data into rows
            String[] rows = data.split("\\n")	// newline
            rows.eachWithIndex { row, i ->
                // For each row, split into columns
                if (row) {
                    int rowIndexDisplay = i+1
                    String[] columns = row.split("\\t", -1) // tab
                    if (columns.length >= 4 ) {    // We expect at least 3 columns

                        String firstName = columns[0]?.trim()
                        String lastName = columns[1]?.trim()
                        String dateOfBirthString = columns[2]?.trim()
                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                        Date dateOfBirth = df.parse(dateOfBirthString);
                        Long patientDbId = Long.parseLong(columns[3]?.trim());

                        // Validate the assay name
                        if (!firstName) {
                            String msg = "Missing first name for row #" + rowIndexDisplay
                            throw new ParseException(msg, i)
                        }

                        // Validate the genotyping result
                        if (!lastName) {
                            String msg = "Missing last name for row #" + rowIndexDisplay
                            throw new ParseException(msg, i)
                        }

                        if (!dateOfBirth) {
                            String msg = "dateOfBirth for row #" + rowIndexDisplay
                            throw new ParseException(msg, i)
                        }

                        if (!patientDbId) {
                            String msg = "patientDbId for row #" + rowIndexDisplay
                            throw new ParseException(msg, i)
                        }

                        def lp = new LookupPerson()
                        lp.firstName = firstName
                        lp.lastName = lastName
                        lp.dateOfBirth = dateOfBirth
                        lp.patientDbId = patientDbId

                        lpList.add(lp)

                    } else {
                        String msg = "Data must have at least 4 columns on row #"+rowIndexDisplay
                        throw new ParseException(msg, i)
                    }

                }
            }

        }

        return lpList

    }


}
