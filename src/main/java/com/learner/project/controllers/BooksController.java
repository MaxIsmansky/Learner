package com.learner.project.controllers;

import com.learner.project.models.Books;
import com.learner.project.models.User;
import com.learner.project.repositories.BookRepository;
import com.learner.project.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

/**
 * @author Ismansky Maxim
 * Date: 23.12.2021
 */
@Controller
public class BooksController {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Autowired
    public BooksController(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    @Value("${upload.path.books}")
    private String uploadPath;

    @GetMapping("/books")
    public String myBooks(@AuthenticationPrincipal User user, Model model) {

        User userFromDb = userRepository.findByUsername(user.getUsername());
        String userPhoto = userFromDb.getUserPhotoName() == null ? "#" : userFromDb.getUserPhotoName();
        model.addAttribute("userPhoto", userPhoto);

        Iterable<Books> books = bookRepository.findBooksByUser_id(user.getId());
        model.addAttribute("books",books);

        return "bookspage";
    }

    @PostMapping("/books")
    public String getBook(@AuthenticationPrincipal User user,
                          @RequestParam(required = false) MultipartFile book) throws IOException {

        Books newBook = new Books();

        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFileName = uuidFile + "." + book.getOriginalFilename();
        book.transferTo(new File(uploadPath + "/" + resultFileName));
        newBook.setPathName(resultFileName);
        newBook.setUser(user);
        bookRepository.save(newBook);
        return "redirect:/books";
    }

    @GetMapping("/books/delete/{bookId}")
    public String deleteBook(@PathVariable Long bookId) throws IOException, URISyntaxException {
        Books bookToBeDeleted = bookRepository.findById(bookId).stream().findFirst().get();
        bookRepository.deleteById(bookId);
        Path path = Paths.get(uploadPath + bookToBeDeleted.getPathName());
        Files.delete(path);
        return "redirect:/books";
    }


}
