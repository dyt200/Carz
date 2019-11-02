package com.example.carz.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.carz.Entities.User;
import com.example.carz.repositories.UserRepository;

import java.util.List;

public class UserListViewModel extends AndroidViewModel {
    private UserRepository repository;

    private Context applicationContext;

    // MediatorLiveData can observe other LiveData objects and react on their emissions.
    private final MediatorLiveData<List<User>> observableUsers;

    public UserListViewModel(@NonNull Application application, UserRepository clientRepository) {
        super(application);

        repository = clientRepository;

        applicationContext = application.getApplicationContext();

        observableUsers = new MediatorLiveData<>();
        // set by default null, until we get data from the database.
        observableUsers.setValue(null);

        LiveData<List<User>> clients = repository.getAllUsers(applicationContext);

        // observe the changes of the entities from the database and forward them
        observableUsers.addSource(clients, observableUsers::setValue);
    }

    /**
     * A creator is used to inject the account id into the ViewModel
     */
    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application application;

        private final UserRepository userRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            userRepository = UserRepository.getInstance();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new UserListViewModel(application, userRepository);
        }
    }

    /**
     * Expose the LiveData ClientEntities query so the UI can observe it.
     */
    public LiveData<List<User>> getUsers() {
        return observableUsers;
    }
}
