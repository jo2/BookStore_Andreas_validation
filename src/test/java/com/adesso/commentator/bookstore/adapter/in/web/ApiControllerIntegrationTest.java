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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ApiControllerIntegrationTest {

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
        System.out.println(books);
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
    void getAllBooks() throws Exception {

        mvc.perform(get("/api/book/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[2].id").value("1"))
                .andExpect(jsonPath("$[2].title").value("ti"))
                .andExpect(jsonPath("$[2].author").value("au"))
                .andExpect(jsonPath("$[2].publicationYear").value("2020"))
                .andExpect(jsonPath("$[2].stockAmount").value("4"));
    }

    @Test
    void getBook_existing() throws Exception {

        mvc.perform(get("/api/book/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.title").value("ti"))
                .andExpect(jsonPath("$.author").value("au"))
                .andExpect(jsonPath("$.publicationYear").value("2020"))
                .andExpect(jsonPath("$.stockAmount").value("4"));
    }

    @Test
    void getBook_notExisting() throws Exception {

        mvc.perform(get("/api/book/404"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook_valid() throws Exception{
        String payload = "{\n" +
                "\t\"title\":\"Static fields and spring\",\n" +
                "\t\"author\":\"SQL error\",\n" +
                "\t\"price\":\"3.14\",\n" +
                "\t\"publicationYear\":\"2021\",\n" +
                "\t\"stockAmount\":\"2\"\n" +
                "}";
        mvc.perform(post("/api/book/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload)
        )
                .andExpect(status().isOk());

        assertThat(bookRepository.existsBookByTitleAndAuthor("Static fields and spring", "SQL error")).isTrue();
    }

    @Test
    void createBook_validationError() throws Exception{
        String payload = "{" +
                "\"title\":\"a\"," +
                "\"author\":\"SQL error\"," +
                "\"price\":\"3.14\"," +
                "\"publicationYear\":\"2021\"," +
                "\"stockAmount\":\"2\"" +
                "}";
        mvc.perform(post("/api/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void createBook_notEnoughArguments() throws Exception {

        String payload = "{" +
                "\"title\":\"Static fields and spring\"," +
                "\"author\":\"SQL error\"," +
                "\"stockAmount\":\"2\"" +
                "}";
        mvc.perform(post("/api/book/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload)
                )
                .andExpect(status().isBadRequest());
    }
}
