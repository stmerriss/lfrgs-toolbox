package com.liferay.gs.util.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class SelectableNode<T> {

	public SelectableNode(T dto) {
		_children = new ArrayList<>();
		_dto = dto;
		_dtoTransformer = node -> _dto;
	}

	public SelectableNode(
		T dto, Function<SelectableNode<T>, T> dtoTransformer) {

		_children = new ArrayList<>();
		_dto = dto;
		_dtoTransformer = dtoTransformer;
	}

	public void addChild(SelectableNode<T> child) {
		_children.add(child);
	}

	public List<SelectableNode<T>> getChildren() {
		return _children;
	}

	public SelectableNode<T> getParent() {
		return _parent;
	}

	public List<T> getAncestorDTOs() {
		if (_parentDTOs == null) {
			_parentDTOs = new ArrayList<>();

			if (hasParent()) {
				_parentDTOs.addAll(_parent.getAncestorDTOs());

				_parentDTOs.add(_dtoTransformer.apply(_parent));
			}
		}

		return _parentDTOs;
	}


	public T getDTO() {
		return _dtoTransformer.apply(this);
	}

	/**
	 * Absolute primogeniture
	 *
	 * @return
	 */
	public List<T> getChildrenDTOs() {
		if (_childDTOs == null) {
			_childDTOs = new ArrayList<>();

			if (hasChildren()) {
				for (SelectableNode<T> child : _children) {
					_childDTOs.add(_dtoTransformer.apply(child));

					_childDTOs.addAll(child.getChildrenDTOs());
				}
			}
		}

		return _childDTOs;
	}

	public boolean hasChildren() {
		return !_children.isEmpty();
	}

	public boolean hasParent() {
		return _parent != null;
	}

	public boolean isAllChildrenSelected() {
		return _allChildrenSelected;
	}

	public boolean isSelected() {
		return _selected;
	}

	public Integer getNumChildren() {
		if (_numChildren == null) {
			_numChildren = _children.size();
		}

		return _numChildren;
	}

	public void setAllChildrenSelected(boolean allChildrenSelected) {
		_allChildrenSelected = allChildrenSelected;

		_children.forEach(
			child -> {
				child.setAllChildrenSelected(allChildrenSelected);
				child.setSelected(allChildrenSelected);
			});
	}

	public void setChildren(List<SelectableNode<T>> children) {
		if (children != null && !children.isEmpty()){
			_children = children;

			_children.forEach(child -> child.setParent(this));
		}
	}

	public void setSelected(boolean selected) {
		_selected = selected;
	}

	public void setParent(SelectableNode<T> parent) {
		_parent = parent;
	}

	private Function<SelectableNode<T>, T> _dtoTransformer;
	private boolean _allChildrenSelected;
	private List<SelectableNode<T>> _children;
	private List<T> _parentDTOs;
	private T _dto;
	private List<T> _childDTOs;
	private boolean _selected;
	private Integer _numChildren;
	private SelectableNode<T> _parent;

}