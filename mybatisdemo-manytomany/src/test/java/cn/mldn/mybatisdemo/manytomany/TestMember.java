package cn.mldn.mybatisdemo.manytomany;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.junit.Test;

import cn.mldn.mybatisdemo.vo.Member;
import cn.mldn.mybatisdemo.vo.Role;
import cn.mldn.util.MybatisSessionFactory;

public class TestMember {
	@Test
	public void testEdit() {
		Member vo = new Member();
		vo.setMid("mybatis-crud426");
		vo.setName("fuck the world");
		Long rid[] = new Long[] { 7L, 8L, 9L };
		// 首先member信息要保存了才能追加角色信息
		if (MybatisSessionFactory.getSession().insert("cn.mldn.mapping.MemberNS.doUpdate", vo) > 0) {
			//先删除原有member与角色role的关系才能添加新的关系
			if (MybatisSessionFactory.getSession().delete("cn.mldn.mapping.RoleNS.doRemoveRoleByMember", vo.getMid()) > 0) {
				for (int i = 0; i < rid.length; i++) {
					Map<String, Object> map = new HashMap<>();
					map.put("rid", rid[i]);
					map.put("mid", vo.getMid());
					MybatisSessionFactory.getSession().insert("cn.mldn.mapping.RoleNS.doCreateMemberRole", map);
				}
			}
		}
		MybatisSessionFactory.getSession().commit();
	}

	@Test
	public void testGet() {
		Member member = MybatisSessionFactory.getSession().selectOne("cn.mldn.mapping.MemberNS.findById", "mldn");
		System.out.println(member);// 测试输出结果
		List<Role> listRoles = MybatisSessionFactory.getSession().selectList("cn.mldn.mapping.RoleNS.findAllByMember",
				"mldn");
		System.out.println(listRoles);
		List<String> allFlags = MybatisSessionFactory.getSession()
				.selectList("cn.mldn.mapping.RoleNS.findAllFlagByMember", "mldn");
		System.out.println(allFlags);
		Set<String> set = new HashSet<>();
		set.addAll(allFlags);
		System.out.println(set);
	}

	@Test
	public void testAdd() {
		Member vo = new Member();
		vo.setMid("mybatis-crud" + new Random().nextInt(1000));
		vo.setName("mybatis-spring");
		Long rid[] = new Long[] { 1L, 2L, 3L, 4L, 5L, 6L };
		// 必须先保证Member类的信息保存在member表中，保存之后才可以追加角色信息
		if (MybatisSessionFactory.getSession().insert("cn.mldn.mapping.MemberNS.doCreate", vo) > 0) {
			for (int i = 0; i < rid.length; i++) {
				Map<String, Object> map = new HashMap<>();
				map.put("mid", vo.getMid());
				map.put("rid", rid[i]);
				MybatisSessionFactory.getSession().insert("cn.mldn.mapping.RoleNS.doCreateMemberRole", map);
			}
		}
		MybatisSessionFactory.getSession().commit();
	}
}
