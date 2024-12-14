package mk.ukim.finki.wp.lab.service.specifications;


import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.*;


public class SpecificationBuilder {
    public static <T> Specification<T> filterContainsText(Class<T> clazz, String field, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(
                criteriaBuilder.lower(fieldToPath(field, (Root<String>) root)),
                "%" + value.toLowerCase() + "%"
        );
    }

    private static <T> Path<T> fieldToPath(String field, Root<T> root) {
        String[] parts = field.split("\\.");
        Path<T> res = root;
        for (String p : parts) {
            res = res.get(p);
        }
        return res;
    }

}
