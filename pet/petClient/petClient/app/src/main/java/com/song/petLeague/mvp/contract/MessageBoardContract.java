package com.song.petLeague.mvp.contract;

import com.song.petLeague.bean.MBCommentItem;
import com.song.petLeague.bean.MessageBoardItem;

import java.util.List;

/**
 * Created by song on 2017/4/10.
 */

public interface MessageBoardContract {

    interface View {
        void updata2LoadData(final int refreshType, List<MessageBoardItem> boardItemList);
        void updata2DeleteMessageBoard(final int boadPosition);
        void updata2showEditTextBodyVisiable(int visiavle, String uId, String msId, int boardPosition);
        void updata2AddBoardComment(int boardPosition, MBCommentItem mbCommentItem);
        void updata2AddMessageBoard();
    }

    interface Presener {
        void loadData(final int refreshType, final int currentPage, final String uId, final String mId);
        void deleteMessageBoard(final int boardPosition, final String uId);
        void addBoardComment(final String content, final String uId, final String msId, final int boardPosition);
        void addMessageBoard(final String ubId, final String umId, final String mContent, final String boardType);

    }

}
