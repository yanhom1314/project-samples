/**
 * Copyright (c) 2011-2015, James Zhan ղ�� (jfinal@126.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jfinal.kit;

import java.io.File;
import java.net.URL;

/**
 * new File("..\path\abc.txt") �е�����������ȡ·���ķ���
 * 1�� getPath() ��ȡ���·��������   ..\path\abc.txt
 * 2�� getAbslutlyPath() ��ȡ����·���������ܰ��� ".." �� "." �ַ�������  D:\otherPath\..\path\abc.txt
 * 3�� getCanonicalPath() ��ȡ����·������������ ".." �� "." �ַ�������  D:\path\abc.txt
 */
public class PathKit {

    private static String webRootPath;
    private static String rootClassPath;

    @SuppressWarnings("rawtypes")
    public static String getPath(Class clazz) {
        String path = clazz.getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getPath(Object object) {
        String path = object.getClass().getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getRootClassPath() {
        if (rootClassPath == null) {
            try {
                String path = PathKit.class.getClassLoader().getResource("").toURI().getPath();
                rootClassPath = new File(path).getAbsolutePath();
            } catch (Exception e) {
                String path = PathKit.class.getClassLoader().getResource("").getPath();
                rootClassPath = new File(path).getAbsolutePath();
            }
        }
        return rootClassPath;
    }

    public void setRootClassPath(String rootClassPath) {
        PathKit.rootClassPath = rootClassPath;
    }

    public static String getPackagePath(Object object) {
        Package p = object.getClass().getPackage();
        return p != null ? p.getName().replaceAll("\\.", "/") : "";
    }

    public static File getFileFromJar(String file) {
        throw new RuntimeException("Not finish. Do not use this method.");
    }

    public static String getWebRootPath() {
        if (webRootPath == null)
            webRootPath = detectWebRootPath();
        return webRootPath;
    }

    public static void setWebRootPath(String webRootPath) {
        if (webRootPath == null)
            return;

        if (webRootPath.endsWith(File.separator))
            webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
        PathKit.webRootPath = webRootPath;
    }

    private static String detectWebRootPath() {
        try {
            URL url = PathKit.class.getResource("/");

            //log
            if (url != null) {
                System.out.print("url:" + url);
                if (url.toURI() != null) {
                    System.out.print("uri:" + url.toURI().getPath());
                }
            }

            String path = url != null ? url.toURI().getPath() : System.getProperty("user.dir");

            return new File(path).getParentFile().getParentFile().getCanonicalPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

	/*
    private static String detectWebRootPath() {
		try {
			String path = PathKit.class.getResource("/").getFile();
			return new File(path).getParentFile().getParentFile().getCanonicalPath();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	*/
}


