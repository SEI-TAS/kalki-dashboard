package models;

import java.io.File;


public class Device {

    private String deviceId;
    private String name;
    private String type;
    private String ipAddress;
    private String historySize;
    private String samplingRate;
    private String[] tags;
    private File policy;
    private String groupId;

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHistorySize() {
        return historySize;
    }

    public void setHistorySize(String historySize) {
        this.historySize = historySize;
    }

    public String getSamplingRate() {
        return samplingRate;
    }

    public void setSamplingRate(String samplingRate) {
        this.samplingRate = samplingRate;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public File getPolicy() {
        return policy;
    }

    public void setPolicy(File policy) {
        this.policy = policy;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}