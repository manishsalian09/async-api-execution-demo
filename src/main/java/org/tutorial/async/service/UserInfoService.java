package org.tutorial.async.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tutorial.async.model.Album;
import org.tutorial.async.model.Post;
import org.tutorial.async.model.User;
import org.tutorial.async.model.UserInfo;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class UserInfoService {

    final HttpClient httpClient = HttpClient.newBuilder().build();
    final ObjectMapper mapper = new ObjectMapper();

    private UserInfoService() {

    }

    public CompletableFuture<User> fetchUser(final Integer userId) {
        return Optional.of("https://jsonplaceholder.typicode.com/users/" + userId)
                .map(uri -> httpClient
                        .sendAsync(
                                HttpRequest.newBuilder(URI.create(uri)).build(),
                                HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(s -> {
                            try {
                                TimeUnit.SECONDS.sleep(10);
                                System.out.println("completed fetchUser by " + Thread.currentThread().getName());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return s;
                        })
                )
                .orElseThrow(() -> new IllegalArgumentException("user id does not exist"))
                .thenApply(future -> {
                    try {
                        return mapper.readValue(future, User.class);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public CompletableFuture<List<Post>> fetchPostsByUserId(final Integer userId) {
        return Optional.of(String.format("https://jsonplaceholder.typicode.com/users/%s/posts", userId))
                .map(uri -> httpClient
                        .sendAsync(
                                HttpRequest.newBuilder(URI.create(uri)).build(),
                                HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(s -> {
                            try {
                                TimeUnit.SECONDS.sleep(5);
                                System.out.println("completed fetchPostsByUserId by " + Thread.currentThread().getName());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return s;
                        })
                )
                .orElseThrow(() -> new IllegalArgumentException("user id does not exist"))
                .thenApply(future -> {
                    try {
                        return mapper.readValue(future, new TypeReference<>() {});
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public CompletableFuture<List<Album>> fetchAlbumsByUserId(final Integer userId) {
        return Optional.of(String.format("https://jsonplaceholder.typicode.com/users/%s/albums", userId))
                .map(uri -> httpClient
                        .sendAsync(
                                HttpRequest.newBuilder(URI.create(uri)).build(),
                                HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenApply(s -> {
                            try {
                                TimeUnit.SECONDS.sleep(2);
                                System.out.println("completed fetchAlbumsByUserId by " + Thread.currentThread().getName());
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return s;
                        })
                )
                .orElseThrow(() -> new IllegalArgumentException("user id does not exist"))
                .thenApply(future -> {
                    try {
                        return mapper.readValue(future, new TypeReference<>() {});
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    return null;
                });
    }

    public Optional<UserInfo> fetchUserInfo(final Integer userId) {
        long start = System.currentTimeMillis();
        final UserInfo userInfo = new UserInfo();
        CompletableFuture.allOf(fetchUser(userId).thenAccept(userInfo::setUser),
                fetchPostsByUserId(userId).thenAccept(userInfo::setPosts),
                fetchAlbumsByUserId(userId).thenAccept(userInfo::setAlbums)).join();
        System.out.println("Time for over all transaction : " + (System.currentTimeMillis() - start)/1000 + "s");
        return Optional.of(userInfo);
    }

    public static UserInfoService getInstance() {
        return new UserInfoService();
    }
}
