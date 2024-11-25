package server;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {

    private String type;
    private List<String> responseList;

    public Response(String type, List<String> responseList) {
        this.type =  type;
        this.responseList = responseList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<String> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<String> responseList) {
        this.responseList = responseList;
    }
}
