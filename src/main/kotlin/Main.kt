import java.io.File

fun main(args: Array<String>) {

    if (args.size != 1) {
        throw RuntimeException("Required one argument with maven project path")
    }
    val xmlParser = XmlParser()
    val mavenPomVersionFinder = MavenPomVersionFinder()
    val mavenVersionGeneratorStrategy = MavenVersionGeneratorStrategy()
    val mavenPomVersionUpdater = MavenPomVersionUpdater()

    File(args[0]).walk().forEach { pomXmlFile ->
        if ("pom.xml".equals(pomXmlFile.name)) {
            println("Checked pom.xml file: ${pomXmlFile.absolutePath}")

            val xmlDom = xmlParser.parseXml(pomXmlFile)
            val pomVersionElement = mavenPomVersionFinder.findPomVersionTag(xmlDom)
            val currentPomVersion = mavenPomVersionFinder.getPomVersion(pomVersionElement)

            val mavenVersionGenerator = mavenVersionGeneratorStrategy.createMavenVersionGenerator()
            val nextPomVersion = mavenVersionGenerator.generateNextPomVersion(currentPomVersion)
            if (nextPomVersion.isNotBlank()) {
                mavenPomVersionUpdater.updateNotEmptyPomVersion(pomVersionElement, nextPomVersion)

                mavenPomVersionUpdater.rewritePomFileWithUpdatedPomVersion(xmlDom, pomXmlFile)
                println("current pom version: $currentPomVersion, next pom version: $nextPomVersion, file path: $pomXmlFile")
            }
        }
    }
}

