import org.w3c.dom.Document
import org.w3c.dom.Node
import java.io.File
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

class MavenPomVersionUpdater {

    private val xmlTransformer = TransformerFactory.newInstance().newTransformer()

    fun updateNotEmptyPomVersion(pomVersionTag: Node, nextPomVersion: String) {
        if (nextPomVersion.isNotBlank()) {
            pomVersionTag.textContent = nextPomVersion
        }
    }

    fun rewritePomFileWithUpdatedPomVersion(xmlDom: Document, xmlPomFile: File) {
        xmlTransformer.transform(DOMSource(xmlDom), StreamResult(xmlPomFile))
    }
}
