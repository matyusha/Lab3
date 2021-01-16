package un.lab.esa.demo.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import un.lab.esa.demo.model.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
}
