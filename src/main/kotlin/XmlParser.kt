import org.w3c.dom.Document
import org.xml.sax.InputSource
import java.io.File
import java.io.StringReader
import javax.xml.parsers.DocumentBuilder
import javax.xml.parsers.DocumentBuilderFactory

class XmlParser {

    private val domXmlBuilder: DocumentBuilder

    init {
        val domXmlFactory = DocumentBuilderFactory.newInstance()
        domXmlBuilder = domXmlFactory.newDocumentBuilder()
    }

    fun parseXml(xmlFile: File): Document {
        val xmlInputStream = InputSource(StringReader(xmlFile.readText()))
        return domXmlBuilder.parse(xmlInputStream)
    }
}
