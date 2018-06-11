package com.base.database;

import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.util.ZipSecureFile.ThresholdInputStream;

/**
 * ����
 * @author huangyuanwei
 *
 */
public class ClassUtil {
    private static final Log logger = LogFactory.getLog(ClassUtil.class);
    private static JavaCompiler compiler;
    static{
        compiler = ToolProvider.getSystemJavaCompiler();
    }

    /**
     * ��ȡjava�ļ�·��
     * @param file
     * @return
     */
    private static String getFilePath(String file){
        int last1 = file.lastIndexOf('/');
        int last2 = file.lastIndexOf('\\');
        return file.substring(0, last1>last2?last1:last2)+File.separatorChar;
    }

    /**
     * ����java�ļ�
     * @param ops �������
     * @param files �����ļ�
     */
    private static void javac(List<String> ops,String... files){
        StandardJavaFileManager manager = null;
        try{
            manager = compiler.getStandardFileManager(null, null, null);
            Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(files);
            JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, ops, null, it);
            task.call();
            if(logger.isDebugEnabled()){
                for(String file:files)
                    logger.debug("Compile Java File:" + file);
            }
        }catch(Exception e){
            logger.error(e);
        }finally{
            if(manager!=null){
                try {
                    manager.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * ����java�ļ�
     * @param file �ļ���
     * @param source java����
     * @throws Exception
     */
    private static void writeJavaFile(String file,String source)throws Exception{
        if(logger.isDebugEnabled()){
            logger.debug("Write Java Source Code to:"+file);
        }
        BufferedWriter bw = null;
        try{
            File dir = new File(getFilePath(file));
            if(!dir.exists())
                dir.mkdirs();
            bw = new BufferedWriter(new FileWriter(file));
            bw.write(source);
            bw.flush();
        }catch(Exception e){
            throw e;
        }finally{
            if(bw!=null){
                bw.close();
            }
        }
    }
    /**
     * ������
     * @param name ����
     * @return
     */
    private static Class<?> load(String name){
        Class<?> cls = null;
        ClassLoader classLoader = null;
        try{
            classLoader = ClassUtil.class.getClassLoader();
            cls = classLoader.loadClass(name);
            if(logger.isDebugEnabled()){
                logger.debug("Load Class["+name+"] by "+classLoader);
            }
        }catch(Exception e){
            logger.error(e);
        }
        return cls;
    }

    /**
     * ������벢������
     * @param filePath java����·��
     * @param source java����
     * @param clsName ����
     * @param ops �������
     * @return
     */
    public static Class<?> loadClass(String filePath,String source,String clsName,List<String> ops){
        try {
            /*writeJavaFile(CLASS_PATH+filePath,source);
            javac(ops,CLASS_PATH+filePath);*/
            
            writeJavaFile(filePath,source);
            javac(ops,filePath);
            return load(clsName);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }
    
    /**
     * ������벢������
     * @param filePath java����·��
     * @param source java����
     * @param clsName ����
     * @param ops �������
     * @return
     */
    public static Class<?> loadClass2(String filePath,String clsName,List<String> ops){
        try {
            /*writeJavaFile(CLASS_PATH+filePath,source);
            javac(ops,CLASS_PATH+filePath);*/
            
            javac(ops,filePath);
            return load(clsName);
        } catch (Exception e) {
            logger.error(e);
        }
        return null;
    }

    /**
     * �����෽��
     * @param cls ��
     * @param methodName ������
     * @param paramsCls ������������
     * @param params ��������
     * @return
     */
    public static Object invoke(Class<?> cls,String methodName,Class<?>[] paramsCls,Object[] params){
        Object result = null;
        try {
            Method method = cls.getDeclaredMethod(methodName, paramsCls);
            Object obj = cls.newInstance();
            result = method.invoke(obj, params);
        } catch (Exception e) {
            logger.error(e);
        }
        return result;
    }
    
    
    public static void main(String args[]){
        StringBuilder sb = new StringBuilder();
        sb.append("package com.even.test;");
        sb.append("import java.util.Map;\nimport java.text.DecimalFormat;\n");
        sb.append("public class Sum{\n");
        sb.append("private final DecimalFormat df = new DecimalFormat(\"#.#####\");\n");
        sb.append("public Double calculate(Map<String,Double> data){\n");
        sb.append("double d = (30*data.get(\"f1\") + 20*data.get(\"f2\") + 50*data.get(\"f3\"))/100;\n");
        sb.append("return Double.valueOf(df.format(d));}}\n");
        //���ñ������
        ArrayList<String> ops = new ArrayList<String>();
        ops.add("-Xlint:unchecked");
        //������룬����class
        Class<?> cls = ClassUtil.loadClass("/com/even/test/Sum.java",sb.toString(),"com.even.test.Sum",ops);
        //׼����������
        Map<String,Double> data = new HashMap<String,Double>();
        data.put("f1", 10.0);
        data.put("f2", 20.0);
        data.put("f3", 30.0);
        //ִ�в��Է���
        Object result = ClassUtil.invoke(cls, "calculate", new Class[]{Map.class}, new Object[]{data});
        //������
        logger.debug(data);
        logger.debug("(30*f1+20*f2+50*f3)/100 = "+result);
    }
}
