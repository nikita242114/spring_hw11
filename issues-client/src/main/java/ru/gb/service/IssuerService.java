package ru.gb.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.model.Issue;
import ru.gb.provider.BookProvider;
import ru.gb.provider.ReaderProvider;
import ru.gb.repository.IssueRepository;


import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IssuerService {

    @Value("${application.max-allowed-books}")
    private int limitBooks;

    // спринг это все заинжектит
    private final BookProvider bookProvider;
    private final ReaderProvider readerProvider;
    private final IssueRepository issueRepository;

    @Autowired
    public IssuerService(BookProvider bookProvider,ReaderProvider readerProvider, IssueRepository issueRepository) {
        this.bookProvider = bookProvider;
        this.readerProvider = readerProvider;
        this.issueRepository = issueRepository;
    }
    // создание завки на выдачу
    public Issue saveIssue(Issue issue) {
        if (bookProvider.getBookById(issue.getBookId()).isEmpty()) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + issue.getBookId() + "\"");
        }
        if (readerProvider.getReaderById(issue.getReaderId()).isEmpty()) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + issue.getReaderId() + "\"");
        }
        // можно проверить, что у читателя нет книг на руках (или его лимит не превышает в Х книг)

//        if (issueRepository.getAllIssueByReaderId(issue.getReaderId()).size() >= limitBooks) {
//            throw new RuntimeException("пользователь взял допустимое колличество книг");
//        }
//        Issue newissue = new Issue(issue.getBookId(), issue.getReaderId());
        issue.setIssuedAt(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    // показать список всех заявок
    public List<Issue> getIssues() {
        return issueRepository.findAll();
    }

    // получить информацию о запросе по id
    public Issue getIssueById(Long id) {
        return issueRepository.findById(id).get();
    }

    // возврат книги
    @Transactional
    public Issue returnBooks(Long id) {
        Issue updateIssue = issueRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Issue not found"));
        updateIssue.setTimeReturn(LocalDateTime.now());
        return issueRepository.save(updateIssue);
    }
    // удаление запроса
    public void deleteIssue(Long id) {
        issueRepository.deleteById(id);
    }


}