package org.cristian;

import java.io.Serializable;

public class FileData implements Serializable {
    private String command;
    private String filename;
    private byte[] fileContent;

    public FileData() {
    }

    public FileData(String command, String filename, byte[] fileContent) {
        this.command = command;
        this.filename = filename;
        this.fileContent = fileContent;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "command='" + command + '\'' +
                ", filename='" + filename + '\'' +
                '}';
    }
}
