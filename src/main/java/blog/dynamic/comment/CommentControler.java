package blog.dynamic.comment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import blog.dynamic.DiscordBotNotify;
import blog.dynamic.comment.dto.CommentDto;
import blog.dynamic.db.dao.CommentDao;
import blog.dynamic.db.dbo.Comment;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = { "http://localhost", "https://shionn.github.io" })
public class CommentControler {

	@Autowired
	private SqlSession session;
	@Autowired
	private DiscordBotNotify bot;

	@GetMapping("/comment")
	public List<CommentDto> getComments(@RequestHeader(name = "page") String page, HttpServletRequest req) {
		CommentDao dao = session.getMapper(CommentDao.class);
		String ip = req.getRemoteAddr();
			dao.addView(page, ip);
			session.commit();
		if (isIndex(page)) {
			return toDto(dao.listForIndex());
		}
		return toDto(dao.listForPage(page));
	}

	private List<CommentDto> toDto(List<Comment> dbos) {
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
		return dbos
				.stream()
				.map(dbo -> CommentDto
						.builder()
						.author(dbo.getAuthor())
						.date(format.format(dbo.getDate()))
						.content(dbo.getContent())
						.build())
				.toList();
	}

	@PostMapping("/comment")
	public String postComment(@RequestHeader(name = "author") String author, @RequestBody String content,
			@RequestHeader("page") String page, HttpServletRequest req) {
		session.getMapper(CommentDao.class).addComment(author, content, page);
		session.commit();
		bot.notifyComment(author, content, page);
		return "ok";
	}

	private boolean isIndex(String page) {
		return StringUtils.isBlank(page) || "index.html".equals(page);
	}
}
