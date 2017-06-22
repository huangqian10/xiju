package com.xiyoukeji.xiju.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiyoukeji.xiju.core.enums.CommentType;
import com.xiyoukeji.xiju.core.enums.Const;
import com.xiyoukeji.xiju.core.utils.Utils;
import com.xiyoukeji.xiju.model.Comment;
import com.xiyoukeji.xiju.model.Receipt;
import com.xiyoukeji.xiju.model.User;

@Controller
public class CommentController extends BaseController {
	@RequestMapping("/user/commentAble.do")
	public void commentAble(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "goodsId", required = true) Integer goodsId) {
		JSONObject jv = new JSONObject();
		Integer userId = getUserId(request);
		Comment comment = commentService.getByUserIdAndTypeId(userId, goodsId);
		if (comment == null) {
			jv.put("code", Const.NOTABLE_COMMENT);
		} else {
			jv.put("code", Const.SUCCESS);
			jv.put("info", comment);
		}
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/addChuanyiComment.do")
	public void addChuanyiComment(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "commentTypeId", required = true) Integer commentTypeId,
			@RequestParam(value = "commentContent", required = true) String commentContent) {
		JSONObject jv = new JSONObject();
		Integer userId = getUserId(request);
		User user = userService.getUserById(userId);
		Comment comment = new Comment();
		comment.setCommentContent(commentContent);
		comment.setCommentType(CommentType.CHUNAYI.value());
		comment.setCommentTypeId(commentTypeId);
		comment.setIsComment(Comment.COMMENTED);
		comment.setUserId(userId);
		comment.setUsername(user.getUsername());
		comment.setCommentTime((int) (System.currentTimeMillis() / 1000));
		comment.setHeadUrl(user.getHeadUrl());
		commentService.save(comment);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}

	@RequestMapping("/user/addGoodsComment.do")
	public void addGoodsComment(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "commentJson", required = true) String commentJson,
			@RequestParam(value = "receiptId", required = true) Integer receiptId) {
		Integer userId = getUserId(request);
		User user = userService.getUserById(userId);
		JSONObject jv = new JSONObject();
		List<Comment> commenList = formatComment(commentJson);
		Receipt r = receiptService.getReceiptById(receiptId);
		if (r != null
				&& !(r.getIsComment() != null && r.getIsComment() == Receipt.ISCOMMENTED)
				&& r.getUserId().equals(userId)) {// 订单未评价且是该用户的订单
			for (Comment comment : commenList) {
				Comment dbComment = commentService.getById(comment.getId());
				if (dbComment.getCommentType().equals(CommentType.GOOD.value())
						&& dbComment.getCommentTypeId().equals(
								comment.getCommentTypeId())
						&& dbComment.getUserId().equals(userId)) {// 是改商品且改评价类型是商品评价且是改用户的
					if (!StringUtils.isEmpty(comment.getCommentContent())) {
						dbComment
								.setCommentContent(comment.getCommentContent());
					}
					if (!StringUtils.isEmpty(comment.getCommentPic())) {
						dbComment.setCommentPic(comment.getCommentPic());
					}
					dbComment.setIsComment(Comment.COMMENTED);
					dbComment
							.setCommentTime((int) (System.currentTimeMillis() / 1000));
					dbComment.setUsername(user.getUsername());
					dbComment.setHeadUrl(user.getHeadUrl());
					commentService.update(dbComment);
				}
			}
			// 修改订单状态
			r.setIsComment(Receipt.ISCOMMENTED);
			receiptService.update(r);
			jv.put("code", Const.SUCCESS);
		} else {
			jv.put("code", Const.HAS_COMMENT);
		}
		Utils.writeBack(request, response, jv);

	}

	@RequestMapping("/admin/getCommentList.do")
	public void getByCommentList(
			HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "commentType", required = true) Integer commentType,
			@RequestParam(value = "commentTypeId", required = true) Integer commentTypeId) {
		JSONObject jv = new JSONObject();
		List<Comment> commentList = commentService.getByTypeAndTypeId(
				commentType, commentTypeId);
		jv.put("info", commentList);
		Utils.writeBack(request, response, jv);
	}
	@RequestMapping("/admin/replyComment.do")
	public void replyComment(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id,
			@RequestParam(value="commentReply",required=true)String commentReply){
		JSONObject jv = new JSONObject();
		Comment comment =commentService.getById(id);
		if(comment!=null&&comment.getIsComment().equals(Comment.COMMENTED)){
			comment.setCommentReply(commentReply);
			comment.setReplyTime((int)(System.currentTimeMillis()/1000));
			commentService.update(comment);
			jv.put("code", Const.SUCCESS);
		}else{
			jv.put("code", Const.DATA_ERROR);
		}
		Utils.writeBack(request, response, jv);
	}
	
	@RequestMapping("/admin/removeComment.do")
	public void removeComment(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(value="id",required=true)Integer id){
		JSONObject jv = new JSONObject();
		commentService.del(id);
		jv.put("code", Const.SUCCESS);
		Utils.writeBack(request, response, jv);
	}
	public List<Comment> formatComment(String json) {
		JSONObject jv = JSONObject.parseObject(json);
		String info = jv.getString("commentJson");
		List<Comment> list = JSONArray.parseArray(info, Comment.class);
		return list;
	}
}
