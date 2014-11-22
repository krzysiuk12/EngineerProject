package jsonserializers.locations;

import domain.locations.Comment;

/**
 * Created by Krzysztof Kicinger on 2014-11-21.
 */
public class CommentSerializer {

    private Comment.Rating rating;
    private String comment;

    public CommentSerializer() {
    }

    public CommentSerializer(Comment.Rating rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public Comment.Rating getRating() {
        return rating;
    }

    public void setRating(Comment.Rating rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
