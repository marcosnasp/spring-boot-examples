package com.in28minutes.springboot.jdbc.h2.example;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	SpringBoot2JdbcWithH2ApplicationTests.class,
	SpringBoot2JdbcWithH2ApplicationWithMockTests.class
})
public class JUnitTestSuite {

}
