package edu.arizona.csc536.book_lending_system.mapper;

import edu.arizona.csc536.book_lending_system.domain.BorrowingBooks;
import edu.arizona.csc536.book_lending_system.domain.BorrowingBooksExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BorrowingBooksMapper {
    long countByExample(BorrowingBooksExample example);

    int deleteByExample(BorrowingBooksExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BorrowingBooks row);

    int insertSelective(BorrowingBooks row);

    List<BorrowingBooks> selectByExample(BorrowingBooksExample example);

    BorrowingBooks selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") BorrowingBooks row, @Param("example") BorrowingBooksExample example);

    int updateByExample(@Param("row") BorrowingBooks row, @Param("example") BorrowingBooksExample example);

    int updateByPrimaryKeySelective(BorrowingBooks row);

    int updateByPrimaryKey(BorrowingBooks row);

    List<BorrowingBooks> selectAllByPage(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    int selectAll();

    List<BorrowingBooks> selectAllBorrowRecord(@Param("userId") int userId);
}