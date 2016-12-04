package net.eztest.steps;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.eztest.EzTestContext;
import net.eztest.EzTestUtils;

public class StepDefinition {

	@When("^I navigate to (.*)$")
	public void i_navigate_to(String url) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		System.out.println("navigate");
		EzTestContext.getDriver().navigate().to(EzTestUtils.unescapeJavaString(url));
	}
	
	@When("^I click element having (class|css|id|linkText|name|partialLinkText|tag|xpath|partialText|text) (.*)$")
	public void i_click_element(String selectorType, String selector) throws Throwable {
		By by = EzTestUtils.buildBy(selectorType, EzTestUtils.unescapeJavaString(selector));
		EzTestContext.getWait().until(ExpectedConditions.elementToBeClickable(by)).click();
	}	

	@When("^I (accept|dismiss) alert$")
	public void i_accept_alert(String action) throws Throwable {
		if("dismiss".equals(action)){
			EzTestContext.getWait().until(ExpectedConditions.alertIsPresent()).dismiss();
		}else{
			EzTestContext.getWait().until(ExpectedConditions.alertIsPresent()).accept();
		}
	}
	
	@When("^I type (.*) into element having (class|css|id|linkText|name|partialLinkText|tag|xpath|partialText|text) (.*)$")
	public void i_type_into_element(String keysToSend, String selectorType, String selector) throws Throwable {
		// Write code here that turns the phrase above into concrete actions
		By by = EzTestUtils.buildBy(selectorType, EzTestUtils.unescapeJavaString(selector));
		EzTestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(by)).sendKeys(EzTestUtils.unescapeJavaString(keysToSend));
	}
	
	@Then("^I expect visible element having (class|css|id|linkText|name|partialLinkText|tag|xpath|partialText|text) (.*)$")
	public void i_expect_visible_element(String selectorType, String selector) throws Throwable {
		By by = EzTestUtils.buildBy(selectorType, EzTestUtils.unescapeJavaString(selector));
		EzTestContext.getWait().until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	@Then("^I expect visible alert having (text|partialText) (.*)$")
	public void i_expect_alert_is_present(final String selectorType, final String selector) throws Throwable {

		EzTestContext.getWait().until(new ExpectedCondition<Alert>() {
			public Alert apply(WebDriver driver) {
				try {
					Alert alert = driver.switchTo().alert();
					if (alert != null && "text".equals(selectorType)
							&& alert.getText().equals(EzTestUtils.unescapeJavaString(selector))) {
						return alert;
					}
					if (alert != null && "partialText".equals(selectorType)
							&& alert.getText().contains(EzTestUtils.unescapeJavaString(selector))) {
						return alert;
					}
					return null;
				} catch (NoAlertPresentException e) {
					return null;
				}
			}
		});
	}

	@Then("^I expect hidden alert$")
	public void i_expect_alert_is_hidden() throws Throwable {

		EzTestContext.getWait().until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver driver) {
				try {
					Alert alert = driver.switchTo().alert();
					if (alert == null) {
						return Boolean.TRUE;
					}
					return null;
				} catch (NoAlertPresentException e) {
					return Boolean.TRUE;
				}
			}
		});
	}
	
}
