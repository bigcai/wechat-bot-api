package org.liangdu.rest;

import io.github.biezhi.wechat.handle.CollectMyGroupToBuffMessageHandler;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/group")
public class MyGroupMessageCtrl {
    @GET
    @Produces(MediaType.TEXT_HTML)
    public String getMessage() {
        String result = "";
        for (int i = CollectMyGroupToBuffMessageHandler.messageBufferInPointor; i >= 0 ; i--) {
            String tmp = CollectMyGroupToBuffMessageHandler.messageBuffer[i];
            if( tmp != null && !tmp.equals("") ) {
                result += tmp + "<br/>";
            }
        }
        return result;
    }
}