package com.example.librarysystem.book.service;

import com.example.librarysystem.book.entity.Book;
import com.example.librarysystem.book.repository.BookRepository;
import com.example.librarysystem.user.entity.User;
import com.example.librarysystem.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void borrowBook(String bookId, Long userId) {
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("도서 없음"));

        if (book.isBorrowed()) {
            throw new IllegalStateException("이미 대여된 도서입니다.");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        book.setBorrowed(true);
        book.setBorrowedBy(user);
    }

    @Transactional
    public void returnBook(String bookId, String userId) {
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("도서 없음"));

        if (!book.isBorrowed() || !book.getBorrowedBy().getId().equals(userId)) {
            throw new IllegalStateException("반납할 수 없는 상태입니다.");
        }

        book.setBorrowed(false);
        book.setBorrowedBy(null);
        bookRepository.save(book);
    }

    public void fetchBooksFromOpenApi() {
        String apiKey = "716879564c6b696d3733576b524255";
        String url = "http://openapi.seoul.go.kr:8088/" + apiKey + "/json/SeoulLibraryBookInfo/1/50/";

        try {
            // Java 내장 HttpURLConnection 사용
            URL apiUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            JSONObject response = new JSONObject(sb.toString());
            JSONArray rows = response.getJSONObject("SeoulLibraryBookInfo").getJSONArray("row");

            for (int i = 0; i < rows.length(); i++) {
                JSONObject bookJson = rows.getJSONObject(i);

                String bookId = bookJson.optString("LEND_NO", "NO_ID");
                String title = bookJson.optString("TITLE", "제목없음");
                String author = bookJson.optString("AUTHOR", "작자미상");

                // 중복 저장 방지
                if (!bookRepository.existsByBookId(bookId)) {
                    Book book = Book.builder()
                            .bookId(bookId)
                            .title(title)
                            .author(author)
                            .isAvailable(true)
                            .build();
                    bookRepository.save(book);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException("OpenAPI 호출 또는 파싱 중 오류 발생", e);
        }
    }
}
