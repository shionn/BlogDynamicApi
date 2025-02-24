package blog.dynamic.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import blog.dynamic.db.dbo.Comment;

public interface CommentDao {

	@Select("SELECT * FROM comments WHERE page = #{page} ORDER BY date DESC")
	List<Comment> listForPage(String page);

	@Select("SELECT * FROM comments ORDER BY date DESC LIMIT 5")
	List<Comment> listForIndex();
}
