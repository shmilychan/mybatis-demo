package org.mybatisdemo.crud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import cn.mldn.util.MybatisSessionFactory;
import cn.mldn.vo.News;
import junit.framework.TestCase;

public class TestNews {
	@Test
	public void testCount() {
		long currentPage = 2;
		int lineSize = 2;
		String keyWord = "努力";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("column", "title");
		map.put("keyWord", "%" + keyWord + "%");
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		Long count = MybatisSessionFactory.getSession()
				.selectOne("cn.mldn.mapping.NewsNS.getAllCount", map);
		System.out.println(count);
	}
	@Test
	public void testAllSplit(){
		int currentPage = 2;
		int lineSize = 2;
		String keyWord = "努力";
		Map<String , Object> map = new HashMap<>();
		map.put("column", "title");
		map.put("keyWord", "%" + keyWord + "%");
		map.put("start", (currentPage - 1) * lineSize);
		map.put("lineSize", lineSize);
		List<News> list = MybatisSessionFactory.getSession().selectList("cn.mldn.mapping.NewsNS.findAllSplit", map);
		System.out.println(list);
	}
	@Test
	public void testAll() {
		List<News> all = MybatisSessionFactory.getSession()
				.selectList("cn.mldn.mapping.NewsNS.findAll");
		System.out.println(all);
		TestCase.assertTrue(all.size() > 0);
	}
	@Test
	public void testGet() {
		// 直接返回一个News的处理对象
		News vo = MybatisSessionFactory.getSession()
				.selectOne("cn.mldn.mapping.NewsNS.findById", 5L);
		System.out.println(vo);
		TestCase.assertNotNull(vo);
	}
	@Test
	public void testDelete() {
		int count = MybatisSessionFactory.getSession()
				.delete("cn.mldn.mapping.NewsNS.doDelete", 1L);
		MybatisSessionFactory.getSession().commit();
		MybatisSessionFactory.close();
		TestCase.assertEquals(count, 1);
	}
	@Test
	public void testEdit() {
		News vo = new News();
		vo.setNid(2L);
		vo.setTitle("死啦死啦地");
		vo.setNote("好好学习，天天向上。");
		int count = MybatisSessionFactory.getSession()
				.update("cn.mldn.mapping.NewsNS.doUpdate", vo);
		MybatisSessionFactory.getSession().commit();
		MybatisSessionFactory.close();
		System.out.println(vo);
		TestCase.assertEquals(count, 1);
	}
	@Test
	public void testInsert(){
		News vo = new News();
		vo.setTitle("努力写代码");
		vo.setNote("学习mybatis");
		int count = MybatisSessionFactory.getSession().insert("cn.mldn.mapping.NewsNS.doCreate", vo);
		MybatisSessionFactory.getSession().commit();
		MybatisSessionFactory.close();
		System.out.println(vo);
		TestCase.assertEquals(count, 1);
	}
}
