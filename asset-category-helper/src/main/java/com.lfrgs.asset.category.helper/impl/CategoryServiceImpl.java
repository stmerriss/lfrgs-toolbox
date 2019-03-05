package com.lfrgs.asset.category.helper.impl;

import com.lfrgs.search.CategoryService;
import com.lfrgs.search.dto.CategoryDTO;
import com.lfrgs.search.dto.CategoryDTOComparator;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Shane Merriss
 */
@Component(
	immediate = true,
	service = CategoryService.class
)
public class CategoryServiceImpl implements CategoryService {

	@Override
	public List<CategoryDTO> getCategoriesTree(long categoryId) {
		List<AssetCategory> children = _assetCategoryLocalService.getChildCategories(categoryId);

		return buildCategoryDTOList(children);
	}

	@Override
	public List<CategoryDTO> getVocabulariesTree(long groupId, String vocabularyName) {

		AssetVocabulary vocabulary = null;

		try {
			vocabulary = _assetVocabularyLocalService.getGroupVocabulary(groupId, vocabularyName);
		}
		catch (PortalException pe) {
			_log.error("Unable to get AssetVocabulary ['" + vocabularyName + "']", pe);
			return null;
		}

		List<AssetCategory> children =
			_assetCategoryLocalService.getVocabularyRootCategories(
				vocabulary.getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);


		return buildCategoryDTOList(children);
	}

	private List<CategoryDTO> buildCategoryDTOList(List<AssetCategory> children) {
		List<CategoryDTO> categoryDTOList = new ArrayList<>();

		if (children.isEmpty()) {
			return categoryDTOList;
		}

		for (AssetCategory child : children) {
			CategoryDTO categoryDTO = new CategoryDTO(child.getCategoryId(), child.getName());

			categoryDTO.setChildren(getCategoriesTree(child.getCategoryId()));

			categoryDTOList.add(categoryDTO);
		}

		Comparator<CategoryDTO> categoryDTOComparator = new CategoryDTOComparator();

		categoryDTOList.sort(categoryDTOComparator);

		return categoryDTOList;
	}

	private Log _log = LogFactoryUtil.getLog(CategoryServiceImpl.class);

	@Reference
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Reference
	private AssetVocabularyLocalService _assetVocabularyLocalService;
}
