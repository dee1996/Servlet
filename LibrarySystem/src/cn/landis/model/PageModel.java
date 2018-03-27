package cn.landis.model;

import java.util.List;


public class PageModel {
	private List<?> list;
	private Integer pageNo;
	private Integer pageSize;
	private Integer totalCount;

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	
	// 获取总页数
	public Integer getTotalPages(){
		if (totalCount % pageSize == 0) {
			return totalCount / pageSize;
		} else {
			return totalCount / pageSize + 1;
		}
	}
	
	// 返回第一页
	public Integer getFirstPage(){
		return 1;
	}
	
	// 返回上一页
	public Integer getPrevious(){
		if (this.pageNo == 1) {
			return 1;
		} else {
			return this.pageNo - 1;
		}
	}
	
	// 返回下一页
	public Integer getNextPage(){
		if (this.pageNo == getTotalPages()) {
			return getTotalPages();
		} else {
			return this.pageNo + 1;
		}
	}
	
	// 返回最后一页
	public Integer getLastPage(){
		return getTotalPages();
	}
}