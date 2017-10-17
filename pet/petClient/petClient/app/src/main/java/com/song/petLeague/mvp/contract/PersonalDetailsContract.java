package com.song.petLeague.mvp.contract;

import com.song.petLeague.bean.PhotoInfo;

import java.util.List;

/**
 * Created by song on 2017/4/2.
 */

public interface PersonalDetailsContract {

        interface View {
            void update2JudgeAttention(final String result);
            void updata2CancleAttention(final String result);
            void updata2attentionFrient(String result);
            void updata2AttentionCounts(String result);
            void updata2fensCounts(String result);
            void updata2circleCounts(String result);
            void updata2AllPicture(List<PhotoInfo> pictureList);
        }

        interface Presenter {
            void changeHeadBgUrl();
            void changeHeadUrl(final String cutPath, final String uIc);
            void attentionFrient(final String uId, final String frientId);
            void judgeAttention(final String UId, final String frinetId);
            void cancelAttention(final String UId, final String frinetId);
            void attentionCounts(final String uId);
            void fensCounts(final String uId);
            void circleCounts(final String uId);
            void allPictures(final String uId);

        }

}
