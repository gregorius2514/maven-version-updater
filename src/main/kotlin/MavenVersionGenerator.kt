interface MavenVersionGenerator {

    fun generateNextPomVersion(projectPomVersion: String): String
}
