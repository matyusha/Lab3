package un.lab.esa.demo.model;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(schema = "public", name="book")
@JacksonXmlRootElement(localName = "Book")
public class Book {
    @Id
    @Column(name = "id")
    @JacksonXmlProperty(isAttribute = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @JacksonXmlProperty
    private String title;

    @Column(name = "price")
    @JacksonXmlProperty
    private double price;

    @Column(name="year")
    @JacksonXmlProperty
    private  int year;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author",
            joinColumns = {@JoinColumn(name = "id_book")},
            inverseJoinColumns = {@JoinColumn(name = "id_author")})
    @JacksonXmlProperty
    Collection<Author> authors;

    public Book() {
    }

    public Book(int id, String title, double price, int year){
        this.id = id;
        this.title = title;
        this.price = price;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice(){
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public int getYear(){
        return year;
    }

    public void setYear(int year){
        this.year = year;
    }

    public Collection<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Collection<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", year=" + year +
                ", authors=" + authors.toString() +
                '}';
    }
}
