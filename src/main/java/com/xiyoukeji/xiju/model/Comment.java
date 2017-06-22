package com.xiyoukeji.xiju.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="comment")
public class Comment {
	
	
	public static final Integer COMMENTED=1;
	public static final Integer NOTCOMMENT=2;
	@Id
	@GeneratedValue(generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", length = 11)
	private Integer id;
	/**
	 * 评论类型，包含创意新闻的评论和商品的评论
	 */
	@Column(name="comment_type",length=11)
	private Integer commentType;
	
	/**
	 * 评论内容
	 */
	@Column(name="comment_content",length=255)
	private String commentContent;
	
	/**
	 * 商户客服回复
	 */
	@Column(name="comment_reply",length=255)
	private String commentReply;
	
	/**
	 * 图片url
	 */
	@Column(name="comment_pic",length=65535)
	private String commentPic;
	
	/**
	 * 假如是创意的，那么就是chuanyiactivity对应的id，如果是商品的，就是goodsid，以此类推
	 */
	@Column(name="comment_type_id",length=11)
	private Integer commentTypeId;
	
	/**
	 * 是否已评价
	 */
	@Column(name="is_comment",length=11)
	private Integer isComment;
	

	@Column(name="user_id",length=11)
	private Integer userId;
	
	
	@Column(name="receipt_id",length=11)
	private Integer receiptId;
	
	@Column(name="comment_time",length=11)
	private Integer commentTime;
	
	@Column(name="reply_time",length=11)
	private Integer replyTime;
	
	@Column(name="username",length=255)
	private String username;
	
	
	@Column(name="head_url",length=255)
	private String headUrl;
	
	
	
	public String getHeadUrl() {
		return headUrl;
	}

	public void setHeadUrl(String headUrl) {
		this.headUrl = headUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getCommentTime() {
		return commentTime;
	}

	public void setCommentTime(Integer commentTime) {
		this.commentTime = commentTime;
	}

	
	public Integer getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Integer replyTime) {
		this.replyTime = replyTime;
	}

	public Integer getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Integer receiptId) {
		this.receiptId = receiptId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getIsComment() {
		return isComment;
	}

	public void setIsComment(Integer isComment) {
		this.isComment = isComment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCommentType() {
		return commentType;
	}

	public void setCommentType(Integer commentType) {
		this.commentType = commentType;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentReply() {
		return commentReply;
	}

	public void setCommentReply(String commentReply) {
		this.commentReply = commentReply;
	}

	

	public String getCommentPic() {
		return commentPic;
	}

	public void setCommentPic(String commentPic) {
		this.commentPic = commentPic;
	}

	public Integer getCommentTypeId() {
		return commentTypeId;
	}

	public void setCommentTypeId(Integer commentTypeId) {
		this.commentTypeId = commentTypeId;
	}

}
