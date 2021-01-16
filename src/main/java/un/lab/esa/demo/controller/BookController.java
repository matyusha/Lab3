package un.lab.esa.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import un.lab.esa.demo.model.Book;
import un.lab.esa.demo.model.enums.ChangeType;
import un.lab.esa.demo.notifications.JmsSenderService;
import un.lab.esa.demo.repository.BookRepository;

import javax.websocket.server.PathParam;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.Optional;

@RestController
public class BookController {

    private BookRepository bookRepository;
    private final JmsSenderService jmsSenderService;

    @Autowired
    public BookController(BookRepository bookRepository, JmsSenderService jmsSenderService) {
        this.bookRepository = bookRepository;
        this.jmsSenderService = jmsSenderService;
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
    public void createBook(@RequestBody Book bookToCreate) throws NoSuchFieldException, IllegalAccessException {
        bookRepository.save(bookToCreate);
        jmsSenderService.sendCreateChange(bookToCreate);
    }

    @PutMapping("/updateBook")
    public void updateBook(@RequestBody Book bookToUpdate) throws NoSuchFieldException, IllegalAccessException {
        Optional<Book> existingBook = bookRepository.findById(bookToUpdate.getId());
        if (existingBook.isPresent()) {
            Book book = existingBook.get();
            bookRepository.save(bookToUpdate);
            jmsSenderService.sendUpdateChange(book, bookToUpdate);
        }
        else {
            bookRepository.save(bookToUpdate);
            jmsSenderService.sendCreateChange(bookToUpdate);
        }
    }

    @DeleteMapping("/deleteBookById")
    public void deleteBookById(@PathParam("id") int id) throws NoSuchFieldException, IllegalAccessException {
        if (bookRepository.existsById(id)) {
            Book bookToDelete = bookRepository.findById(id).get();
            bookRepository.deleteById(id);
            jmsSenderService.sendDeleteChange(bookToDelete);
        }
    }

}