package com.example.newspaper;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.newspaper.databinding.FragmentAllNewsBinding;
import com.example.newspaper.model.News;
import com.example.newspaper.model.NewsRepository;
import com.example.newspaper.view.AllNewsAdapter;
import com.example.newspaper.viewmodel.AllNewsViewModel;
import com.example.newspaper.viewmodel.AllNewsViewModelFactory;

import java.util.ArrayList;
import java.util.List;

public class AllNewsFragment extends Fragment {
    private ArrayList<News> news;
    private RecyclerView recyclerView;
    private FragmentAllNewsBinding fragmentAllNewsBinding;
    private AllNewsViewModel allNewsViewModel;
    private SwipeRefreshLayout srl;
    private AllNewsAdapter allNewsAdapter;
    private String currentCategory = "general";

    public AllNewsFragment() {
        // Required empty public constructor
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);    //tell the fragment that you have a menu
//    }


    //creating menu
//    @Override
//    public void onCreateOptionsMenu(Menu menu) {
//        inflater().inflate(R.menu.category_menu, menu);
//        return true;
//    }
    //Slightly different onCreateOptionsMenu for fragments


//    @Override
//    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
//        inflater.inflate(R.menu.category_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        String category = null;
//        int id = item.getItemId();
//        if(id == R.id.business){
//            category = "business";
//        }else if(id == R.id.politics){
//            category = "politics";
//        }else if(id == R.id.technology){
//            category = "technology";
//        }else if(id == R.id.sports){
//            category = "sports";
//        }
//        if(category != null){   //if category is null, chup chap general se assign kr do in AllNewsFragment
//            getNewsByCategory(category);    //now directly call method of this fragment without searching for fragment.
//            return true;
//        }
//        return super.onOptionsItemSelected(item);   //a safety measure. calls AppCompatActivity with menu that has default behaviour defined if nothing of if else matched.
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_all_news, container, false);
        fragmentAllNewsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_news, container, false);
        return fragmentAllNewsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = fragmentAllNewsBinding.recyclerView;
        allNewsAdapter = new AllNewsAdapter(requireContext(), new ArrayList<>());
        recyclerView.setAdapter(allNewsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
//        fragmentAllNewsBinding = DataBindingUtil.setContentView(requireActivity(), R.layout.fragment_all_news); setContentView is for activities. not fragments.
//        allNewsViewModel = new ViewModelProvider(requireActivity()).get(AllNewsViewModel.class); this created a viewModel with default factory which has no Repository parameter which is a necessary parameter in AllNewsViewModel. So use custom factory.
        //initializing newsRepository and ViewModelFactory to create allNewsViewModel passing both as parameters
        NewsRepository newsRepository = new NewsRepository(requireActivity().getApplication());
        AllNewsViewModelFactory factory = new AllNewsViewModelFactory(requireActivity().getApplication(), newsRepository);
        allNewsViewModel = new ViewModelProvider(this, factory).get(AllNewsViewModel.class);
        getNewsByCategory(currentCategory);
        srl = fragmentAllNewsBinding.swipeRefreshLayout;
        srl.setColorSchemeColors(getResources().getColor(R.color.black));
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNewsByCategory(currentCategory);
            }
        });

        //setting up menu click handler
        fragmentAllNewsBinding.menuAnchor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCategoryMenu(view);
            }
        });


        //right swipe functionality:
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getBindingAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    News selectedNews = news.get(position);
                    allNewsViewModel.insertNews(selectedNews);
                    Toast.makeText(requireContext(), "News saved", Toast.LENGTH_SHORT).show();
                    allNewsAdapter.notifyItemChanged(position);
                }
            }
        }).attachToRecyclerView(recyclerView);  //recycler view is initialized before this code.
    }

    public void getNewsByCategory(String category){
        this.currentCategory = category;   //update state of category
//        srl.setRefreshing(true);
        allNewsViewModel.getNewsByCategory(category).observe(getViewLifecycleOwner(), new Observer<List<News>>() {  //use getViewLifecyclerOwner() instead of requireActivity()
            @Override
            public void onChanged(List<News> newsFromLiveData) {
                news = (ArrayList<News>) newsFromLiveData;
//                displayNewsInRecyclerView(); Directly writing logic here
                allNewsAdapter.setNewsArrayList(news);  //just update the adapter when there is a change in news
                srl.setRefreshing(false);
            }
        });
    }

    private void showCategoryMenu(View v){
        PopupMenu popupMenu = new PopupMenu(requireContext(), v);
        popupMenu.getMenuInflater().inflate(R.menu.category_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(item -> {
            String category = null;
            int id = item.getItemId();
            if(id == R.id.business){
                category = "business";
            }else if(id == R.id.politics){
                category = "politics";
            }else if(id == R.id.technology){
                category = "technology";
            }else if(id == R.id.sports){
                category = "sports";
            }
            if(category != null){   //if category is null, chup chap general se assign kr do in AllNewsFragment
                getNewsByCategory(category);    //now directly call method of this fragment without searching for fragment.
                return true;
            }
            return false;
        });
        popupMenu.show();
    }
}