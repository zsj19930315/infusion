package cn.org.fjiot.infusion.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.org.fjiot.infusion.enums.Resource;

@Entity
@Table(name = "sj_permission")
public class Permission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String permission;
	
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Resource resource = Resource.MENU;
	
	private String url;
	
	private Long parentId;
	
	private Long parentIds;
	
	private Boolean avaliable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Long getParentIds() {
		return parentIds;
	}

	public void setParentIds(Long parentIds) {
		this.parentIds = parentIds;
	}

	public Boolean getAvaliable() {
		return avaliable;
	}

	public void setAvaliable(Boolean avaliable) {
		this.avaliable = avaliable;
	}

}
