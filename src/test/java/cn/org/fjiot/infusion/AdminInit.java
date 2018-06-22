package cn.org.fjiot.infusion;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cn.org.fjiot.infusion.dao.PermissionDao;
import cn.org.fjiot.infusion.dao.RoleDao;
import cn.org.fjiot.infusion.dao.RolePermissionDao;
import cn.org.fjiot.infusion.dao.UserDao;
import cn.org.fjiot.infusion.dao.UserRoleDao;
import cn.org.fjiot.infusion.entity.Permission;
import cn.org.fjiot.infusion.entity.Role;
import cn.org.fjiot.infusion.entity.RolePermission;
import cn.org.fjiot.infusion.entity.User;
import cn.org.fjiot.infusion.entity.UserRole;
import cn.org.fjiot.infusion.enums.Resource;
import cn.org.fjiot.infusion.enums.Sex;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminInit {
	
	@Autowired
	private PermissionDao 		permissionDao;		// test
	
	@Autowired
	private RoleDao 			roleDao;			// test
	
	@Autowired
	private RolePermissionDao	 rolePermissionDao;	//test
	
	@Autowired
	private UserDao				 userDao;			// test
	
	@Autowired
	private UserRoleDao 		 userRoleDao;		// test
	
	@Test
	public void init() {
		List<Permission> adminPs = createAdminPs();
		Role adminR = createAdminR();
		for (Permission adminP : adminPs) {
			RolePermission adminRP = new RolePermission();
			adminRP.setRoleId(adminR.getId());
			adminRP.setPermissionId(adminP.getId());
			rolePermissionDao.saveAndFlush(adminRP);
		}
		User adminU = createAdminU();
		UserRole adminUR = new UserRole();
		adminUR.setUserId(adminU.getId());
		adminUR.setRoleId(adminR.getId());
		userRoleDao.saveAndFlush(adminUR);
	}
	
	private List<Permission> createAdminPs() {
		String[] ps = {"permission:view", "permission:add", "permission:delete", "permission:select", "permission:update"};
		Long pid = null;
		List<Permission> permissions = new ArrayList<>();
		for (String permission : ps) {
			Resource r;
			Long parentId = null;
			String parentIds;
			if (permission.endsWith(":view")) {
				r = Resource.MENU;
				parentId = 0L;
				parentIds = "0/";
			} else {
				r = Resource.BUTTON;
				parentId = pid;
				parentIds = "0/" + pid;
			}
			Permission adminP = new Permission();
			adminP.setPermission(permission);
			adminP.setDescription(String.format("admin操作权限:%s", permission));
			adminP.setResource(r);
			adminP.setParentId(parentId);
			adminP.setParentIds(parentIds);
			adminP.setAvaliable(true);
			adminP.setUrl("");
			permissions.add(permissionDao.saveAndFlush(adminP));
			if (parentId == 0L) {
				pid = adminP.getId();
			}
		}
		return permissions;
	}
	
	private Role createAdminR() {
		Role adminR = new Role();
		adminR.setRole("admin");
		adminR.setDescription("最高权限");
		adminR.setAvaliable(true);
		return roleDao.saveAndFlush(adminR);
	}
	
	private User createAdminU() {
		String algorithmName = "md5";
		Integer hashIterations = 2;
		String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		String password = new SimpleHash(algorithmName, "admin", salt, hashIterations).toString();
		User adminU = new User();
		adminU.setName("最高权限账号");
		adminU.setUsername("admin");
		adminU.setPassword(password);
		adminU.setImage(null);
		adminU.setJob(null);
		adminU.setMotto(null);
		adminU.setPhone(null);
		adminU.setSalt(salt);
		adminU.setSex(Sex.UDF);
		adminU.setDepartmentId(0L);
		return userDao.saveAndFlush(adminU);
	}

}
