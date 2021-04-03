package org.tutorial.async.model;

import java.util.List;

public class UserInfo {

    private User user;
    private List<Post> posts;
    private List<Album> albums;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(List<Album> albums) {
        this.albums = albums;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "user=" + user +
                ", posts=" + posts +
                ", albums=" + albums +
                '}';
    }
}
