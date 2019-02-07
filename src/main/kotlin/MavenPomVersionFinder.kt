import org.w3c.dom.Document
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import javax.xml.xpath.XPath
import javax.xml.xpath.XPathConstants
import javax.xml.xpath.XPathFactory

class MavenPomVersionFinder {

    private var xpath: XPath

    init {
        val xpathFactory = XPathFactory.newInstance()
        xpath = xpathFactory.newXPath()
    }

    fun findPomVersionTag(xmlDom: Document): Node {
        val pomVersionRegex = "//version"
        val pomVersionElement = xpath.evaluate(pomVersionRegex, xmlDom, XPathConstants.NODESET) as NodeList
        return pomVersionElement.item(0)
    }

    fun getPomVersion(pomVersionTag: Node): String {
        return pomVersionTag.textContent
    }
}
