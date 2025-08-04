import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = {"src/test/resources/feature"},
        plugin = {"pretty",
                //"json:target/cucumber-reports/Cucumber.json", //no JSON report as it is not good-looking
                "html:target/cucumber-reports/Cucumber.html"},
        glue = {"lesson_20.step_definitions"}
)

public class FeatureRunner extends AbstractTestNGCucumberTests {
}
