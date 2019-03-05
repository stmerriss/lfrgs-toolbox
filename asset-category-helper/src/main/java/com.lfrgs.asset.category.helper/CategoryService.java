package com.lfrgs.asset.category.helper;

import com.lfrgs.asset.category.helper.dto.CategoryDTO;

import java.util.List;

/**
 * @author Shane Merriss
 */
public interface CategoryService {

	public List<CategoryDTO> getCategoriesTree(long categoryId);

	public List<CategoryDTO> getVocabulariesTree(long groupId, String vocabularyName);
}
