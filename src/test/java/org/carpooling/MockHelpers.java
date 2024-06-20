package org.carpooling;

import org.carpooling.helpers.constants.TravelStatus;
import org.carpooling.helpers.constants.UserRole;
import org.carpooling.models.*;

import java.time.LocalDateTime;
import java.util.HashSet;

public class MockHelpers {

    public static User createMockUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("mockUser");
        user.setPassword("mockPass");
        user.setFirstName("mock");
        user.setLastName("user");
        user.setEmail("mockUserEmail@mock.com");
        user.setPhoneNumber("1234567890");
        user.setArchived(false);
        user.setBlocked(false);
        user.setVerified(true);
        user.setDriverRating(createMockRating());
        user.setPassengerRating(createAnotherMockRating());
        user.setRole(UserRole.USER);
        return user;
    }

    public static User createAnotherMockUser() {
        User user = new User();
        user.setId(1);
        user.setUsername("mockUser2");
        user.setPassword("mockPass2");
        user.setFirstName("mock");
        user.setLastName("user2");
        user.setEmail("mockUser2Email@mock.com");
        user.setPhoneNumber("0987654321");
        user.setArchived(false);
        user.setBlocked(false);
        user.setVerified(true);
        user.setDriverRating(createMockRating());
        user.setPassengerRating(createAnotherMockRating());
        user.setRole(UserRole.USER);
        return user;
    }

    public static Rating createMockRating() {
        Rating rating = new Rating();
        rating.setId(1);
        rating.setTotalFeedbacks(10);
        rating.setAvgRating(4);
        return rating;
    }

    public static Rating createAnotherMockRating() {
        Rating rating = new Rating();
        rating.setId(2);
        rating.setTotalFeedbacks(5);
        rating.setAvgRating(3);
        return rating;
    }

    public static Rating createAnotherMockRatingTwo() {
        Rating rating = new Rating();
        rating.setId(3);
        rating.setTotalFeedbacks(3);
        rating.setAvgRating(2);
        return rating;
    }

    public static Rating createMockRatingTwo() {
        Rating rating = new Rating();
        rating.setId(4);
        rating.setTotalFeedbacks(6);
        rating.setAvgRating(4);
        return rating;
    }

    public static Candidate createMockCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(1);
        candidate.setTravelId(1);
        candidate.setUserId(1);
        candidate.setUsername("mockUser");
        return candidate;
    }

    public static Candidate createAnotherMockCandidate() {
        Candidate candidate = new Candidate();
        candidate.setId(2);
        candidate.setTravelId(2);
        candidate.setUserId(2);
        candidate.setUsername("mockUser2");
        return candidate;
    }

    /**
     * Creates mock travel that only needs to have a creator set after initialization.
     * internally uses createMockStartingPoint and createMockEndingPoint
     * @return Travel object
     */
    public static Travel createMockTravel() {
        Travel travel = new Travel();
        travel.setId(1);
        travel.setStartingPoint(createMockStaringPoint());
        travel.setEndingPoint(createMockEndingPoint());
        travel.setDepartureTime(LocalDateTime.of(2030, 10,
                10, 10, 10));
        travel.setStatus(TravelStatus.PLANNED);
        travel.setFree_spots(3);
        travel.setDistance(100.0);
        travel.setDuration(59.0);
        travel.setArchived(false);
        travel.setCandidates(new HashSet<>());
        travel.setPassengers(new HashSet<>());
        return travel;
    }

    public static TravelPoint createMockStaringPoint() {
        TravelPoint travelPoint = new TravelPoint();
        travelPoint.setId(1);
        travelPoint.setAddress("mockAddress");
        travelPoint.setLatitude(33.55);
        travelPoint.setLongitude(33.55);
        return travelPoint;
    }

    public static TravelPoint createMockEndingPoint() {
        TravelPoint travelPoint = new TravelPoint();
        travelPoint.setId(2);
        travelPoint.setAddress("endingMockAddress");
        travelPoint.setLatitude(33.66);
        travelPoint.setLongitude(55.44);
        return travelPoint;
    }
}