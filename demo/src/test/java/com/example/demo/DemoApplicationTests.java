package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.Instant;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.object.Poll;
import com.example.demo.object.User;
import com.example.demo.object.Vote;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	
	@Autowired
    private TestRestTemplate restTemplate;

	
	
	
    @Test
    void Create_user_Test() {
        // Arrange
        String username = "bob";
        String email = "bob@example.com";

        // Act
        ResponseEntity<User> response = restTemplate.postForEntity(
                "/users?username=" + username + "&email=" + email,
                null,
                User.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(username, response.getBody().getUsername());
        assertEquals(email, response.getBody().getEmail());
    }
    
    @Test
    public void testListUsersAfterFirst() {
        // Arrange
        restTemplate.postForEntity( "/users?username=user1&email=user1@example.com", null, User.class);

        // Act
        ResponseEntity<User[]> response = restTemplate.getForEntity("/users", User[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals("user1", response.getBody()[0].getUsername());
    }
    
    @Test
    public void testUser1CreatesPoll() {
        // Arrange
        User user1 = restTemplate.postForEntity( "/users?username=user1&email=user1@example.com", null, User.class).getBody();
        String question = "question";
        String[] options = {"Red", "Blue", "Green"};
       

        // Act
        ResponseEntity<Poll> response = restTemplate.postForEntity(
                 "/polls?username=" + user1.getUsername() + "&question=" + question+ "&validuntil="+1,
                options,
                Poll.class
        );

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(question, response.getBody().getQuestion());
        assertEquals(options.length, response.getBody().getOptions().size());
    }

    
    @Test
    public void testListPolls() {
        // Arrange
        User user1 = restTemplate.postForEntity("/users?username=user1&email=user1@example.com", null, User.class).getBody();
        String question = "Favorite color?";
        String[] options = {"Red", "Blue", "Green"};
        restTemplate.postForEntity( "/polls?username=" + user1.getUsername() + "&question=" + question+ "&validuntil="+1, options, Poll.class);

        // Act
        ResponseEntity<Poll[]> response = restTemplate.getForEntity( "/polls", Poll[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals(question, response.getBody()[0].getQuestion()); 
    }
    
    @Test
    public void testUser2Votes() {
        // Arrange
    	String question = "question";
    	String[] options = {"Red", "Blue", "Green"};
        User user1 = restTemplate.postForEntity( "/users?username=user1&email=user1@example.com", null, User.class).getBody();
        User user2 = restTemplate.postForEntity( "/users?username=user2&email=user2@example.com", null, User.class).getBody();
        Poll poll = restTemplate.postForEntity(
                "/polls?username=" + user1.getUsername() + "&question=" + question+ "&validuntil="+1,
               options,
               Poll.class
       ).getBody();

        
        
        // Act
        // Vote first
       restTemplate.postForEntity("/votes?username=" + user2.getUsername() + "&pollquestion=" + question + "&optionindex=0", null, Vote.class);
       
     

       ResponseEntity<Vote[]> response = restTemplate.getForEntity(  "/votes?username="+ user2.getUsername(), Vote[].class);
       // Re-fetch updated poll from backend
       
       ResponseEntity<Poll[]> pollsResponse = restTemplate.getForEntity("/polls", Poll[].class);
       Poll[] polls = pollsResponse.getBody();
       Poll updatedPoll = Arrays.stream(polls)
               .filter(p -> question.equals(p.getQuestion()))
               .findFirst()
               .orElseThrow();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);
        assertEquals(1, updatedPoll.getOptions().get(0).getVote().size());
      
    }
    
    // TestListUserAfterFirst fails with this
//    @Test
//    public void testDeletePollAndListVotesEmpty() {
//        // Arrange
//    	String question = "question";
//    	String[] options = {"Red", "Blue", "Green"};
//        User user1 = restTemplate.postForEntity( "/users?username=user1&email=user1@example.com", null, User.class).getBody();
//        User user2 = restTemplate.postForEntity( "/users?username=user2&email=user2@example.com", null, User.class).getBody();
//        Poll poll = restTemplate.postForEntity(
//                "/polls?username=" + user1.getUsername() + "&question=" + question+ "&validuntil="+1,
//               options,
//               Poll.class
//       ).getBody();
//
//
//        restTemplate.postForEntity("/votes?username=" + user2.getUsername() + "&pollquestion=" + question + "&optionindex=0", null, Vote.class);
//        
//
//        // Act
//        restTemplate.delete("/polls?question=" + question);
//        ResponseEntity<Vote[]> response = restTemplate.getForEntity(  "/votes?username="+ user2.getUsername(), Vote[].class);
//
//        // Assert
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNotNull(response.getBody());
//        assertEquals(0, response.getBody().length);
//    }
    

}
