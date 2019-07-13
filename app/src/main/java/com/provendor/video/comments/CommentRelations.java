package com.provendor.video.comments;

import com.google.firebase.firestore.PropertyName;

public class CommentRelations {
    int likeaffinity; //0 for neutral, -1 for disliked, 1 for liked

    public CommentRelations() {
    }

    @PropertyName("likeaffinity")
    public int getLikeaffinity() {
        return likeaffinity;
    }

    public void setLikeaffinity(int likeaffinity) {
        this.likeaffinity = likeaffinity;
    }

}
