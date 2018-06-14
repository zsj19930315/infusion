package cn.org.fjiot.infusion.vendor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
		sffb.setSecurityManager(securityManager);
		Map<String, String> fcdm = new LinkedHashMap<>();
		fcdm.put("/static/**", "anon");
		fcdm.put("/user/regist", "anon");
		fcdm.put("/app/user/regist", "anon");
		fcdm.put("/user/logout", "logout");
		fcdm.put("/app/user/logout", "logout");
		fcdm.put("/**", "authc");
		sffb.setFilterChainDefinitionMap(fcdm);
		sffb.setLoginUrl("/login");
		sffb.setSuccessUrl("/index");
		sffb.setUnauthorizedUrl("/403");
		return sffb;
	}

}
