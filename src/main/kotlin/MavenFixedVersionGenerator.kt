import java.util.regex.Pattern

class MavenFixedVersionGenerator (private val pomFixedVersion : String): MavenVersionGenerator {
    
    override fun generateNextPomVersion(projectPomVersion: String): String {
        return pomFixedVersion
    }
}
