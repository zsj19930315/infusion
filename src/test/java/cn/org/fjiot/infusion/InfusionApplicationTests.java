package cn.org.fjiot.infusion;

import javax.servlet.Filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.MockMvcSecurityAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.ConfigurableMockMvcBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfusionApplicationTests {
	
	private MockMvc mvc;
	
	@Autowired
	private WebApplicationContext context;
	
	@Autowired
	private DefaultSecurityManager sm;
	//org.apache.shiro.web.mgt.DefaultWebSecurityManager
	//org.apache.shiro.web.subject.support.WebDelegatingSubject
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
		SecurityUtils.setSecurityManager(context.getBean(SecurityManager.class));
//		DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(context);
//		try {			
//			builder.addFilters((Filter[]) context.getBean("shiroFilter"));
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//		}
//		MockMvcConfigurer c = new SharedHttpSessionConfigurer().beforeMockMvcCreated(ConfigurableMockMvcBuilder<ConfigurableMockMvcBuilder<B>>, context)
	}

	@Test
	public void contextLoads() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("username", "sb");
		params.add("password", "sb");
		mvc.perform(MockMvcRequestBuilders.post("/user/login").params(params).accept(MediaType.APPLICATION_JSON_UTF8))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}

}
