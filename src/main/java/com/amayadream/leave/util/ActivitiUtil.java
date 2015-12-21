package com.amayadream.leave.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.dom4j.*;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NAME   :  Activiti-demo/com.amayadream.demo.util
 * Author :  Amayadream
 * Date   :  2015.12.18 22:02
 * TODO   :  工作流工具类
 */
public class ActivitiUtil {

    private String name;    //userTask名称
    private String groups;  //角色分组
    private String tools;   //使用工具

    /**
     * 获取字符串形式的模型xml,返回null则代表失败
     * @param modelId 模型编号
     * @return 字符串形式的模型xml
     */
    public String getStringXmlByByte(byte[] source) {
        try {
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            JsonNode editorNode = new ObjectMapper().readTree(source);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);
            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                return null;
            }
            byte[] exportBytes = null;
            BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
            exportBytes = xmlConverter.convertToXML(bpmnModel);
            return new String(exportBytes, "UTF-8");
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据字符串类型的xml解析出步骤名,角色以及工具
     * @param xml
     * @return
     */
    public List<ActivitiUtil> getInfoByStringXml(String xml){
        try {
            Document document = null;      //读取字符串形式的xml,转化成org.dom4j.document
            document = DocumentHelper.parseText(xml);
            //获取根节点元素对象
            Element definitions = document.getRootElement();    //根节点definitions
            Element process = definitions.element("process");   //二层节点process
            List<Element> userTasks = process.elements("userTask");     //用户任务,其中可以读取activiti:candidateGroups的属性,为角色组
            List<ActivitiUtil> list = new ArrayList<ActivitiUtil>();
            for (Element node : userTasks) {
                ActivitiUtil entity = new ActivitiUtil();
                Attribute name = node.attribute("name");
                Attribute groups = node.attribute("candidateGroups");
                Element documentation = node.element("documentation");
                if(name != null){
                    entity.setName(name.getStringValue());
                }
                if(groups != null){
                    entity.setGroups(groups.getStringValue());
                }
                if(documentation != null){
                    entity.setTools(documentation.getTextTrim());
                }
                list.add(entity);
            }
            return list;
        } catch (DocumentException e) {     //返回null
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 接收字符串类型的xml,然后设置每个userTask的角色以及工具,然后返回String
     * @param xml       字符串类型的xml
     * @param name      userTask名称
     * @param groups    角色
     * @param tools     工具
     * @return
     */
    public String setInfoByStringXml(String xml, String[] name, String[] groups, String[] tools){
        try {
            Document document = null;      //读取字符串形式的xml,转化成org.dom4j.document
            document = DocumentHelper.parseText(xml);
            //获取根节点元素对象
            Element definitions = document.getRootElement();    //根节点definitions
            Element process = definitions.element("process");   //二层节点process
            List<Element> userTasks = process.elements("userTask");     //用户任务,其中可以读取activiti:candidateGroups的属性,为角色组
            for (Element node : userTasks) {
                for(int i=0;i<=name.length-1;i++){
                    if(name[i].equals(node.attribute("name").getStringValue())){
                        if(node.attribute("candidateGroups") != null){
                            node.attribute("candidateGroups").setValue(groups[i]);
                        }else{
                            node.addAttribute("activiti:candidateGroups",groups[i]);
                        }
                        if(node.element("documentation") != null){
                            node.element("documentation").setText(tools[i]);
                        }else{
                            node.addElement("documentation").setText(tools[i]);
                        }
                    }
                }
            }
//            writerDocumentToNewFile(document);
            return document.asXML();    //将dom4j的document对象转化成String
        } catch (DocumentException e) {     //返回null
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("写入错误!");
            return null;
        }
    }

    //document写入新的文件
    public void writerDocumentToNewFile(Document document)throws Exception{
        //输出格式
        OutputFormat format = OutputFormat.createPrettyPrint();
        //设置编码
        format.setEncoding("UTF-8");
        //XMLWriter 指定输出文件以及格式
        XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File("C:\\Users\\Administrator\\Desktop\\test.xml")),"UTF-8"), format);
        //写入新文件
        writer.write(document);
        writer.flush();
        writer.close();
    }

    /**
     * 将document对象写入文件
     * @param document
     * @param url
     * @return
     */
    public boolean writeDocumentToFile(Document document, String url){
        try {
            //输出格式
            OutputFormat format = OutputFormat.createPrettyPrint();
            //设置编码
            format.setEncoding("utf-8");
            //XMLWriter指定输出文件以及格式
            XMLWriter writer = new XMLWriter(new OutputStreamWriter(new FileOutputStream(new File(url)),"utf-8"), format);
            writer.write(document);
            writer.flush();
            writer.close();
            return true;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return false;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }
}
