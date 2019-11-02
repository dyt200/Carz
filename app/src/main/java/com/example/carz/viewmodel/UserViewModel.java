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

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;

    private Application application;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<User> observableUser;

    public UserViewModel(
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

        LiveData<User> client = repository.validateLogin(email, pass, application);

        // observe the changes of the client entity from the database and forward them
        observableUser.addSource(client, observableUser::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
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

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserViewModel(application, email, pass, repository);
        }
    }

    /**
     * Expose the LiveData ClientEntity query so the UI can observe it.
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
