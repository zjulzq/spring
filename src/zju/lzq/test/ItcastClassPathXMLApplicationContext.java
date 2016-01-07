package zju.lzq.test;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 * ���ǲ��Ͱ�����
 * 
 * @author Li Zhiqiang
 * 
 */
public class ItcastClassPathXMLApplicationContext {

	private List<BeanDefinition> beanDefines = new ArrayList<BeanDefinition>();
	private Map<String, Object> sigletons = new HashMap<String, Object>();

	public ItcastClassPathXMLApplicationContext(String fileName) {
		this.readXML(fileName);
		this.instanceBeans();
		this.injectObject();
	}

	/**
	 * Ϊbean���������ע��ֵ
	 */
	private void injectObject() {
		for (BeanDefinition beanDefinition : beanDefines) {
			Object bean = sigletons.get(beanDefinition.getId());
			if (bean != null) {
				try {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(
							bean.getClass()).getPropertyDescriptors();
					for (PropertyDefinition propertyDefinition : beanDefinition
							.getPropertys()) {
						for (PropertyDescriptor properdesc : ps) {
							if (propertyDefinition.getName().equals(
									properdesc.getName())) {
								Method setter = properdesc.getWriteMethod();// ��ȡ���Ե�setter����
								if (setter != null) {
									Object value = null;
									if (propertyDefinition.getRef() != null
											&& propertyDefinition.getRef() != "") {
										value = sigletons
												.get(propertyDefinition
														.getRef());
									} else {
										value = ConvertUtils.convert(
												propertyDefinition.getValue(),
												properdesc.getPropertyType());
									}
									setter.setAccessible(true);
									setter.invoke(bean, value);// �����ö���ע�뵽������
								}
								break;
							}
						}
					}
				} catch (Exception e) {

				}
			}
		}

	}

	/**
	 * ���bean��ʵ����
	 */
	private void instanceBeans() {
		for (BeanDefinition beanDefine : beanDefines) {

			if (beanDefine.getClassName() != null
					&& beanDefine.getClassName() != "") {

				try {
					sigletons.put(beanDefine.getId(),
							Class.forName(beanDefine.getClassName())
									.newInstance());
				} catch (InstantiationException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * ��ȡ�����ļ�
	 * 
	 * @param fileName
	 */
	private void readXML(String fileName) {
		SAXReader saxReader = new SAXReader();
		Document document = null;

		try {
			URL xmlpath = this.getClass().getClassLoader()
					.getResource(fileName);
			document = saxReader.read(xmlpath);
			Map<String, String> nsMap = new HashMap<String, String>();
			nsMap.put("ns", "http://www.springframework.org/schema/beans");// ���������ռ�
			XPath xsub = document.createXPath("//ns:beans/ns:bean");// ����beans/bean��ѯ·��
			xsub.setNamespaceURIs(nsMap);// ���������ռ�
			List<Element> beans = xsub.selectNodes(document);// ��ȡ�ĵ�������bean�ڵ�
			for (Element element : beans) {
				String id = element.attributeValue("id");// ��ȡid����
				String clazz = element.attributeValue("class");// ��ȡclass����
				BeanDefinition beanDefine = new BeanDefinition(id, clazz);
				XPath propertySub = element.createXPath("ns:property");
				propertySub.setNamespaceURIs(nsMap);
				List<Element> propertys = propertySub.selectNodes(element);
				List<PropertyDefinition> propertyDefinitions = new ArrayList<PropertyDefinition>();
				for (Element property : propertys) {
					String propertyName = property.attributeValue("name");
					String propertyRef = property.attributeValue("ref");
					String propertyValue = property.attributeValue("value");
					PropertyDefinition propertyDefinition = new PropertyDefinition(
							propertyName, propertyRef, propertyValue);
					propertyDefinitions.add(propertyDefinition);
				}
				beanDefine.setPropertys(propertyDefinitions);
				beanDefines.add(beanDefine);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ��ȡbeanʵ��
	 */
	public Object getBean(String beanName) {
		return sigletons.get(beanName);
	}
}
