package Model;

public class CustomModel {
    private String channelName = null;
    private String channelUrl = null;

    public CustomModel(){

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
}
