package com.adesso.commentator.bookstore.adapter.in.web;

import com.adesso.commentator.bookstore.MockData;
import com.adesso.commentator.bookstore.adapter.out.Mapper;
import com.adesso.commentator.bookstore.adapter.out.repositories.BillRepository;
import com.adesso.commentator.bookstore.adapter.out.repositories.BillingBookRepository;
import com.adesso.commentator.bookstore.adapter.out.repositories.BookRepository;
import com.adesso.commentator.bookstore.domain.Bill;
import com.adesso.commentator.bookstore.domain.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class PageControllerIntegrationTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillingBookRepository billingBookRepository;


    @BeforeEach
    public void init() {
        List<Book> books = MockData.getMockedBooks();
        bookRepository.saveAll(books.stream().map(Mapper::toDto).collect(Collectors.toList()));

        List<Bill> bills = MockData.getMockedBills();
        List<com.adesso.commentator.bookstore.adapter.out.entities.Bill> billdto =bills.stream().map(Mapper::toDto).collect(Collectors.toList());
        billingBookRepository.saveAll(MockData.getMockedBillingBooks().stream().map(Mapper::toDto).collect(Collectors.toList()));
        billRepository.saveAll(billdto);
    }

    @AfterEach
    public void cleanUp(@Autowired EntityManager em) {
        bookRepository.deleteAll();
        billRepository.deleteAll();
        billingBookRepository.deleteAll();
        em.createNativeQuery("ALTER TABLE BILL ALTER COLUMN BILL_ID RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE BOOK ALTER COLUMN BOOK_ID RESTART WITH 1").executeUpdate();
        em.createNativeQuery("ALTER TABLE BILLING_BOOK ALTER COLUMN BILLING_BOOK_ID RESTART WITH 1").executeUpdate();
    }

    @Test
    void index() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2>BookStore</h2>")))
                .andExpect(content().string(containsString("ti")))
                .andExpect(content().string(containsString("Hello World")))
                .andExpect(content().string(containsString("Error 418")))
                .andExpect(content().string(containsString("au")))
                .andExpect(content().string(containsString("404")))
                .andExpect(content().string(containsString("1999")))
                .andExpect(content().string(containsString("42")));

    }

    @Test
    void accounting() throws Exception {
        mvc.perform(get("/accounting"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("<h2>Accounting</h2>")))
                .andExpect(content().string(containsString("ti by au")))
                .andExpect(content().string(containsString("1.0")))
                .andExpect(content().string(containsString("2")));
    }

    @Test
    void addBookGet() throws Exception {
        mvc.perform(get("/add_book"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Book Title")))
                .andExpect(content().string(containsString("Book Author")))
                .andExpect(content().string(containsString("Book Price")));

    }

    @Test
    void addBookPost_valid() throws Exception {
       mvc.perform(post("/add_book")
                        .contentType("application/x-www-form-urlencoded")
                        .content("title=abc&author=def&publicationYear=2020&price=25.0&stockAmount=12"))
                .andExpect(status().is(302));

        assertThat(bookRepository.count()).isEqualTo(4L);
        assertThat(bookRepository.findById(4L)).isPresent();
        assertThat(bookRepository.findById(4L))
                .contains(new com.adesso.commentator.bookstore.adapter.out.entities.Book(4L, "abc", "def", 25.0, 2020, 12));
    }

    @Test
    void addBookPost_authorTitleNotUnique() throws Exception {
        mvc.perform(post("/add_book")
                        .contentType("application/x-www-form-urlencoded")
                        .content("title=ti&author=au&publicationYear=2020&price=25.0&stockAmount=12"))
                .andExpect(status().isOk());

        assertThat(bookRepository.count()).isEqualTo(3L);
        assertThat(bookRepository.findById(4L)).isEmpty();
    }

    @Test
    void bookById() throws Exception {
        mvc.perform(get("/book/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ti")))
                .andExpect(content().string(containsString("written by au")))
                .andExpect(content().string(containsString("1.0")))
                .andExpect(content().string(containsString("Only 4 left")));

    }

    @Test
    void deleteBook() throws Exception {
        mvc.perform(post("/book/delete/1"))
                .andExpect(status().is(302))
                .andExpect(content().string(not(containsString("ti"))));
    }

    @Test
    void editBookGet() throws Exception {
        mvc.perform(get("/book/edit/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("ti")))
                .andExpect(content().string(containsString("au")));
    }

    @Test
    void editBookPost_valid() throws Exception {
        mvc.perform(post("/book/edit/1")
                        .contentType("application/x-www-form-urlencoded")
                        .content("title=title2&author=author2&publicationYear=2020&price=25.0&stockAmount=12"))
                .andExpect(status().is(302));
    }

    @Test
    void editBookPost_duplicate() throws Exception {
        mvc.perform(post("/book/edit/1")
                .contentType("application/x-www-form-urlencoded")
                .content("title=Error 418&author=Teapot&publicationYear=2020&price=25.0&stockAmount=12"))
                .andExpect(status().is(302));
    }

}
