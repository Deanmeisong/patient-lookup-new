package patient.lookup

import grails.gorm.transactions.Transactional
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

    @Transactional
    public String importGenotypingResults(String data) throws ParseException {

        StringBuilder sbReport = new StringBuilder()

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
                        String[] plateDetails = columns[1]?.trim()
                        String assayName = columns[1]?.trim()
                        String genotypingResult = columns[2]?.trim()

                        String firstName = columns[0]?.trim()
                        String lastName = columns[1]?.trim()
                        String dateOfBirthString = columns[2]?.trim()
                        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                        Date dateOfBirth = df.parse(dateOfBirthString);
                        Long patientDbId = Long.parseLong(columns[3]?.trim());

                        def lp = new LookupPerson()
                        lp.firstName = firstName
                        lp.lastName = lastName
                        lp.dateOfBirth = dateOfBirth
                        lp.patientDbId = patientDbId

                        lookupPersonService.save(lp)

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
                    } else {
                        String msg = "Data must have at least 4 columns on row #"+rowIndexDisplay
                        throw new ParseException(msg, i)
                    }

                }
            }
            sbReport.append("\nCompleted")

        }
        return sbReport.toString()

    }


}
