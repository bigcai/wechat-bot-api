package io.github.biezhi.wechat.handle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.github.biezhi.wechat.Utils;
import io.github.biezhi.wechat.model.Environment;
import io.github.biezhi.wechat.model.GroupMessage;
import io.github.biezhi.wechat.model.UserMessage;

/**
 * 一个默认的消息处理实现
 *
 * @author biezhi
 * 17/06/2017
 */
public class CollectMyGroupToBuffMessageHandler implements MessageHandle {

    // 配置文件环境参数
    protected Environment environment ;

    public static int MAX_BUFFER_SIZE = 30000;
    public String[] messageBuffer = new String[MAX_BUFFER_SIZE];
    private static int messageBufferInPointor = 0;

    private String myGroupId = null;
    private String myGroupName = null;

    public CollectMyGroupToBuffMessageHandler(Environment environment){
        this.environment = environment;
        myGroupName = environment.get("my_group_name");
    }
    /**
     * 保存微信消息
     *
     * @param msg
     */
    @Override
    public void wxSync(JsonObject msg) {
    }

    @Override
    public void userMessage(UserMessage userMessage) {

    }

    @Override
    public void groupMessage(GroupMessage groupMessage) {

        System.out.println(groupMessage);
        String text = groupMessage.getText();

        if (Utils.isNotBlank(text)) {
            // 防止群消息中途改名字
            if( myGroupId == null && groupMessage.getGroup_name().equals( this.myGroupName )) {
                myGroupId = groupMessage.getGroupId();
            }

            if( groupMessage.getGroupId().equals( this.myGroupId )) {
                if( !myGroupName.equals( groupMessage.getGroup_name() ) ) {
                    myGroupName = groupMessage.getGroup_name();
                    environment.set( "my_group_name", groupMessage.getGroup_name() );
                }

                System.out.println( "yes" + groupMessage.getGroup_name() );
                // 将制定的群消息推入循环队列
                if( messageBufferInPointor >= 0 && messageBufferInPointor < MAX_BUFFER_SIZE) {
                    messageBuffer[  messageBufferInPointor  ] = text;
                    messageBufferInPointor ++ ;
                }
                // 如果指针位移等于循环队列的长度就置 0
                if( messageBufferInPointor >= MAX_BUFFER_SIZE) {
                    messageBufferInPointor = 0;
                }
            }

        }

    }

    @Override
    public void groupMemberChange(String groupId, JsonArray memberList) {

    }

    @Override
    public void groupListChange(String groupId, JsonArray memberList) {

    }

}
