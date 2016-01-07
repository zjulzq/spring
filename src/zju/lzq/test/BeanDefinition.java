package zju.lzq.test;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BeanDefinition {
	private String id;
	private String className;
	private List<PropertyDefinition> propertys = new ArrayList<PropertyDefinition>();

	public BeanDefinition() {
		super();

	}

	public BeanDefinition(String id, String className) {
		super();
		this.id = id;
		this.className = className;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<PropertyDefinition> getPropertys() {
		return propertys;
	}

	public void setPropertys(List<PropertyDefinition> propertys) {
		this.propertys = propertys;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.DEFAULT_STYLE);
	}

}
