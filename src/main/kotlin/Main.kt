import java.io.File

fun main(args: Array<String>) {

    if (args.size != 1) {
        throw RuntimeException("Required one argument with maven project path")
    }

//    todo Move belowe code to separate class. Then it can be tested easier.
    val versionParamater = args.toMutableList().stream().filter { aaa -> aaa.contains("-setVersion=") }
        .map { bbb -> bbb.replace("-setVersion=", "") }.findAny()

    val xmlParser = XmlParser()
    val mavenPomVersionFinder = MavenPomVersionFinder()
    val mavenPomVersionUpdater = MavenPomVersionUpdater()

    // fixme Improve readability initialization mavenVersionGenerator
    val mavenVersionGenerator = versionParamater.map { pomFixedVersion -> MavenFixedVersionGenerator(pomFixedVersion) }
        .map { abc -> MavenPomNextVersionGenerator() }.orElseThrow()

    File(args[0]).walk().forEach { pomXmlFile ->
        if ("pom.xml".equals(pomXmlFile.name)) {
            println("Checked pom.xml file: ${pomXmlFile.absolutePath}")

            val xmlDom = xmlParser.parseXml(pomXmlFile)
            val pomVersionElement = mavenPomVersionFinder.findPomVersionTag(xmlDom)
            val currentPomVersion = mavenPomVersionFinder.getPomVersion(pomVersionElement)

            val nextPomVersion = mavenVersionGenerator.generateNextPomVersion(currentPomVersion)
            if (nextPomVersion.isNotBlank()) {
                mavenPomVersionUpdater.updateNotEmptyPomVersion(pomVersionElement, nextPomVersion)

                mavenPomVersionUpdater.rewritePomFileWithUpdatedPomVersion(xmlDom, pomXmlFile)
                println("current pom version: $currentPomVersion, next pom version: $nextPomVersion, file path: $pomXmlFile")
            }
        }
    }
}

