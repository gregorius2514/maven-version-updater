import java.util.regex.Pattern

class MavenPomNextVersionGenerator : MavenVersionGenerator {

    private val MAVEN_VERSION_GROUP = 1
    private val searchVersionRegex = Pattern.compile("([0-9.]+)(-SNAPSHOT)?")

    override fun generateNextPomVersion(projectPomVersion: String): String {
        val matcher = searchVersionRegex.matcher(projectPomVersion)

        matcher.find()
        val version = matcher.group(MAVEN_VERSION_GROUP)
        var nextProjectVersion = ""

        if (version.contains(".")) {
            val versions = version.split(".")
            val mainorVersionNumber = versions.get(versions.size - 1)
            if (mainorVersionNumber.isBlank()) {
                return ""
            }
            val mainorVersion = mainorVersionNumber.toInt().inc()
            // todo do it with kotlin way (better way)
            if (versions.size == 3) {
                nextProjectVersion = "${versions.get(0)}.${versions.get(1)}.$mainorVersion"
            } else if (versions.size == 2) {
                nextProjectVersion = "${versions.get(0)}.$mainorVersion"
            }
        } else {
            nextProjectVersion = version.toInt().inc().toString()
        }
        val nextVersion = version.replace("$version", "$nextProjectVersion")
        return projectPomVersion.replace(version, nextVersion)
    }
}
