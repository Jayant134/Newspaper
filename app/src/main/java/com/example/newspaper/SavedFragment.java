package com.example.newspaper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.newspaper.databinding.FragmentSavedBinding;
import com.example.newspaper.model.News;
import com.example.newspaper.model.NewsRepository;
import com.example.newspaper.view.AllNewsAdapter;
import com.example.newspaper.viewmodel.AllNewsViewModel;
import com.example.newspaper.viewmodel.AllNewsViewModelFactory;

import java.util.ArrayList;

public class SavedFragment extends Fragment {
    private ArrayList<News> savedNewsList;
    private FragmentSavedBinding binding;
    private AllNewsViewModel allNewsViewModel;
    private AllNewsAdapter adapter;

    public SavedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_saved, container, false);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_saved, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = binding.savedNewsRecyclerView;
        //initializing same viewModel using same viewModelFactory
        NewsRepository newsRepository = new NewsRepository(requireActivity().getApplication());
        AllNewsViewModelFactory factory = new AllNewsViewModelFactory(requireActivity().getApplication(), newsRepository);
        allNewsViewModel = new ViewModelProvider(this, factory).get(AllNewsViewModel.class);

        setUpRecyclerView();

        allNewsViewModel.getNewsFromRoomDB().observe(getViewLifecycleOwner(), news -> {
            savedNewsList = (ArrayList<News>) news;
            adapter.setNewsArrayList(savedNewsList);    //adapter is also updating in AllNewsFragment
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                News news = savedNewsList.get(viewHolder.getAdapterPosition());
                allNewsViewModel.deleteNews(news);
                Toast.makeText(requireContext(), "News Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
    }

    private void setUpRecyclerView(){
        adapter = new AllNewsAdapter(requireContext(), new ArrayList<>());
        binding.savedNewsRecyclerView.setAdapter(adapter);
        binding.savedNewsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
    }
}