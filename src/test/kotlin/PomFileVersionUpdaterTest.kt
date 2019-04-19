import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File
import java.nio.file.Paths

class PomFileVersionUpdaterTest {

    @Rule
    @JvmField
    val tempFolder = TemporaryFolder()

    lateinit var pomFileVersionUpdater: PomFileVersionUpdater

    @Before
    fun setUp() {
        val xmlParser = XmlParser()
        val mavenPomVersionFinder = MavenPomVersionFinder()
        val mavenPomVersionUpdater = MavenPomVersionUpdater()

        // fixme version shouldn't be injected by constructor ?
        val mavenPomGenerator = MavenFixedVersionGenerator("3.0.0")

        pomFileVersionUpdater =
            PomFileVersionUpdater(xmlParser, mavenPomVersionFinder, mavenPomGenerator, mavenPomVersionUpdater)
    }

    @Test
    fun shouldUpdatePomVersion() {
        // given
        val testPomFile = File("src/test/resources/pom.xml")
        testPomFile.copyTo(Paths.get(tempFolder.root.absolutePath, "pom.xml").toFile())

        val expectedPomFile = File("src/test/resources/updatedPomVersion/pom.xml")
        val expectedPomFileContent = expectedPomFile.readText()

        val actualPomFile = File(Paths.get(tempFolder.root.absolutePath, "pom.xml").toString())

        // when
        pomFileVersionUpdater.updatePomVersion(actualPomFile)

        // then
        val actualPomFileContent = actualPomFile.readText()
        Assert.assertEquals(expectedPomFileContent, actualPomFileContent)
    }
}
