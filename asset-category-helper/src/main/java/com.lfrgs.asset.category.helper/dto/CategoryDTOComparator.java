package com.lfrgs.asset.category.helper.dto;

import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.Objects;

/**
 * @author Shane Merriss
 */
public class CategoryDTOComparator extends OrderByComparator<CategoryDTO> {

	public CategoryDTOComparator() {
		new CategoryDTOComparator("asc");
	}

	public CategoryDTOComparator(String orderByType) {
		_asc = orderByType.equals("asc");
	}

	@Override
	public int compare(CategoryDTO dto1, CategoryDTO dto2) {

		int compare = compareCategoryDTO(dto1, dto2);

		if (_asc) {
			return -compare;
		}

		return compare;
 	}

 	private int compareCategoryDTO(CategoryDTO dto1, CategoryDTO dto2) {
	    if (Objects.isNull(dto1)) {
		    return -1;
	    }

	    if (Objects.isNull(dto2)) {
		    return 1;
	    }

	    if (Objects.deepEquals(dto1, dto2)) {
		    return 0;
	    }

	    boolean dtoHasChildren1 = dto1.hasChildren();
	    boolean dtoHasChildren2 = dto2.hasChildren();

	    if ((dtoHasChildren1 && dtoHasChildren2) || (!dtoHasChildren1 && !dtoHasChildren2)) {
		    return dto1.getName().compareToIgnoreCase(dto2.getName());
	    }

	    if (dto1.hasChildren() && !dto2.hasChildren()) {
		    return 1;
	    }

	    if (dto2.hasChildren() && !dto1.hasChildren()) {
		    return -1;
	    }

	    return 0;
    }

	private boolean _asc;
}
