import org.junit.Assert
import org.junit.Test

class MainKtTest {

    @Test
    fun shouldPassAndReturnTheSameMainFunctionArgs() {
        val testArgs = TestArgs()

        val args = arrayOf("/home/test", "-setVersion=1.0.0-SNAPSHOT")
        val actualArgs = testArgs.testMain(args)

        Assert.assertEquals(arrayOf("/home/test", "-setVersion=1.0.0-SNAPSHOT"), actualArgs)
    }
}

class TestArgs {

    fun testMain(args: Array<String>): Array<String> {
        return args
    }
}
