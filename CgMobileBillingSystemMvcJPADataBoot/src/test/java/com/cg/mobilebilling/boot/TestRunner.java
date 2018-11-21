package com.cg.mobilebilling.boot;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features= {"features/addPostpaidAccountFeature.feature"},glue= {"com.cg.mobilebilling.stepdefinition"})
public class TestRunner {

}
