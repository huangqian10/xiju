package com.xiyoukeji.xiju.service;


import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.xiyoukeji.xiju.core.dao.BaseHibernateDao;
import com.xiyoukeji.xiju.core.enums.CommentType;
import com.xiyoukeji.xiju.model.Comment;

@Service
public class CommentService {
	@Autowired
	@Qualifier("hibernateDao")
	BaseHibernateDao<Comment,Integer> dao ;
	
	@Transactional
	public void save(Comment c){
		dao.save(c);
	}
	
	@Transactional
	public void update(Comment c){
		dao.update(c);
	}
	
	public List<Comment> getByTypeAndTypeId(Integer commentType,Integer typeId){
		DetachedCriteria dc = DetachedCriteria.forClass(Comment.class);
		dc.add(Restrictions.eq("commentType", commentType));
		dc.add(Restrictions.eq("commentTypeId", typeId));
		dc.add(Restrictions.eq("isComment", Comment.COMMENTED));
		dc.addOrder(Order.desc("commentTime"));
		return dao.list(dc);
	}
	
	public List<Comment> getByReceipt(Integer receiptId){
		DetachedCriteria dc = DetachedCriteria.forClass(Comment.class);
		dc.add(Restrictions.eq("receiptId", receiptId));
		return dao.list(dc);
	}
	
	public Comment getByUserIdAndTypeId(Integer userId,Integer typeId){
		DetachedCriteria dc =DetachedCriteria.forClass(Comment.class);
		dc.add(Restrictions.eq("userId", userId));
		dc.add(Restrictions.eq("commentTypeId", typeId));
		dc.add(Restrictions.eq("commentType",CommentType.GOOD.value()));
		List<Comment> commentList =dao.list(dc);
		for(Comment comment:commentList){
			if(!(comment.getIsComment()!=null&&comment.getIsComment().equals(Comment.COMMENTED))){
				return comment;
			}
		}
		return null;
	}
	
	public Comment getById(Integer id){
		return dao.get(id, Comment.class);
	}
	@Transactional
	public void del(Integer id){
		dao.delete(id, Comment.class);
	}
}
