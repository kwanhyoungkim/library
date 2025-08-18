package com.example.librarysystem.book.service;

import com.example.librarysystem.book.dto.BookRentalResponseDto;
import com.example.librarysystem.book.entity.Book;
import com.example.librarysystem.book.repository.BookRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;

    @PostConstruct
    // 도서 등록 (중복 bookId 방지)
    public void init() {
        fetchBooksFromOpenApi();
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

    public BookRentalResponseDto getBookByBookId(String bookId) {
        Book book = bookRepository.findByBookId(bookId)
                .orElseThrow(() -> new IllegalArgumentException("도서를 찾을 수 없습니다."));
        return BookRentalResponseDto.fromEntity(book);
    }

    public List<BookRentalResponseDto> searchBooksByTitle(String title) {
        return bookRepository.findByTitleContaining(title).stream()
                .map(BookRentalResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public BookRentalResponseDto rentBook(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));

        if (!book.isAvailable()) {
            return new BookRentalResponseDto("이미 대여 중인 책입니다.");
        }

        book.setAvailable(false);
        bookRepository.save(book);
        return new BookRentalResponseDto("대여 완료");
    }

    public BookRentalResponseDto returnBook(String bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("책을 찾을 수 없습니다."));

        if (book.isAvailable()) {
            return new BookRentalResponseDto("이미 반납된 책입니다.");
        }

        book.setAvailable(true);
        bookRepository.save(book);
        return new BookRentalResponseDto("반납 완료");
    }
}
