import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

fun main(args: Array<String>) {
    val xmlFile = File("src/main/resources/pom.xml")

    val dbFactory = DocumentBuilderFactory.newInstance()
    val dBuilder = dbFactory.newDocumentBuilder()
    val xmlInput = InputSource(StringReader(xmlFile.readText()))
    val doc = dBuilder.parse(xmlInput) 
    
    
    val xpFactory = XPathFactory.newInstance()
    val xPath = xpFactory.newXPath()

    val xpath = "//version"

    val itemsTypeT1 = xPath.evaluate(xpath, doc, XPathConstants.NODESET) as NodeList
    println("version number: " + itemsTypeT1.item(0).textContent))

}
