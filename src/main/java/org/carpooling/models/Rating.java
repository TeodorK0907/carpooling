package org.carpooling.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "feedbacks", schema = "rose-valley-travel")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "feedback_id")
    private int id;
    @Column(name = "avg_feedback")
    private double avgRating;
    @Column(name = "total_feedbacks")
    private int totalFeedbacks;

    public Rating() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public int getTotalFeedbacks() {
        return totalFeedbacks;
    }

    public void setTotalFeedbacks(int totalFeedbacks) {
        this.totalFeedbacks = totalFeedbacks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating rating)) return false;
        return id == rating.id
                && Double.compare(avgRating, rating.avgRating) == 0
                && totalFeedbacks == rating.totalFeedbacks;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, avgRating, totalFeedbacks);
    }
}
