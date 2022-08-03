package com.inthergroup;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;

/**
 * Goal which touches a timestamp file.
 *
 * @goal touch
 * @phase process-sources
 */
@Mojo(name = "countfiles")
public class CountFiles
        extends AbstractMojo {
    private static int folderCounter = 0;
    private static int filesCounter = 0;

    @Parameter(property = "msg", defaultValue = "from maven")
    private String msg;

    @Parameter(readonly = true, defaultValue = "${project}")
    private MavenProject mavenProject;

    public void execute()
            throws MojoExecutionException {
        String path = (String) (mavenProject.getCompileSourceRoots().get(0));
        displayDirectoryContents(new File(path));
        System.out.println("The project has: \n" + "Folders: " + folderCounter + "\nFiles: " + filesCounter);
    }


    public static void displayDirectoryContents(File dir) {
        File[] files = dir.listFiles();
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    folderCounter++;
                    System.out.println("Directory: " + file.getCanonicalPath());
                    displayDirectoryContents(file);
                } else {
                    filesCounter++;
                    System.out.println("File: " + file.getCanonicalPath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
