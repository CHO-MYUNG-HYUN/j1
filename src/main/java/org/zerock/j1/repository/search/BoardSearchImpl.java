package org.zerock.j1.repository.search;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.j1.domain.Board;
import org.zerock.j1.domain.QBoard;
import org.zerock.j1.domain.QReply;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

  public BoardSearchImpl() {
    super(Board.class);
  }

  @Override
  public Page<Board> search1(String searchType, String keyword, Pageable pageable) {

    QBoard board = QBoard.board;

    JPQLQuery<Board> query = from(board);

    if (keyword != null && searchType != null) {

      // tc -> [t, c]
      String[] searchArr = searchType.split("");

      // ( )
      BooleanBuilder searchBuilder = new BooleanBuilder();

      for (String type : searchArr) {
        switch (type) {
          case "t" -> searchBuilder.or(board.title.contains(keyword));
          case "c" -> searchBuilder.or(board.content.contains(keyword));
          case "w" -> searchBuilder.or(board.writer.contains(keyword));
        }

      } // endfor

      query.where(searchBuilder);

    }

    // query.where(null)

    this.getQuerydsl().applyPagination(pageable, query);

    List<Board> list = query.fetch();
    long count = query.fetchCount();

    log.info(">>> list: " + list);
    log.info(">>> count: " + count);

    return new PageImpl<>(list, pageable, count);

  }

  @Override
  public Page<Object[]> searchWithRcnt(String searchType, String keyword, Pageable pageable) {

    QBoard board = QBoard.board;
    QReply reply = QReply.reply;

    JPQLQuery<Board> query = from(board);
    query.leftJoin(reply).on(reply.board.eq(board));
    query.groupBy(board);

    if (keyword != null && searchType != null) {

      // tc -> [t, c]
      String[] searchArr = searchType.split("");

      // ( )
      BooleanBuilder searchBuilder = new BooleanBuilder();

      for (String type : searchArr) {
        switch (type) {
          case "t" -> searchBuilder.or(board.title.contains(keyword));
          case "c" -> searchBuilder.or(board.content.contains(keyword));
          case "w" -> searchBuilder.or(board.writer.contains(keyword));
        }

      } // endfor

      query.where(searchBuilder);

    }    

    JPQLQuery<Tuple> tupleQuery = query
        .select(board.bno, board.title, board.writer, reply.countDistinct());

    this.getQuerydsl().applyPagination(pageable, tupleQuery); // 페이징 처리

    List<Tuple> tuples = tupleQuery.fetch();

    List<Object[]> arrList = 
    tuples.stream().map(tuple -> tuple.toArray()).collect(Collectors.toList());

    log.info(tuples);

    long count = tupleQuery.fetchCount();

    log.info("count: " + count);

    return new PageImpl<>(arrList, pageable, count);
  }

}
