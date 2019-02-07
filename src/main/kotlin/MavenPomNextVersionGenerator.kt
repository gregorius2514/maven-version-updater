import java.util.regex.Pattern

class MavenPomNextVersionGenerator {

    private val MAVEN_VERSION_GROUP = 1
    private val searchVersionRegex = Pattern.compile("([0-9.]+)(-SNAPSHOT)?")

    fun generateNextPomVersion(projectPomVersion: String): String? {
        val matcher = searchVersionRegex.matcher(projectPomVersion)

        matcher.find()
        val version = matcher.group(MAVEN_VERSION_GROUP)

        if (version.contains(".")) {
            val versions = version.split(".")
            val mainorVersion = versions.get(versions.size - 1).toInt()

            val nextVersion = version.replace(".$mainorVersion", ".${mainorVersion.inc()}")
            return projectPomVersion.replace(version, nextVersion)
        }
        // fixme replace returning null with something safer
        return null
    }
}
