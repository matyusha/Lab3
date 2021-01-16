package un.lab.esa.demo.repository;

import org.springframework.data.repository.CrudRepository;
import un.lab.esa.demo.model.Author;

public interface AuthorRepository extends CrudRepository<Author, Integer> {
}
