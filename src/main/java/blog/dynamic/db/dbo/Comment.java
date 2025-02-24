package blog.dynamic.db.dbo;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comment {
	private int id;
	private Date date;
	private String author;
	private String page;
	private String content;
}
