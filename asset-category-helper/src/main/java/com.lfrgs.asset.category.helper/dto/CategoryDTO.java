package com.lfrgs.asset.category.helper.dto;

import com.liferay.portal.kernel.util.KeyValuePair;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CategoryDTO {

	public CategoryDTO(long categoryId, String name) {
		_categoryId = categoryId;
		_name = name;

		_children = new ArrayList<>();
	}

	public void addChild(CategoryDTO child) {
		_children.add(child);
	}

	public long getCategoryId() {
		return _categoryId;
	}

	public List<CategoryDTO> getChildren() {
		return _children;
	}

	public String getName() {
		return _name;
	}

	public CategoryDTO getParent() {
		return _parent;
	}

	public String getParentName() {
		if (Objects.isNull(_parent)) {
			return "";
		}

		return _parent.getName();
	}

	public List<KeyValuePair> getParentPairs() {
		if (Objects.isNull(_parentPairs)) {
			_parentPairs = new ArrayList<>();

			if (hasParent()) {
				_parentPairs.addAll(_parent.getParentPairs());

				KeyValuePair parentPair = new KeyValuePair(String.valueOf(_parent.getCategoryId()), _parent.getName());

				_parentPairs.add(parentPair);
			}
		}

		return _parentPairs;
	}

	public boolean hasChildren() {
		return !_children.isEmpty();
	}

	public boolean hasParent() {
		return !Objects.isNull(_parent);
	}

	public boolean isAllChildrenSelected() {
		return _allChildrenSelected;
	}

	public boolean isSelected() {
		return _selected;
	}

	public Integer getNumChildren() {
		if (Objects.isNull(_numChildren)) {
			_numChildren = _children.size();
		}

		return _numChildren;
	}

	public void setAllChildrenSelected(boolean allChildrenSelected) {
		_allChildrenSelected = allChildrenSelected;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;
	}

	public void setChildren(List<CategoryDTO> children) {
		if (!Objects.isNull(children) && !children.isEmpty()){
			_children = children;

			_children.forEach(child -> child.setParent(this));
		}
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setParent(CategoryDTO parent) {
		_parent = parent;
	}

	private boolean _allChildrenSelected;
	private List<CategoryDTO> _children;
	private long _categoryId;
	private String _name;
	private List<KeyValuePair> _parentPairs;
	private boolean _selected;
	private Integer _numChildren;
	private CategoryDTO _parent;
}