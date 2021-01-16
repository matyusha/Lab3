package un.lab.esa.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import un.lab.esa.demo.model.Book;
import un.lab.esa.demo.repository.BookRepository;

import javax.websocket.server.PathParam;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.Optional;

@RestController
public class BookController {

    private BookRepository bookRepository;

    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping(value = "/books")
    public ModelAndView getXSLBooks() throws JsonProcessingException {
        String data = new XmlMapper().writeValueAsString(bookRepository.findAll());
        ModelAndView modelAndView = new ModelAndView("book-list");
        Source src = new StreamSource(new StringReader(data));
        modelAndView.addObject("BooksList", src);
        return modelAndView;
    }

    @GetMapping(value = "/getAllBooks", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE  })
    public Iterable<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @GetMapping("/getBookById")
    public Book getBookById(@PathParam("id") int id) throws Exception {
        Optional<Book> requestedBook = bookRepository.findById(id);
        if (requestedBook.isPresent())
            return requestedBook.get();
        else throw new Exception("There is no book with such id");
    }

    @PostMapping("/createBook")
    public Book createBook(@RequestBody Book bookToCreate) {
        return bookRepository.save(bookToCreate);
    }

    @PutMapping("/updateBook")
    public Book updateBook(@RequestBody Book bookToUpdate) {
        Optional<Book> existingBook = bookRepository.findById(bookToUpdate.getId());
        if (existingBook.isPresent()) {
            Book bookToSave = existingBook.get();
            bookToSave.setTitle(bookToUpdate.getTitle());
            bookToSave.setPrice(bookToUpdate.getPrice());
            bookToSave.setYear(bookToUpdate.getYear());
            bookToSave.setAuthors(bookToUpdate.getAuthors());
            return bookRepository.save(bookToSave);
        }
        else return bookRepository.save(bookToUpdate);
    }

    @DeleteMapping("/deleteBookById")
    public void deleteBookById(@PathParam("id") int id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        }
    }

    @DeleteMapping("/deleteAllBooks")
    public void deleteAllBooks() {
        bookRepository.deleteAll();
    }


}