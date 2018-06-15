package cn.org.fjiot.infusion.vendor;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import cn.org.fjiot.infusion.entity.User;
import cn.org.fjiot.infusion.exception.NotFoundException;
import cn.org.fjiot.infusion.service.UserService;

public class InfusionRealm extends AuthorizingRealm {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InfusionRealm.class);
	
	@Override
	public String getName() {
		return "infusionRealm";
	}
	
	@Autowired
	private UserService userService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = (String) token.getPrincipal();
		User user = null;
		try {
			user = userService.findOneByUsername(username);
		} catch (NotFoundException e) {
			LOGGER.error(e.getMessage());
		}
		if (null == user) {
			return null;
		}
		SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
		return sai;
	}

}
