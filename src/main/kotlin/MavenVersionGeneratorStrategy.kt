class MavenVersionGeneratorStrategy {
    
    fun createMavenVersionGenerator() : MavenVersionGenerator {
       return MavenPomNextVersionGenerator() 
    }
}