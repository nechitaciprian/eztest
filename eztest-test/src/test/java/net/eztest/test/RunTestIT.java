package net.eztest.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import net.eztest.runner.junit.EzTestJUnitRunner;

@RunWith(EzTestJUnitRunner.class)
@CucumberOptions(plugin = {"pretty", "html:target/cucumber"},glue="net.eztest.steps", features="src/test/resources/features/steps/" )
public class RunTestIT {

}
