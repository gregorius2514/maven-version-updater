import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import java.lang.RuntimeException
import java.util.regex.Pattern
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory
import javax.xml.transform.stream.StreamResult
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.TransformerFactory

fun main(args: Array<String>) {
   
    if (args.size != 1) {
       throw RuntimeException("Required one argument with maven project path") 
    }
    
    File(args[0]).walk().forEach { file ->
        if ("pom.xml".equals(file.name))
            println(file)
    }

    val xmlFile = File("src/main/resources/pom.xml")

    val domXmlFactory = DocumentBuilderFactory.newInstance()
    val domXmlBuilder = domXmlFactory.newDocumentBuilder()
    val xmlInputStream = InputSource(StringReader(xmlFile.readText()))
    val xmlDom = domXmlBuilder.parse(xmlInputStream)


    val xpathFactory = XPathFactory.newInstance()
    val xpath = xpathFactory.newXPath()

    val pomVersionRegex = "//version"
    val pomVersionElement = xpath.evaluate(pomVersionRegex, xmlDom, XPathConstants.NODESET) as NodeList
    val projectVersion = pomVersionElement.item(0).textContent

    val searchVersionRegex = Pattern.compile("([0-9.]+)(-SNAPSHOT)?")
    val matcher = searchVersionRegex.matcher(projectVersion)

    matcher.find()
    val version = matcher.group(1)

    if (version.contains(".")) {
        val versions = version.split(".")
        val mainorVersion = versions.get(versions.size - 1).toInt()

        val nextVersion = version.replace(".$mainorVersion", ".${mainorVersion.inc()}")
        val nextProjectVersion = projectVersion.replace(version, nextVersion)
        println("next: $nextProjectVersion")

        pomVersionElement.item(0).textContent = nextProjectVersion

        val xformer = TransformerFactory.newInstance().newTransformer()
        xformer.transform(DOMSource(xmlDom), StreamResult(xmlFile))
    } else {
        println("dots not detected")
    }

    println("version number: $projectVersion, version: $version")

}
