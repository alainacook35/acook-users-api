package com.acook.magmutualusersapi.spec;

import com.acook.magmutualusersapi.entity.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class UserSpecification {
    public static Specification<User> filterUsers(
            String searchTerm,
            String profession,
            String country,
            String city,
            LocalDate startDate,
            LocalDate endDate
    ) {
        Specification<User> userSpecification = Specification.unrestricted();
        // Universal-ish search for search all "free text" fields.
        if (searchTerm != null && !searchTerm.isBlank()) {
            String lowerSearch = searchTerm.toLowerCase();

            Specification<User> searchSpec = userSpecification
                    .or(likeFirstName(lowerSearch))
                    .or(likeLastName(lowerSearch))
                    .or(likeEmail(lowerSearch));

            userSpecification = userSpecification.and(searchSpec);
        }

        if (profession != null && !profession.isBlank()) {
            userSpecification = userSpecification.and(hasProfession(profession));
        }

        if (country != null && !country.isBlank()) {
            userSpecification = userSpecification.and(hasCountry(country));
        }

        if (city != null && !city.isBlank()) {
            userSpecification = userSpecification.and(hasCity(city));
        }

        if (startDate != null) {
            userSpecification = userSpecification.and(hasStartDate(startDate));
        }

        if (endDate != null) {
            userSpecification = userSpecification.and(hasEndDate(endDate));
        }

        return userSpecification;
    }

    private static Specification<User> likeFirstName(String searchTerm) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + searchTerm + "%");
    }

    private static Specification<User> likeLastName(String searchTerm) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + searchTerm + "%");
    }

    private static Specification<User> likeEmail(String searchTerm) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + searchTerm + "%");
    }

    /**
     * Front end is provided with a list of options to filter on for profession. One selected option can be passed to
     * the get all API to filter on users
     *
     * @param profession - item from provided list of profession options
     * @return Specification<User>
     */
    private static Specification<User> hasProfession(String profession) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("profession"), profession);
    }

    /**
     * Front end is provided with a list of options to filter on for country. One selected option can be passed to
     * the get all API to filter on users
     *
     * @param country - item from provided list of country options
     * @return Specification<User>
     */
    private static Specification<User> hasCountry(String country) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("country"), country);
    }

    /**
     * Front end is provided with a list of options to filter on for city. One selected option can be passed to
     * the get all API to filter on users
     *
     * @param city - item from provided list of city options
     * @return Specification<User>
     */
    private static Specification<User> hasCity(String city) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("city"), city);
    }

    private static Specification<User> hasStartDate(LocalDate startDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("dateCreated"), startDate);

    }

    private static Specification<User> hasEndDate(LocalDate endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("dateCreated"), endDate);

    }
}
