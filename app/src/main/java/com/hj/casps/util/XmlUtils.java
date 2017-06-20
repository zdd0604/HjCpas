package com.hj.casps.util;

import android.content.Context;
import android.content.res.AssetManager;

import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2017/5/22.
 */

public class XmlUtils {
    /**
     * Dom4j方式，解析 XML
     */
    public static List<MenuBean> dom4jXMLResolve(Context context) {
        StringWriter xmlWriter = new StringWriter();
            List<MenuBean> menusList = null;
        try {
            AssetManager assets = context.getAssets();
            InputStream is = assets.open("menu.xml");
            SAXReader reader = new SAXReader();
            org.dom4j.Document doc = reader.read(is);
            MenuBean menu = null;
            StringBuffer xmlHeader = new StringBuffer();
            Element eleRoot = doc.getRootElement();        // 获得root根节点，引用类为 org.dom4j.Element
            String attrDircode = eleRoot.attributeValue("key");
            String attrdirname = eleRoot.attributeValue("string");
//            String attrDirvc = eleRoot.attributeValue("dirvc");
//            String attrIcon = eleRoot.attributeValue("icon");
            xmlHeader.append("root").append("\t\t");
            xmlHeader.append(attrDircode).append("\t");
            xmlHeader.append(attrdirname).append("\t");
//            xmlHeader.append(attrDirvc).append("\t");
//            xmlHeader.append(attrIcon).append("\n");
            menusList = new ArrayList<MenuBean>();
            // 获取root子节点，即person
            Iterator<Element> iter = eleRoot.elementIterator();
            for (; iter.hasNext(); ) {
                Element elePerson = (Element) iter.next();
                if ("dict".equals(elePerson.getName())) {
                    // 获取menu子节点，即id、name、blog
                                 menu = new MenuBean();
                    Iterator<Element> innerIter = elePerson.elementIterator();
                    for (; innerIter.hasNext(); ) {
                        Element ele = (Element) innerIter.next();
                        if ("dircode".equals(ele.getName())) {
                            String dircode = ele.getText();
                            menu.setDircode(dircode);
                        } else if ("dirname".equals(ele.getName())) {
                            String dirname = ele.getText();
                            menu.setDirname(dirname);
                        } else if("dirvc".equals(ele.getName())) {
                            String dirvc = ele.getText();
                            menu.setDirvc(dirvc);
                        } else if ("icon".equals(ele.getName())) {
                            String icon = ele.getText();
                            menu.setIcon(icon);
                        }
                    }
                    menusList.add(menu);
                    menu = null;
                }
            }
            xmlWriter.append(xmlHeader);
            int menusLen = menusList.size();
            for (int i = 0; i < menusLen; i++) {
                xmlWriter.append(menusList.get(i).toString());
            }
            System.out.println("xml====" + xmlWriter.toString());
//            System.out.println("xml====" + xmlWriter.toString());

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return menusList;
    }

    public static class MenuBean {

        private String dircode;
        private String dirname;
        private String dirvc;
        private String icon;

        @Override
        public String toString() {
            return "MenuBean{" +
                    "dircode='" + dircode + '\'' +
                    ", dirname='" + dirname + '\'' +
                    ", dirvc='" + dirvc + '\'' +
                    ", icon='" + icon + '\'' +
                    '}';
        }

        public String getDircode() {
            return dircode;
        }

        public void setDircode(String dircode) {
            this.dircode = dircode;
        }

        public String getDirname() {
            return dirname;
        }

        public void setDirname(String dirname) {
            this.dirname = dirname;
        }

        public String getDirvc() {
            return dirvc;
        }

        public void setDirvc(String dirvc) {
            this.dirvc = dirvc;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
