// Generated code from Butter Knife. Do not modify!
package com.song.petLeague.fragment.innerFragment;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class CommondFragment$$ViewBinder<T extends com.song.petLeague.fragment.innerFragment.CommondFragment> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131558736, "field 'multiplestatusview'");
    target.multiplestatusview = finder.castView(view, 2131558736, "field 'multiplestatusview'");
    view = finder.findRequiredView(source, 2131558735, "field 'ptrLayout'");
    target.ptrLayout = finder.castView(view, 2131558735, "field 'ptrLayout'");
  }

  @Override public void unbind(T target) {
    target.multiplestatusview = null;
    target.ptrLayout = null;
  }
}
