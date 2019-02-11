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
            val mainorVersionNumber = versions.get(versions.size - 1)
            if (mainorVersionNumber.isBlank()) {
                return null
            }
            val mainorVersion =  mainorVersionNumber.toInt().inc()
            // todo do it with kotlin way (better way)
            val nextProjectVersion = "${versions.get(0)}.${versions.get(1)}.$mainorVersion"

            val nextVersion = version.replace("$version", "$nextProjectVersion")
            return projectPomVersion.replace(version, nextVersion)
        }
        // fixme replace returning null with something safer
        return null
    }
}
