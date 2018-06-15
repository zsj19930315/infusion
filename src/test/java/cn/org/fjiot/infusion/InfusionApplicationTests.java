package cn.org.fjiot.infusion;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
	private SecurityManager manage;			// error
	
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.webAppContextSetup(context).build();
//		SecurityUtils.setSecurityManager(manage);
	}

	@Test
	public void contextLoads() throws Exception {
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("username", "张三");
		params.add("password", "123456");
		mvc.perform(MockMvcRequestBuilders.post("/user/login").params(params).accept(MediaType.APPLICATION_JSON_UTF8))
			.andDo(MockMvcResultHandlers.print())
			.andReturn();
	}

}
