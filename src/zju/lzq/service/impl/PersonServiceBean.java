package zju.lzq.service.impl;

import zju.lzq.dao.PersonDao;
import zju.lzq.service.PersonService;

public class PersonServiceBean implements PersonService {

	private PersonDao personDao;

	public PersonDao getPersonDao() {
		return personDao;
	}

	public void setPersonDao(PersonDao personDao) {
		this.personDao = personDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see zju.lzq.service.impl.PersonService#save()
	 */
	public void save() {
		personDao.add();
	}

}
