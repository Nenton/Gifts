package com.nenton.photon.ui.screens.edit_photocard;

import android.os.Bundle;

import com.nenton.photon.R;
import com.nenton.photon.data.network.req.PhotocardReq;
import com.nenton.photon.data.storage.dto.FiltersDto;
import com.nenton.photon.data.storage.dto.UserInfoDto;
import com.nenton.photon.data.storage.realm.PhotocardRealm;
import com.nenton.photon.data.storage.realm.UserRealm;
import com.nenton.photon.di.DaggerService;
import com.nenton.photon.di.sqopes.DaggerScope;
import com.nenton.photon.flow.AbstractScreen;
import com.nenton.photon.flow.Screen;
import com.nenton.photon.mvp.model.MainModel;
import com.nenton.photon.mvp.presenters.AbstractPresenter;
import com.nenton.photon.mvp.presenters.RootPresenter;
import com.nenton.photon.ui.activities.RootActivity;
import com.nenton.photon.ui.screens.account.AccountScreen;
import com.nenton.photon.ui.screens.add_photocard.AddPhotocardSelectTagsAdapter;
import com.nenton.photon.ui.screens.add_photocard.AddPhotocardSuggestionTagsAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import dagger.Provides;
import flow.Flow;
import mortar.MortarScope;

/**
 * Created by serge on 07.07.2017.
 */
@Screen(R.layout.screen_edit_photocard)
public class EditPhotocardScreen extends AbstractScreen<RootActivity.RootComponent> {

    private final PhotocardRealm mPhotocardRealm;

    public EditPhotocardScreen(PhotocardRealm photocardRealm) {
        mPhotocardRealm = photocardRealm;
    }

    @Override
    public Object createScreenComponent(RootActivity.RootComponent parentComponent) {
        return DaggerEditPhotocardScreen_Component.builder()
                .rootComponent(parentComponent)
                .module(new Module())
                .build();
    }

    @dagger.Module
    public class Module {
        @Provides
        @DaggerScope(EditPhotocardScreen.class)
        MainModel providePhotoModel() {
            return new MainModel();
        }

        @Provides
        @DaggerScope(EditPhotocardScreen.class)
        EditPhotocardPresenter provideAccountPresenter() {
            return new EditPhotocardPresenter();
        }

        @Provides
        @DaggerScope(EditPhotocardScreen.class)
        EditPhotocardSelectTagsAdapter provideEditPhotocardSelectTagsAdapter() {
            return new EditPhotocardSelectTagsAdapter();
        }
    }

    @dagger.Component(dependencies = RootActivity.RootComponent.class, modules = Module.class)
    @DaggerScope(EditPhotocardScreen.class)
    public interface Component {
        void inject(EditPhotocardPresenter presenter);

        void inject(EditPhotocardView view);

        void inject(EditPhotocardSelectAlbumAdapter adapter);

        void inject(EditPhotocardSuggestionTagsAdapter adapter);

        Picasso getPicasso();

        RootPresenter getRootPresenter();
    }

    public class EditPhotocardPresenter extends AbstractPresenter<EditPhotocardView, MainModel> {

        @Override
        protected void initActionBar() {
            mRootPresenter.newActionBarBuilder()
                    .setBackArrow(true)
                    .setTitle("Редактирование фотокарточки")
                    .build();
        }

        @Override
        protected void initMenuPopup() {
            mRootPresenter.newMenuPopupBuilder().build();
        }

        @Override
        protected void initDagger(MortarScope scope) {
            ((Component) scope.getService(DaggerService.SERVICE_NAME)).inject(this);
        }

        @Override
        protected void onLoad(Bundle savedInstanceState) {
            super.onLoad(savedInstanceState);
            getView().initView();
            getView().showView(mPhotocardRealm);
            initPropertyView();
            initAdapterSuggestionTags();
        }

        public void clickOnSuggestTag(String albumRealm) {
            getView().addTag(albumRealm);
        }

        public void clickAlbum(int positionOnSelectItem) {
            getView().checkedCurrentAlbum(positionOnSelectItem);
        }

        private void initPropertyView() {
            UserInfoDto userInfo = mModel.getUserInfo();
            mCompSubs.add(mModel.getUser(userInfo.getId()).subscribe(new ViewSubscriber<UserRealm>() {
                @Override
                public void onNext(UserRealm userRealm) {
                    getView().initAlbums(userRealm);
                }
            }));
        }

        private void initAdapterSuggestionTags() {
            mCompSubs.add(mModel.getPhotocardTagsObs().subscribe(new ViewSubscriber<String>() {
                @Override
                public void onNext(String s) {
                    getView().getTagsSuggestionAdapter().addTag(s);
                }
            }));
        }

        void savePhotocard() {
            FiltersDto filters = getView().getFilters();
            String namePhotocard = getView().getNamePhotocard();
            List<String> tags = getView().getTags();
            String idAlbum = getView().getIdAlbum();
            if (filters == null) {
                getRootView().showMessage("Не все фильтры выбраны");
                return;
            }
            if (namePhotocard == null) {
                getRootView().showMessage("Не выбрано имя фотокарточки");
                return;
            }
            if (tags == null) {
                getRootView().showMessage("Не выбрано ни одного тэга");
                return;
            }
            if (idAlbum == null) {
                getRootView().showMessage("Не выбран альбом");
                return;
            }

            PhotocardReq photocardReq = new PhotocardReq(namePhotocard, mPhotocardRealm.getPhoto(), idAlbum, tags, filters);

            mModel.editPhotocards(mPhotocardRealm.getId(), idAlbum, photocardReq, () -> {
                ((RootActivity) getRootView()).runOnUiThread(() -> Flow.get(getView().getContext()).set(new AccountScreen()));
            });
        }

        public void clickOnCancel() {
            ((RootActivity) getRootView()).onBackPressed();
        }
    }
}
