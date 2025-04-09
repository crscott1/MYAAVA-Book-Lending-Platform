package edu.arizona.csc536.book_lending_system.mapper;

import edu.arizona.csc536.book_lending_system.domain.BookCategory;
import edu.arizona.csc536.book_lending_system.domain.BookCategoryExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BookCategoryMapper {
    long countByExample(BookCategoryExample example);

    int deleteByExample(BookCategoryExample example);

    int deleteByPrimaryKey(Integer categoryId);

    int insert(BookCategory row);

    int insertSelective(BookCategory row);

    List<BookCategory> selectByExample(BookCategoryExample example);

    BookCategory selectByPrimaryKey(Integer categoryId);

    int updateByExampleSelective(@Param("row") BookCategory row, @Param("example") BookCategoryExample example);

    int updateByExample(@Param("row") BookCategory row, @Param("example") BookCategoryExample example);

    int updateByPrimaryKeySelective(BookCategory row);

    int updateByPrimaryKey(BookCategory row);

    List<BookCategory> selectByPageNum(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    int selectAllCount();
}