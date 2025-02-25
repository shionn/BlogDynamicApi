package blog.dynamic.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import blog.dynamic.db.dbo.Comment;

public interface CommentDao {

	@Select("SELECT * FROM comments WHERE page = #{page} ORDER BY date DESC")
	List<Comment> listForPage(String page);

	@Select("SELECT * FROM comments ORDER BY date DESC LIMIT 5")
	List<Comment> listForIndex();

	@Insert("INSERT INTO comments (author, content, page) VALUES (#{author}, #{content}, #{page}) ")
	int addComment(@Param("author") String author, @Param("content") String content, @Param("page") String page);

	@Insert("INSERT INTO view (page, ip) VALUES (#{page}, #{addr})")
	void addView(@Param("page") String page, @Param("addr") String addr);
}
