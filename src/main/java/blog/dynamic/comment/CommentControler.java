package blog.dynamic.comment;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import blog.dynamic.db.dao.CommentDao;
import blog.dynamic.db.dbo.Comment;

@RestController
@CrossOrigin
public class CommentControler {

	@Autowired
	private SqlSession session;

	@GetMapping("/comment")
	public List<Comment> getComments(@RequestHeader(name = "page") String page) {
		if (isIndex(page)) {
			return session.getMapper(CommentDao.class).listForIndex();
		}
		return session.getMapper(CommentDao.class).listForPage(page);
	}

	@PostMapping("/comment")
	public String postComment(@RequestHeader(name = "author") String author, //
			@RequestHeader("content") String content) {
		session.getMapper(CommentDao.class);
		return "ok";
	}

	private boolean isIndex(String page) {
		return StringUtils.isBlank(page) || "index.html".equals(page);
	}
}
