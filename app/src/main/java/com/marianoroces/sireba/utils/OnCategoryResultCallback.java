package com.marianoroces.sireba.utils;

import com.marianoroces.sireba.model.Category;

import java.util.List;

public interface OnCategoryResultCallback {
    void onResult(List<Category> categoriesList);
    void onResult(Category category);
}
