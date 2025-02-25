package blog.dynamic.comment.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDto {
	private String author;
	private String date;
	private String content;
}
