package com.krista.books.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.krista.books.models.Book;
import com.krista.books.services.BookService;

@Controller
public class Books {
 
 private final BookService bookService;
 
 public Books(BookService bookService){
     this.bookService = bookService;
 }
 @RequestMapping("/")
 public String books(Model model) {
     List<Book> books = bookService.allBooks();
     model.addAttribute("books", books);
     return "books";
 }
 
 @RequestMapping("/{index}")
 public String findBookByIndex(@PathVariable("index") int index, Model model) {
     Book book = bookService.findBookByIndex(index);
     model.addAttribute("book", book);
     return "showBook";
}
 
 @RequestMapping("/new")
 public String newBook(@ModelAttribute("book") Book book) {
     return "newBook";
 }
 
 
 @PostMapping("/new")
 public String createBook(@Valid @ModelAttribute("book") Book book, BindingResult result) {
     if (result.hasErrors()) {
         return "newBook";
     }else{
         bookService.addBook(book);
         return "redirect:/";
     }
 }
 
 @RequestMapping("/edit/{id}")
 public String editBook(@PathVariable("id") int id, Model model) {
     Book book = bookService.findBookByIndex(id);
     if (book != null){
         model.addAttribute("book", book);
         return "editBook";
     }else{
         return "redirect:/";
     }
 }
 
 @PostMapping("/edit/{id}")
 public String updateBook(@PathVariable("id") int id, @Valid @ModelAttribute("book") Book book, BindingResult result) {
     if (result.hasErrors()) {
         return "editBook";
     }else{
         bookService.updateBook(id, book);
         return "redirect:/";
     }
 }
 
 @RequestMapping(value="/delete/{id}")
 public String destroyBook(@PathVariable("id") int id) {
     bookService.destroyBook(id);
     return "redirect:/";
 }
 
}
