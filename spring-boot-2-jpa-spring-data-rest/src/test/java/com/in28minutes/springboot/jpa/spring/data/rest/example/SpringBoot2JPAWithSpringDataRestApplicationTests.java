package com.in28minutes.springboot.jpa.spring.data.rest.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringWhiteSpace;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBoot2JPAWithSpringDataRestApplication.class)
public class SpringBoot2JPAWithSpringDataRestApplicationTests {

    private final int port = 8080;

    private TestRestTemplate restTemplate = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void init() {
    }

	@Test
	public void contextLoads() {
	}

    @Test
    public void findAllStudentsRequestTest() {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response = getStringResponseEntity(entity, "/students/");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void findAllStudentsWithAssuredRestTest() {
        final String RESPONSE = "{ \"name\" : \"Ravi\", \"passportNumber\" : \"A1234568\", " +
                "\"_links\" : { \"self\" : { \"href\" : \"http://localhost:8080/students/10002\" }, " +
                "\"student\" : { \"href\" : \"http://localhost:8080/students/10002\" } } }";

        final Response response = RestAssured.given()
                .baseUri(createURLWithPort("/students/"))
                .when().get("10002");

        response.then().assertThat().statusCode(equalTo(200));
        assertThat(response.getBody().asString(),
                equalToIgnoringWhiteSpace(RESPONSE));

    }

    @Test
    public void findOneStudentByIdTest() throws Exception {

        final String RESPONSE = "{name: Ravi, passportNumber: A1234568, " +
                "_links: { self: { href: 'http://localhost:8080/students/10002'}, " +
                "student: { href: 'http://localhost:8080/students/10002'}}}";

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = getStringResponseEntity(entity, "/students/10002");

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        JSONAssert.assertEquals(RESPONSE, response.getBody(), false);

    }

    private ResponseEntity<String> getStringResponseEntity(HttpEntity<String> entity, String url) {
        return restTemplate.exchange(createURLWithPort(url),
                HttpMethod.GET, entity, String.class);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

}
