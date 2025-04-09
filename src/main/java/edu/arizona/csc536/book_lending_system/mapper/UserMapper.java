package edu.arizona.csc536.book_lending_system.mapper;

import edu.arizona.csc536.book_lending_system.domain.User;
import edu.arizona.csc536.book_lending_system.domain.UserExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);


    int deleteByPrimaryKey(Integer userId);

    int insert(User row);

    int insertSelective(User row);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer userId);


    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updateByExampleSelective(@Param("row") User row, @Param("example") UserExample example);

    int updateByExample(@Param("row") User row, @Param("example") UserExample example);
    //分页查询
    List<User> selectByPageNum(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    //查询总数
    int selectUserCount();

}