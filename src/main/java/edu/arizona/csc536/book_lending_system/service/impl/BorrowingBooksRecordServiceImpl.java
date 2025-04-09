package edu.arizona.csc536.book_lending_system.service.impl;

import edu.arizona.csc536.book_lending_system.domain.Vo.BorrowingBooksVo;
import edu.arizona.csc536.book_lending_system.service.IBorrowingBooksRecordService;
import edu.arizona.csc536.book_lending_system.utils.page.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;
import edu.arizona.csc536.book_lending_system.mapper.*;
import edu.arizona.csc536.book_lending_system.domain.*;

import jakarta.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class BorrowingBooksRecordServiceImpl implements IBorrowingBooksRecordService {
    @Resource
    private BorrowingBooksMapper borrowingBooksMapper;

    @Resource
    private BookMapper bookMapper;

    @Resource
    private UserMapper userMapper;


    @Override
    public Page<BorrowingBooksVo> selectAllByPage(int pageNum) {

        //查询10条数据
        List<BorrowingBooks> list = borrowingBooksMapper.selectAllByPage((pageNum - 1) * 10, 10);
        if (null == list) {
            return null;
        }
        Page<BorrowingBooksVo> page = new Page<BorrowingBooksVo>();
        List<BorrowingBooksVo> borrowingBooksVos = new LinkedList<>();
        for (BorrowingBooks b : list) {
            User user = userMapper.selectByPrimaryKey(b.getUserId());
            Book book = bookMapper.selectByPrimaryKey(b.getBookId());
            BorrowingBooksVo borrowingBooksVo = new BorrowingBooksVo();

            borrowingBooksVo.setUser(user);
            borrowingBooksVo.setBook(book);
            //日期转换
            Date date1 = b.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBorrowing = sdf.format(date1);

            //算出还书日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 2);//增加两个月
            Date date2 = calendar.getTime();
            String dateOfReturn = sdf.format(date2);

            borrowingBooksVo.setDateOfBorrowing(dateOfBorrowing);
            borrowingBooksVo.setDateOfReturn(dateOfReturn);
            borrowingBooksVos.add(borrowingBooksVo);
        }
        page.setList(borrowingBooksVos);
        page.setPageNum(pageNum);
        page.setPageSize(10);

        //查找总页数
        int recordCount = 0;//总和记录
        recordCount = borrowingBooksMapper.selectAll();
        //计算页数
        int pageCount = recordCount / 10;
        if (recordCount % 10 != 0) {
            pageCount++;
        }
        page.setPageCount(pageCount);
        return page;
    }

    @Override
    public ArrayList<BorrowingBooksVo> selectAllBorrowRecord(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (null == user) {
            return null;
        }

        List<BorrowingBooks> list = borrowingBooksMapper.selectAllBorrowRecord(user.getUserId());
        if (null == list) {
            return null;
        }

        ArrayList<BorrowingBooksVo> borrowingBooksVos = new ArrayList<>();

        for (BorrowingBooks b : list) {
            Book book = bookMapper.selectByPrimaryKey(b.getBookId());
            BorrowingBooksVo borrowingBooksVo = new BorrowingBooksVo();

            borrowingBooksVo.setBook(book);
            //日期转换
            Date date1 = b.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateOfBorrowing = sdf.format(date1);

            //算出还书日期
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, 2);//增加两个月
            Date date2 = calendar.getTime();
            String dateOfReturn = sdf.format(date2);

            borrowingBooksVo.setDateOfBorrowing(dateOfBorrowing);
            borrowingBooksVo.setDateOfReturn(dateOfReturn);

            borrowingBooksVos.add(borrowingBooksVo);
        }

        return borrowingBooksVos;
    }
}

