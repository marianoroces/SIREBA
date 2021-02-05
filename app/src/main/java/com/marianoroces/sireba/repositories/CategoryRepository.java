package com.marianoroces.sireba.repositories;

import android.util.Log;

import com.marianoroces.sireba.model.Category;
import com.marianoroces.sireba.services.CategoryService;
import com.marianoroces.sireba.services.MyRetrofit;
import com.marianoroces.sireba.utils.OnCategoryResultCallback;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository implements OnCategoryResultCallback {

    private CategoryService categoryService;
    private List<Category> categoriesList = new ArrayList<>();
    private OnCategoryResultCallback categoryCallback;

    public CategoryRepository(OnCategoryResultCallback context){
        categoryCallback = context;
        categoryService = MyRetrofit.getInstance().createCategoryService();
    }

    public void getAll(){
        Call<List<Category>> callCategories = categoryService.getAllCategories();

        callCategories.enqueue(
                new Callback<List<Category>>() {
                    @Override
                    public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                        if(response.code() == 200){
                            Log.d("DEBUG", "Buscar todas las categorias exitoso");
                            categoriesList.clear();
                            categoriesList.addAll(response.body());
                            categoryCallback.onCategoryListResult(categoriesList);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Category>> call, Throwable t) {
                        Log.d("DEBUG", "Fallo buscar categorias");
                        Log.d("DEBUG", t.getMessage());
                    }
                }
        );
    }

    @Override
    public void onCategoryListResult(List<Category> categoriesList) {
        categoryCallback.onCategoryListResult(categoriesList);
    }

    @Override
    public void onCategoryListResult(Category category) {
        categoryCallback.onCategoryListResult(category);
    }
}
