package com.marianoroces.sireba.utils;

import com.marianoroces.sireba.model.Category;

import java.util.List;

public interface OnCategoryResultCallback {
    void onCategoryListResult(List<Category> categoriesList);
    void onCategoryListResult(Category category);
}
