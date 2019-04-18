import java.util.regex.Pattern

// todo pomFixedVersion shouldn't be injected by constructor
class MavenFixedVersionGenerator (private val pomFixedVersion : String): MavenVersionGenerator {
    
    override fun generateNextPomVersion(projectPomVersion: String): String {
        return pomFixedVersion
    }
}
