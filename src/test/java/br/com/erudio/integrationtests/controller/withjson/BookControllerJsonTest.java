package br.com.erudio.integrationtests.controller.withjson;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.erudio.configs.TestConfigs;
import br.com.erudio.data.vo.v1.security.AccountCredentialsVO;
import br.com.erudio.integrationtests.testcontainers.AbstractIntegrationTest;
import br.com.erudio.integrationtests.vo.BookVO;
import br.com.erudio.integrationtests.vo.TokenVO;
import br.com.erudio.integrationtests.vo.wrappes.WrapperBookVO;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;

/*@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)*/
public class BookControllerJsonTest extends AbstractIntegrationTest {
/*
    private static RequestSpecification specification;
    private static ObjectMapper objectMapper;

    private static BookVO book;

    @BeforeAll
    public static void setup() {
        objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        
        book = new BookVO();
    }
    
    @Test
    @Order(1)
    public void authorization() {
        AccountCredentialsVO user = new AccountCredentialsVO();
        user.setUsername("leandro");
        user.setPassword("admin123");

        var token =
                given()
                    .basePath("/auth/signin")
                    .port(TestConfigs.SERVER_PORT)
                    .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(user)
                    .when()
                        .post()
                    .then()
                        .statusCode(200)
                    .extract()
                    .body()
                        .as(TokenVO.class)
                    .getAccessToken();

            specification =
                new RequestSpecBuilder()
                    .addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + token)
                    .setBasePath("/api/book/v1")
                    .setPort(TestConfigs.SERVER_PORT)
                    .addFilter(new RequestLoggingFilter(LogDetail.ALL))
                    .addFilter(new ResponseLoggingFilter(LogDetail.ALL))
                    .build();
    }
      
    @Test
    @Order(2)
    public void testCreate() throws JsonMappingException, JsonProcessingException {
        
        mockBook();

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(book)
                    .when()
                    .post()
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();
        
        book = objectMapper.readValue(content, BookVO.class);
        
        assertNotNull(book.getId());
        assertNotNull(book.getTitle());
        assertNotNull(book.getAuthor());
        assertNotNull(book.getPrice());
        assertTrue(book.getId() > 0);
        assertEquals("Docker Deep Dive", book.getTitle());
        assertEquals("Nigel Poulton", book.getAuthor());
        assertEquals(55.99, book.getPrice());
    }
      
    @Test
    @Order(3)
    public void testUpdate() throws JsonMappingException, JsonProcessingException {
        
        book.setTitle("Docker Deep Dive - Updated");

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .body(book)
                    .when()
                    .put()
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();
        
        BookVO bookUpdated = objectMapper.readValue(content, BookVO.class);
        
        assertNotNull(bookUpdated.getId());
        assertNotNull(bookUpdated.getTitle());
        assertNotNull(bookUpdated.getAuthor());
        assertNotNull(bookUpdated.getPrice());
        assertEquals(bookUpdated.getId(), book.getId());
        assertEquals("Docker Deep Dive - Updated", bookUpdated.getTitle());
        assertEquals("Nigel Poulton", bookUpdated.getAuthor());
        assertEquals(55.99, bookUpdated.getPrice());
    }

    @Test
    @Order(4)
    public void testFindById() throws JsonMappingException, JsonProcessingException {
        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .pathParam("id", book.getId())
                    .when()
                    .get("{id}")
                .then()
                    .statusCode(200)
                        .extract()
                        .body()
                            .asString();
        
        BookVO foundBook = objectMapper.readValue(content, BookVO.class);
        
        assertNotNull(foundBook.getId());
        assertNotNull(foundBook.getTitle());
        assertNotNull(foundBook.getAuthor());
        assertNotNull(foundBook.getPrice());
        assertEquals(foundBook.getId(), book.getId());
        assertEquals("Docker Deep Dive - Updated", foundBook.getTitle());
        assertEquals("Nigel Poulton", foundBook.getAuthor());
        assertEquals(55.99, foundBook.getPrice());
    }
    
    @Test
    @Order(5)
    public void testDelete() {
        given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .pathParam("id", book.getId())
                    .when()
                    .delete("{id}")
                .then()
                    .statusCode(204);
    }
    
    @Test
    @Order(6)
    public void testFindAll() throws JsonMappingException, JsonProcessingException {

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .queryParams("page", 0 , "limit", 10, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                .asString();
        
        WrapperBookVO wrapper = objectMapper.readValue(content, WrapperBookVO.class);
        var books = wrapper.getEmbedded().getBooks();
		
        BookVO foundBookOne = books.get(0);
        
        assertNotNull(foundBookOne.getId());
        assertNotNull(foundBookOne.getTitle());
        assertNotNull(foundBookOne.getAuthor());
        assertNotNull(foundBookOne.getPrice());
        assertEquals(144, foundBookOne.getId());
        assertEquals("10 000 timmar", foundBookOne.getTitle());
        assertEquals("Wyatan", foundBookOne.getAuthor());
        assertEquals(26.29, foundBookOne.getPrice());
        
        BookVO foundBookFive = books.get(4);
        
        assertNotNull(foundBookFive.getId());
        assertNotNull(foundBookFive.getTitle());
        assertNotNull(foundBookFive.getAuthor());
        assertNotNull(foundBookFive.getPrice());
        assertEquals(349, foundBookFive.getId());
        assertEquals("3 A.M.", foundBookFive.getTitle());
        assertEquals("Odelinda", foundBookFive.getAuthor());
        assertEquals(45.43, foundBookFive.getPrice());
    }
    
    @Test
    @Order(7)
    public void testHATEOAS() throws JsonMappingException, JsonProcessingException {

        var content = given().spec(specification)
                .contentType(TestConfigs.CONTENT_TYPE_JSON)
                    .queryParams("page", 0 , "limit", 10, "direction", "asc")
                    .when()
                    .get()
                .then()
                    .statusCode(200)
                .extract()
                    .body()
                .asString();
        
        var serverPort = TestConfigs.SERVER_PORT;
        String port = serverPort == 80 ? "" : ":" + String.valueOf(serverPort);
        
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost"+ port +"/api/book/v1/144\"}}}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost"+ port +"/api/book/v1/234\"}}}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost"+ port +"/api/book/v1/261\"}}}"));
        
        assertTrue(content.contains("\"page\":{\"size\":12,\"totalElements\":1015,\"totalPages\":85,\"number\":0}}"));
        
        assertTrue(content.contains("\"first\":{\"href\":\"http://localhost"+ port +"/api/book/v1?direction=asc&page=0&size=12&sort=title,asc\"}"));
        assertTrue(content.contains("\"self\":{\"href\":\"http://localhost"+ port +"/api/book/v1?page=0&size=12&direction=asc\"}"));
        assertTrue(content.contains("\"next\":{\"href\":\"http://localhost"+ port +"/api/book/v1?direction=asc&page=1&size=12&sort=title,asc\"}"));
        assertTrue(content.contains("\"last\":{\"href\":\"http://localhost"+ port +"/api/book/v1?direction=asc&page=84&size=12&sort=title,asc\"}}"));
        
    }
     
    private void mockBook() {
        book.setTitle("Docker Deep Dive");
        book.setAuthor("Nigel Poulton");
        book.setPrice(Double.valueOf(55.99));
        book.setLaunchDate(new Date("05/11/2017"));
    }   */ 
}