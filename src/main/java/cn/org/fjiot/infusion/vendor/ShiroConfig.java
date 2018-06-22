package cn.org.fjiot.infusion.vendor;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
	
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
		ShiroFilterFactoryBean sffb = new ShiroFilterFactoryBean();
		sffb.setSecurityManager(securityManager);
		Map<String, String> fcdm = new LinkedHashMap<>();
		fcdm.put("/user/u", "anon");
		fcdm.put("/user/upload", "anon");
		fcdm.put("/static/**", "anon");
		fcdm.put("/user/regist", "anon");
		fcdm.put("/app/user/regist", "anon");
		fcdm.put("/user/logout", "logout");
		fcdm.put("/app/user/logout", "logout");
		fcdm.put("/user/login", "anon");
		fcdm.put("/app/user/login", "anon");
		fcdm.put("/**", "authc");
		sffb.setFilterChainDefinitionMap(fcdm);
		sffb.setLoginUrl("/user/toLogin");
		sffb.setSuccessUrl("/index");
		sffb.setUnauthorizedUrl("/403");
		return sffb;
	}
	
	@Bean
	public SecurityManager securityManager(Realm realm) {
		DefaultWebSecurityManager dwsm = new DefaultWebSecurityManager();
		dwsm.setRealm(realm);
		return dwsm;
	}
	
	@Bean
	public Realm realm(CredentialsMatcher credentialsMatcher) {
		InfusionRealm ir = new InfusionRealm();
		ir.setCredentialsMatcher(credentialsMatcher);
		return ir;
	}
	
	@Bean
	public CredentialsMatcher credentialsMatcher() {
		HashedCredentialsMatcher hcm = new HashedCredentialsMatcher();
		hcm.setHashAlgorithmName("md5");
		hcm.setHashIterations(2);
		return hcm;
	}

}
