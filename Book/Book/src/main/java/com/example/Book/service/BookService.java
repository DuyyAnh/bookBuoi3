package com.example.Book.service;

import com.example.Book.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookService {
    private List<Book> listBook = new ArrayList<>( Arrays.asList(
            new Book(1, "Lập trình Windows", "Nguyễn Huy Cường", 99999),
            new Book(2, "Lập trình Web", "Nguyễn Huy Cường", 12345),
            new Book(3, "Lâp trình ứng dụng Java", "Nguyễn Huy Cường", 454534),
            new Book(4, "Thương mại điện tử", "Nguyễn Đình Ánh", 848593)));
    public List<Book> getAll()
    {
        return listBook;
    }
    public Book get(int id)
    {
        var findBook = listBook.stream().filter(p->p.getId()== id).findFirst().orElse(null);
        if(findBook == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return findBook;
    }
    public void add(Book newbook)
    {
        var maxId = listBook
                .stream()
                .mapToInt(v -> v.getId())
                .max().orElse( 0);
        newbook.setId(maxId +1);
        listBook.add(newbook);
    }
    public void remove(int id)
    {
        var findBook = listBook.stream().filter(p->p.getId() == id).findFirst().orElseThrow();
        listBook.remove(findBook);
    }
    public List<Book> search(String key)
    {
        return listBook.stream().filter(p->p.getAuthor().toLowerCase().contains(key) || p.getTitle().toLowerCase().contains(key)).toList();
    }
}
