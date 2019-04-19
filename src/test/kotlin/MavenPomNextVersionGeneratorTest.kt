import junit.framework.Assert
import org.junit.Before
import org.junit.Test

class MavenPomNextVersionGeneratorTest {

    lateinit var mavenPomNextVersionGenerator: MavenPomNextVersionGenerator

    @Before
    fun setUp() {
        mavenPomNextVersionGenerator = MavenPomNextVersionGenerator()
    }

    @Test
    fun shouldGenerateNextVersion() {
        val nextVersion = mavenPomNextVersionGenerator.generateNextPomVersion("1.1.0-SNAPSHOT")
        Assert.assertEquals("1.1.1-SNAPSHOT", nextVersion)
    }

    @Test
    fun shouldGenerateNextVersion2() {
        val nextVersion = mavenPomNextVersionGenerator.generateNextPomVersion("1.1.0")
        Assert.assertEquals("1.1.1", nextVersion)
    }

    @Test
    fun shouldGenerateNextVersion3() {
        val nextVersion = mavenPomNextVersionGenerator.generateNextPomVersion("1.1-SNAPSHOT")
        Assert.assertEquals("1.2-SNAPSHOT", nextVersion)
    }

    @Test
    fun shouldGenerateNextVersion4() {
        val nextVersion = mavenPomNextVersionGenerator.generateNextPomVersion("1.1")
        Assert.assertEquals("1.2", nextVersion)
    }
    
    @Test
    fun shouldGenerateNextVersion5() {
        val nextVersion = mavenPomNextVersionGenerator.generateNextPomVersion("1")
        Assert.assertEquals("2", nextVersion)
    }
    
    @Test
    fun shouldGenerateNextVersion6() {
        val nextVersion = mavenPomNextVersionGenerator.generateNextPomVersion("1-SNAPSHOT")
        Assert.assertEquals("2-SNAPSHOT", nextVersion)
    }
}
