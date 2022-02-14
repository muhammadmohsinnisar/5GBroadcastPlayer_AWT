package Model;

import com.bumptech.glide.load.engine.Resource;

public class CustomModel {
    private String channelName = null;
    private String channelUrl = null;
    private String channelImage = null;

    public CustomModel(){
    }

    public CustomModel(String name, String url) {
    this.channelName = name;
    this.channelUrl = url;
    }

    public String getChannelName(){
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelUrl(){
        return channelUrl;
    }

    public void setChannelUrl(String channelUrl){
        this.channelUrl = channelUrl;
    }

    public String getChannelImage() {
        return channelImage;
    }

    public void setChannelImage(String channelImage) {
        this.channelImage = channelImage;
    }
}
