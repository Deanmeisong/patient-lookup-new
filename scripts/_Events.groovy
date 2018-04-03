import java.text.SimpleDateFormat

eventCompileStart = { arg ->

    Date date = new Date()
    SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy.MM.dd")

    String buildNumber = dateFormatter.format(date)

    metadata.'app.buildNumber' = buildNumber
    metadata.persist()

    println "**** Compiling with build number: ${buildNumber}"

}