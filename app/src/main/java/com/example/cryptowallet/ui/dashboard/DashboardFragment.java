package com.example.cryptowallet.ui.dashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cryptowallet.databinding.FragmentDashboardBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);



        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        ApiInterface apiInterface= retrofit.create(ApiInterface.class);

        Call<Post> call= apiInterface.getPost();

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Log.i("response_title",response.body().getTitle()+"");
                Log.i("response_userId",response.body().getUserId()+"");
                Log.i("response_id",response.body().getId()+"");
                Log.i("response_body",response.body().getBody()+"");
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
            }

        });













        return root;
    }


    public interface ApiInterface {

        @GET("Posts/1")
        Call<Post> getPost();
    }

    public class Post {

        private int id;
        private int userId;
        private String title;
        private String body;

        public int getId() {
            return id;
        }

        public int getUserId() {
            return userId;
        }

        public String getTitle() {
            return title;
        }

        public String getBody() {
            return body;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}