package un.lab.esa.demo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import un.lab.esa.demo.model.Author;
import un.lab.esa.demo.repository.AuthorRepository;

import javax.websocket.server.PathParam;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.io.StringReader;
import java.util.Optional;

@RestController
public class AuthorController {

    private AuthorRepository authorRepository;

    @Autowired
    public  AuthorController(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    @GetMapping(value = "/authors")
    public ModelAndView getXSLAuthors() throws JsonProcessingException {
        String data = new XmlMapper().writeValueAsString(authorRepository.findAll());
        ModelAndView modelAndView = new ModelAndView("author-list");
        Source src = new StreamSource(new StringReader(data));
        modelAndView.addObject("ArrayList", src);
        return modelAndView;
    }

    @GetMapping(value = "/getAllAuthors", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE  })
    public Iterable<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    @GetMapping(value = "/getAuthorById", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE  })
    public Author getAuthorById(@PathParam("id") int id) throws Exception {
        Optional<Author> requestedAuthor = authorRepository.findById(id);
        if (requestedAuthor.isPresent()){
            return requestedAuthor.get();
        }
        else throw new Exception("There is no author with such id");
    }

    @PostMapping("/createAuthor")
    public Author createAuthor(@RequestBody Author authorToCreate){
        return authorRepository.save(authorToCreate);
    }

    @PutMapping("/updateAuthor")
    public Author updateAuthor(@RequestBody Author authorToUpdate){
        Optional<Author> existingAuthor = authorRepository.findById(authorToUpdate.getId());
        if (existingAuthor.isPresent()){
            Author authorToSave = existingAuthor.get();
            authorToSave.setName(authorToUpdate.getName());
            authorToSave.setSurname(authorToUpdate.getSurname());
            return authorRepository.save(authorToSave);
        }
        else return authorRepository.save(authorToUpdate);
    }

    @DeleteMapping("/deleteAuthor")
    public void deleteAuthor(@PathParam("id") int id){
        if (authorRepository.existsById(id)){
            authorRepository.deleteById(id);
        }
    }

    @DeleteMapping("/deleteAllAuthors")
    public void deleteAllAuthors(){
        authorRepository.deleteAll();
    }
}
