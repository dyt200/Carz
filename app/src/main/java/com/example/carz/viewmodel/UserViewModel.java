package com.example.carz.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.BaseApp;
import com.example.carz.Entities.User;
import com.example.carz.repositories.UserRepository;
import com.example.carz.util.OnAsyncEventListener;

import org.jetbrains.annotations.NotNull;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<User> observableUser;

    private UserViewModel(
            @NonNull Application application,
            final String email,
            final String pass,
            UserRepository userRepository
    ) {
        super(application);

        this.application = application;
        repository = userRepository;
        observableUser = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableUser.setValue(null);
        LiveData<User> user = repository.validateLogin(email, pass, application);

        // observe the changes of the client entity from the database and forward them
        observableUser.addSource(user, observableUser::setValue);
    }

    private UserViewModel(
            int userId,
            @NonNull Application application,
            UserRepository userRepository
    ) {
        super(application);

        this.application = application;
        repository = userRepository;
        observableUser = new MediatorLiveData<>();

        // set by default null, until we get data from the database.
        observableUser.setValue(null);
        LiveData<User> user = repository.getUserById(userId, application);

        // observe the changes of the client entity from the database and forward them
        observableUser.addSource(user, observableUser::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final String email;
        private final String pass;

        private final UserRepository repository;

        public Factory(@NonNull Application application, String email, String pass){
            this.application = application;
            this.email = email;
            this.pass = pass;
            repository = UserRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, email, pass, repository);
        }
    }

    public static class UserFromIdFactory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final int userId;

        private final UserRepository repository;

        public UserFromIdFactory(@NonNull Application application, int userId){
            this.application = application;
            this.userId = userId;
            repository = UserRepository.getInstance();
        }

        @NotNull
        @Override
        public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(userId, application, repository);
        }
    }

    /**
     * Expose the LiveData User query so the UI can observe it.
     */
    public LiveData<User> getUser() {
        return observableUser;
    }

    public void createUser(User user, OnAsyncEventListener callback) {
        repository.insert(user, callback, application);
    }

    public void updateUser(User user, OnAsyncEventListener callback) {
        repository.update(user, callback, application);
    }

    public void deleteUser(User user, OnAsyncEventListener callback) {
        repository.delete(user, callback, application);

    }
}
