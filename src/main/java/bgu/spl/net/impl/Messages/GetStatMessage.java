package bgu.spl.net.impl.Messages;

import bgu.spl.net.Database;
import bgu.spl.net.api.Message;

public class GetStatMessage implements Message {
    /*---------------------------------fields---------------------------------*/
    private int opt;
    private String username;
    private int courseNum;
    private Database database;
    /*-------------------------------constructors------------------------------*/

    public GetStatMessage(int opt, String username, int courseNum) {
        this.opt = opt;
        this.username = username;
        this.courseNum = courseNum;
        database = Database.getInstance();
    }
    /*---------------------------------getters---------------------------------*/
    /*---------------------------------setters---------------------------------*/
    /*---------------------------------methods---------------------------------*/
    @Override
    public Message process() {
        if (courseNum < 0)
            System.out.print(database.ComposeStudentStat(username));
        else
            System.out.print(database.ComposeCourseStat(courseNum));
        return new AckMessage(opt, null); //always ackMessage? sometime error? need to check if student logged in? if course is registered? fuck them
    }


}
