package com.song.petLeague.mvp.contract;

import com.song.petLeague.bean.User;

import java.util.List;

/**
 * Created by song on 2017/4/4.
 */

public interface PersonalAttentionContract {

        interface View {
                void updata2LoadData(int type, final List<User> users);
                void updata2LoadFensData(int type, final List<User> users);
        }

        interface Presenter {

                void loadData(final int type, final int current,final String uId);
                void loadFensData(final int type, final int currentPage, String uId);
        }

}
