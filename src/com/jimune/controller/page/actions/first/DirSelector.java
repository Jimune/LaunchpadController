package com.jimune.controller.page.actions.first;

import com.jimune.controller.SharedVars;
import com.jimune.controller.page.LaunchAction;
import com.jimune.controller.page.LaunchPageHandler;
import com.jimune.controller.util.KeyColor;
import com.jimune.controller.util.Utils;

import javax.swing.*;
import java.io.*;

public class DirSelector extends LaunchAction {

    JFileChooser fc;

    public DirSelector() {
        super(5, 7, KeyColor.ORANGE_HIGH);
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    @Override
    public void press() {
        if (selectFrom()) {
            if (selectTo()) {
                LaunchPageHandler.curPage.removeAction(6, 7);
                LaunchPageHandler.curPage.addAction(new LaunchAction(6, 7, KeyColor.GREEN_HIGH) {

                    @Override
                    public void press() {
                        try {
                            copyFolder(SharedVars.file_Dirs[0], SharedVars.file_Dirs[1]);
                        } catch (Exception e) {

                        }
                    }

                    @Override
                    public void release() {

                    }

                    private void copyFolder(File src, File dest) throws IOException {
                        if (!src.exists()) {
                            System.out.println("Source does not exist in the copy mechanism!");
                            return;
                        }

                        if (src.isDirectory()) {

                            if (!dest.exists()) {
                                dest.mkdir();
                                System.out.println("Created directory " + dest);
                            }

                            String files[] = src.list();

                            for (String file : files) {
                                File srcFile = new File(src, file);
                                File destFile = new File(dest, file);

                                copyFolder(srcFile, destFile);
                            }

                        } else {
                            InputStream in = new FileInputStream(src);
                            OutputStream out = new FileOutputStream(dest);

                            byte[] buffer = new byte[1024];
                            int length;

                            while ((length = in.read(buffer)) > 0) {
                                out.write(buffer, 0, length);
                            }

                            in.close();
                            out.close();
                            System.out.println("File copied from " + src + " to " + dest);
                        }
                    }
                });

                Utils.setPadFromPage(LaunchPageHandler.curPage);
                super.setColor(KeyColor.YELLOW_HIGH);
            }
        }
    }

    @Override
    public void release() {

    }

    private boolean selectFrom() {
        int returnval = fc.showDialog(null, "Select From");

        if (returnval == JFileChooser.APPROVE_OPTION) {
            SharedVars.file_Dirs[0] = fc.getSelectedFile();

            System.out.println("Chose source file/directory: " + SharedVars.file_Dirs[0].getAbsolutePath());

            return true;
        }

        return false;
    }

    private boolean selectTo() {
        int returnval = fc.showDialog(null, "Select To");

        if (returnval == JFileChooser.APPROVE_OPTION) {
            if (SharedVars.file_Dirs[0].getAbsolutePath() == fc.getSelectedFile().getAbsolutePath()) {
                return selectTo();
            }

            SharedVars.file_Dirs[1] = fc.getSelectedFile();

            System.out.println("Chose destination file/directory: " + SharedVars.file_Dirs[1].getAbsolutePath());

            return true;
        }

        return false;
    }

}
