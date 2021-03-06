package edu.byu.cs.tweeter.model.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


import java.io.IOException;
import java.time.LocalDateTime;

import edu.byu.cs.tweeter.client.model.service.PostServiceProxy;
import edu.byu.cs.tweeter.model.domain.AuthToken;
import edu.byu.cs.tweeter.model.domain.User;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.client.model.net.ServerFacade;
import edu.byu.cs.tweeter.model.net.TweeterRemoteException;
import edu.byu.cs.tweeter.model.service.request.PostRequest;
import edu.byu.cs.tweeter.model.service.response.PostResponse;

public class PostServiceProxyTest {

    private PostRequest validRequest;
    private PostRequest invalidRequest;

    private PostResponse successResponse;
    private PostResponse failureResponse;

    private PostServiceProxy postServiceSpy;

    private static final LocalDateTime time1 = LocalDateTime.now();

    /**
     * Create a PostService spy that uses a mock ServerFacade to return known responses to
     * requests.
     */
    @BeforeEach
    public void setup() throws IOException, TweeterRemoteException {
        User currentUser = new User("FirstName", "LastName", "https://faculty.cs.byu.edu/~jwilkerson/cs340/tweeter/images/donald_duck.png");

        String content1 = "Content: Hello World! \uD83D\uDE03, Mentions: @BobBobson, URLs: http://www.google.com";

        Status status1 = new Status(content1, currentUser, time1);

        // Setup request objects to use in the tests
        validRequest = new PostRequest(currentUser.getAlias(), content1, time1, new AuthToken());
        invalidRequest = new PostRequest(null, null, null, null);

        // Setup a mock ServerFacade that will return known responses
        successResponse = new PostResponse(status1);
        ServerFacade mockServerFacade = Mockito.mock(ServerFacade.class);
        Mockito.when(mockServerFacade.savePost(validRequest, PostServiceProxy.URL_PATH)).thenReturn(successResponse);

        failureResponse = new PostResponse(false, "Failed to post status");
        Mockito.when(mockServerFacade.savePost(invalidRequest, PostServiceProxy.URL_PATH)).thenReturn(failureResponse);

        // Create a FollowingService instance and wrap it with a spy that will use the mock service
        postServiceSpy = Mockito.spy(new PostServiceProxy());
        Mockito.when(postServiceSpy.getServerFacade()).thenReturn(mockServerFacade);
    }

    /**
     * Verify that for successful requests the {@link PostService#savePost(PostRequest)}
     * method returns the same result as the {@link ServerFacade}.
     * .
     *
     * @throws Exception if an database error occurs.
     */
    @Test
    public void testSavePost_validRequest_correctResponse() throws Exception {
        PostResponse response = postServiceSpy.savePost(validRequest);
        Assertions.assertEquals(successResponse, response);
    }

    /**
     * Verify that for failed requests the {@link PostService#savePost(PostRequest)}
     * method returns the same result as the {@link ServerFacade}.
     *
     * @throws IOException if an IO error occurs.
     */
    @Test
    public void testSavePost_invalidRequest_returnsNoStatus() throws Exception {
        PostResponse response = postServiceSpy.savePost(invalidRequest);
        Assertions.assertEquals(failureResponse, response);
    }
}
