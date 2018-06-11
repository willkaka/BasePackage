package com.base.function;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import javax.tools.JavaCompiler.CompilationTask;

/**
 * @author zhengtian
 * 
 * @date 2012-4-17 ����07:24:24
 */
@SuppressWarnings("all")
public class DynamicCompilerUtil {

	/**
	 * ����java�ļ�
	 * 
	 * @param filePath
	 *            �ļ�����Ŀ¼����ΪĿ¼���Զ��ݹ���룩
	 *            ��Ҫ�����.java�ļ�·������Ϊ�ļ���ֻ�����.java�ļ�����ΪĿ¼������Ŀ¼�µ�����.java�ļ�
	 * @param sourceDir
	 *            javaԴ�ļ����Ŀ¼
	 * @param targetDir
	 *            �����class���ļ����Ŀ¼
	 * @param diagnostics
	 *            ��ű�������еĴ�����Ϣ
	 * @return
	 * @throws Exception
	 */
	public static boolean compiler(String filePath, String sourceDir, String targetDir, DiagnosticCollector<JavaFileObject> diagnostics)
			throws Exception {
		// ��ȡ������ʵ��
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		// ��ȡ��׼�ļ�������ʵ��
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		try {
			if ((filePath == null || filePath == "") && 
				(sourceDir == null || sourceDir == "") && 
				(targetDir == null || targetDir == "")) {
				return false;
			}
			// �õ�filePathĿ¼�µ�����javaԴ�ļ�
			File sourceFile = new File(filePath);
			List<File> sourceFileList = new ArrayList<File>();
			getSourceFiles(sourceFile, sourceFileList);
			// û��java�ļ���ֱ�ӷ���
			if (sourceFileList.size() == 0) {
				System.out.println(filePath + "Ŀ¼�²��Ҳ����κ�java�ļ�");
				return false;
			}
			// ��ȡҪ����ı��뵥Ԫ
			Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromFiles(sourceFileList);
			/**
			 * ����ѡ����趨javac�����ִ�в�����
			 * javac���������-d ָ���������ɵ����ļ���λ�� ����.class�ļ��ķ���λ�á�
			 * -sourcepathѡ����Ƕ���javaԴ�ļ��Ĳ���Ŀ¼�� ��.javaԴ�ļ��Ĳ���Ŀ¼��
			 * -classpathѡ����Ƕ���class�ļ��Ĳ���Ŀ¼���ڱ���java�ļ�ʱ�����������Զ���ȥѰ��java�ļ����õ�������javaԴ�ļ�����class��
			 * -verbose����йر���������ִ�еĲ�������Ϣ
			 */
			Iterable<String> options = Arrays.asList("-d", targetDir, "-sourcepath", sourceDir, "-verbose");
			
			/* JavaCompiler.getTask()������
			 * out - �������Ա���������������� Writer�����Ϊ null����ʹ�� System.err
			 * fileManager - �ļ������������Ϊ null����ʹ�ñ������ı�׼�ļ�������
			 * diagnosticListener - ��������������Ϊ null����ʹ�ñ�������Ĭ�Ϸ������������Ϣ
			 * options - ������ѡ� null ��ʾû��ѡ��
			 * classes - �����ƣ�����ע�ʹ����� null ��ʾû��������
			 * compilationUnits - Ҫ����ı��뵥Ԫ�� null ��ʾû�б��뵥Ԫ
			 */
			CompilationTask compilationTask = compiler.getTask(null, fileManager, diagnostics, options, null, compilationUnits);
			// ���б�������
			return compilationTask.call();
		} finally {
			fileManager.close();
		}
	}

	/**
	 * ���Ҹ�Ŀ¼�µ����е�java�ļ�
	 * 
	 * @param sourceFile
	 * @param sourceFileList
	 * @throws Exception
	 */
	private static void getSourceFiles(File sourceFile, List<File> sourceFileList) throws Exception {
		if (sourceFile.exists() && sourceFileList != null) {// �ļ�����Ŀ¼�������
			if (sourceFile.isDirectory()) {// ��file����ΪĿ¼
				// �õ���Ŀ¼����.java��β���ļ�����Ŀ¼
				File[] childrenFiles = sourceFile.listFiles(new FileFilter() {
					public boolean accept(File pathname) {
						if (pathname.isDirectory()) {
							return true;
						} else {
							String name = pathname.getName();
							return name.endsWith(".java") ? true : false;
						}
					}
				});
				// �ݹ����
				for (File childFile : childrenFiles) {
					getSourceFiles(childFile, sourceFileList);
				}
			} else {// ��file����Ϊ�ļ�
				sourceFileList.add(sourceFile);
			}
		}
	}

	public static void main(String[] args) {
		try {
			// ����F:\\���Ź���\\SDL�ļ�\\sdl\\srcĿ¼�µ�����java�ļ�
			String filePath = "F:\\���Ź���\\SDL�ļ�\\sdl\\src";
			String sourceDir = "F:\\���Ź���\\SDL�ļ�\\sdl\\src";
			String targetDir = "F:\\���Ź���\\SDL�ļ�\\sdl\\classes";
			DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
			boolean compilerResult = compiler(filePath, sourceDir, targetDir, diagnostics);
			if (compilerResult) {
				System.out.println("����ɹ�");
			} else {
				System.out.println("����ʧ��");
				for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
					// System.out.format("%s[line %d column %d]-->%s%n", diagnostic.getKind(), diagnostic.getLineNumber(),
					// diagnostic.getColumnNumber(),
					// diagnostic.getMessage(null));
					System.out.println(diagnostic.getMessage(null));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


