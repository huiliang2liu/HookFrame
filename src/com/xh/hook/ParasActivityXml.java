package com.xh.hook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.xh.string.StringUtil;

/**
 * HookFrame com.xh.hook 2018 2018-4-23 下午4:30:22 instructions：
 * author:liuhuiliang email:825378291@qq.com
 **/

public final class ParasActivityXml {
	private List<Activity> activities;

	public ParasActivityXml() {
		// TODO Auto-generated constructor stub
		activities = new ArrayList<>();
	}

	public ParasActivityXml(InputStream is) {
		// TODO Auto-generated constructor stub
		try {
			activities = new ArrayList<>();
			DocumentBuilder documentBuilder = DocumentBuilderFactory
					.newInstance().newDocumentBuilder();
			Document document = documentBuilder.parse(is);
			NodeList list = document.getChildNodes();
			Node node = list.item(0);
			list = node.getChildNodes();
			for (int i = 0; i < list.getLength(); i++) {
				node = list.item(i);
				if ("value".equals(node.getNodeName())) {
					Activity activity = new Activity();
					NodeList value = node.getChildNodes();
					for (int j = 0; j < value.getLength(); j++) {
						Node valueNode = value.item(j);
						if ("activity".equals(valueNode.getNodeName())) {
							NodeList activityNode = valueNode.getChildNodes();
							activity.className = activityNode.item(0)
									.getNodeValue().trim();
						} else if ("package".equals(valueNode.getNodeName())) {
							NodeList activityNode = valueNode.getChildNodes();
							activity.packageName = activityNode.item(0)
									.getNodeValue().trim();
						}
					}
					activities.add(activity);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public void merge(ParasActivityXml xml) {
		if (xml != null)
			activities.addAll(xml.activities);
	}

	public String class2packageName(Class cl) {
		if (cl == null)
			return null;
		Activity activity = new Activity();
		activity.className = cl.getName();
		int index = activities.indexOf(activity);
		if (index != -1)
			return activities.get(index).packageName;
		return null;
	}

	private class Activity {
		String className;
		String packageName;

		@Override
		public boolean equals(Object o) {
			// TODO Auto-generated method stub
			if (o == null || StringUtil.isEmpty(className))
				return false;
			if (!o.getClass().equals(Activity.class))
				return false;
			Activity activity = (Activity) o;
			return className.equals(activity.className);
		}
	}
}
