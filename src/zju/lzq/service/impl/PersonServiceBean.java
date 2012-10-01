package zju.lzq.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;

import zju.lzq.dao.PersonDao;
import zju.lzq.service.PersonService;

public class PersonServiceBean implements PersonService {

	private PersonDao personDao;
	private String name;
	private Integer id;

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see zju.lzq.service.impl.PersonService#save()
	 */
	public void save() {
		System.out.println(name);
		System.out.println(id);

		personDao.add();
	}

}
