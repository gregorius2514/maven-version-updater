import java.io.File

class PomFileVersionUpdater(
    private val xmlParser: XmlParser,
    private val mavenPomVersionFinder: MavenPomVersionFinder,
    private val mavenPomVersionGenerator: MavenPomNextVersionGenerator,
    private val mavenPomVersionUpdater: MavenPomVersionUpdater
) {

    fun updatePomVersion(pomXmlFile: File) {
        val xmlDom = xmlParser.parseXml(pomXmlFile)
        val pomVersionElement = mavenPomVersionFinder.findPomVersionTag(xmlDom)
        val currentPomVersion = mavenPomVersionFinder.getPomVersion(pomVersionElement)

        val nextPomVersion = mavenPomVersionGenerator.generateNextPomVersion(currentPomVersion)
        if (nextPomVersion.isNotBlank()) {
            println("Checked pom.xml file: ${pomXmlFile.absolutePath}")
            mavenPomVersionUpdater.updateNotEmptyPomVersion(pomVersionElement, nextPomVersion)

            mavenPomVersionUpdater.rewritePomFileWithUpdatedPomVersion(xmlDom, pomXmlFile)
            println("current pom version: $currentPomVersion, next pom version: $nextPomVersion, file path: $pomXmlFile")
        }
    }
}